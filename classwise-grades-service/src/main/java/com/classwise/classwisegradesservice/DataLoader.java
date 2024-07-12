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
        grades2.setStudentId(2L);
        grades2.setCourseId(1L);
        grades2.setTestNumber(TestNumber.FIRST);
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
        grades3.setStudentId(3L);
        grades3.setCourseId(1L);
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
        grades4.setStudentId(4L);
        grades4.setCourseId(1L);
        grades4.setTestNumber(TestNumber.FIRST);
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
        grades5.setStudentId(5L);
        grades5.setCourseId(1L);
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
        grades6.setStudentId(6L);
        grades6.setCourseId(1L);
        grades6.setTestNumber(TestNumber.FIRST);
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
        grades7.setStudentId(7L);
        grades6.setCourseId(1L);
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
        grades8.setStudentId(8L);
        grades6.setCourseId(1L);
        grades8.setTestNumber(TestNumber.FIRST);
        grades8.setAbilities(abilities8);
        gradesRepository.save(grades8);

        // Ninth set of grades
        Abilities abilities9 = new Abilities();
        Set<Skill> skills9 = new HashSet<>();
        skills9.add(new Skill(SkillName.READING, 75.0, 82.0));
        skills9.add(new Skill(SkillName.WRITING, 70.0, 78.0));
        skills9.add(new Skill(SkillName.LISTENING, 78.0, 85.0));
        skills9.add(new Skill(SkillName.USEOFENGLISH, 65.0, 72.0));
        abilities9.setSkills(skills9);

        Speaking speaking9 = new Speaking();
        speaking9.setProductionAndFluencyGrade(4);
        speaking9.setSpokenInteractionGrade(4);
        speaking9.setLanguageRangeGrade(3);
        speaking9.setAccuracyGrade(4);
        speaking9.setLanguageUse(3);

        ClassPerformance classPerformance9 = new ClassPerformance();
        classPerformance9.setPresenceGrade(3);
        classPerformance9.setHomeworkGrade(3);
        classPerformance9.setParticipationGrade(4);
        classPerformance9.setBehaviorGrade(5);

        abilities9.setSpeaking(speaking9);
        abilities9.setClassPerformance(classPerformance9);

        Grades grades9 = new Grades();
        grades9.setStudentId(1L);
        grades9.setCourseId(1L);
        grades9.setTestNumber(TestNumber.SECOND);
        grades9.setAbilities(abilities9);
        gradesRepository.save(grades9);

// Tenth set of grades
        Abilities abilities10 = new Abilities();
        Set<Skill> skills10 = new HashSet<>();
        skills10.add(new Skill(SkillName.READING, 85.0, 90.0));
        skills10.add(new Skill(SkillName.WRITING, 80.0, 85.0));
        skills10.add(new Skill(SkillName.LISTENING, 88.0, 92.0));
        skills10.add(new Skill(SkillName.USEOFENGLISH, 75.0, 80.0));
        abilities10.setSkills(skills10);

        Speaking speaking10 = new Speaking();
        speaking10.setProductionAndFluencyGrade(5);
        speaking10.setSpokenInteractionGrade(5);
        speaking10.setLanguageRangeGrade(4);
        speaking10.setAccuracyGrade(5);
        speaking10.setLanguageUse(4);

        ClassPerformance classPerformance10 = new ClassPerformance();
        classPerformance10.setPresenceGrade(5);
        classPerformance10.setHomeworkGrade(5);
        classPerformance10.setParticipationGrade(5);
        classPerformance10.setBehaviorGrade(5);

        abilities10.setSpeaking(speaking10);
        abilities10.setClassPerformance(classPerformance10);

        Grades grades10 = new Grades();
        grades10.setStudentId(2L);
        grades10.setCourseId(1L);
        grades10.setTestNumber(TestNumber.SECOND);
        grades10.setAbilities(abilities10);
        gradesRepository.save(grades10);

