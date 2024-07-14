import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";
import {
    deleteCourseById,
    deleteTeacherById,
    getCourseById,
    getTeacherById,
    putCourse,
    putTeacher
} from "../../components/APIService";
import ChartsCard from "../../components/Cards/ChartsCard";

const TeacherDetailsPage = () => {
    const {id} = useParams()
    const[teacher, setTeacher] = useState(null)
    const [alert, setAlert] = useState(null)
    const [modified, setModified] = useState(false)
    const [saved, setSaved] = useState(true)
    const [tempCourses, setTempCourses] = useState([])
    const navigate = useNavigate()

    useEffect(() => {
        fetchTeacher()
    }, [id]);

    useEffect(() => {
        if(modified && !saved){
            setAlert("This item has been edited, don't forget to save")
        }
    }, [modified, saved]);

    const fetchTeacher = () => {
        getTeacherById(id)
            .then(response => {
                const teacher = response.data;
                const updatedCourses = teacher.courses.map(course => {
                    return {
                        ...course,
                        teacher: { ...teacher, courses: null }
                    };
                });
                console.log(response.data)
                setTeacher({ ...teacher, courses: updatedCourses });
            }).catch(error => {
            console.error("There was an error fetching the teacher data!", error);
        });
    }

    const updateTeacher = (teacher) => {
        putTeacher(id, teacher)
            .then(response => {
                setAlert(response.data.message)
                fetchTeacher()
                setTempCourses([])
            })
            .catch(error => console.error('Error saving student:', error));
    }

    const deleteTeacher = () => {
        deleteTeacherById(id)
            .then(response => {
                setAlert(response.data.message)
            })
            .catch(error => console.error('Error saving student:', error));
    }

    const handleItemChange = (newTeacher) => {
        setModified(true)
        setSaved(false)
        setTeacher(newTeacher)
    }

    const handleUpdate = () => {
        setAlert(null)
        setModified(false)
        setSaved(true)
        handleUpdateCourses()
        updateTeacher(teacher);
    }

    const handleDelete = () => {
        deleteTeacher()
        const timeoutId = setTimeout(() => {
            navigate('/teachers');
        }, 3000);
        return () => clearTimeout(timeoutId);
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
        }else if(teacher.courses.find(currentCourse => currentCourse.courseId === course.courseId)){
            alertMessage = "This teacher is already assigned to this course."
        }else if(tempCourses.find(tempCourse => tempCourse.courseId === course.courseId)) {
            alertMessage = "This course has already been added. Awaiting save."
        } else {
            const updatedTempCourses = [...tempCourses,course]
            setTempCourses(updatedTempCourses)
            setModified(true)
            setSaved(false)
        }
        setTemporaryAlert(alertMessage)
    }

    const handleUpdateCourses = () => {
        tempCourses.forEach(course => {
            course.teacherId = teacher.teacherId;
            putCourse(course.courseId, course)
                .catch(error => console.error('Error updating course:', error));
        });
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
                        teacher == null ?
                        (
                            <CircularProgress color={"secondary"}/>
                        ) : (
                            <>
                                <Row>
                                    <Col md="4">
                                        <InfoCard itemType={"teachers"} item={teacher} onItemChange={handleItemChange} onDelete={handleDelete}/>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md="8">
                                        <ItemList onItemAdded={handleAddCourse} mode="update" itemType={"courses"} itemList={teacher.courses} tempItems={tempCourses}/>
                                        <ChartsCard/>
                                    </Col>
                                </Row>
                            </>
                        )
                    }
                </div>
            </>
    );
};

export default TeacherDetailsPage;
