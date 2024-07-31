import React, {useEffect, useState} from "react";
import {getAllCourses, getAllStudents} from "../APIService";
import {
    Alert,
    ButtonDropdown,
    Col,
    DropdownItem,
    DropdownMenu,
    DropdownToggle, FormGroup, Input,
    InputGroup, InputGroupAddon, InputGroupText, Label, Row,
    UncontrolledDropdown
} from "reactstrap";
import {capitalize} from "../../utils/utils";

const AddNewGradesModalContent = ({onItemChange, onInputInvalid}) =>{
    const [errorAlert, setErrorAlert] = useState(null)
    const [invalidInputs, setInvalidInputs] = useState({
        skills: false,
        useofenglish: false,
        classPerformance: false,
    });
    const [students, setStudents] = useState([])
    const [courses, setCourses] = useState([])
    const [courseFilter] = useState({
        "filters": {
            "byName": null,
            "byYear": null,
            "bySemester": null,
            "byTeacher": null,
        },
        "inclusions": {
            "includeStudents": false,
            "includeTeacher": false,
            "includeSemester": true,
            "includeGrades": false
        }
    })
    const[grades, setGrades] = useState({
        "studentId": null,
        "courseId": null,
        "testNumber": null,
        "abilities": {
            "skills":[
                {"skillName":"READING","teacherGrade":0.0,"testGrade":0.0},
                {"skillName":"WRITING","teacherGrade":0.0,"testGrade":0.0},
                {"skillName":"USEOFENGLISH","teacherGrade":0.0,"testGrade":0.0},
                {"skillName":"LISTENING","teacherGrade":0.0,"testGrade":0.0}
            ],
            "speaking":
                {
                    "productionAndFluencyGrade":0,
                    "spokenInteractionGrade":0,
                    "languageRangeGrade":0,
                    "accuracyGrade":0,
                    "languageUse":0
                },
            "classPerformance":
                {
                    "presenceGrade":0,
                    "homeworkGrade":0,
                    "participationGrade":0,
                    "behaviorGrade":0
                }
        }
    })

    useEffect(() => {
        getAllStudents().then(response => setStudents(response.data))
        getAllCourses(courseFilter.filters, courseFilter.inclusions).then(response => setCourses(response.data))
    }, []);

    const handleSelectChange = (value, field) => {
        setGrades({...grades, [field]: value})
        onItemChange({...grades, [field]: value});
    };

    const handleGradeChange = (value, index, category, subField) => {
        const parsedValue = parseFloat(value);
        const isInvalid = isGradeInvalid(value, category);
        const updatedGrades = { ...grades };

        if (subField) {
            if (category === "skills") {
                updatedGrades.abilities.skills[index][subField] = parsedValue;
            } else {
                updatedGrades.abilities[category][subField] = parsedValue;
            }
        } else {
            updatedGrades.abilities[category] = parsedValue;
        }

        handleInvalidInput(isInvalid, category);
        setGrades(updatedGrades);
        onItemChange(updatedGrades);
    };

    const getSelectedStudentText = () => {
        const selectedStudent = students ? students?.find(student => student.studentId === grades.studentId) : null;
        return selectedStudent ? `${selectedStudent.studentName}` : "None";
    }

    const getSelectedCourseText = () => {
        const selectedCourse = courses ? courses?.find(course => course.courseId === grades.courseId) : null;
        return selectedCourse ? buildCourseName(selectedCourse) : "None";
    }

    const buildCourseName = (course) => {
        if(!course.semester){
            return course.courseName
        } else {
            return `${course.courseName} ${course.semester.schoolYear}-${course.semester.semesterNumber}`
        }
    }

    const isGradeInvalid = (grade, category) => {
        const parsedGrade = parseFloat(grade);
        if (category === "speaking" || category === "classPerformance") {
            return isNaN(parsedGrade) || parsedGrade < 0 || parsedGrade > 5;
        }
        return isNaN(parsedGrade) || parsedGrade < 0.0 || parsedGrade > 100.0;
    };

    const handleInvalidInput = (isInputInvalid, inputType) => {
        setInvalidInputs((prevState) => ({
            ...prevState,
            [inputType]: isInputInvalid,
        }));
        onInputInvalid(isInputInvalid);
        if (isInputInvalid) {
            setErrorAlert(`Invalid input in ${capitalize(inputType)}!`);
        } else {
            setErrorAlert(null);
        }
    };

    const isFormInvalid = Object.values(invalidInputs).some((isInvalid) => isInvalid);

    return(
        <Col>
            {isFormInvalid &&
                <Alert color="danger">{errorAlert}</Alert>
            }
            <InputGroup>
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {getSelectedStudentText()}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Students</DropdownItem>
                            <DropdownItem onClick={() => handleSelectChange(null, "studentId")}>None</DropdownItem>
                            {students && students?.map(student => (
                                <DropdownItem
                                    key={student.studentId}
                                    onClick={() => handleSelectChange(student.studentId, "studentId")}
                                >
                                    {student.studentName}
                                </DropdownItem>
                            ))}
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </InputGroup>
            <InputGroup>
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {getSelectedCourseText()}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Semesters</DropdownItem>
                            <DropdownItem onClick={() => handleSelectChange(null, "courseId")}>None</DropdownItem>
                            {courses && courses?.map(course => (
                                <DropdownItem
                                    key={course.courseId}
                                    onClick={() => handleSelectChange(course.courseId, "courseId")}
                                >
                                    {buildCourseName(course)}
                                </DropdownItem>
                            ))}
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </InputGroup>
            <InputGroup>
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {grades.testNumber !== null ? <>Test Number: {capitalize(grades.testNumber.toLowerCase())} Test</> : "Select Test"}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Semester Number</DropdownItem>
                            <DropdownItem key={1} onClick={() => handleSelectChange("FIRST", "testNumber")}>First Test</DropdownItem>
                            <DropdownItem key={2} onClick={() => handleSelectChange("SECOND", "testNumber")}>Second Test</DropdownItem>
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </InputGroup>
            <Row>
                <Col md="6">
                    <h5>Reading</h5>
                    <FormGroup>
                        <Label for="readingTeacherGrade">Reading Teacher Grade</Label>
                        <Input id="readingTeacherGrade" value={grades.abilities.skills[0].teacherGrade}
                               onChange={(e) => handleGradeChange(e.target.value, 0, "skills", "teacherGrade")}
                               invalid={isGradeInvalid(grades.abilities.skills[0].teacherGrade)}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Label for="readingTestGrade">Reading Test Grade</Label>
                        <Input id="readingTestGrade" value={grades.abilities.skills[0].testGrade}
                               onChange={(e) => handleGradeChange(e.target.value, 0, "skills", "testGrade")}
                               invalid={isGradeInvalid(grades.abilities.skills[0].testGrade)}
                        />
                    </FormGroup>
                </Col>
                <Col md="6">
                    <h5>Writing</h5>
                    <FormGroup>
                        <Label for="writingTeacherGrade">Writing Teacher Grade</Label>
                        <Input id="writingTeacherGrade" value={grades.abilities.skills[1].teacherGrade}
                               onChange={(e) => handleGradeChange(e.target.value, 1, "skills", "teacherGrade")}
                               invalid={isGradeInvalid(grades.abilities.skills[1].teacherGrade)}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Label for="writingTestGrade">Writing Test Grade</Label>
                        <Input id="writingTestGrade" value={grades.abilities.skills[1].testGrade}
                               onChange={(e) => handleGradeChange(e.target.value, 1, "skills", "testGrade")}
                               invalid={isGradeInvalid(grades.abilities.skills[1].testGrade)}
                        />
                    </FormGroup>
                </Col>
            </Row>
            <Row>
                <Col md="6">
                    <h5>Use of English</h5>
                    <FormGroup>
                        <Label for="useOfEnglishTeacherGrade">Use of English Teacher Grade</Label>
                        <Input id="useOfEnglishTeacherGrade"
                               value={grades.abilities.skills[2].teacherGrade}
                               onChange={(e) => handleGradeChange(e.target.value, 2, "skills", "teacherGrade")}
                               invalid={isGradeInvalid(grades.abilities.skills[2].teacherGrade)}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Label for="useOfEnglishTestGrade">Use of English Test Grade</Label>
                        <Input id="useOfEnglishTestGrade" value={grades.abilities.skills[2].testGrade}
                               onChange={(e) => handleGradeChange(e.target.value, 2, "skills", "testGrade")}
                               invalid={isGradeInvalid(grades.abilities.skills[2].testGrade)}
                        />
                    </FormGroup>
                </Col>
                <Col md="6">
                    <h5>Listening</h5>
                    <FormGroup>
                        <Label for="listeningTeacherGrade">Listening Teacher Grade</Label>
                        <Input id="listeningTeacherGrade" value={grades.abilities.skills[3].teacherGrade}
                               onChange={(e) => handleGradeChange(e.target.value, 3, "skills", "teacherGrade")}
                               invalid={isGradeInvalid(grades.abilities.skills[3].teacherGrade)}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Label for="listeningTestGrade">Listening Test Grade</Label>
                        <Input id="listeningTestGrade" value={grades.abilities.skills[3].testGrade}
                               onChange={(e) => handleGradeChange(e.target.value, 3, "skills", "testGrade")}
                               invalid={isGradeInvalid(grades.abilities.skills[3].testGrade)}
                        />
                    </FormGroup>
                </Col>
            </Row>
            <Row>
                <Col md="6">
                    <h5>Speaking</h5>
                    {Object.keys(grades.abilities.speaking).map((key) => (
                        <FormGroup key={key}>
                            <Label for={`speaking${key}`}>{capitalize(key)}</Label>
                            <Input id={`speaking${key}`} value={grades.abilities.speaking[key]}
                                   onChange={(e) => handleGradeChange(e.target.value, null, "speaking", key)}
                                   invalid={isGradeInvalid(grades.abilities.speaking[key], "speaking")}
                            />
                        </FormGroup>
                    ))}
                </Col>
                <Col md="6">
                    <h5>Class Performance</h5>
                    {Object.keys(grades.abilities.classPerformance).map((key) => (
                                <FormGroup key={key}>
                                    <Label for={`classPerformance${key}`}>{capitalize(key)}</Label>
                                    <Input id={`classPerformance${key}`} value={grades.abilities.classPerformance[key]} onChange={(e) => handleGradeChange(e.target.value, null, "classPerformance", key)}
                                           invalid={isGradeInvalid(grades.abilities.classPerformance[key], "classPerformance")}
                                    />
                                </FormGroup>
                            ))}
                </Col>
            </Row>
        </Col>
    )

}
export default AddNewGradesModalContent