// Eleventh set of grades
        Abilities abilities11 = new Abilities();
        Set<Skill> skills11 = new HashSet<>();
        skills11.add(new Skill(SkillName.READING, 65.0, 70.0));
        skills11.add(new Skill(SkillName.WRITING, 60.0, 65.0));
        skills11.add(new Skill(SkillName.LISTENING, 68.0, 72.0));
        skills11.add(new Skill(SkillName.USEOFENGLISH, 55.0, 60.0));
        abilities11.setSkills(skills11);

        Speaking speaking11 = new Speaking();
        speaking11.setProductionAndFluencyGrade(3);
        speaking11.setSpokenInteractionGrade(3);
        speaking11.setLanguageRangeGrade(2);
        speaking11.setAccuracyGrade(3);
        speaking11.setLanguageUse(2);

        ClassPerformance classPerformance11 = new ClassPerformance();
        classPerformance11.setPresenceGrade(4);
        classPerformance11.setHomeworkGrade(3);
        classPerformance11.setParticipationGrade(4);
        classPerformance11.setBehaviorGrade(5);

        abilities11.setSpeaking(speaking11);
        abilities11.setClassPerformance(classPerformance11);

        Grades grades11 = new Grades();
        grades11.setStudentId(3L);
        grades11.setCourseId(1L);
        grades11.setTestNumber(TestNumber.SECOND);
        grades11.setAbilities(abilities11);
        gradesRepository.save(grades11);

// Twelfth set of grades
        Abilities abilities12 = new Abilities();
        Set<Skill> skills12 = new HashSet<>();
        skills12.add(new Skill(SkillName.READING, 78.0, 85.0));
        skills12.add(new Skill(SkillName.WRITING, 72.0, 78.0));
        skills12.add(new Skill(SkillName.LISTENING, 75.0, 82.0));
        skills12.add(new Skill(SkillName.USEOFENGLISH, 70.0, 75.0));
        abilities12.setSkills(skills12);

        Speaking speaking12 = new Speaking();
        speaking12.setProductionAndFluencyGrade(4);
        speaking12.setSpokenInteractionGrade(4);
        speaking12.setLanguageRangeGrade(3);
        speaking12.setAccuracyGrade(4);
        speaking12.setLanguageUse(3);

        ClassPerformance classPerformance12 = new ClassPerformance();
        classPerformance12.setPresenceGrade(4);
        classPerformance12.setHomeworkGrade(4);
        classPerformance12.setParticipationGrade(4);
        classPerformance12.setBehaviorGrade(4);

        abilities12.setSpeaking(speaking12);
        abilities12.setClassPerformance(classPerformance12);

        Grades grades12 = new Grades();
        grades12.setStudentId(4L);
        grades12.setCourseId(1L);
        grades12.setTestNumber(TestNumber.SECOND);
        grades12.setAbilities(abilities12);
        gradesRepository.save(grades12);

// Thirteenth set of grades
        Abilities abilities13 = new Abilities();
        Set<Skill> skills13 = new HashSet<>();
        skills13.add(new Skill(SkillName.READING, 82.0, 88.0));
        skills13.add(new Skill(SkillName.WRITING, 78.0, 84.0));
        skills13.add(new Skill(SkillName.LISTENING, 80.0, 85.0));
        skills13.add(new Skill(SkillName.USEOFENGLISH, 75.0, 80.0));
        abilities13.setSkills(skills13);

        Speaking speaking13 = new Speaking();
        speaking13.setProductionAndFluencyGrade(5);
        speaking13.setSpokenInteractionGrade(5);
        speaking13.setLanguageRangeGrade(4);
        speaking13.setAccuracyGrade(5);
        speaking13.setLanguageUse(4);

        ClassPerformance classPerformance13 = new ClassPerformance();
        classPerformance13.setPresenceGrade(5);
        classPerformance13.setHomeworkGrade(5);
        classPerformance13.setParticipationGrade(5);
        classPerformance13.setBehaviorGrade(5);

        abilities13.setSpeaking(speaking13);
        abilities13.setClassPerformance(classPerformance13);

        Grades grades13 = new Grades();
        grades13.setStudentId(5L);
        grades13.setCourseId(1L);
        grades13.setTestNumber(TestNumber.SECOND);
        grades13.setAbilities(abilities13);
        gradesRepository.save(grades13);

