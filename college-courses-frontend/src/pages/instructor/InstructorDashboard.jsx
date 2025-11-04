import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import "./InstructorDashboard.css";

export default function InstructorDashboard() {
  const [classes, setClasses] = useState([]);

  useEffect(() => {
    // Fetch instructor’s assigned classes from backend (update API as needed)
    fetch("http://172.24.219.181:8080/api/instructor/classes", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setClasses(data))
      .catch((err) => console.error("Error fetching instructor classes:", err));
  }, []);

  return (
    <div className="instructor-dashboard">
      <Sidebar customClass="instructor-sidebar" />
      <div className="instructor-content">
        <h1>Instructor Dashboard</h1>
        <p>Welcome! Here are the classes you are currently teaching.</p>

        {/* Instructor Classes Section */}
        <div className="classes-section">
          <h3>Your Classes</h3>
          {classes.length > 0 ? (
            <table className="classes-table">
              <thead>
                <tr>
                  <th>Course Name</th>
                  <th>Department</th>
                  <th>Semester</th>
                  <th>Credits</th>
                </tr>
              </thead>
              <tbody>
                {classes.map((cls) => (
                  <tr key={cls.courseId}>
                    <td>{cls.name}</td>
                    <td>{cls.departmentName}</td>
                    <td>{cls.semester || "N/A"}</td>
                    <td>{cls.credits}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p>No assigned classes found.</p>
          )}
        </div>
      </div>
    </div>
  );
}
