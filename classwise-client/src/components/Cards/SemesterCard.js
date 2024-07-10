import {Col, DropdownItem, DropdownMenu, DropdownToggle, Row, UncontrolledDropdown} from "reactstrap";
import {useState} from "react";

const SemesterCard = ({item:initialItem, editing, onItemChange}) => {
    const [item, setItem] = useState(initialItem)

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setItem((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({ ...item, [fieldName]: value });
    }

    const handleSelectChange = (value) => {
            setItem(prevItem => ({
                ...prevItem,
                semesterNumber: value,
            }));
            onItemChange({
                ...item,
                semesterNumber: value
            });
    };

    return (
        <>
            <div className="button-container">
                <Row>
                    <Col>
                        {editing ?
                            (
                                <>
                                    <input type={"text"} value={item.schoolYear}
                                           onChange={(event) => handleTextChange(event, "schoolYear")}/>
                                    <UncontrolledDropdown>
                                        <DropdownToggle caret size="sm">
                                            Select Semester
                                        </DropdownToggle>
                                        <DropdownMenu>
                                            <DropdownItem header>Semesters</DropdownItem>
                                            <DropdownItem onClick={() => handleSelectChange(1)}>1</DropdownItem>
                                            <DropdownItem onClick={() => handleSelectChange(2)}>2</DropdownItem>
                                        </DropdownMenu>
                                    </UncontrolledDropdown>
                                    <h5 className="title">{item.schoolYear}-{item.semesterNumber}</h5>
                                </>
                            ) : (
                                <h5 className="title">{item.schoolYear}-{item.semesterNumber}</h5>
                            )}
                    </Col>
                </Row>
                <hr/>
                <Row>
                    <Col className="ml-auto" lg="3" md="6" xs="6">
                        <h5>
                            <small>Id</small><br/>
                            {item.semesterId}
                        </h5>
                    </Col>
                    <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Courses</small><br/>
                            {item.courses.length}
                        </h5>
                    </Col>
                </Row>
            </div>
        </>
    )
}

export default SemesterCard