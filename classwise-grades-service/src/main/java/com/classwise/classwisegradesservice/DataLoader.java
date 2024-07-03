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
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private GradesRepository gradesRepository;

    @Override
    @Transactional
    public void run(String... args) {

        Abilities abilities = new Abilities();

        Skill readingSkill = new Skill(SkillName.READING, 80.0, 85.0);
        Skill writingSkill = new Skill(SkillName.WRITING, 75.0, 80.0);
        Skill listeningSkill = new Skill(SkillName.LISTENING,  85.0, 90.0);
        Skill useOfEnglishSkill = new Skill(SkillName.USEOFENGLISH, 70.0, 75.0);

        Set<Skill> skills = new HashSet<>();
        skills.add(readingSkill);
        skills.add(writingSkill);
        skills.add(listeningSkill);
        skills.add(useOfEnglishSkill);

        abilities.setSkills(skills);

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

        Abilities abilities2 = new Abilities();

        Skill readingSkill2 = new Skill(SkillName.READING, 50.0, 85.0);
        Skill writingSkill2 = new Skill(SkillName.WRITING, 50.0, 80.0);
        Skill listeningSkill2 = new Skill(SkillName.LISTENING,  50.0, 90.0);
        Skill useOfEnglishSkill2 = new Skill(SkillName.USEOFENGLISH, 50.0, 75.0);

        Set<Skill> skills2 = new HashSet<>();
        skills2.add(readingSkill2);
        skills2.add(writingSkill2);
        skills2.add(listeningSkill2);
        skills2.add(useOfEnglishSkill2);

        abilities2.setSkills(skills2);

        Grades grades2 = new Grades();
        grades2.setStudentId(2L);
        grades2.setCourseId(2L);
        grades2.setTestNumber(TestNumber.FIRST);

        grades2.setAbilities(abilities2);

        Speaking speaking2 = new Speaking();
        speaking2.setProductionAndFluencyGrade(5);
        speaking2.setSpokenInteractionGrade(5);
        speaking2.setLanguageRangeGrade(4);
        speaking2.setAccuracyGrade(4);
        speaking2.setLanguageUse(5);

        ClassPerformance classPerformance2 = new ClassPerformance();
        classPerformance2.setPresenceGrade(3);
        classPerformance2.setHomeworkGrade(3);
        classPerformance2.setParticipationGrade(4);
        classPerformance2.setBehaviorGrade(5);

        abilities2.setSpeaking(speaking2);
        abilities2.setClassPerformance(classPerformance2);

        gradesRepository.save(grades2);

    }
}
