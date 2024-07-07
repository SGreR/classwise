import {useParams} from "react-router-dom";

const GradesDetailsPage = () => {
    const {id} = useParams()
    return (
        <>
            <div className="content">
                <h2>Welcome to the Grades Details Page</h2>
                <h3>Grades id: {id}</h3>
                <p>This is the grades details page content. Feel free to add more information here.</p>
            </div>
        </>

    );
};

export default GradesDetailsPage;
