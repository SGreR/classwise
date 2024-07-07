import {useEffect, useState} from "react";

const ListHeaders = ({itemType}) => {
    const [tableHeaders, setTableHeaders] = useState([])

    useEffect(() => {
        switch (itemType){
            case "grades":
                setTableHeaders(["Id", "Semester", "Parecer", "Reading", "Writing", "Listening", "Use of English", "Speaking", "Class Performance", "Final Grade"])
                break;
            case "students":
                setTableHeaders(["Id", "Name"]);
                break;
            case "teachers":
                setTableHeaders(["Id", "Name"]);
                break;
            case "courses":
                setTableHeaders(["Id", "Course", "Semester", "Teacher", "Active"])
                break;
            case "semesters":
                setTableHeaders(["Id", "Name"]);
                break;
            default:
                break;

        }
    }, [itemType]);

    return (
        tableHeaders.map((column) => {
            return <th>{column}</th>
        })
    )
}

export default ListHeaders