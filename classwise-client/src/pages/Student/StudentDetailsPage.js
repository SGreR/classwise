import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import React, {useEffect, useState} from "react";
import ItemList from "../../components/List/ItemList";
import InfoCard from "components/Cards/InfoCard";
import CircularProgress from "@mui/material/CircularProgress";
import {deleteStudentById, getStudentById, putStudent} from "../../components/APIService";
import ChartsCard from "../../components/Cards/ChartsCard";

const StudentDetailsPage = () => {
    const {id} = useParams()
    const [student, setStudent] = useState(null)
    const [alert, setAlert] = useState(null)
    const [modified, setModified] = useState(false)
    const [saved, setSaved] = useState(true)
    const [tempCourses, setTempCourses] = useState([])
    const navigate = useNavigate()

    useEffect(() => {
        fetchStudent();
    }, []);

    useEffect(() => {
        if(modified && !saved){
            setAlert("This item has been edited, don't forget to save")
        }
    }, [modified, saved]);

    const handleItemChange = (newStudent) => {
        setModified(true)
        setSaved(false)
        setStudent(newStudent)
    }

    const setTemporaryAlert = (alertMessage) => {
        setAlert(alertMessage)
        const timeoutId = setTimeout(() => {
            setAlert(null)
        }, 3000);
        return () => clearTimeout(timeoutId);
    }

    const handleAddCourse = (course) => {
        let alertMessage = null;
        if(course === null) {
            alertMessage = "Error receiving course."
            setTemporaryAlert(alertMessage)
        }else if(student.courseIds.includes(course.courseId)){
            alertMessage = "This student already belongs to this course."
            setTemporaryAlert(alertMessage)
        }else if(tempCourses.find(tempCourse => tempCourse.courseId === course.courseId)) {
            alertMessage = "This course has already been added. Awaiting save."
            setTemporaryAlert(alertMessage)
        } else {
            const updatedTempCourses = [...tempCourses,course]
            setTempCourses(updatedTempCourses)
            setModified(true)
            setSaved(false)
        }
    }

    const handleUpdate = () => {
        setAlert(null)
        setModified(false)
        setSaved(true)
        const updatedStudent = {
            ...student,
            courseIds: [...student.courseIds, ...tempCourses.map(course => course.courseId)],
        };
        setStudent(updatedStudent);
        updateStudent(updatedStudent);
    }

    const handleDelete = () => {
        deleteStudent()
        const timeoutId = setTimeout(() => {
            navigate('/students');
            }, 3000);
        return () => clearTimeout(timeoutId);
    }

    const fetchStudent = () => {
        getStudentById(id)
            .then(response => setStudent(response.data))
            .catch(error => console.error('Error fetching student:', error))
    }

    const updateStudent = (student) => {
        putStudent(id, student)
            .then(response => {
                setAlert(response.data.message)
                setTempCourses([])
                fetchStudent()
            })
            .catch(error => console.error('Error saving student:', error));
        }

    const deleteStudent = () => {
        deleteStudentById(id)
            .then(response => {
                setAlert(response.data.message)
            })
            .catch(error => console.error('Error saving student:', error));
    }

    return (
        <>
            <div className="content">
                {alert &&
                    <Alert color="info">{alert}</Alert>
                }
                {
                    (modified && !saved) && <Button color="success" size={"sm"} onClick={handleUpdate}>Save</Button>
                }
                {
                student == null ?
                (
                    <CircularProgress color={"secondary"}/>
                ) : (
                    <>
                        <Row>
                            <Col md="3">
                                <InfoCard itemType={"students"} item={student} onItemChange={handleItemChange} onDelete={handleDelete}/>
                            </Col>
                            <Col md="9">
                                <ItemList onItemAdded={handleAddCourse} mode="update" itemType={"courses"} itemList={student.courses} tempItems={tempCourses}/>
                            </Col>
                        </Row>
                        <Row>
                            <Col md="12">
                                <ChartsCard grades={student.grades} chartType={"student"}/>
                            </Col>
                        </Row>
                    </>
                )}
            </div>
        </>
    );
};

export default StudentDetailsPage;
