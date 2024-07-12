import {Card} from "reactstrap";
import CircularProgress from "@mui/material/CircularProgress";
import StudentGradesChart from "../Charts/StudentGradesChart";
import CourseGradesChart from "../Charts/CourseGradesChart";

const ChartsCard = ({grades, chartType}) => {


    return (
        <Card className="text-center">
            {grades == null ? (
                <CircularProgress color={"secondary"} />
            ) : (
                chartType === "student" ? (
                    <StudentGradesChart grades={grades} />
                ) : (
                    <CourseGradesChart grades={grades} />
                )
            )}
        </Card>
    );

}

export default ChartsCard;