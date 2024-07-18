import {Input, InputGroup} from "reactstrap";
import {useEffect, useState} from "react";

const AddNewTeacherModalContent = ({onItemChange}) => {
    const [teacher, setTeacher] = useState({
        "teacherName": ""
    })

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setTeacher((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({...teacher, [fieldName]: value});
    }

    return(
        <>
            <InputGroup>
                <Input onChange={(event) => handleTextChange(event, "teacherName")} placeholder="Teacher Name"/>
            </InputGroup>
        </>
    )
}
export default AddNewTeacherModalContent