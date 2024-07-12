import {Button, Card, CardBody, CardHeader, CardTitle, Col, Row, Table} from "reactstrap";
import ListHeaders from "./ListHeaders";
import ListBody from "./ListBody";
import {capitalize} from "../../utils/utils";
import CircularProgress from "@mui/material/CircularProgress";

const StripedList = ({itemType, itemList}) => {

    return (
        itemList === null ?
            (
                <Col md="12">
                    <Card>
                        <CircularProgress color={"secondary"}/>
                    </Card>
                </Col>
            ) : itemList.length === 0 ? (
                <>
                    <Card>
                        <CardHeader>
                            <Row>
                                <Col>
                                    <CardTitle tag="h4">{capitalize(itemType)} Table</CardTitle>
                                </Col>
                                <Col className="text-right">
                                    <Button color="primary">+ Add {itemType}</Button>
                                </Col>
                            </Row>
                        </CardHeader>
                        <CardBody>
                            <h5>No Items found</h5>
                        </CardBody>
                    </Card>
                </>
            ) : (
            <>
                <Card>
                    <CardHeader>
                        <Row>
                            <Col>
                                <CardTitle tag="h4">{capitalize(itemType)} Table</CardTitle>
                            </Col>
                            <Col className="text-right">
                                <Button color="primary">+ Add {itemType}</Button>
                            </Col>
                        </Row>
                    </CardHeader>
                    <CardBody>
                        <Table striped={true} responsive>
                            <thead className="text-primary">
                            <tr>
                                <ListHeaders itemType={itemType}/>
                            </tr>
                            </thead>
                            <tbody>
                                <ListBody itemType={itemType} itemList={itemList}/>
                            </tbody>
                        </Table>
                    </CardBody>

                </Card>
            </>
        )
    )
}

export default StripedList