import { useEffect, useState } from "react";
import {Button, Col, Input, Label, Row} from "reactstrap";

const QueryMenu = ({itemType, filter, onUpdateQueryFields }) => {
    const [fieldLabels, setFieldLabels] = useState([])
    const [queryFields, setQueryFields] = useState([]);

    useEffect(() => {
        updateQueryFields();
        updateFieldLabels();
    }, [filter, itemType]);

    const updateQueryFields = () => {
        const newQueryFields = [];
        for (const key in filter) {
            if (filter.hasOwnProperty(key)) {
                newQueryFields.push({
                    name: key,
                    value: filter[key]
                });
            }
        }
        setQueryFields(newQueryFields);
    };

    const updateFieldLabels = () => {
        if(itemType === "semesters"){
            setFieldLabels(["Year", "Semester"])
        }
        if(itemType === "courses"){
            setFieldLabels(["Course Name", "Course Year", "Semester Number", "Teacher Name"])
        }
        if(itemType === "students"){
            setFieldLabels(["Student Name"])
        }
    }

    const handleInputChange = (index, value) => {
        const newFields = [...queryFields];
        newFields[index].value = value;
        setQueryFields(newFields);
        onUpdateQueryFields(newFields);
    };

    const handleClear = () => {
        const clearedFields = queryFields.map(field => ({
            ...field,
            value: ""
        }));
        setQueryFields(clearedFields);
        onUpdateQueryFields(clearedFields);
    }

    return (
        <Row className="align-items-end">
            {queryFields.map((field, index) => (
                <Col key={index} >
                        <Label>
                            {fieldLabels[index]}
                        </Label>
                        <Input
                            type="text"
                            value={field.value}
                            onChange={(e) => handleInputChange(index, e.target.value)}
                            placeholder={`Enter ${fieldLabels[index]}`}
                        />
                </Col>
                ))}
                <Col>
                    <Button onClick={handleClear}>Clear Filters</Button>
                </Col>
        </Row>
    );
};

export default QueryMenu;
