import { useEffect, useState } from "react";
import {Button, Col, Input, InputGroup, InputGroupText, Label, Row} from "reactstrap";
import { Switch } from "@mui/material";

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
                    checked: true,
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
    }

    const handleInputChange = (index, value) => {
        const newFields = [...queryFields];
        newFields[index].value = value;
        setQueryFields(newFields);
        onUpdateQueryFields(newFields);
    };

    const handleClear = () => {

    }

    return (
        <Row className="align-items-baseline">
            {queryFields.map((field, index) => (
                <Col key={index}>
                    <InputGroup>
                        <InputGroupText>
                            {fieldLabels[index]}
                        </InputGroupText>
                        <Input
                            type="text"
                            value={field.value}
                            disabled={!field.checked}
                            onChange={(e) => handleInputChange(index, e.target.value)}
                            placeholder={`Enter ${fieldLabels[index]}`}
                        />
                    </InputGroup>
                </Col>
                ))}
                <Col>
                    <Button onClick={handleClear}>Clear Filters</Button>
                </Col>
        </Row>
    );
};

export default QueryMenu;
