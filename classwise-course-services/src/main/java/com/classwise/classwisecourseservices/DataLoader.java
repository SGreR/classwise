package com.classwise.classwisecourseservices;

import com.classwise.classwisecourseservices.model.Course;
import com.classwise.classwisecourseservices.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(String... args) {

        Course course = new Course();
        course.setCourseName("Discover 1");
        course.setSemesterId(1L);
        course.setTeacherId(1L);
        course.setStudentIds(setOf(1L, 2L));
        courseRepository.save(course);

        Course course2 = new Course();
        course2.setCourseName("Independent 1");
        course2.setSemesterId(1L);
        course2.setTeacherId(1L);
        course2.setStudentIds(setOf(1L, 2L));
        courseRepository.save(course2);

        Course course3 = new Course();
        course3.setCourseName("Expert B2a");
        course3.setSemesterId(2L);
        course3.setTeacherId(2L);
        course3.setStudentIds(setOf(1L, 2L));
        courseRepository.save(course3);

    }
}
