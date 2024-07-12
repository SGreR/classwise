import StripedList from "../../components/List/StripedList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Alert, Card, Col} from "reactstrap";
import axios from 'axios';
import {getAllCourses} from "../../components/APIService";

const CourseListPage = () => {
    const [courses, setCourses] = useState(null)
    const [alert, setAlert] = useState(null)

    useEffect(() => {
        fetchCourses()
    }, []);

    const fetchCourses = () => {
        getAllCourses()
            .then(response => setCourses(response.data))
    }

    const handleItemSaved = () =>{
        setAlert("Item salvo com sucesso!")
        const timeoutId = setTimeout(() => {
            setAlert(null);
            fetchCourses();
        }, 1500);
        return () => clearTimeout(timeoutId);
    }

    return (
        <>
            <div className="content">
                {alert &&
                    <Alert color="info">{alert}</Alert>
                }
                {courses == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                <StripedList triggerAlert={handleItemSaved} itemType={"courses"} itemList={courses}/>
                )}
            </div>
        </>

    );
};

export default CourseListPage;
