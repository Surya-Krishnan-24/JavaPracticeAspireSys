package com.example.SpringJDBC.repo;

import com.example.SpringJDBC.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepo {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Student student) {
        String Query = "insert into student (rollno, name, marks) values (?,?,?)";

        int rows = jdbcTemplate.update(Query, student.getRollNo(), student.getName(),student.getMarks());

        System.out.println("Rows affected "+ rows);
    }

    public List<Student> findAll() {
        String sql = "select * from student";
        RowMapper<Student> mapper = new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

                Student student = new Student();
                student.setRollNo(rs.getInt(1));
                student.setName(rs.getString(2));
                student.setMarks(rs.getInt(3));
                return student;
            }
        };
        return jdbcTemplate.query(sql,mapper);

    }
}