// Fourteenth set of grades
        Abilities abilities14 = new Abilities();
        Set<Skill> skills14 = new HashSet<>();
        skills14.add(new Skill(SkillName.READING, 72.0, 78.0));
        skills14.add(new Skill(SkillName.WRITING, 68.0, 75.0));
        skills14.add(new Skill(SkillName.LISTENING, 70.0, 75.0));
        skills14.add(new Skill(SkillName.USEOFENGLISH, 65.0, 70.0));
        abilities14.setSkills(skills14);

        Speaking speaking14 = new Speaking();
        speaking14.setProductionAndFluencyGrade(4);
        speaking14.setSpokenInteractionGrade(4);
        speaking14.setLanguageRangeGrade(3);
        speaking14.setAccuracyGrade(4);
        speaking14.setLanguageUse(3);

        ClassPerformance classPerformance14 = new ClassPerformance();
        classPerformance14.setPresenceGrade(4);
        classPerformance14.setHomeworkGrade(3);
        classPerformance14.setParticipationGrade(4);
        classPerformance14.setBehaviorGrade(5);

        abilities14.setSpeaking(speaking14);
        abilities14.setClassPerformance(classPerformance14);

        Grades grades14 = new Grades();
        grades14.setStudentId(6L);
        grades14.setCourseId(1L);
        grades14.setTestNumber(TestNumber.SECOND);
        grades14.setAbilities(abilities14);
        gradesRepository.save(grades14);

// Fifteenth set of grades
        Abilities abilities15 = new Abilities();
        Set<Skill> skills15 = new HashSet<>();
        skills15.add(new Skill(SkillName.READING, 78.0, 82.0));
        skills15.add(new Skill(SkillName.WRITING, 72.0, 78.0));
        skills15.add(new Skill(SkillName.LISTENING, 75.0, 80.0));
        skills15.add(new Skill(SkillName.USEOFENGLISH, 70.0, 75.0));
        abilities15.setSkills(skills15);

        Speaking speaking15 = new Speaking();
        speaking15.setProductionAndFluencyGrade(4);
        speaking15.setSpokenInteractionGrade(4);
        speaking15.setLanguageRangeGrade(3);
        speaking15.setAccuracyGrade(4);
        speaking15.setLanguageUse(3);

        ClassPerformance classPerformance15 = new ClassPerformance();
        classPerformance15.setPresenceGrade(3);
        classPerformance15.setHomeworkGrade(3);
        classPerformance15.setParticipationGrade(4);
        classPerformance15.setBehaviorGrade(5);

        abilities15.setSpeaking(speaking15);
        abilities15.setClassPerformance(classPerformance15);

        Grades grades15 = new Grades();
        grades15.setStudentId(7L);
        grades15.setCourseId(1L);
        grades15.setTestNumber(TestNumber.SECOND);
        grades15.setAbilities(abilities15);
        gradesRepository.save(grades15);

// Sixteenth set of grades
        Abilities abilities16 = new Abilities();
        Set<Skill> skills16 = new HashSet<>();
        skills16.add(new Skill(SkillName.READING, 80.0, 85.0));
        skills16.add(new Skill(SkillName.WRITING, 75.0, 80.0));
        skills16.add(new Skill(SkillName.LISTENING, 78.0, 85.0));
        skills16.add(new Skill(SkillName.USEOFENGLISH, 70.0, 75.0));
        abilities16.setSkills(skills16);

        Speaking speaking16 = new Speaking();
        speaking16.setProductionAndFluencyGrade(4);
        speaking16.setSpokenInteractionGrade(4);
        speaking16.setLanguageRangeGrade(3);
        speaking16.setAccuracyGrade(4);
        speaking16.setLanguageUse(3);

        ClassPerformance classPerformance16 = new ClassPerformance();
        classPerformance16.setPresenceGrade(4);
        classPerformance16.setHomeworkGrade(3);
        classPerformance16.setParticipationGrade(4);
        classPerformance16.setBehaviorGrade(5);

        abilities16.setSpeaking(speaking16);
        abilities16.setClassPerformance(classPerformance16);

        Grades grades16 = new Grades();
        grades16.setStudentId(8L);
        grades16.setCourseId(1L);
        grades16.setTestNumber(TestNumber.SECOND);
        grades16.setAbilities(abilities16);
        gradesRepository.save(grades16);


    }
}

