import {useEffect, useState} from "react";
import {
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    ReferenceLine, ResponsiveContainer, PieChart, Pie, Sector, BarChart, Bar, Cell, LabelList,
} from 'recharts';
import {Tab, Tabs} from "@mui/material";
import {Col, Row} from "reactstrap";

const renderActiveShape = (props) => {
    const RADIAN = Math.PI / 180;
    const { cx, cy, midAngle, innerRadius, outerRadius, startAngle, endAngle, fill, payload, percent, value } = props;
    const sin = Math.sin(-RADIAN * midAngle);
    const cos = Math.cos(-RADIAN * midAngle);
    const sx = cx + (outerRadius + 10) * cos;
    const sy = cy + (outerRadius + 10) * sin;
    const mx = cx + (outerRadius + 30) * cos;
    const my = cy + (outerRadius + 30) * sin;
    const ex = mx + (cos >= 0 ? 1 : -1) * 22;
    const ey = my;
    const textAnchor = cos >= 0 ? 'start' : 'end';

    return (
        <g>
            <text x={cx} y={cy} dy={8} textAnchor="middle" fontWeight='bold' fill="black">
                {payload.name}
            </text>
            <Sector
                cx={cx}
                cy={cy}
                innerRadius={innerRadius}
                outerRadius={outerRadius}
                startAngle={startAngle}
                endAngle={endAngle}
                fill={fill}
            />
            <Sector
                cx={cx}
                cy={cy}
                startAngle={startAngle}
                endAngle={endAngle}
                innerRadius={outerRadius + 6}
                outerRadius={outerRadius + 10}
                fill={fill}
            />
            <path d={`M${sx},${sy}L${mx},${my}L${ex},${ey}`} stroke={fill} fill="none" />
            <circle cx={ex} cy={ey} r={2} fill={fill} stroke="none" />
            <text x={ex + (cos >= 0 ? 1 : -1) * 12} y={ey} textAnchor={textAnchor} fill="#333">{`${value}`}</text>
            <text x={ex + (cos >= 0 ? 1 : -1) * 12} y={ey} dy={18} textAnchor={textAnchor} fill="#999">
                {`(Rate ${(percent * 100).toFixed(0)}%)`}
            </text>
        </g>
    );
};

