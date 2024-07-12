import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import StripedList from "../../components/List/StripedList";
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

    const updateTeacher = (course) => {
        putTeacher(id, course)
            .then(response => {
                setAlert(response.data.message)
                fetchTeacher()
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
        updateTeacher(teacher)
    }

    const handleDelete = () => {
        //open modal here for confirmation later on
        deleteTeacher()
        const timeoutId = setTimeout(() => {
            navigate('/teachers');
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
                        teacher == null ?
                        (
                            <CircularProgress color={"secondary"}/>
                        ) : (
                            <Row>
                                <Col md="4">
                                    <InfoCard itemType={"teachers"} item={teacher} onItemChange={handleItemChange} onDelete={handleDelete}/>
                                </Col>
                                <Col md="8">
                                    <StripedList itemType={"courses"} itemList={teacher.courses}/>
                                    <ChartsCard/>
                                </Col>
                            </Row>
                        )
                    }
                </div>
            </>
    );
};

export default TeacherDetailsPage;
