package com.jdbc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class Server {
    public static void main(String[] ar) throws SQLException {

        MySql mySql = new MySql();
        mySql.start();

        int port = 6666;
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("-------------");
            System.out.println("Теперь мы ждем нашего клиента! ");
            System.out.println("-------------");

            Socket socket = ss.accept();
            System.out.println("Вот и он! ");
            System.out.println("-------------");



            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();


            DataInputStream  in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            boolean skip = false;

            String line = null;
            String DB = null;
            String tables = null;
            String choise = null;
            boolean exitAdd = false;

            String columnName;
            String typeName;
            String column = null;
            String columnValue;
            String term1;



            while (true) {

                if (skip == false) {
                    out.writeUTF("Создадим базу? (Y / N)");
                    out.flush();
                    line = in.readUTF();
                    if (line.equals("N") || line.equals("No")) {
                        out.writeUTF("Тогда введи имя своей базы данных");
                        out.flush();
                        line = in.readUTF();
                        DB = line;
                        skip = true;
                    }
                }
                if (skip == false){
                out.writeUTF("Хорошо. Введи ее имя");
                out.flush();

                line = in.readUTF();
                DB = line;
                mySql.create(DB);


                out.writeUTF("Теперь надо создать хотя бы одну таблицу. Введи ее имя");
                out.flush();

                line = in.readUTF();
                tables = line;
                mySql.createTable(tables,DB);

                skip = true;
                }

                out.writeUTF("Отлично, пора выбрать действие с таблицами! \n" +
                                  "1 - Создание новой таблицы \n"+
                                  "2 - Добавление столбцов в таблицу \n" +
                                  "3 - Заполнение таблицы данными \n" +
                                  "4 - Показать содержимое таблицы \n" +
                                  "5 - Работа с оператором Select\n"+
                                  "6 - Работа с оператором Update \n"+
                                  "7 - Работа с оператором Delete\n");
                out.flush();
                line = in.readUTF();



                switch (line){

                    case "1":

                        out.writeUTF("Введи имя создаваемой таблицы");
                        out.flush();

                        line = in.readUTF();
                        tables = line;
                        mySql.createTable(tables,DB);

                        break;


                    case "2":
                        exitAdd = false;

                        while(exitAdd == false){

                            out.writeUTF("В какую таблицу добавим столбцы?");
                            out.flush();
                            line = in.readUTF();
                            tables = line;


                            out.writeUTF("Введи имя столбца.");
                            out.flush();
                            line = in.readUTF();
                            columnName = line;

                            out.writeUTF("Его тип данных");
                            out.flush();
                            line = in.readUTF();
                            typeName = line;

                            mySql.letsFullTable(DB,tables,columnName,typeName);

                            out.writeUTF("Продолжить ввод столбцов? Y / N");
                            out.flush();
                            line = in.readUTF();
                            if (line.equals("N") || line.equals("No")){
                                exitAdd = true;
                            }

                        }
                        break;

                    case "3":

                        out.writeUTF("Какую таблицу будем заполнять?");
                        out.flush();
                        line = in.readUTF();
                        tables = line;

                        out.writeUTF("Теперь введи в какие столбцы будешь добавлять информацию. Пиши через запятую");
                        out.flush();
                        line = in.readUTF();
                        column = line;

                        out.writeUTF("Теперь сами значения через запятую.");
                        out.flush();
                        line = in.readUTF();
                        columnValue = line;

                        mySql.insert(DB,tables,column,columnValue);

                        break;
                        
                    case "4":

                        out.writeUTF("Какую таблицу показать??");
                        out.flush();
                        line = in.readUTF();
                        tables = line;

                        String show;
                        show = mySql.show(DB,tables);

                        out.writeUTF(show);
                        out.flush();
                        break;

                    case "5":

                        String term,result;

                        out.writeUTF("Какую таблицу использовать??");
                        out.flush();
                        line = in.readUTF();
                        tables = line;

                        out.writeUTF("По какому столбцу выборку??");
                        out.flush();
                        line = in.readUTF();
                        column = line;

                        out.writeUTF("Условие выобрки ( =, !=, >, <, >=, <=) и само значение выборки через пробел");
                        out.flush();
                        line = in.readUTF();
                        term = line;

                        result = mySql.select(DB,tables,column,term);
                        out.writeUTF(result);
                        out.flush();

                        break;

                    case "6":

                        String colums2,term2;

                            out.writeUTF("Какую таблицу использовать??");
                            out.flush();
                            line = in.readUTF();
                            tables = line;

                            out.writeUTF("Какой столбец будем обнавлять?");
                            out.flush();
                            line = in.readUTF();
                            column = line;

                            out.writeUTF("На какое значение. Если не число, то в кавычках ");
                            out.flush();
                            term1 = in.readUTF();

                            out.writeUTF("Использовать условие при обновлении?(Y / N)");
                            out.flush();
                            line = in.readUTF();
                            choise = line;


                        if (choise.equals("Y")) {

                            out.writeUTF("Столбец в условии");
                            out.flush();
                            line = in.readUTF();
                            colums2 = line;

                            out.writeUTF("Само условие( =, !=, >, <, >=, <=) и значение выборки через пробел");
                            out.flush();
                            line = in.readUTF();
                            term2 = line;


                            mySql.update(DB, tables, column, term1, colums2, term2);

                        }
                        else {
                            mySql.updateAll(DB, tables, column, term1);
                        }
                        break;

                    case "7":

                        out.writeUTF("Какую таблицу использовать??");
                        out.flush();
                        line = in.readUTF();
                        tables = line;

                        out.writeUTF("По какому столбцу будем делать проверку и удаление??");
                        out.flush();
                        line = in.readUTF();
                        column = line;

                        out.writeUTF("Условие удаления ( =, !=, >, <, >=, <=) и само значение выборки через пробел");
                        out.flush();
                        line = in.readUTF();
                        term1 = line;

                        mySql.delete(DB,tables,column,term1);

                        break;
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}
