import {Card} from "reactstrap";
import CircularProgress from "@mui/material/CircularProgress";
import StudentGradesChart from "../Charts/StudentGradesChart";

const ChartsCard = ({grades}) => {

    return (
        <Card className="text-center">
            {grades == null ?
            (
                    <CircularProgress color={"secondary"}/>
            ) : (
                    <StudentGradesChart grades={grades}/>
            )}
        </Card>
        )

}

export default ChartsCard;