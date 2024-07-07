import {Card, CardBody, CardHeader, CardTitle, Col, Table} from "reactstrap";
import ListHeaders from "./ListHeaders";
import ListBody from "./ListBody";
import {capitalize} from "../../utils/utils";
import CircularProgress from "@mui/material/CircularProgress";

const StripedList = ({itemType, itemList}) => {

    return (
        itemList == null ?
            (
                <Col md="12">
                    <Card>
                        <CircularProgress color={"secondary"}/>
                    </Card>
                </Col>
            ): (
            <Col md="12">
                <Card>
                    <CardHeader>
                        <CardTitle tag="h4">{capitalize(itemType)} Table</CardTitle>
                    </CardHeader>
                    <CardBody>
                        <Table responsive>
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
            </Col>
        )
    )
}

export default StripedList