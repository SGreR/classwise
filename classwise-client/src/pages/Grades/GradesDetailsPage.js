import {useNavigate, useParams} from "react-router-dom";
import {Alert, Button, Col, Row} from "reactstrap";
import React, {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import InfoCard from "../../components/Cards/InfoCard";
import {deleteGradesById, getGradesById, putGrades} from "../../components/APIService";

const GradesDetailsPage = () => {
    const {id} = useParams()
    const [grades, setGrades] = useState(null)
    const [alert, setAlert] = useState(null)
    const [errorAlert, setErrorAlert] = useState(null)
    const [modified, setModified] = useState(false)
    const [saved, setSaved] = useState(true)
    const [invalidInputs, setInvalidInputs] = useState({
        reading: false,
        listening: false,
        speaking: false,
        writing: false,
        useofenglish: false,
        classPerformance: false,
    });
    const navigate = useNavigate()

    useEffect(() => {
        fetchGrades();
    }, [id]);

    useEffect(() => {
        if(modified && !saved){
            setAlert("This item has been edited, don't forget to save")
        }
    }, [modified, saved]);

    const fetchGrades = () => {
        getGradesById(id)
            .then(response => setGrades(response.data))
    }

    const updateGrades = (grades) => {
        putGrades(id, grades)
            .then(response => {
                setAlert(response.data.message)
                fetchGrades()
            })
            .catch(error => console.error('Error saving grades:', error));
    }

    const deleteGrades = () => {
        deleteGradesById(id)
            .then(response => {
                setAlert(response.data.message)
            })
            .catch(error => console.error('Error saving grades:', error));
    }

    const handleSkillChange = (newSkill) => {
        setModified(true)
        setSaved(false)
        setGrades(prevGrades => ({
            ...prevGrades,
            abilities : {
                ...prevGrades.abilities,
                skills: prevGrades.abilities.skills.map(skill =>
                    skill.skillName === newSkill.skillName ? newSkill : skill)
            }
        }))
    }

    const handleSpeakingChange = (newSpeaking) => {
        setModified(true)
        setSaved(false)
        setGrades(prevGrades => ({
            ...prevGrades,
            abilities : {
                ...prevGrades.abilities,
                speaking: newSpeaking
            }
        }))
    }

    const handleClassPerformanceChange = (newClassPerformance) => {
        setModified(true)
        setSaved(false)
        setGrades(prevGrades => ({
            ...prevGrades,
            abilities : {
                ...prevGrades.abilities,
                classPerformance: newClassPerformance
            }
        }))
    }

    const handleItemChange = (newGrades) => {
        setModified(true)
        setSaved(false)
        setGrades(newGrades)
    }

    const handleUpdate = () => {
        setAlert(null)
        setModified(false)
        setSaved(true)
        updateGrades(grades)
    }

    const handleDelete = () => {
        deleteGrades()
        const timeoutId = setTimeout(() => {
            navigate('/grades');
        }, 3000);
        return () => clearTimeout(timeoutId);
    }

    const handleInvalidInput = (isInputInvalid, inputType) => {
        setInvalidInputs(prevState => ({
            ...prevState,
            [inputType]: isInputInvalid
        }));
        if(isInputInvalid) {
            setErrorAlert(`Invalid input!`)
        }
    }

    const isFormInvalid = Object.values(invalidInputs).some(isInvalid => isInvalid);

    return (
        <>
            <div className="content">
                {alert &&
                    <Alert color="info">{alert}</Alert>
                }
                {isFormInvalid &&
                    <Alert color="danger">{errorAlert}</Alert>
                }
                {
                    (modified && !saved) && <Button color="success" size={"sm"} onClick={handleUpdate} disabled={isFormInvalid} >Save</Button>
                }
                {
                    grades === null ?
                        (
                            <CircularProgress color={"secondary"}/>
                        ) : (
                            <>
                                <Row className="d-flex justify-content-center">
                                    <Col md="6">
                                        <InfoCard itemType={"grades"} item={grades} onItemChange={handleItemChange} onDelete={handleDelete} />
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md="6">
                                        <InfoCard item={grades.abilities.skills.find(skill => skill.skillName === "READING")} itemType="abilities" onItemChange={handleSkillChange} onInvalidGrade={handleInvalidInput}/>
                                        <InfoCard item={grades.abilities.skills.find(skill => skill.skillName === "LISTENING")} itemType="abilities" onItemChange={handleSkillChange} onInvalidGrade={handleInvalidInput}/>
                                        <InfoCard item={grades.abilities.speaking} itemType="speaking" onItemChange={handleSpeakingChange} onInvalidGrade={handleInvalidInput}/>

                                    </Col>
                                    <Col md="6">
                                        <InfoCard item={grades.abilities.skills.find(skill => skill.skillName === "WRITING")} itemType="abilities" onItemChange={handleSkillChange} onInvalidGrade={handleInvalidInput}/>
                                        <InfoCard item={grades.abilities.skills.find(skill => skill.skillName === "USEOFENGLISH")} itemType="abilities" onItemChange={handleSkillChange} onInvalidGrade={handleInvalidInput}/>
                                        <InfoCard item={grades.abilities.classPerformance} itemType="class performance" onItemChange={handleClassPerformanceChange} onInvalidGrade={handleInvalidInput}/>
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
