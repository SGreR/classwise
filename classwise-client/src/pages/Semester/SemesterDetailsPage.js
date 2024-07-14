import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";
import {deleteSemesterById, getSemesterById, putCourse, putSemester} from "../../components/APIService";

const SemesterDetailsPage = () => {
    const {id} = useParams()
    const [semester, setSemester] = useState(null)
    const [alert, setAlert] = useState(null)
    const [modified, setModified] = useState(false)
    const [saved, setSaved] = useState(true)
    const [tempCourses, setTempCourses] = useState([])
    const navigate = useNavigate()

    useEffect(() => {
        fetchSemester()
    }, [id]);

    useEffect(() => {
        if(modified && !saved){
            setAlert("This item has been edited, don't forget to save")
        }
    }, [modified, saved]);

    const fetchSemester = () => {
        getSemesterById(id)
            .then(response => {
                const semester = response.data;
                const updatedCourses = semester.courses.map(course => {
                    return {
                        ...course,
                        semester: { ...semester, courses: null }
                    };
                });
                setSemester({ ...semester, courses: updatedCourses });
            }).catch(error => {
            console.error("There was an error fetching the semester data!", error);
        });
    }

    const updateSemester = (semester) => {
        putSemester(id, semester)
            .then(response => {
                setAlert(response.data.message)
                fetchSemester()
                setTempCourses([])
            })
            .catch(error => console.error('Error saving student:', error));
    }

    const deleteSemester = () => {
        deleteSemesterById(id)
            .then(response => {
                setAlert(response.data.message)
            })
            .catch(error => console.error('Error saving student:', error));
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
        }else if(semester.courses.find(currentCourse => currentCourse.courseId === course.courseId)){
            alertMessage = "This course already belongs to this semester."
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

    const handleItemChange = (newSemester) => {
        setModified(true)
        setSaved(false)
        setSemester(newSemester)
    }

    const handleUpdate = () => {
        setAlert(null)
        setModified(false)
        setSaved(true)
        handleUpdateCourses()
        updateSemester(semester)
    }

    const handleDelete = () => {
        deleteSemester()
        const timeoutId = setTimeout(() => {
            navigate('/semesters');
        }, 3000);
        return () => clearTimeout(timeoutId);
    }

    const handleUpdateCourses = () => {
        tempCourses.forEach(course => {
            course.semesterId = semester.semesterId;
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
                    semester == null ?
                    (
                        <CircularProgress color={"secondary"}/>
                    ) : (
                        <Row>
                            <Col md="4">
                                <InfoCard itemType={"semesters"} item={semester} onItemChange={handleItemChange} onDelete={handleDelete}/>
                            </Col>
                            <Col md="8">
                                <ItemList mode="update" itemType={"courses"} itemList={semester.courses} tempItems={tempCourses} onItemAdded={handleAddCourse}/>
                            </Col>
                        </Row>
                    )
                }
            </div>
        </>
    );
};

export default SemesterDetailsPage;
