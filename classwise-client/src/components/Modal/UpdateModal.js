import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import AddExistingCourseModalContent from "./AddExistingCourseModalContent";
import AddExistingStudentModalContent from "./AddExistingStudentModalContent";

const UpdateModal = ({type, onItemSelected}) => {
    const [content, setContent] = useState(null)

    useEffect(() => {
        switch(type){
            case "students":
                setContent(<AddExistingStudentModalContent onItemSelected={onItemSelected}/>)
                break;
            case "courses":
                setContent(<AddExistingCourseModalContent onItemSelected={onItemSelected}/>)
                break;
            default:
                break;
        }
    }, [type]);


    return (
        content == null ?
            (
                <CircularProgress color="secondary"/>
            ) : (
                content
            )
    )
}

export default UpdateModal