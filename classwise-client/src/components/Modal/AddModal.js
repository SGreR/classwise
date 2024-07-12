import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import AddStudentContent from "./AddStudentContent";
import AddCourseContent from "./AddCourseContent";

const AddModal = ({type, onItemChange}) => {
    const [content, setContent] = useState(null)

    useEffect(() => {
        switch(type){
            case "students":
                setContent(<AddStudentContent onItemChange={onItemChange}/>)
                break;
            case "courses":
                setContent(<AddCourseContent onItemChange={onItemChange}/>)
                break;
            case "grades":
                setContent(<></>)
                break
            case "teachers":
                setContent(<></>)
                break
            case "semesters":
                setContent(<></>)
                break
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

export default AddModal