import StripedList from "../../components/List/StripedList";
import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Card, Col} from "reactstrap";
import axios from "axios";

const SemesterListPage = () => {
    const[semesters, setSemesters] = useState(null)

    useEffect(() => {
        axios({
            method: 'get',
            url: 'http://localhost:8080/classwise/semesters',
            auth: {
                username: "admin",
                password: "admin"
            },

        }).then(response => setSemesters(response.data))
    }, []);
    return (
        <>
            <div className="content">
                {
                    semesters == null ?
                        (
                            <Col md="12">
                                <Card>
                                    <CircularProgress size={"50px"} color={"secondary"}/>
                                </Card>
                            </Col>
                        ) : (
                            <StripedList itemType={"semesters"} itemList={semesters}/>
                        )}
            </div>
        </>

    );
};

export default SemesterListPage;
