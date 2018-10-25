package ru.wirbel.lab02.myapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Driver;

public class Application {

    private static final String DEFAULT_CONSOLE_ENCODING = "UTF-8";
    private static final String CONSOLE_ENCODING_PROPERTY = "consoleEncoding";
    private static final String PROPERTIES_FILENAME = "application.properties";
   
    private Scanner scanner = new Scanner(System.in);
    private Connection connection;
       
    public static void main(String[] args) {
      Application app = new Application();

        app.run();
    }

private void run() {
        // установить кодировку консоли
        setConsoleEncoding();
        // установить соединение
        establishConnection();
        // код приложения
        showMainMenu();
        // закрыть соединение
        closeConnection();
        this.scanner.close();
    }

    private void showMainMenu() {
        
        System.out.println("Menu:");
        System.out.println("\t1) Sputnics");
        System.out.println("\t0) Exit)");

        loop:
        while (true) {
            System.out.print("Choose [0-3]: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    showSputnikMenu();
                    break;
                
                case "0":
                    break;
                default:
                    continue loop;
            }
            break;
        }
    }

    private void showSputnikMenu() {
        clearConsole();

        System.out.println("Sputnics:");
        System.out.println("\t1) All");
        System.out.println("\t3) Add");
        System.out.println("\t0) Menu");

        loop:
        while (true) {
            System.out.print("Choose [0]: ");
            switch (scanner.nextLine()) {
                case "0":
                    showMainMenu();
                    break;
                case "1":
                    listAllSputnik();
                    break;
                case "2":
                    addSputnik();
                    break;
                default:
                    continue loop;
            }
            break;
        }
    }

    


    /**
     * insert into Person
     */
    private void addSputnik() {
        clearConsole();

        String query =
                "insert into Sputnik (ID_Sputnik, ID_Scientist , ID_Planet , Name , Diametr , Period , Data )" ;
        
       
            PreparedStatement stmt = null;
        ResultSet rs = null;

        
        System.out.print("\tID Sputnik: ");
        String ID_Sputnik = scanner.nextLine();
        System.out.print("\tID_Scientist: ");
        String ID_Scientist = scanner.nextLine();
        System.out.print("\tID_Planet: ");
        String ID_Planet = scanner.nextLine();
    System.out.print("\tName: ");
        String Name = scanner.nextLine();
        System.out.print("\tD: ");
        String Diametr = scanner.nextLine();
      System.out.print("\tTime: ");
        String Period = scanner.nextLine();
        System.out.print("\tDate: ");
        String Data = scanner.nextLine();
        

        try {
            stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, ID_Sputnik);
            stmt.setString(2, ID_Scientist);
            stmt.setString(3, ID_Planet);
            stmt.setString(4, Name);
             stmt.setString(5, Diametr);
              stmt.setString(6, Period);
               stmt.setString(7,Data );
            int rows = stmt.executeUpdate();

              if (rows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("6666Пользователь успешно добавлен с ID = " + rs.getInt(1));
                }
            } else {
                System.out.println("777Не удалось добавить пользователя.");
            }
        } catch (SQLException sqle) {
            System.out.println("999Произошла ошибка при выполнении SQL запроса:");
            System.out.println(sqle.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        // Вернуться в меню
        System.out.print("\nНажмите Enter...");
        scanner.nextLine();
        showSputnikMenu();
    }

    /**
     * select <brief> from Person
     */
    private void listAllSputnik() {
        clearConsole();

        String lineFormat = "%s. %s %s (%s)";
        String query = "select `ID_Sputnik`, `ID_Planet`, `Name` from Sputnik order by `ID_Planet`, `Name`";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.connection.prepareStatement(query);
            rs = stmt.executeQuery();

            int rowNum = 0;
            while(rs.next()) {
                System.out.println(String.format(lineFormat,
                        ++rowNum,
                        rs.getString("Name"),
                        rs.getString("ID_Planet"),
                        rs.getString("ID_Sputnik")
                        ));
            }
        } catch (SQLException sqle) {
            System.out.println("Произошла ошибка при выполнении SQL запроса:");
            System.out.println(sqle.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }

        // Вернуться в меню
        System.out.print("\nНажмите Enter...");
        scanner.nextLine();
        showSputnikMenu();
    }

    
    

   private static final String URLB = "jdbc:mysql://localhost:3306/db_Sputniks?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "131828man1318";
    /**
     * Установить соединение с БД
     */
    private void establishConnection() {
        Connection connection; 
       
        
       try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.out.println(" MySQL Faild.");
            System.exit(1);
        }
        
         try {
    

            connection = DriverManager.getConnection( URLB, USERNAME, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connection");
            }
        }catch (SQLException e) {
            System.out.println("7777Не удалось загрузить класс драйвера");
        }
}
    

    /**
     * Закрытие соединения с БД
     */
    private void closeConnection() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException sqle) {
            System.out.println("Произошла ошибка при закрытии соединения:");
            System.out.println(sqle.getMessage());
        }
    }


    /**
     * Очистка консоли
     */
    private static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Установка кодировки консоли
     */
    private static void setConsoleEncoding() {
        // чтение системной переменной
        String consoleEncoding = System.getProperty(CONSOLE_ENCODING_PROPERTY, DEFAULT_CONSOLE_ENCODING);

        try {
            // установить кодировку стандартной консоли вывода
            System.setOut(new PrintStream(System.out, true, consoleEncoding));
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported encoding set for console: " + consoleEncoding);
        }
    }
}