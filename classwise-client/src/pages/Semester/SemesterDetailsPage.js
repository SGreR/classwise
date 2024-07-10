import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import StripedList from "../../components/List/StripedList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";
import {deleteSemesterById, getSemesterById, putSemester} from "../../components/APIService";

const SemesterDetailsPage = () => {
    const {id} = useParams()
    const [semester, setSemester] = useState(null)
    const [alert, setAlert] = useState(null)
    const [modified, setModified] = useState(false)
    const [saved, setSaved] = useState(true)
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

    const handleItemChange = (newSemester) => {
        setModified(true)
        setSaved(false)
        setSemester(newSemester)
    }

    const handleSave = () => {
        setAlert(null)
        setModified(false)
        setSaved(true)
        updateSemester(semester)
    }

    const handleDelete = () => {
        //open modal here for confirmation later on
        deleteSemester()
        const timeoutId = setTimeout(() => {
            navigate('/semesters');
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
                    semester == null ?
                    (
                        <CircularProgress color={"secondary"}/>
                    ) : (
                        <Row>
                            <Col md="4">
                                <InfoCard itemType={"semesters"} item={semester} onItemChange={handleItemChange} onDelete={handleDelete}/>
                            </Col>
                            <Col md="8">
                                <StripedList itemType={"courses"} itemList={semester.courses}/>
                            </Col>
                        </Row>
                    )
                }
            </div>
        </>
    );
};

export default SemesterDetailsPage;
