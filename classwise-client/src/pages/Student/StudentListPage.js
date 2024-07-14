import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Alert, Card, Col} from "reactstrap";
import {getAllStudents, postStudent} from "../../components/APIService";

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

    const handleSave = (student) =>{
        postStudent(student)
            .then(response => {if(response.status === 201) {
                setAlert("Item salvo com sucesso!")
            }});
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
                <ItemList mode="add" onSave={handleSave} itemType={"students"} itemList={students}/>
                )}
            </div>
        </>

    );
};

export default StudentListPage;
