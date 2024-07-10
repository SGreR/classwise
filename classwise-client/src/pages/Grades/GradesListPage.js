import {Card, CardBody, CardHeader, CardTitle, Col, Table} from "reactstrap";
import {useEffect, useState} from "react";
import StripedList from "../../components/List/StripedList";
import CircularProgress from '@mui/material/CircularProgress';
import {getAllGrades} from "../../components/APIService";


const GradesListPage = () => {
    const[grades, setGrades] = useState(null)

    useEffect(() => {
        getAllGrades()
            .then(response => setGrades(response.data))
    }, []);

    return (
        <>
            <div className="content">
                {
                grades == null ?
                    (
                        <Col md="12">
                            <Card>
                                <CircularProgress size={"50px"} color={"secondary"}/>
                            </Card>
                        </Col>
                    ) : (
                    <StripedList itemType={"grades"} itemList={grades}/>
                    )}
            </div>
        </>

    );
};

export default GradesListPage;
