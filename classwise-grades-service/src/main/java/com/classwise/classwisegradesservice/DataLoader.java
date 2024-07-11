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
        // First set of grades
        Abilities abilities1 = new Abilities();
        Set<Skill> skills1 = new HashSet<>();
        skills1.add(new Skill(SkillName.READING, 80.0, 85.0));
        skills1.add(new Skill(SkillName.WRITING, 75.0, 80.0));
        skills1.add(new Skill(SkillName.LISTENING, 85.0, 90.0));
        skills1.add(new Skill(SkillName.USEOFENGLISH, 70.0, 75.0));
        abilities1.setSkills(skills1);

        Speaking speaking1 = new Speaking();
        speaking1.setProductionAndFluencyGrade(4);
        speaking1.setSpokenInteractionGrade(4);
        speaking1.setLanguageRangeGrade(3);
        speaking1.setAccuracyGrade(4);
        speaking1.setLanguageUse(3);

        ClassPerformance classPerformance1 = new ClassPerformance();
        classPerformance1.setPresenceGrade(5);
        classPerformance1.setHomeworkGrade(4);
        classPerformance1.setParticipationGrade(4);
        classPerformance1.setBehaviorGrade(5);

        abilities1.setSpeaking(speaking1);
        abilities1.setClassPerformance(classPerformance1);

        Grades grades1 = new Grades();
        grades1.setStudentId(1L);
        grades1.setCourseId(1L);
        grades1.setTestNumber(TestNumber.FIRST);
        grades1.setAbilities(abilities1);
        gradesRepository.save(grades1);

        // Second set of grades
        Abilities abilities2 = new Abilities();
        Set<Skill> skills2 = new HashSet<>();
        skills2.add(new Skill(SkillName.READING, 50.0, 85.0));
        skills2.add(new Skill(SkillName.WRITING, 50.0, 80.0));
        skills2.add(new Skill(SkillName.LISTENING, 50.0, 90.0));
        skills2.add(new Skill(SkillName.USEOFENGLISH, 50.0, 75.0));
        abilities2.setSkills(skills2);

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

        Grades grades2 = new Grades();
        grades2.setStudentId(1L);
        grades2.setCourseId(1L);
        grades2.setTestNumber(TestNumber.SECOND);
        grades2.setAbilities(abilities2);
        gradesRepository.save(grades2);

        // Third set of grades
        Abilities abilities3 = new Abilities();
        Set<Skill> skills3 = new HashSet<>();
        skills3.add(new Skill(SkillName.READING, 90.0, 92.0));
        skills3.add(new Skill(SkillName.WRITING, 85.0, 87.0));
        skills3.add(new Skill(SkillName.LISTENING, 88.0, 89.0));
        skills3.add(new Skill(SkillName.USEOFENGLISH, 80.0, 83.0));
        abilities3.setSkills(skills3);

        Speaking speaking3 = new Speaking();
        speaking3.setProductionAndFluencyGrade(3);
        speaking3.setSpokenInteractionGrade(4);
        speaking3.setLanguageRangeGrade(4);
        speaking3.setAccuracyGrade(3);
        speaking3.setLanguageUse(4);

        ClassPerformance classPerformance3 = new ClassPerformance();
        classPerformance3.setPresenceGrade(4);
        classPerformance3.setHomeworkGrade(4);
        classPerformance3.setParticipationGrade(5);
        classPerformance3.setBehaviorGrade(4);

        abilities3.setSpeaking(speaking3);
        abilities3.setClassPerformance(classPerformance3);

        Grades grades3 = new Grades();
        grades3.setStudentId(1L);
        grades3.setCourseId(2L);
        grades3.setTestNumber(TestNumber.FIRST);
        grades3.setAbilities(abilities3);
        gradesRepository.save(grades3);

        // Fourth set of grades
        Abilities abilities4 = new Abilities();
        Set<Skill> skills4 = new HashSet<>();
        skills4.add(new Skill(SkillName.READING, 60.0, 75.0));
        skills4.add(new Skill(SkillName.WRITING, 65.0, 70.0));
        skills4.add(new Skill(SkillName.LISTENING, 75.0, 78.0));
        skills4.add(new Skill(SkillName.USEOFENGLISH, 55.0, 60.0));
        abilities4.setSkills(skills4);

        Speaking speaking4 = new Speaking();
        speaking4.setProductionAndFluencyGrade(2);
        speaking4.setSpokenInteractionGrade(3);
        speaking4.setLanguageRangeGrade(3);
        speaking4.setAccuracyGrade(2);
        speaking4.setLanguageUse(3);

        ClassPerformance classPerformance4 = new ClassPerformance();
        classPerformance4.setPresenceGrade(5);
        classPerformance4.setHomeworkGrade(3);
        classPerformance4.setParticipationGrade(4);
        classPerformance4.setBehaviorGrade(5);

        abilities4.setSpeaking(speaking4);
        abilities4.setClassPerformance(classPerformance4);

        Grades grades4 = new Grades();
        grades4.setStudentId(1L);
        grades4.setCourseId(2L);
        grades4.setTestNumber(TestNumber.SECOND);
        grades4.setAbilities(abilities4);
        gradesRepository.save(grades4);

        // Fifth set of grades
        Abilities abilities5 = new Abilities();
        Set<Skill> skills5 = new HashSet<>();
        skills5.add(new Skill(SkillName.READING, 70.0, 80.0));
        skills5.add(new Skill(SkillName.WRITING, 60.0, 75.0));
        skills5.add(new Skill(SkillName.LISTENING, 68.0, 70.0));
        skills5.add(new Skill(SkillName.USEOFENGLISH, 65.0, 68.0));
        abilities5.setSkills(skills5);

        Speaking speaking5 = new Speaking();
        speaking5.setProductionAndFluencyGrade(4);
        speaking5.setSpokenInteractionGrade(4);
        speaking5.setLanguageRangeGrade(3);
        speaking5.setAccuracyGrade(4);
        speaking5.setLanguageUse(4);

        ClassPerformance classPerformance5 = new ClassPerformance();
        classPerformance5.setPresenceGrade(4);
        classPerformance5.setHomeworkGrade(4);
        classPerformance5.setParticipationGrade(4);
        classPerformance5.setBehaviorGrade(4);

        abilities5.setSpeaking(speaking5);
        abilities5.setClassPerformance(classPerformance5);

        Grades grades5 = new Grades();
        grades5.setStudentId(1L);
        grades5.setCourseId(3L);
        grades5.setTestNumber(TestNumber.FIRST);
        grades5.setAbilities(abilities5);
        gradesRepository.save(grades5);

        // Sixth set of grades
        Abilities abilities6 = new Abilities();
        Set<Skill> skills6 = new HashSet<>();
        skills6.add(new Skill(SkillName.READING, 85.0, 90.0));
        skills6.add(new Skill(SkillName.WRITING, 80.0, 85.0));
        skills6.add(new Skill(SkillName.LISTENING, 88.0, 92.0));
        skills6.add(new Skill(SkillName.USEOFENGLISH, 75.0, 80.0));
        abilities6.setSkills(skills6);

        Speaking speaking6 = new Speaking();
        speaking6.setProductionAndFluencyGrade(5);
        speaking6.setSpokenInteractionGrade(5);
        speaking6.setLanguageRangeGrade(4);
        speaking6.setAccuracyGrade(5);
        speaking6.setLanguageUse(4);

        ClassPerformance classPerformance6 = new ClassPerformance();
        classPerformance6.setPresenceGrade(5);
        classPerformance6.setHomeworkGrade(5);
        classPerformance6.setParticipationGrade(5);
        classPerformance6.setBehaviorGrade(5);

        abilities6.setSpeaking(speaking6);
        abilities6.setClassPerformance(classPerformance6);

        Grades grades6 = new Grades();
        grades6.setStudentId(1L);
        grades6.setCourseId(3L);
        grades6.setTestNumber(TestNumber.SECOND);
        grades6.setAbilities(abilities6);
        gradesRepository.save(grades6);

        // Seventh set of grades
        Abilities abilities7 = new Abilities();
        Set<Skill> skills7 = new HashSet<>();
        skills7.add(new Skill(SkillName.READING, 75.0, 85.0));
        skills7.add(new Skill(SkillName.WRITING, 70.0, 80.0));
        skills7.add(new Skill(SkillName.LISTENING, 72.0, 88.0));
        skills7.add(new Skill(SkillName.USEOFENGLISH, 65.0, 75.0));
        abilities7.setSkills(skills7);

        Speaking speaking7 = new Speaking();
        speaking7.setProductionAndFluencyGrade(4);
        speaking7.setSpokenInteractionGrade(4);
        speaking7.setLanguageRangeGrade(3);
        speaking7.setAccuracyGrade(4);
        speaking7.setLanguageUse(3);

        ClassPerformance classPerformance7 = new ClassPerformance();
        classPerformance7.setPresenceGrade(4);
        classPerformance7.setHomeworkGrade(3);
        classPerformance7.setParticipationGrade(4);
        classPerformance7.setBehaviorGrade(5);

        abilities7.setSpeaking(speaking7);
        abilities7.setClassPerformance(classPerformance7);

        Grades grades7 = new Grades();
        grades7.setStudentId(1L);
        grades6.setCourseId(3L);
        grades7.setTestNumber(TestNumber.FIRST);
        grades7.setAbilities(abilities7);
        gradesRepository.save(grades7);

        // Eighth set of grades
        Abilities abilities8 = new Abilities();
        Set<Skill> skills8 = new HashSet<>();
        skills8.add(new Skill(SkillName.READING, 80.0, 88.0));
        skills8.add(new Skill(SkillName.WRITING, 75.0, 82.0));
        skills8.add(new Skill(SkillName.LISTENING, 78.0, 85.0));
        skills8.add(new Skill(SkillName.USEOFENGLISH, 70.0, 78.0));
        abilities8.setSkills(skills8);

        Speaking speaking8 = new Speaking();
        speaking8.setProductionAndFluencyGrade(5);
        speaking8.setSpokenInteractionGrade(5);
        speaking8.setLanguageRangeGrade(4);
        speaking8.setAccuracyGrade(4);
        speaking8.setLanguageUse(5);

        ClassPerformance classPerformance8 = new ClassPerformance();
        classPerformance8.setPresenceGrade(3);
        classPerformance8.setHomeworkGrade(3);
        classPerformance8.setParticipationGrade(4);
        classPerformance8.setBehaviorGrade(5);

        abilities8.setSpeaking(speaking8);
        abilities8.setClassPerformance(classPerformance8);

        Grades grades8 = new Grades();
        grades8.setStudentId(1L);
        grades6.setCourseId(3L);
        grades8.setTestNumber(TestNumber.SECOND);
        grades8.setAbilities(abilities8);
        gradesRepository.save(grades8);
    }
}

