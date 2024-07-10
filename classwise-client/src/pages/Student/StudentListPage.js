import StripedList from "../../components/List/StripedList";
import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Card, Col} from "reactstrap";
import axios from "axios";
import {getAllStudents} from "../../components/APIService";

const StudentListPage = () => {
    const[students, setStudents] = useState(null)

    useEffect(() => {
        getAllStudents()
            .then(response => setStudents(response.data))
    }, []);

    return (
        <>
            <div className="content">
                {
                students == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                <StripedList itemType={"students"} itemList={students}/>
                )}
            </div>
        </>

    );
};

export default StudentListPage;
