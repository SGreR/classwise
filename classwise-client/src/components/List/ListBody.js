import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

const ListBody = ({itemType, itemList}) => {
    const[listBody, setListBody] = useState(null)
    const navigate = useNavigate();

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

    const handleRowClick = (path, id) => {
        navigate(`${path}/${id}`);
    };

    const buildStudentList = () => {
        setListBody(
            itemList.map(item => {
                return (
                    <tr key={item.studentId} onClick={() => handleRowClick('/students', item.studentId)}>
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
                    <tr key={item.teacherId} onClick={() => handleRowClick('/teachers', item.teacherId)}>
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
                    <tr key={item.semesterId} onClick={() => handleRowClick('/semesters', item.semesterId)}>
                        <td>{item.semesterId}</td>
                        <td>{item.schoolYear}-{item.semesterNumber}</td>
                    </tr>
                )
            })
        )
    }

    const buildCourseList = () => {
        setListBody(
            itemList.map(item => {
                return (
                    <tr key={item.courseId} onClick={() => handleRowClick('/courses', item.courseId)}>
                        <td>{item.courseId}</td>
                        <td>{item.courseName}</td>
                        <td>{item.semester !== null ? `${item.semester.schoolYear}-${item.semester.semesterNumber}` : "Unassigned"}</td>
                        <td>{item.teacher !== null ? item.teacher.teacherName : "Unassigned"}</td>
                        <td>{item.active ? "Active" : "Inactive"}</td>
                    </tr>
                )
            })
        )
    }

    const buildGradesList = () => {
        setListBody(itemList.map((item) => {
            return (
                <tr key={item.gradesId} onClick={() => handleRowClick('/grades', item.gradesId)}>
                    <td>{item.gradesId}</td>
                    <td>{item.student ? item.student.studentName : "Unassigned"}</td>
                    <td>{item.course ? item.course.courseName : "Unassigned"}</td>
                    <td>{item.course && item.course.semester ? `${item.course.semester.schoolYear}-${item.course.semester.semesterNumber}` : "Unassigned"}</td>
                    <td>{item.testNumber}</td>
                    <td>{Math.round(item.abilities.skills.find(skill => skill.skillName === 'READING')?.averageGrade) || 0}%</td>
                    <td>{Math.round(item.abilities.skills.find(skill => skill.skillName === 'WRITING')?.averageGrade) || 0}%</td>
                    <td>{Math.round(item.abilities.skills.find(skill => skill.skillName === 'LISTENING')?.averageGrade) || 0}%</td>
                    <td>{Math.round(item.abilities.skills.find(skill => skill.skillName === 'USEOFENGLISH')?.averageGrade) || 0}%</td>
                    <td>{Math.round(item.abilities.speaking.averageGrade)}%</td>
                    <td>{Math.round(item.abilities.classPerformance.averageGrade)}%</td>
                    <td>{Math.round(item.abilities.finalGrade)}%</td>
                </tr>
            )

        }))
    }

    return (listBody)
}

export default ListBody;