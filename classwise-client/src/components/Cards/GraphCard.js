import {Card, CardBody, CardFooter, CardHeader, CardTitle} from "reactstrap";
import { Line } from "react-chartjs-2";
import CircularProgress from "@mui/material/CircularProgress";

const GraphCard = ({grades = null}) => {

    return (
            grades == null ?
            (
                <Card className="card-chart">
                    <CircularProgress color={"secondary"}/>
                </Card>
            ) : (
                <Card className="card-chart">
                    <CardHeader>
                        <CardTitle tag="h5">NASDAQ: AAPL</CardTitle>
                        <p className="card-category">Line Chart with Points</p>
                    </CardHeader>
                    <CardBody>
                        <Line
                            data={grades.data}
                            width={400}
                            height={100}
                        />
                    </CardBody>
                    <CardFooter>
                        <div className="chart-legend">
                            <i className="fa fa-circle text-info" /> Tesla Model S{" "}
                            <i className="fa fa-circle text-warning" /> BMW 5 Series
                        </div>
                        <hr />
                        <div className="card-stats">
                            <i className="fa fa-check" /> Data information certified
                        </div>
                    </CardFooter>
                </Card>
            )
        )

}

export default GraphCard;