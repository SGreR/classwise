import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Alert, Card, Col} from "reactstrap";
import {getAllStudents, postStudent} from "../../components/APIService";
import QueryMenu from "../../components/Query/QueryMenu";

const StudentListPage = () => {
    const [students, setStudents] = useState(null)
    const [alert, setAlert] = useState(null)
    const [filter, setFilter] = useState({
        "filters":{
            "byName":null
        },
        "inclusions":{
            "includeCourses": false,
            "includeGrades":false
        }
    })

    useEffect(() => {
        fetchStudents()
    }, [filter]);

    const fetchStudents = () => {
        getAllStudents(filter.filters, filter.inclusions)
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

    const updateFilter = (newFilters) => {
        setFilter(prevFilter => ({
            ...prevFilter,
            filters: {
                ...prevFilter.filters,
                ...newFilters
            },
        }));
    };

    const handleUpdateQueryFields = (queryFields) => {
        const newFilter = {};
        queryFields.forEach(field => {
            newFilter[field.name] = field.value;
        });
        updateFilter(newFilter);
    };

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
                <ItemList mode="add" queryMenu={<QueryMenu filter={filter.filters} itemType="students" onUpdateQueryFields={handleUpdateQueryFields}/> } onSave={handleSave} itemType={"students"} itemList={students}/>
                )}
            </div>
        </>

    );
};

export default StudentListPage;
