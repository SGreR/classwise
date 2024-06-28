package com.classwise.classwisegradesservice;

import com.classwise.classwisegradesservice.model.Skill;
import com.classwise.classwisegradesservice.enums.SkillName;
import com.classwise.classwisegradesservice.enums.TestNumber;
import com.classwise.classwisegradesservice.model.Grades;
import com.classwise.classwisegradesservice.model.Abilities;
import com.classwise.classwisegradesservice.model.Speaking;
import com.classwise.classwisegradesservice.model.ClassPerformance;
import com.classwise.classwisegradesservice.repository.GradesRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private GradesRepository gradesRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Abilities abilities = new Abilities();

        Skill readingSkill = new Skill(SkillName.READING, 80.0, 85.0);
        Skill writingSkill = new Skill(SkillName.WRITING, 75.0, 80.0);
        Skill listeningSkill = new Skill(SkillName.LISTENING,  85.0, 90.0);
        Skill useOfEnglishSkill = new Skill(SkillName.USEOFENGLISH, 70.0, 75.0);
        abilities.setSkills(Arrays.asList(readingSkill, writingSkill, listeningSkill, useOfEnglishSkill));


        Grades grades = new Grades();
        grades.setStudentId(1L);
        grades.setCourseId(1L);
        grades.setTestNumber(TestNumber.FIRST);

        grades.setAbilities(abilities);

        Speaking speaking = new Speaking();
        speaking.setProductionAndFluencyGrade(4);
        speaking.setSpokenInteractionGrade(4);
        speaking.setLanguageRangeGrade(3);
        speaking.setAccuracyGrade(4);
        speaking.setLanguageUse(3);

        ClassPerformance classPerformance = new ClassPerformance();
        classPerformance.setPresenceGrade(5);
        classPerformance.setHomeworkGrade(4);
        classPerformance.setParticipationGrade(4);
        classPerformance.setBehaviorGrade(5);

        abilities.setSpeaking(speaking);
        abilities.setClassPerformance(classPerformance);

        gradesRepository.save(grades);

    }
}