const CourseGradesChart = ({grades:initialData}) => {
    const [grades, setGrades] = useState(null)
    const [chartType, setChartType] = useState("Final Grade")
    const [testNumber, setTestNumber] = useState("First Test")
    const [state, setState] = useState({activeIndex: 0})

    useEffect(() => {
        console.log(initialData)
        if(initialData.length > 0){
            setGrades(filterData(initialData))
        }
    }, [initialData]);

    const filterData = (data) => {

        const skills = {
            "First Test": {
                "Reading": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Writing": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Use of English": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Listening": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Speaking": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Class Performance": {"Fail":0,"Average":0,"Very Good":0,"Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Final Grade": {"Fail":0,"Average":0,"Very Good":0,"Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0}
            },
            "Second Test":{
                "Reading": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Writing": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Use of English": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Listening": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Speaking": {"Fail":0,"Average":0,"Good":0,"Very Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Class Performance": {"Fail":0,"Average":0,"Very Good":0,"Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0},
                "Final Grade": {"Fail":0,"Average":0,"Very Good":0,"Good":0,"Merit":0,"Distinction":0, "Highest":0, "Lowest":0, "Median":0}
            }
        }

        const grades = {
            "First Test": {
                "Reading": [],
                "Writing": [],
                "Use of English": [],
                "Listening": [],
                "Speaking": [],
                "Class Performance": [],
                "Final Grade": []
            },
            "Second Test": {
                "Reading": [],
                "Writing": [],
                "Use of English": [],
                "Listening": [],
                "Speaking": [],
                "Class Performance": [],
                "Final Grade": []
            }
        }

        data.forEach((data) => {
            let testNumber = data.testNumber;
            let gradeMapping = {
                "Reading" : data.abilities.skills.find(skill => skill.skillName === 'READING').averageGrade,
                "Writing" : data.abilities.skills.find(skill => skill.skillName === 'WRITING').averageGrade,
                "Use of English" : data.abilities.skills.find(skill => skill.skillName == 'USEOFENGLISH').averageGrade,
                "Listening" : data.abilities.skills.find(skill => skill.skillName == 'LISTENING').averageGrade,
                "Speaking" : data.abilities.speaking.averageGrade,
                "Class Performance" : data.abilities.classPerformance.averageGrade,
                "Final Grade" : data.abilities.finalGrade
            }


            for(const key in gradeMapping){
                const skillGrade = gradeMapping[key]
                const testNumKey = testNumber === "FIRST" ? "First Test" : "Second Test"
                grades[testNumKey][key].push(skillGrade)
                if (skillGrade >= 95 ){
                    skills[testNumKey][key]["Distinction"]++
                } else if(skillGrade >= 90){
                    skills[testNumKey][key]["Merit"]++
                } else if(skillGrade >= 80){
                    skills[testNumKey][key]["Very Good"]++
                } else if(skillGrade >= 70){
                    skills[testNumKey][key]["Good"]++
                } else if(skillGrade >= 60){
                    skills[testNumKey][key]["Average"]++
                } else {
                    skills[testNumKey][key]["Fail"]++
                }
            }
        })

        for(const testNumKey in grades){
            for(const key in grades[testNumKey]){
                skills[testNumKey][key]["Highest"] = Math.round(Math.max(...grades[testNumKey][key]))
                skills[testNumKey][key]["Lowest"] = Math.round(Math.min(...grades[testNumKey][key]))
                const sum = grades[testNumKey][key].reduce((a, b) => a + b, 0);
                skills[testNumKey][key]["Median"] = Math.round(sum / grades[testNumKey][key].length)
            }
        }
        return skills;
    }

    const handleSkillChange = (event, newValue) => {
            setChartType(newValue);
    };

    const handleTestChange = (event, newValue) => {
        setTestNumber(newValue)
    }

    const onPieEnter = (_, index) => {
        setState({activeIndex: index});
    };

    const COLORS = {
        "Fail": "#ff4c4c",
        "Average": "#FFAC1C",
        "Good": "#ffff00",
        "Very Good": "#4caf50",
        "Merit": "#1E90FF",
        "Distinction": "#9c27b0"
    };

    const pieChart = () => {
        const thresholdCategories = ["Fail", "Average", "Good", "Very Good", "Merit", "Distinction"];

        const data = thresholdCategories.map((threshold) => ({
            name: threshold,
            value: grades[testNumber][chartType][threshold],
            fill: COLORS[threshold]
        }));

        return (
            <PieChart width="100%">
                <Pie
                    activeIndex={state.activeIndex}
                    activeShape={renderActiveShape}
                    data={data}
                    cx="50%"
                    cy="50%"
                    innerRadius={60}
                    outerRadius={100}
                    dataKey="value"
                    onMouseEnter={onPieEnter}
                >{
                    data.map((entry, index) => <Cell key={`cell-${index}`} fill={entry.fill} />)
                }</Pie>
            </PieChart>
        )
    }

    const barChart = () => {
        const barData = [{
            name: chartType,
            Highest: grades[testNumber][chartType]["Highest"],
            Median: grades[testNumber][chartType]["Median"],
            Lowest: grades[testNumber][chartType]["Lowest"]
        }];

        const formatPercentages = (value) => `${value}%`;

        return (
            <BarChart barSize={75} barGap={50} data={barData}>
                <CartesianGrid strokeDasharray="3 3"/>
                <XAxis dataKey="name" fill='black' fontWeight='bold' />
                <YAxis/>
                <Legend/>
                <Tooltip formatter={formatPercentages} />
                <Bar dataKey="Highest" fill="#9c27b0"><LabelList dataKey="Highest" fill='black' fontWeight='bold' position="top" formatter={formatPercentages} /></Bar>
                <Bar dataKey="Median" fill="#ffff00"><LabelList dataKey="Median" fill='black' fontWeight='bold' position="top" formatter={formatPercentages} /></Bar>
                <Bar dataKey="Lowest" fill="#ff4c4c"><LabelList dataKey="Lowest" fill='black' fontWeight='bold' position="top" formatter={formatPercentages}/></Bar>
                <ReferenceLine y={60} label={{ value: 'Passing Grade', position: 'insideTop', fill: 'black', fontWeight:'bold' }}  stroke="black" strokeDasharray="3 3" strokeWidth={3}/>
            </BarChart>
        )
    }

    return(
        <>
            <Row>
                <Col md="12">
                    <Tabs
                        name="testNumber"
                        centered
                        value={testNumber}
                        onChange={handleTestChange}
                        textColor="secondary"
                        indicatorColor="secondary"
                        aria-label="secondary tabs example"
                    >
                        <Tab value="First Test" label="First Test" />
                        <Tab value="Second Test" label="SecondTest" />
                    </Tabs>
                </Col>

            </Row>

                <Row>
                    <Col md='2'>
                        <Tabs
                            centered
                            orientation="vertical"
                            value={chartType}
                            onChange={handleSkillChange}
                            textColor="secondary"
                            indicatorColor="secondary"
                            aria-label="secondary tabs example"
                        >
                            <Tab value="Final Grade" label="Final Grades" />
                            <Tab value="Reading" label="Reading Performance" />
                            <Tab value="Writing" label="Writing Performance" />
                            <Tab value="Listening" label="Listening Performance" />
                            <Tab value="Use of English" label="Use of English Performance" />
                            <Tab value="Speaking" label="Speaking Performance" />
                            <Tab value="Class Performance" label="Class Performance" />
                        </Tabs>
                    </Col>
                    {grades != null ?
                    (
                        <>
                            <Col md="5">
                                <ResponsiveContainer width="100%" >
                                    {pieChart()}
                                </ResponsiveContainer>
                            </Col>
                            <Col md="5">
                                <ResponsiveContainer width="100%" >
                                    {barChart()}
                                </ResponsiveContainer>
                            </Col>
                        </>
                    ) : (
                        <Col className="d-flex justify-content-center align-items-center" md="10">
                            <h1>No Grades to Show</h1>
                        </Col>
                    )
                    }


                </Row>
        </>
    )
}

export default CourseGradesChart