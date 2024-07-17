import ItemList from "../../components/List/ItemList";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import {Alert, Card, Col} from "reactstrap";
import {getAllCourses, postCourse} from "../../components/APIService";
import QueryMenu from "../../components/Query/QueryMenu";

const CourseListPage = () => {
    const [courses, setCourses] = useState(null)
    const [alert, setAlert] = useState(null)
    const [filter, setFilter] = useState({
        "filters": {
            "byName": null,
            "byYear": null,
            "bySemester": null,
            "byTeacher": null,
        },
        "inclusions": {
            "includeStudents": false,
            "includeTeacher": true,
            "includeSemester": true,
            "includeGrades": false
        }
    })

    useEffect(() => {
        fetchCourses()
    }, [filter]);

    const fetchCourses = () => {
        getAllCourses(filter.filters, filter.inclusions)
            .then(response => setCourses(response.data))
    }

    const handleSave = (course) =>{
        postCourse(course)
            .then(response => {if(response.status === 201) {
                setAlert("Item salvo com sucesso!")
            }});
        const timeoutId = setTimeout(() => {
            setAlert(null);
            fetchCourses();
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
                {courses == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                <ItemList mode="add" queryMenu={<QueryMenu filter={filter.filters} itemType="courses" onUpdateQueryFields={handleUpdateQueryFields}/>} onSave={handleSave} itemType={"courses"} itemList={courses}/>
                )}
            </div>
        </>

    );
};

export default CourseListPage;
