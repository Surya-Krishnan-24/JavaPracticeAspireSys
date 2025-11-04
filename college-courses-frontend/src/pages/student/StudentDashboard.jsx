import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import "./StudentDashboard.css";

export default function StudentDashboard() {
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    // Fetch enrolled courses from backend (replace URL with your actual API)
    fetch("http://172.24.219.181:8080/api/student/courses", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setCourses(data))
      .catch((err) => console.error("Error fetching courses:", err));
  }, []);

  return (
    <div className="student-dashboard">
      <Sidebar />
      <div className="student-content">
        <h1>Student Dashboard</h1>
        <p>Welcome, Student! Here are your enrolled courses.</p>

        {/* Enrolled Courses */}
        <div className="courses-section">
          <h3>Your Enrolled Courses</h3>
          {courses.length > 0 ? (
            <table className="courses-table">
              <thead>
                <tr>
                  <th>Course Name</th>
                  <th>Department</th>
                  <th>Instructor</th>
                  <th>Credits</th>
                </tr>
              </thead>
              <tbody>
                {courses.map((course) => (
                  <tr key={course.courseId}>
                    <td>{course.name}</td>
                    <td>{course.departmentName}</td>
                    <td>{course.instructorName}</td>
                    <td>{course.credits}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p>No enrolled courses found.</p>
          )}
        </div>
      </div>
    </div>
  );
}
