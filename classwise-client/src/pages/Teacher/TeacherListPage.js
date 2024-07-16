import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Alert, Card, Col} from "reactstrap";
import {getAllTeachers, postTeacher} from "../../components/APIService";

const TeacherListPage = () => {
    const[teachers, setTeachers] = useState(null)
    const [alert, setAlert] = useState(null)

    useEffect(() => {
        fetchTeachers()
    }, []);

    const fetchTeachers = () => {
        getAllTeachers()
            .then(response => setTeachers(response.data))
    }

    const handleSave = (teacher) =>{
        postTeacher(teacher)
            .then(response => {if(response.status === 201) {
                setAlert("Item salvo com sucesso!")
            }});
        const timeoutId = setTimeout(() => {
            setAlert(null);
            fetchTeachers();
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
                teachers == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                <ItemList mode="add" onSave={handleSave} itemType={"teachers"} itemList={teachers}/>
                )}
            </div>
        </>

    );
};

export default TeacherListPage;
