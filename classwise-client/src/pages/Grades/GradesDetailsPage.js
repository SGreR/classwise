import {useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import InfoCard from "../../components/Cards/InfoCard";
import {getGradeById} from "../../components/APIService";
import AbilitiesCard from "../../components/Cards/AbilitiesCard";

const GradesDetailsPage = () => {
    const {id} = useParams()
    const [grades, setGrades] = useState(null)
    const [alert, setAlert] = useState(null)
    const [modified, setModified] = useState(false)
    const [saved, setSaved] = useState(true)

    useEffect(() => {
        fetchGrades();
    }, [id]);

    useEffect(() => {
        console.log(grades)
        grades != null && console.log(grades.abilities.skills.find(skill => skill.skillName === "READING"))
    }, [grades]);

    const fetchGrades = () => {
        getGradeById(id)
            .then(response => setGrades(response.data))
    }

    return (
        <>
            <div className="content">
                {alert &&
                    <Alert color="info">{alert}</Alert>
                }
                {
                    (modified && !saved) && <Button color="success" size={"sm"} >Save</Button>
                }
                {
                    grades === null ?
                        (
                            <CircularProgress color={"secondary"}/>
                        ) : (
                            <>
                                <Row>
                                    <Col md="4">
                                        <InfoCard itemType={"grades"} item={grades} />
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md="4">
                                        <InfoCard item={grades.abilities.skills.find(skill => skill.skillName === "READING")} itemType="abilities"/>
                                        <InfoCard item={grades.abilities.skills.find(skill => skill.skillName === "WRITING")} itemType="abilities"/>
                                    </Col>
                                    <Col md="4">
                                        <InfoCard item={grades.abilities.skills.find(skill => skill.skillName === "LISTENING")} itemType="abilities"/>
                                        <InfoCard item={grades.abilities.skills.find(skill => skill.skillName === "USEOFENGLISH")} itemType="abilities"/>
                                    </Col>
                                    <Col md="4">
                                        <InfoCard item={grades.abilities.speaking} itemType=""/>
                                        <InfoCard item={grades.abilities.classPerformance} itemType=""/>
                                    </Col>
                                </Row>
                            </>

                        )
                }
            </div>
        </>

    );
};

export default GradesDetailsPage;
