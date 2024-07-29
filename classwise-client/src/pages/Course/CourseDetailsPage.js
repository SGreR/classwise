import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {deleteCourseById, getCourseById, putCourse} from "../../components/APIService";
import ChartsCard from "../../components/Cards/ChartsCard";

const CourseDetailsPage = () => {
    const {id} = useParams()
    const [course, setCourse] = useState(null);
    const [alert, setAlert] = useState(null)
    const [modified, setModified] = useState(false)
    const [saved, setSaved] = useState(true)
    const [tempStudents, setTempStudents] = useState([])
    const navigate = useNavigate()

    useEffect(() => {
        fetchCourse();
    }, []);

    useEffect(() => {
        if(modified && !saved){
            setAlert("This item has been edited, don't forget to save")
        }
    }, [modified, saved]);

    const fetchCourse = () => {
        getCourseById(id)
            .then(response => setCourse(response.data))
    }

    const updateCourse = (course) => {
        putCourse(id, course)
            .then(response => {
                setAlert(response.data.message)
                setTempStudents([])
                fetchCourse()
            })
            .catch(error => console.error('Error saving student:', error));
    }

    const deleteCourse = () => {
        deleteCourseById(id)
            .then(response => {
                setAlert(response.data.message)
            })
            .catch(error => console.error('Error saving student:', error));
    }

    const handleItemChange = (newCourse) => {
        setModified(true)
        setSaved(false)
        setCourse(newCourse)
    }

    const setTemporaryAlert = (alertMessage) => {
        setAlert(alertMessage)
        const timeoutId = setTimeout(() => {
            setAlert(null)
        }, 3000);
        return () => clearTimeout(timeoutId);
    }

    const handleAddStudent = (student) => {
        let alertMessage = null;
        if(student === null) {
            alertMessage = "Error receiving student."
        }else if(course.studentIds.includes(student.studentId)){
            alertMessage = "This student already belongs to this course."
        }else if(tempStudents.find(tempStudent => tempStudent.studentId === student.studentId)) {
            alertMessage = "This student has already been added. Awaiting save."
        } else {
            const updatedTempStudents = [...tempStudents, student]
            setTempStudents(updatedTempStudents)
            setModified(true)
            setSaved(false)
        }
        setTemporaryAlert(alertMessage)
        const timeoutId = setTimeout(() => {
            setTemporaryAlert(null);
        }, 3000);
        return () => clearTimeout(timeoutId);
    }

    const handleUpdate = () => {
        setAlert(null)
        setModified(false)
        setSaved(true)
        const updatedCourse = {
            ...course,
            studentIds: [...course.studentIds, ...tempStudents.map(student => student.studentId)],
        };
        setCourse(updatedCourse);
        updateCourse(updatedCourse)
    }

    const handleDelete = () => {
        deleteCourse()
        const timeoutId = setTimeout(() => {
            navigate('/courses');
        }, 3000);
        return () => clearTimeout(timeoutId);
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
                    course == null ?
                (
                    <CircularProgress color={"secondary"}/>
                ) : (
                    <>
                        <Row>
                            <Col md="5">
                                <InfoCard itemType={"courses"} item={course} onItemChange={handleItemChange} onDelete={handleDelete}/>
                            </Col>
                        </Row>
                        <Row>
                            <Col md="12">
                                <ItemList onItemAdded={handleAddStudent} mode="update" itemType={"students"} itemList={course.students} tempItems={tempStudents}/>
                            </Col>
                        </Row>
                        <Row>
                            <Col md="12">
                                <ChartsCard grades={course.grades} chartType={"course"}/>
                            </Col>
                        </Row>
                        <Row>
                            <Col md="12">
                                <ItemList mode="update" itemType={"grades"} itemList={course.grades}/>
                            </Col>
                        </Row>
                    </>



                )
                }
            </div>
        </>
    );
}

export default CourseDetailsPage;
