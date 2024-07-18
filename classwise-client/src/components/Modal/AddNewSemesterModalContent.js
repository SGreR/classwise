import {useState} from "react";
import {
    ButtonDropdown,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Input,
    InputGroup,
    UncontrolledDropdown
} from "reactstrap";

const AddNewSemesterModalContent = ({onItemChange}) => {
    const [semester, setSemester] = useState({
        "schoolYear": "",
        "semesterNumber": ""
    })

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setSemester((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({...semester, [fieldName]: value});
    }

    const handleSelectChange = (value, field) => {
        setSemester({...semester, [field]: value})
        onItemChange({...semester, [field]: value});
    };

    return(
        <>
            <InputGroup>
                <Input onChange={(event) => handleTextChange(event, "schoolYear")} placeholder="Year"/>
            </InputGroup>
            <InputGroup>
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {semester.semesterNumber !== "" ? <>Semester Number: {semester.semesterNumber}</> : "Select Semester"}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Semester Number</DropdownItem>
                            <DropdownItem key={1} onClick={() => handleSelectChange(1, "semesterNumber")}>First Semester</DropdownItem>
                            <DropdownItem key={2} onClick={() => handleSelectChange(2, "semesterNumber")}>Second Semester</DropdownItem>
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </InputGroup>
        </>
    )
}
export default AddNewSemesterModalContent