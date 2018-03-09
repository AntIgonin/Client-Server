package com.jdbc;


import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.Arrays;

public class MySql {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/";


    public void start() throws SQLException {

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            System.out.println("МУСКЛ ПОДКЛЮЧЕН!!!!");

        } catch (SQLException ex) {

            System.out.println("Ошибка с драйвером!");
            return;
        }
    }

    public void create(String name) {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE DATABASE " + name);


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(" Мы  не законектились с базой данных!!");
            ;
        }

    }

    public void createTable(String nameTable, String DB) {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("USE " + DB);
            statement.execute("CREATE TABLE " + nameTable + " (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY ( id ))");


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Не получилось создать таблицу!!");
            ;
        }

    }

    public void letsFullTable(String DB, String table, String column, String type) {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("USE " + DB);
            statement.execute("ALTER TABLE " + table + " ADD COLUMN " + column + " " + type);


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Не получилось добавить запись");
            ;
        }

    }

    public void insert(String DB, String tables, String colums, String columsV) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("insert into " + DB + "." + tables + "(" + colums + ") values (" + columsV + ")");


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(" Мы  не законектились с базой данных!!");
            ;
        }

    }

    public String show(String DB, String tables) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("USE " + DB);
            ResultSet resultSet = statement.executeQuery("select * from " + tables);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            String res = "";
            String res2 = "";
            int count = resultSetMetaData.getColumnCount();
            int str = 0;

            while (resultSet.next()) {

                str++;

            }

            String[][] arr = new String[str+1][count];

            for (int y = 0;y < count;y++){

                arr[0][y] = resultSetMetaData.getColumnName(y+1);

            }

            int x = 0;

            while (x < count) {

                str = 1;
                resultSet.beforeFirst();

                while (resultSet.next()) {

                    arr[str][x] = resultSet.getString(x + 1);
                    str++;

                }

                x++;
            }
            String lineSeparator = System.lineSeparator();
            StringBuilder sb = new StringBuilder();

            for (String[] row : arr) {
                sb.append(Arrays.toString(row))
                        .append(lineSeparator);
            }

            String result = sb.toString();

            return result;

        }


    }

    public String select(String DB, String tables, String colums, String term) {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("USE " + DB);
            ResultSet resultSet = statement.executeQuery("SELECT * from " + tables + " WHERE " + colums + " " + term);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            String res = "";
            String res2 = "";
            int count = resultSetMetaData.getColumnCount();
            int str = 0;

            while (resultSet.next()) {
                str++;
            }

            String[][] arr = new String[str][count];

            for (int y = 0;y < count;y++){

                arr[0][y] = resultSetMetaData.getColumnName(y+1);

            }

            int x = 0;

            while (x < count) {

                str = 1;
                resultSet.beforeFirst();

                while (resultSet.next()) {

                    arr[str][x] = resultSet.getString(x + 1);
                    str++;

                }

                x++;
            }

            String lineSeparator = System.lineSeparator();
            StringBuilder sb = new StringBuilder();

            for (String[] row : arr) {
                sb.append(Arrays.toString(row))
                        .append(lineSeparator);
            }

            String result = sb.toString();

            return result;


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(" Мы  не законектились с базой данных!!");
            ;
        }

        return DB;
    }

    public void delete(String DB, String tables, String colums, String term) {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("USE " + DB);
            statement.executeUpdate("delete from " + tables + " WHERE " + colums + " " + term);


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(" Мы  не законектились с базой данных!!");
            ;
        }

    }

    public void update(String DB,String tables,String colums,String znach,String colums2,String term) {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("USE " + DB);

            statement.executeUpdate("UPDATE "+ tables +" SET " + colums + " = " + znach +" WHERE "+colums2+" "+term);


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(" Мы  не законектились с базой данных!!");
            ;
        }

    }
    public void updateAll(String DB,String tables,String colums,String znach) {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("USE " + DB);
            statement.executeUpdate("UPDATE "+ tables +" SET " + colums + " = " + znach);


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(" Мы  не законектились с базой данных!!");
            ;
        }

    }
}

