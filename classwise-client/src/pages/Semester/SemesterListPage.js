import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Alert, Card, Col} from "reactstrap";
import {getAllSemesters, postSemester} from "../../components/APIService";
import QueryMenu from "../../components/Query/QueryMenu";

const SemesterListPage = () => {
    const [semesters, setSemesters] = useState(null)
    const [alert, setAlert] = useState(null)
    const [filter, setFilter] = useState({
        "bySchoolYear": null,
        "bySemesterNumber": null
    })

    useEffect(() => {
        fetchSemesters()
    }, [filter]);

    const fetchSemesters = () => {
        getAllSemesters(filter)
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

    const updateFilter = (newFilter) => {
        setFilter(prevFilter => ({
            ...prevFilter,
            ...newFilter,
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
                    semesters == null ?
                        (
                            <Col md="12">
                                <Card>
                                    <CircularProgress size={"50px"} color={"secondary"}/>
                                </Card>
                            </Col>
                        ) : (
                            <ItemList mode="add" queryMenu={<QueryMenu itemType={"semesters"} filter={filter} onUpdateQueryFields={handleUpdateQueryFields} />} onSave={handleSave} itemType={"semesters"} itemList={semesters} onUpdateQueryFields={updateFilter}/>
                        )}
            </div>
        </>

    );
};

export default SemesterListPage;
