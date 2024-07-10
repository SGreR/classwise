import StripedList from "../../components/List/StripedList";
import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Card, Col} from "reactstrap";
import axios from "axios";
import {getAllTeachers} from "../../components/APIService";

const TeacherListPage = () => {
    const[teachers, setTeachers] = useState(null)

    useEffect(() => {
        getAllTeachers()
            .then(response => setTeachers(response.data))
    }, []);

    return (
        <>
            <div className="content">
                {
                teachers == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                <StripedList itemType={"teachers"} itemList={teachers}/>
                )}
            </div>
        </>

    );
};

export default TeacherListPage;
