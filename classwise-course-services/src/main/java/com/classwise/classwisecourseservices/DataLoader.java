package com.classwise.classwisecourseservices;

import com.classwise.classwisecourseservices.model.Course;
import com.classwise.classwisecourseservices.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {

        Course course = new Course();
        course.setCourseId(1L);
        course.setCourseName("Discover 1");
        course.setSemesterId(1L);
        course.setTeacherId(1L);
        course.setStudentIds(listOf(1L, 2L));

        courseRepository.save(course);

    }
}
