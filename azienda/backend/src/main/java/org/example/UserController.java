package org.example;

import io.javalin.http.Context;

import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class UserController {

    private static String user = "root";
    private static String password = "Root123!";
    private static String url = "jdbc:mysql://localhost:3306/trizeta";

    public static void registerUser(@NotNull Context ctx) {
        //String url = "jdbc:mysql://192.168.5.8:3307/4AI_ROSSATO";

        String email1 = ctx.formParam("email");
        String pass1 = ctx.formParam("password1");
        String pass2 = ctx.formParam("password2");

        String email2 = "";

        String query1 = "Select * from user;";
        String query2 = "insert into user(email, password) values('"+email1+"', '"+pass1+"');";


        boolean result = false;
        boolean emailValidator1 = false;
        boolean passwordValidator1 = false;
        boolean passwordValidator2 = false;

        if(!EmailValidator.isValidEmail(email1)){

            ctx.status(400);
            emailValidator1 = true;

        }

        if(!PasswordValidator.isValidPassword(pass1)){

            ctx.status(400);
            passwordValidator1 = true;

        }

        if(!PasswordValidator.isValidPassword(pass2)){

            ctx.status(400);
            passwordValidator2 = true;

        }

        if(!emailValidator1 && !passwordValidator1 && !passwordValidator2){

            try{

                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Driver caricati con successo");

            } catch (ClassNotFoundException e) {

                System.out.println(e.getMessage());

            }

            try {

                Connection connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connesso con il DataBase");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query1);

                while (rs.next()){

                    email2 = rs.getString("email");

                    if(email1.equals(email2)) {

                        ctx.status(501);
                        result = true;
                        ctx.json("Error: email already present");

                    }

                }

                if(!pass1.equals(pass2)) {

                    ctx.status(501);
                    result = true;
                    ctx.json("Error: different passwords");

                }

                if(!result){

                    int rows= stmt.executeUpdate(query2);

                    if(rows > 0){

                        System.out.println("Inserimento riuscito di "+ rows+" righe");
                        ctx.status(200);

                    }else{

                        System.out.println("Inserimento fallito");
                        ctx.status(500);

                    }

                }

                rs.close();
                stmt.close();
                connection.close();

                System.out.println();
                System.out.println("Connessione chiusa con successo");

            } catch (SQLException e) {

                System.out.println("Connessione fallita" + e.getMessage());

            }

        }

    }

    public static void loginUser(@NotNull Context ctx) {
        //String url = "jdbc:mysql://192.168.5.8:3307/4AI_ROSSATO";
        String query = "Select * from user;";

        String email1 = ctx.formParam("email");
        String pass1 = ctx.formParam("password");

        String email2 = "";
        String pass2 = "";

        boolean result = false;

        try{

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver caricati con successo");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){

                email2 = rs.getString("email");

                pass2 = rs.getString("password");

                if(email1.equals(email2) && pass1.equals(pass2)) {

                    ctx.status(200);
                    result = true;
                    break;

                }

                System.out.println();

                System.out.println(email2);
                System.out.println(pass2);
            }

            if(!result){

                ctx.status(401);

            }

            rs.close();
            stmt.close();
            connection.close();

            System.out.println();
            System.out.println("Connessione chiusa con successo");

        } catch (SQLException e) {

            System.out.println("Connessione fallita" + e.getMessage());

        }

    //ctx.bodyAtClass(User.class)
    }
}
