package org.example.repository;

import org.example.domain.Student;
import org.example.domain.Student.StudentBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMysqlRepository implements StudentRepository {
    private static final String DB_URL = "jdbc:mysql://robot-do-user-1968994-0.b.db.ondigitalocean.com:25060/dino";
    private static final String DB_USER = "doadmin";
    private static final String DB_PASSWORD = "AVNS_I6wlDKjGszZn1wvLr9t";
    private static final String SELECT_FROM_STUDENTS = "SELECT * FROM Student";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM Student WHERE id = ?";
    private static final String INSERT_STUDENT = "INSERT INTO Student (name, age, group_id) VALUES (?, ?, ?)";

    @Override
    public void save(Student student) {
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(INSERT_STUDENT);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Student> findAll() {
        List<Student> result = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_FROM_STUDENTS)) {
            while (rs.next()) {

                Student student = Student.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .age(rs.getInt("age"))

                        .build();

                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Student findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            ps = conn.prepareStatement(SELECT_STUDENT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            return Student.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))

                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}