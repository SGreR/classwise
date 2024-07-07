import StripedList from "../../components/List/StripedList";
import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Card, Col} from "reactstrap";
import axios from 'axios';

const CourseListPage = () => {
    const[courses, setCourses] = useState(null)

    useEffect(() => {
        axios({
            method: 'get',
            url: 'http://localhost:8080/classwise/courses',
            auth: {
                username: "admin",
                password: "admin"
            },
            headers: {
                'Include-Semester' : 'true',
                'Include-Teacher' : 'true'
            }

        }).then(response => setCourses(response.data))
    }, []);

    return (
        <>
            <div className="content">
                {
                courses == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                <StripedList itemType={"courses"} itemList={courses}/>
                )}
            </div>
        </>

    );
};

export default CourseListPage;
