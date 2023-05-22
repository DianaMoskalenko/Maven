package org.example;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.example.domain.Student;
import org.example.repository.StudentMysqlRepository;
import org.example.repository.StudentRepository;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentMysqlRepository();

        Student s1 = Student.builder()
                .age(18)
                .name("Anna")
                .build();
        studentRepository.save(s1);

        Student s2 = Student.builder()
                .age(24)
                .name("Serhiy")
                .build();
        studentRepository.save(s2);

        Student s3 = Student.builder()
                .age(21)
                .name("Nick")
                .build();
        studentRepository.save(s3);

        Student s4 = Student.builder()
                .age(20)
                .name("Oleksandr")
                .build();
        studentRepository.save(s4);

        List<Student> students = studentRepository.findAll();
        students.forEach(System.out::println);
    }
}