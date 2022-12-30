package io.rogermoore.sdi.http.example;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class StudentService {
    public Student getStudent(final String name,
                              final int age) {
        return new Student(name, age);
    }
}
