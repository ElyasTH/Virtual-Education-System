package com.example.educationsystem;

import Exceptions.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;

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

    public static User getUser(String id, boolean getLessons) {
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
                        rs.getString("picture"), rs.getString("username"), rs.getString("password"),
                        rs.getString("lessons"), getLessons);
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
        String firstNameQuery = "update users set email = ?, password = ?, phone = ?, picture = ?, lessons = ? where id = ?";

        Connection conn = get_connection();
        try {
            if (conn == null) return;
            PreparedStatement preparedStmt = conn.prepareStatement(firstNameQuery);
            preparedStmt.setString(1, user.getEmail());
            preparedStmt.setString(2, user.getPassword());
            preparedStmt.setString(3, user.getPhone());
            preparedStmt.setString(4, user.getPicture());

            StringBuilder lessonList = new StringBuilder("");
            for (Lesson lesson: user.getLessons()){
                lessonList.append(lesson.getLessonId()).append(",");
            }
            preparedStmt.setString(5, String.valueOf(lessonList));
            preparedStmt.setString(6, user.getId());
            preparedStmt.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        } finally {
            try{
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Lesson getLesson(int lessonId){
        final String query = "SELECT name, id, teacherId, studentIds, examIds, contentIds, assignmentIds, noticeIds FROM lessons " +
                            "WHERE id=" + lessonId;

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return new Lesson(rs.getString("name"), rs.getInt("id"), rs.getString("teacherId"), 30 , rs.getString("studentIds"),
                        rs.getString("examIds"), rs.getString("contentIds"), rs.getString("assignmentIds"), rs.getString("noticeIds"));
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

    public static Assignment getAssignment(int assignmentId){
        final String query = "SELECT name, description, id, lessonId, file, startTime, endTime" +
                "WHERE id=" + assignmentId;

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Extract data from result set
            if (rs.next()) {
                return new Assignment(rs.getString("name"), rs.getString("description"), rs.getString("file"), rs.getInt("lessonId"),
                        rs.getInt("id"), rs.getDate("startTime").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        rs.getDate("endTime").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
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

    public static Content getContent(int contentId){
        final String query = "SELECT name, description, id, lessonId, file" +
                "WHERE id=" + contentId;

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Extract data from result set
            if (rs.next()) {
                return new Content(rs.getString("name"), rs.getString("description"), rs.getInt("id"),
                        rs.getInt("lessonId"), rs.getString("file"));
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

    public static Notice getNotice(int noticeId){
        final String query = "SELECT name, description, id, lessonId" +
                "WHERE id=" + noticeId;

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Extract data from result set
            if (rs.next()) {
                return new Notice(rs.getString("name"), rs.getString("description"), rs.getInt("lessonId"), rs.getInt("id"));
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

    public static Exam getExam(int examId){
        final String query = "SELECT name, id, lessonId, startTime, endTime, questionIds" +
                "WHERE id=" + examId;
        ArrayList<Question> questions = new ArrayList<>();

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String[] questionIds = rs.getString("questionIds").split(",");

            for (String questionId: questionIds){
                questions.add(getQuestion(Integer.parseInt(questionId)));
            }

            if (rs.next()) {
                return new Exam(rs.getString("name"), rs.getInt("id"), rs.getInt("lessonId"),
                        rs.getDate("startTime").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        rs.getDate("endTime").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), questions);
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

    public static Question getQuestion(int questionId){
        final String query = "SELECT lessonId, examId, id, question, score, type, options, correctOption, correctAnswer" +
                "WHERE id=" + questionId;

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                switch (rs.getString("type")){
                    case "descriptive": return new DescriptiveQuestion(rs.getInt("lessonId"), rs.getInt("examId"),
                            rs.getInt("id"), rs.getDouble("score"), rs.getString("question"));
                    case "multipleChoice": return new MultipleChoiceQuestion(rs.getInt("lessonId"), rs.getInt("examId"),
                            rs.getInt("questionId"), rs.getDouble("score"), rs.getString("question"), rs.getString("options").split(","),
                            rs.getInt("correctOption"));
                    case "trueFalseQuestion": return new TrueFalseQuestion(rs.getInt("lessonId"), rs.getInt("examId"),
                            rs.getInt("questionId"), rs.getDouble("score"), rs.getString("question"),
                            rs.getBoolean("correctAnswer"));
                }
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

    public static void addLesson(Lesson lesson){
        String sql = " insert into lessons (name, id, teacherId, studentIds)"
                + " values (?, ?, ?, ?)";

        Connection conn = get_connection();

        StringBuilder studentIds = new StringBuilder("");
        for (String studentId: lesson.getStudentIds()){
            studentIds.append(studentId).append(",");
            Database.getUser(studentId, true).addLesson(lesson);
        }
        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, lesson.getTitle());
            preparedStmt.setInt(2, lesson.getLessonId());
            preparedStmt.setString(3, lesson.getTeacher().getId());
            preparedStmt.setString(4, String.valueOf(studentIds));
            preparedStmt.execute();
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

    public static void addNotice(Notice notice ,Lesson lesson){
        String sql = " insert into notices (name, description, id, lessonId)"
                + " values (?, ?, ?, ?)";

        Connection conn = get_connection();

        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, notice.getTitle());
            preparedStmt.setString(2, notice.getDescription());
            preparedStmt.setInt(3, notice.getId());
            preparedStmt.setInt(4, lesson.getLessonId());
            preparedStmt.execute();
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

    public static void addContent(Content content, Lesson lesson){
        String sql = " insert into content (name, description, file, id, lessonId)"
                + " values (?, ?, ?, ?, ?)";

        Connection conn = get_connection();

        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, content.getTitle());
            preparedStmt.setString(2, content.getDescription());
            preparedStmt.setString(3, content.getFile());
            preparedStmt.setInt(4, content.getId());
            preparedStmt.setInt(5, lesson.getLessonId());
            preparedStmt.execute();
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

    public static void addAssignment(Assignment assignment, Lesson lesson){
        String sql = " insert into assignments (name, description, id, file, startTime, endTime, lessonId)"
                + " values (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = get_connection();

        java.util.Date startTime = Date.from(assignment.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date endTime = Date.from(assignment.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, assignment.getTitle());
            preparedStmt.setString(2, assignment.getDescription());
            preparedStmt.setInt(3, assignment.getId());
            preparedStmt.setString(4, assignment.getFile());
            preparedStmt.setString(5, format.format(startTime));
            preparedStmt.setString(6, format.format(endTime));
            preparedStmt.setInt(7, lesson.getLessonId());
            preparedStmt.execute();
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

    public static int getLastId(String tableName){
        String query = "SELECT MAX(id) FROM " + tableName;

        Connection conn = get_connection();
        try {
            if (conn == null) return -1;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try{
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}

