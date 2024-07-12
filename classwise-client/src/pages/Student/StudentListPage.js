import StripedList from "../../components/List/StripedList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Alert, Card, Col} from "reactstrap";
import {getAllStudents} from "../../components/APIService";

const StudentListPage = () => {
    const [students, setStudents] = useState(null)
    const [alert, setAlert] = useState(null)

    useEffect(() => {
        fetchStudents()
    }, []);

    const fetchStudents = () => {
        getAllStudents()
            .then(response => setStudents(response.data))
    }

    const handleItemSaved = () =>{
        setAlert("Item salvo com sucesso!")
        const timeoutId = setTimeout(() => {
            setAlert(null);
            fetchStudents();
        }, 1500);
        return () => clearTimeout(timeoutId);
    }

    return (
        <>
            <div className="content">
                {alert &&
                    <Alert color="info">{alert}</Alert>
                }
                {
                students == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                <StripedList triggerAlert={handleItemSaved} itemType={"students"} itemList={students}/>
                )}
            </div>
        </>

    );
};

export default StudentListPage;
