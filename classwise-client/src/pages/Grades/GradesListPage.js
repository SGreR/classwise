import {Alert, Card, CardBody, CardHeader, CardTitle, Col, Table} from "reactstrap";
import {useEffect, useState} from "react";
import ItemList from "../../components/List/ItemList";
import CircularProgress from '@mui/material/CircularProgress';
import {getAllGrades, postCourse, postGrades} from "../../components/APIService";
import QueryMenu from "../../components/Query/QueryMenu";


const GradesListPage = () => {
    const [grades, setGrades] = useState(null)
    const [alert, setAlert] = useState(null)
    const [filter, setFilter] = useState({
        "filters": {
            "byStudent": null,
            "byCourse": null,
            "bySemester": null,
            "byYear": null,
            "byTeacher": null,
        },
        "inclusions": {
            "includeStudent": true,
            "includeCourse": true,
        }
    })

    useEffect(() => {
        if(grades){
            setAlert(<><CircularProgress color="secondary"/> Loading grades...</>)
        }
        fetchGrades()
    }, [filter]);

    const fetchGrades = () => {
        getAllGrades(filter.filters, filter.inclusions)
            .then(response => {
                setGrades(response.data)
                setAlert(null)
            })
    }

    const handleSave = (grades) =>{
        postGrades(grades)
            .then(response => {if(response.status === 201) {
                setAlert("Item salvo com sucesso!")
            }});
        const timeoutId = setTimeout(() => {
            setAlert(null);
            fetchGrades();
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
                grades == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                    <ItemList mode="add" queryMenu={<QueryMenu filter={filter.filters} itemType="grades" onUpdateQueryFields={handleUpdateQueryFields}/> } onSave={handleSave} itemType={"grades"} itemList={grades}/>
                    )}
            </div>
        </>

    );
};

export default GradesListPage;
