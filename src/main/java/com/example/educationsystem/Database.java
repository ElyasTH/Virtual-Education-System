package com.example.educationsystem;

import Exceptions.*;

import java.sql.*;
import java.util.Arrays;

public class Database {
    static final String DB_URL = "jdbc:mysql://localhost/educationsystem";
    static final String USER = "root";
    static final String PASS = "2831";

    private static Connection get_connection(){
        try{
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static User getUser(String id) {
        final String getUserQuery = "SELECT username, password, firstname, lastname, major, id, email," +
                " phone, role, picture, lessons FROM users WHERE id='" + id + "'";

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getUserQuery);
            // Extract data from result set
            if (rs.next()) {
                return new User(rs.getString("firstname"), rs.getString("lastname"), rs.getString("major"),
                        rs.getString("id"), rs.getString("email"), rs.getString("phone"), rs.getString("role"),
                        rs.getString("picture"), rs.getString("username"), rs.getString("password"), rs.getString("lessons"));
            } else throw new InvalidUserException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try{
               if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void updateUser(User user){
        Connection conn = get_connection();
        String firstNameQuery = "update users set email = ?, password = ?, phone = ?, picture = ?, lessons = ? where id = ?";

        if (conn == null) return;
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(firstNameQuery);
            preparedStmt.setString(1, user.getEmail());
            preparedStmt.setString(2, user.getPassword());
            preparedStmt.setString(3, user.getPhone());
            preparedStmt.setString(4, user.getPicture());

            StringBuilder lessonList = new StringBuilder("");
            for (String lesson: user.getLessons()){
                lessonList.append(lesson).append(",");
            }
            preparedStmt.setString(5, String.valueOf(lessonList));
            preparedStmt.setString(6, user.getId());
            preparedStmt.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void newUser(User user){
        Connection conn = null;
        String sql = " insert into users (username, password, firstname, lastname, major, id, email, phone, role, picture, lessons)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, user.getUsername());
            preparedStmt.setString(2, user.getPassword());
            preparedStmt.setString(3, user.getFirstname());
            preparedStmt.setString(4, user.getLastname());
            preparedStmt.setString(5, user.getMajor());
            preparedStmt.setString(6, user.getId());
            preparedStmt.setString(7, user.getEmail());
            preparedStmt.setString(8, user.getPhone());
            preparedStmt.setString(9, user.getRole());
            preparedStmt.setString(10, user.getPicture());
            preparedStmt.setString(11, "");
            preparedStmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void check_user_info_existence(String id, String username, String email, String phone){
        final String idQuery = "SELECT * FROM users WHERE id=" + id;
        final String usernameQuery = "SELECT * FROM users WHERE username='" + username + "'";
        final String emailQuery = "SELECT * FROM users WHERE email='" + email + "'";
        final String phoneQuery = "SELECT * FROM users WHERE phone='" + phone + "'";

        Connection conn = get_connection();
        try{
            if (conn == null) return;
            Statement stmt = conn.createStatement();

            ResultSet rsId = stmt.executeQuery(idQuery);
            if (rsId.next()) throw new IdAlreadyExistsException();

            ResultSet rsUsername = stmt.executeQuery(usernameQuery);
            if (rsUsername.next()) throw new UsernameAlreadyExistsException();

            ResultSet rsEmail = stmt.executeQuery(emailQuery);
            if (rsEmail.next()) throw new EmailAlreadyExistsException();

            ResultSet rsPhone = stmt.executeQuery(phoneQuery);
            if (rsPhone.next()) throw new PhoneAlreadyExistsException();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

