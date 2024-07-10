import StripedList from "../../components/List/StripedList";
import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Card, Col} from "reactstrap";
import axios from 'axios';
import {getAllCourses} from "../../components/APIService";

const CourseListPage = () => {
    const[courses, setCourses] = useState(null)

    useEffect(() => {
        getAllCourses()
            .then(response => setCourses(response.data))
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
