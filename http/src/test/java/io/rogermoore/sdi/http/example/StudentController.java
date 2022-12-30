package io.rogermoore.sdi.http.example;

import io.rogermoore.sdi.http.annotation.method.Get;
import io.rogermoore.sdi.http.annotation.request.Parameter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class StudentController {

    private final StudentService studentService;

    @Inject
    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @Get("/students")
    public Student getStudent(@Parameter("name") final String name,
                              @Parameter("age") final int age) {
        return studentService.getStudent(name, age);
    }
}
