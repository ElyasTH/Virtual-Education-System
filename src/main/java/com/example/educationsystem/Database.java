package com.example.educationsystem;

import Exceptions.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                " phone, role, picture, lessons, assignmentsContent, examsContent FROM users WHERE id='" + id + "'";

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
                        rs.getString("lessons"), rs.getString("assignmentsContent"), rs.getString("examsContent"), getLessons);
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
        String firstNameQuery = "update users set email = ?, password = ?, phone = ?, picture = ?," +
                " lessons = ?, assignmentsContent = ?, examsContent = ? where id = ?";

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

            StringBuilder assignmentsContent = new StringBuilder();
            for (Map.Entry<Integer, ArrayList<Object>> content: user.getAssignmentsContent().entrySet()){
                assignmentsContent.append(content.getKey()).append("/,/").append(content.getValue().get(0)).append("/,/")
                        .append(content.getValue().get(1)).append("/,/").append(content.getValue().get(2)).append("\n");
            }
            preparedStmt.setString(6, String.valueOf(assignmentsContent));

            StringBuilder examsContent = new StringBuilder();
            for (Map.Entry<Integer, ArrayList<Object>> content: user.getExamsContent().entrySet()){
                examsContent.append(content.getKey()).append("/,/").append(content.getValue().get(0)).append("/,/")
                        .append(content.getValue().get(1)).append("/,/");
                StringBuilder answers = new StringBuilder();
                for (Map.Entry<Integer, Object> answer: ((HashMap<Integer, Object>) content.getValue().get(2)).entrySet()){
                    answers.append(answer.getKey()).append("/:/").append(answer.getValue()).append("/@/");
                }
                examsContent.append(answers).append("/,/").append(content.getValue().get(3)).append("\n");
            }
            preparedStmt.setString(7, String.valueOf(examsContent));
            preparedStmt.setString(8, user.getId());
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
        String sql = " insert into users (username, password, firstname, lastname, major, id, email, phone, role, picture)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                        rs.getString("assignmentIds"), rs.getString("contentIds"), rs.getString("examIds"), rs.getString("noticeIds"));
            } else throw new InvalidContentIdException();
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
        final String query = "SELECT name, description, id, lessonId, file, startTime, endTime FROM assignments " +
                "WHERE id=" + assignmentId;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Extract data from result set
            if (rs.next()) {
                return new Assignment(rs.getString("name"), rs.getString("description"), rs.getString("file"), rs.getInt("lessonId"),
                        rs.getInt("id"), rs.getTimestamp("startTime").toLocalDateTime(), rs.getTimestamp("endTime").toLocalDateTime());
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
        final String query = "SELECT name, description, id, lessonId, file FROM content " +
                "WHERE id=" + contentId;

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Extract data from result set
            if (rs.next()) {
                return new Content(rs.getString("name"), rs.getString("description"), rs.getInt("lessonId"),
                        rs.getInt("id"), rs.getString("file"));
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
        final String query = "SELECT name, description, id, lessonId FROM notices " +
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
        final String query = "SELECT name, id, lessonId, startTime, endTime, questionIds FROM exams " +
                "WHERE id=" + examId;
        ArrayList<Question> questions = new ArrayList<>();

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return new Exam(rs.getString("name"), rs.getInt("lessonId"), rs.getInt("id"),
                        rs.getTimestamp("startTime").toLocalDateTime(), rs.getTimestamp("endTime").toLocalDateTime(),
                        rs.getString("questionIds"));
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
        final String query = "SELECT lessonId, id, question, score, type, options, correctOption, correctAnswer FROM questions " +
                "WHERE id=" + questionId;

        Connection conn = get_connection();
        try {
            if (conn == null) return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                switch (rs.getString("type")){
                    case "descriptive": return new DescriptiveQuestion(rs.getInt("lessonId"),
                            rs.getInt("id"), rs.getFloat("score"), rs.getString("question"));
                    case "multipleChoice": return new MultipleChoiceQuestion(rs.getInt("lessonId"),
                            rs.getInt("id"), rs.getFloat("score"), rs.getString("question"), rs.getString("options").split(","),
                            rs.getInt("correctOption"));
                    case "trueFalse": return new TrueFalseQuestion(rs.getInt("lessonId"),
                            rs.getInt("id"), rs.getFloat("score"), rs.getString("question"),
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
        Database.getUser(lesson.getTeacherId(), true).addLesson(lesson);
        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, lesson.getTitle());
            preparedStmt.setInt(2, lesson.getLessonId());
            preparedStmt.setString(3, lesson.getTeacherId());
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

    public static void addNotice(Notice notice){
        String sql = " insert into notices (name, description, id, lessonId)"
                + " values (?, ?, ?, ?)";

        Connection conn = get_connection();

        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, notice.getTitle());
            preparedStmt.setString(2, notice.getDescription());
            preparedStmt.setInt(3, notice.getId());
            preparedStmt.setInt(4, notice.getLessonId());
            preparedStmt.execute();
            Lesson lesson = getLesson(notice.getLessonId());
            lesson.addNotice(notice);
            Database.updateLesson(lesson);
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

    public static void addContent(Content content){
        String sql = " insert into content (name, description, file, id, lessonId)"
                + " values (?, ?, ?, ?, ?)";

        Connection conn = get_connection();

        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, content.getTitle());
            preparedStmt.setString(2, content.getDescription());
            preparedStmt.setString(3, content.getFile());
            preparedStmt.setInt(4, content.getId());
            preparedStmt.setInt(5, content.getLessonId());
            preparedStmt.execute();
            Lesson lesson = getLesson(content.getLessonId());
            lesson.addContent(content);
            Database.updateLesson(lesson);
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

    public static void addAssignment(Assignment assignment){
        String sql = " insert into assignments (name, description, id, file, startTime, endTime, lessonId)"
                + " values (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = get_connection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, assignment.getTitle());
            preparedStmt.setString(2, assignment.getDescription());
            preparedStmt.setInt(3, assignment.getId());
            preparedStmt.setString(4, assignment.getFile());
            preparedStmt.setString(5, assignment.getStartDate().format(formatter));
            preparedStmt.setString(6, assignment.getEndDate().format(formatter));
            preparedStmt.setInt(7, assignment.getLessonId());
            preparedStmt.execute();
            Lesson lesson = getLesson(assignment.getLessonId());
            lesson.addAssignment(assignment);
            Database.updateLesson(lesson);
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

    public static void addExam(Exam exam){
        String sql = " insert into exams (name, id, questionIds, startTime, endTime, lessonId)"
                + " values (?, ?, ?, ?, ?, ?)";

        Connection conn = get_connection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try{
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, exam.getTitle());
            preparedStmt.setInt(2, exam.getId());

            StringBuilder questionIds = new StringBuilder();
            for (Question question: exam.getQuestions()){
                questionIds.append(question.getQuestionId()).append(",");
            }
            preparedStmt.setString(3, String.valueOf(questionIds));
            preparedStmt.setString(4, exam.getStartDate().format(formatter));
            preparedStmt.setString(5, exam.getEndDate().format(formatter));
            preparedStmt.setInt(6, exam.getLessonId());
            preparedStmt.execute();
            Lesson lesson = getLesson(exam.getLessonId());
            lesson.addExam(exam);
            Database.updateLesson(lesson);
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

    public static void addQuestion(Question question){
        String sql = " insert into questions (type, question, score, id, lessonId, options, correctOption, correctAnswer)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = get_connection();
        try{
            if (conn == null) return;
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            String type;
            if (question instanceof DescriptiveQuestion) type = "descriptive";
            else if (question instanceof TrueFalseQuestion) type = "trueFalse";
            else if (question instanceof MultipleChoiceQuestion) type = "multipleChoice";
            else throw new InvalidQuestionTypeException();
            preparedStmt.setString(1, type);
            preparedStmt.setString(2, question.getQuestion());
            preparedStmt.setFloat(3, question.getScore());
            preparedStmt.setInt(4, question.getQuestionId());
            preparedStmt.setInt(5, question.getLessonId());
            if (type.equals("multipleChoice")){
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                StringBuilder options = new StringBuilder();
                for (String option: mcQuestion.getOptions()){
                    options.append(option).append(",");
                }
                preparedStmt.setString(6, String.valueOf(options));
                preparedStmt.setInt(7, mcQuestion.getCorrectOption());
                preparedStmt.setString(8, null);
            }
            else if (type.equals("trueFalse")) {
                preparedStmt.setString(6, null);
                preparedStmt.setString(7, null);
                preparedStmt.setBoolean(8, ((TrueFalseQuestion) question).getCorrectAnswer());
            }
            else {
                preparedStmt.setString(6, null);
                preparedStmt.setString(7, null);
                preparedStmt.setString(8, null);
            }
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

    public static void updateLesson(Lesson lesson){
        String sql = "update lessons set studentIds = ?, noticeIds = ?, assignmentIds = ?, examIds = ?, contentIds = ? " +
                "where id=" + lesson.getLessonId();
        StringBuilder studentIds = new StringBuilder();
        StringBuilder noticeIds = new StringBuilder();
        StringBuilder assignmentIds = new StringBuilder();
        StringBuilder examIds = new StringBuilder();
        StringBuilder contentIds = new StringBuilder();

        for (String studentId: lesson.getStudentIds()){
            studentIds.append(studentId).append(",");
        }

        for (Notice notice: lesson.getNotices()){
            noticeIds.append(notice.getId()).append(",");
        }

        for (Assignment assignment: lesson.getAssignments()){
            assignmentIds.append(assignment.getId()).append(",");
        }

        for (Content content: lesson.getContent()){
            contentIds.append(content.getId()).append(",");
        }

        for (Exam exam: lesson.getExams()){
            examIds.append(exam.getId()).append(",");
        }

        Connection conn = get_connection();
        try {
            if (conn == null) return;
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, String.valueOf(studentIds));
            preparedStmt.setString(2, String.valueOf(noticeIds));
            preparedStmt.setString(3, String.valueOf(assignmentIds));
            preparedStmt.setString(4, String.valueOf(examIds));
            preparedStmt.setString(5, String.valueOf(contentIds));
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

    public static void updateExam(Exam exam){
        String sql = "update exams set questionIds = ? " +
                "where id=" + exam.getExamId();
        StringBuilder questionIds = new StringBuilder();

        Connection conn = get_connection();
        try {
            if (conn == null) return;
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, String.valueOf(questionIds));
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
}

