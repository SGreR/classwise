import {useEffect, useState} from "react";

const ListBody = ({itemType, itemList}) => {
    const[listBody, setListBody] = useState(null)

    useEffect(() => {
        switch (itemType){
            case "grades":
                buildGradesList();
                break;
            case "students":
                buildStudentList();
                break;
            case "teachers":
                buildTeacherList();
                break;
            case "courses":
                buildCourseList();
                break;
            case "semesters":
                buildSemesterList();
                break;
            default:
                break;

        }
    }, [itemType, itemList]);

    const buildStudentList = () => {
        setListBody(
            itemList.map(item => {
                return (
                    <tr>
                        <td>{item.studentId}</td>
                        <td>{item.studentName}</td>
                    </tr>
                )
            })
        )
    }

    const buildTeacherList = () => {
        setListBody(
            itemList.map(item => {
                return (
                    <tr>
                        <td>{item.teacherId}</td>
                        <td>{item.teacherName}</td>
                    </tr>
                )
            })
        )
    }

    const buildSemesterList = () => {
        setListBody(
            itemList.map(item => {
                return (
                    <tr>
                        <td>{item.semesterId}</td>
                        <td>{item.schoolYear}-{item.semesterNumber}</td>
                    </tr>
                )
            })
        )
    }

    const buildCourseList = () => {
        console.log(itemList)
        setListBody(
            itemList.map(item => {
                return (
                    <tr>
                        <td>{item.courseId}</td>
                        <td>{item.courseName}</td>
                        <td>{item.semester.schoolYear}-{item.semester.semesterNumber}</td>
                        <td>{item.teacher.teacherName}</td>
                        <td>{item.active ? "Active" : "Inactive"}</td>
                    </tr>
                )
            })
        )
    }

    const buildGradesList = () => {
        setListBody(itemList.map((item) => {
            return (
                <tr>
                    <td>{item.gradesId}</td>
                    <td>{item.course.courseName} {item.course.semester.schoolYear}-{item.course.semester.semesterNumber}</td>
                    <td>{item.testNumber}</td>
                    <td>{item.abilities.skills.find(skill => skill.skillName === 'READING')?.averageGrade || 0}%</td>
                    <td>{item.abilities.skills.find(skill => skill.skillName === 'WRITING')?.averageGrade || 0}%</td>
                    <td>{item.abilities.skills.find(skill => skill.skillName === 'LISTENING')?.averageGrade || 0}%</td>
                    <td>{item.abilities.skills.find(skill => skill.skillName === 'USEOFENGLISH')?.averageGrade || 0}%</td>
                    <td>{item.abilities.speaking.averageGrade}%</td>
                    <td>{item.abilities.classPerformance.averageGrade}%</td>
                    <td>{item.abilities.finalGrade}%</td>
                </tr>
            )

        }))
    }

    return (listBody)
}

export default ListBody;