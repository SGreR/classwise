import {Input, InputGroup} from "reactstrap";
import {useEffect, useState} from "react";

const AddNewStudentModalContent = ({onItemChange}) => {
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
                <Input onChange={(event) => handleTextChange(event, "studentName")} placeholder="Student Name"/>
            </InputGroup>
        </>
    )
}
export default AddNewStudentModalContent