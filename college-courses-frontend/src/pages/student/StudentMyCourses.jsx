import React, { useEffect, useState } from "react";
import axios from "axios";
import Sidebar from "../../components/Sidebar";
import { useNavigate } from "react-router-dom";
import "./StudentCourses.css";

export default function StudentMyCourses() {
  const [courses, setCourses] = useState([]); // State to store courses
  const [message, setMessage] = useState(""); // State to store messages (success/error)
  const [loading, setLoading] = useState(true); // Loading state for API calls
  const navigate = useNavigate();
  const BASE_URL = "http://localhost:8080";

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      // If no token found, display message but do not proceed
      setMessage("❌ Please log in to view your courses.");
      setLoading(false);
      return;
    }

    fetchCourses(token); // Fetch courses if token is available
  }, [navigate]);

  const fetchCourses = async (token) => {
    try {
      setLoading(true); // Set loading state to true when API call is made

      const res = await axios.get(`${BASE_URL}/api/student/mycourses`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      // If the response is successful, set the courses
      setCourses(res.data);
      setLoading(false); // Set loading state to false after the data is fetched
    } catch (err) {
      console.error("Error fetching courses", err);

      if (err.response && err.response.status === 401) {
        // Handle unauthorized (expired or missing token)
        setMessage("❌ Session expired or invalid. Please log in again.");
        localStorage.removeItem("token"); // Remove invalid token from localStorage
        navigate("/login"); // Redirect to login
      } else {
        setMessage("❌ Failed to fetch courses. Please try again later.");
      }

      setCourses([]); // Clear the courses on error
      setLoading(false); // Set loading state to false even if there is an error
    }
  };

  return (
    <div className="student-dashboard">
      <Sidebar />
      <div className="student-content">
        <h1 className="page-title">📚 My Courses</h1>

        {message && <p className="status-message">{message}</p>}

        {loading ? (
          <p>Loading courses...</p> // Show loading state
        ) : courses.length === 0 ? (
          <p className="no-courses">You are not enrolled in any courses yet.</p> // No courses message
        ) : (
          <div className="courses-grid">
            {courses.map((course) => (
              <div key={course.courseId} className="course-card">
                <div className="course-image-wrapper">
                  <img
                    src={
                      course.thumbnailUrl
                        ? `${BASE_URL}${course.thumbnailUrl}`
                        : "/default-course.png"
                    }
                    alt={course.title}
                    className="course-thumb"
                  />
                </div>

                <div className="course-info">
                  <h3>{course.title}</h3>
                  <button
                    onClick={() =>
                      navigate(`/student/coursess/${course.courseId}`)
                    }
                    className="view-btn"
                  >
                    View Details
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
