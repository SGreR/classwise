import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import AddNewStudentModalContent from "./AddNewStudentModalContent";
import AddNewCourseModalContent from "./AddNewCourseModalContent";
import AddNewSemesterModalContent from "./AddNewSemesterModalContent";
import AddNewTeacherModalContent from "./AddNewTeacherModalContent";
import AddNewGradesModalContent from "./AddNewGradesModalContent";

const AddModal = ({type, onItemChange}) => {
    const [content, setContent] = useState(null)

    useEffect(() => {
        switch(type){
            case "students":
                setContent(<AddNewStudentModalContent onItemChange={onItemChange}/>)
                break;
            case "courses":
                setContent(<AddNewCourseModalContent onItemChange={onItemChange}/>)
                break;
            case "grades":
                setContent(<AddNewGradesModalContent onItemChange={onItemChange}/>)
                break
            case "teachers":
                setContent(<AddNewTeacherModalContent onItemChange={onItemChange}/>)
                break
            case "semesters":
                setContent(<AddNewSemesterModalContent onItemChange={onItemChange}/>)
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