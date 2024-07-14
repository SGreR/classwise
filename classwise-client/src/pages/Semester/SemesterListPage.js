import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Alert, Card, Col} from "reactstrap";
import axios from "axios";
import {getAllSemesters, postSemester} from "../../components/APIService";

const SemesterListPage = () => {
    const [semesters, setSemesters] = useState(null)
    const [alert, setAlert] = useState(null)

    useEffect(() => {
        fetchSemesters()
    }, []);

    const fetchSemesters = () => {
        getAllSemesters()
            .then(response => setSemesters(response.data))
    }

    const handleSave = (semester) =>{
        postSemester(semester)
            .then(response => {if(response.status === 201) {
                setAlert("Item salvo com sucesso!")
            }});
        const timeoutId = setTimeout(() => {
            setAlert(null);
            fetchSemesters();
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
                    semesters == null ?
                        (
                            <Col md="12">
                                <Card>
                                    <CircularProgress size={"50px"} color={"secondary"}/>
                                </Card>
                            </Col>
                        ) : (
                            <ItemList mode="add" onSave={handleSave} itemType={"semesters"} itemList={semesters}/>
                        )}
            </div>
        </>

    );
};

export default SemesterListPage;
