package com.wugq.dao;

import com.wugq.model.Student;
import java.util.List;

/**
 * Created by wuguoquan on 7/18/17.
 */
public interface StudentDao {

    public void addStudent(Student student);

    public void deleteStudent(int studentId);

    public void updateStudent(Student student);

    public List<Student> getAllStudents();

    public Student getStudentById(int studentId);
}
