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
        "filters": {
            "bySchoolYear": null,
            "bySemesterNumber": null
        },
        "inclusions": {
            "includeCourses": false
        }
    })

    useEffect(() => {
        fetchSemesters()
    }, [filter]);

    const fetchSemesters = () => {
        getAllSemesters(filter.filters, filter.inclusions)
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
                    semesters == null ?
                        (
                            <Col md="12">
                                <Card>
                                    <CircularProgress size={"50px"} color={"secondary"}/>
                                </Card>
                            </Col>
                        ) : (
                            <ItemList mode="add" onItemAdded={handleSave} queryMenu={<QueryMenu itemType={"semesters"} filter={filter.filters} onUpdateQueryFields={handleUpdateQueryFields} />} onSave={handleSave} itemType={"semesters"} itemList={semesters} onUpdateQueryFields={updateFilter}/>
                        )}
            </div>
        </>

    );
};

export default SemesterListPage;
