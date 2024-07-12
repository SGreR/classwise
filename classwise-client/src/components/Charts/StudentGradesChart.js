import {useEffect, useState} from "react";
import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    ReferenceLine, ResponsiveContainer,
} from 'recharts';
import {Tab, Tabs} from "@mui/material";
import {capitalize} from "../../utils/utils";



const StudentGradesChart = ({grades:data}) => {
    const [skill, setSkill] = useState('all')
    const [grades, setGrades] = useState(null)

    useEffect(() => {
        setGrades(data);
    }, [data]);


    const gradesToShow = () => {
        if (skill === "final") return <Line type="monotone" name="Final Grade" dataKey={(grade) => Math.round(grade.abilities.finalGrade)} stroke="#8A2BE2" strokeWidth={3}/>
        if (skill === "reading") return <Line type="monotone" name="Reading" dataKey={(grade) => Math.round(grade.abilities.skills.find(skill => skill.skillName === 'READING').averageGrade)} stroke="#4B0082" strokeWidth={3}/>
        if (skill === "writing") return <Line type="monotone" name="Writing" dataKey={(grade) => Math.round(grade.abilities.skills.find(skill => skill.skillName === 'WRITING').averageGrade)} stroke="#0000FF" strokeWidth={3}/>
        if (skill === "listening") return <Line type="monotone" name="Listening" dataKey={(grade) => Math.round(grade.abilities.skills.find(skill => skill.skillName === 'LISTENING').averageGrade)} stroke="#006400" strokeWidth={3}/>
        if (skill === "grammar") return <Line type="monotone" name="Use of English" dataKey={(grade) => Math.round(grade.abilities.skills.find(skill => skill.skillName === 'USEOFENGLISH').averageGrade)} stroke="#CCCC00" strokeWidth={3}/>
        if (skill === "speaking") return <Line type="monotone" name="Speaking" dataKey={(grade) => Math.round(grade.abilities.speaking.averageGrade)} stroke="#FFA500" strokeWidth={3}/>
        if (skill === "classPerformance") return <Line type="monotone" name="Class Performance" dataKey={(grade) => Math.round(grade.abilities.classPerformance.averageGrade)} stroke="#FF0000" strokeWidth={3}/>
        if (skill === "all") {
            return (
                <>
                    <Line type="monotone" name="Final Grade" dataKey={(grade) => Math.round(grade.abilities.finalGrade)} stroke="#8A2BE2" strokeWidth={3}/>
                    <Line type="monotone" name="Reading" dataKey={(grade) => Math.round(grade.abilities.skills.find(skill => skill.skillName === 'READING').averageGrade)} stroke="#4B0082" strokeWidth={3}/>
                    <Line type="monotone" name="Writing" dataKey={(grade) => Math.round(grade.abilities.skills.find(skill => skill.skillName === 'WRITING').averageGrade)} stroke="#0000FF" strokeWidth={3}/>
                    <Line type="monotone" name="Listening" dataKey={(grade) => Math.round(grade.abilities.skills.find(skill => skill.skillName === 'LISTENING').averageGrade)} stroke="#006400" strokeWidth={3}/>
                    <Line type="monotone" name="Use of English" dataKey={(grade) => Math.round(grade.abilities.skills.find(skill => skill.skillName === 'USEOFENGLISH').averageGrade)} stroke="#CCCC00" strokeWidth={3}/>
                    <Line type="monotone" name="Speaking" dataKey={(grade) => Math.round(grade.abilities.speaking.averageGrade)} stroke="#FFA500" strokeWidth={3}/>
                    <Line type="monotone" name="Class Performance" dataKey={(grade) => Math.round(grade.abilities.classPerformance.averageGrade)} stroke="#FF0000" strokeWidth={3}/>
                </>
            )
        }
    }

    const handleChange = (event, newValue) => {
        setSkill(newValue);
    };

    const formatTooltip = (value) => `${value.toFixed(0)}%`;

    return(
        <>

            <Tabs
                centered
                value={skill}
                onChange={handleChange}
                textColor="secondary"
                indicatorColor="secondary"
                aria-label="secondary tabs example"

            >
                <Tab value="all" label="All Grades" />
                <Tab value="final" label="Final Grade" />
                <Tab value="reading" label="Reading" />
                <Tab value="writing" label="Writing" />
                <Tab value="listening" label="Listening" />
                <Tab value="grammar" label="Grammar" />
                <Tab value="speaking" label="Speaking" />
                <Tab value="classPerformance" label="Class Performance" />
            </Tabs>

            <ResponsiveContainer width="90%" height={400}>
                <LineChart
                    data={grades}
                    margin={{
                        top: 20,
                        right: 50,
                        left: 20,
                        bottom: 5,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey={(grade) => {
                        var courseName = grade.course ? grade.course.courseName : "";
                        var semesterInfo = grade.course && grade.course.semester ? ` (${grade.course.semester.schoolYear}-${grade.course.semester.semesterNumber}) ` : ""
                        var testNumber = grade.course ? `${capitalize(grade.testNumber.toLowerCase())} Test` : "";
                        return courseName + semesterInfo + testNumber
                    }} />
                    <YAxis domain={[0, 100]} />
                    <Tooltip formatter={formatTooltip} />
                    <Legend />
                    <ReferenceLine y={60} label={{ value: 'Passing Grade', position: 'insideTop', fill: 'black', fontWeight:'bold' }}  stroke="black" strokeDasharray="3 3" strokeWidth={3}/>
                    {gradesToShow()}
                </LineChart>
            </ResponsiveContainer>

        </>
    )
}

export default StudentGradesChart