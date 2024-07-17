import {
    Card,
    CardBody,
    CardHeader,
    CardTitle,
    Col,
    Row,
    Table
} from "reactstrap";
import ListHeaders from "./ListHeaders";
import ListBody from "./ListBody";
import {capitalize} from "../../utils/utils";
import CircularProgress from "@mui/material/CircularProgress";
import ModalCard from "../Modal/ModalCard";

const ItemList = ({mode, itemType, itemList, tempItems, queryMenu, onItemAdded, onSave}) => {

    return (
        itemList === null ?
            (
                <Col md="12">
                    <Card>
                        <CircularProgress color={"secondary"}/>
                    </Card>
                </Col>
            ) : tempItems?.length === 0 && itemList?.length === 0 ? (
                <>
                    <Card>
                        <CardHeader>
                            <Row>
                                <Col>
                                    <CardTitle tag="h4">{capitalize(itemType)} Table</CardTitle>
                                </Col>
                                <Col className="text-right">
                                    <ModalCard onItemAdded={onItemAdded} onSave={onSave} mode={mode} type={itemType}/>
                                </Col>
                            </Row>
                        </CardHeader>
                        <CardBody>
                            <h5>No {capitalize(itemType)} Found</h5>
                        </CardBody>
                    </Card>
                </>
            ) : (
            <>
                <Card>
                    <CardHeader>
                        <Row md="12" className="d-flex align-items-center justify-content-between">
                            <Col md="2">
                                <CardTitle tag="h4">{capitalize(itemType)} Table</CardTitle>
                            </Col>
                            <Col md="8">
                                {queryMenu && queryMenu}
                            </Col>
                            <Col className="text-right" md="2">
                                <ModalCard onItemAdded={onItemAdded} onSave={onSave} mode={mode} type={itemType}/>
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
                                <ListBody tempItems={tempItems} itemType={itemType} itemList={itemList}/>
                            </tbody>
                        </Table>
                    </CardBody>

                </Card>
            </>
        )
    )
}

export default ItemList