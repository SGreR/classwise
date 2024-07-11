import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import React, {useEffect, useState} from "react";
import StripedList from "components/List/StripedList";
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

    const handleSave = () => {
        setAlert(null)
        setModified(false)
        setSaved(true)
        updateStudent(student)
    }

    const handleDelete = () => {
        //open modal here for confirmation later on
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
                    (modified && !saved) && <Button color="success" size={"sm"} onClick={handleSave}>Save</Button>
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
                                <StripedList itemType={"courses"} itemList={student.courses}/>
                            </Col>
                        </Row>
                        <Row>
                            <Col md="12">
                                <ChartsCard grades={student.grades}/>
                            </Col>
                        </Row>
                    </>
                )}
            </div>
        </>
    );
};

export default StudentDetailsPage;
