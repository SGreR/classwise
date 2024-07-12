import {Input, InputGroup} from "reactstrap";
import {useEffect, useState} from "react";

const AddStudentContent = ({onItemChange}) => {
    const [student, setStudent] = useState({
        "studentName": ""
    })

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setStudent((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({...student, [fieldName]: value});
    }

    return(
        <>
            <InputGroup>
                <Input onChange={(event) => handleTextChange(event, "studentName")} placeholder="Nome do aluno"/>
            </InputGroup>
        </>
    )
}
export default AddStudentContent