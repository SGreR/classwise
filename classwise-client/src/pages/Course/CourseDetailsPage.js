import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import StripedList from "../../components/List/StripedList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";
import {deleteCourseById, getCourseById, putCourse} from "../../components/APIService";
import ChartsCard from "../../components/Cards/ChartsCard";

const CourseDetailsPage = () => {
    const {id} = useParams()
    const[course, setCourse] = useState(null);
    const [alert, setAlert] = useState(null)
    const [modified, setModified] = useState(false)
    const [saved, setSaved] = useState(true)
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

    const handleSave = () => {
        setAlert(null)
        setModified(false)
        setSaved(true)
        updateCourse(course)
    }

    const handleDelete = () => {
        //open modal here for confirmation later on
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
                    (modified && !saved) && <Button color="success" size={"sm"} onClick={handleSave}>Save</Button>
                }
                {
                    course == null ?
                (
                    <CircularProgress color={"secondary"}/>
                ) : (
                    <Col>
                        <Col md="5">
                            <InfoCard itemType={"courses"} item={course} onItemChange={handleItemChange} onDelete={handleDelete}/>
                        </Col>
                        <StripedList itemType={"students"} itemList={course.students}/>
                        <ChartsCard/>
                    </Col>
                )
                }
            </div>
        </>
    );
}

export default CourseDetailsPage;
