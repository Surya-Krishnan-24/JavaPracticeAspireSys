import React, { useEffect, useState } from "react";
import axios from "axios";
import Sidebar from "../../components/Sidebar";
import { useNavigate } from "react-router-dom";
import "./InstructorCourses.css";

export default function InstructorCourses() {
  const [courses, setCourses] = useState([]);
  const [message, setMessage] = useState("");
  const token = localStorage.getItem("token");
  const BASE_URL = "http://localhost:8080";
  const navigate = useNavigate();

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      const res = await axios.get(`${BASE_URL}/api/instructor/courses`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      const data = Array.isArray(res.data) ? res.data : res.data?.data || [];
      setCourses(data);
    } catch (err) {
      console.error("Error fetching courses", err);
      setCourses([]);
      setMessage("❌ Failed to fetch courses");
    }
  };

  return (
    <div className="instructor-dashboard">
      <Sidebar />
      <div className="instructor-content">
        <h1 className="page-title">📚 My Courses</h1>

        {message && <p className="status-message">{message}</p>}

        {courses.length === 0 ? (
          <p className="no-courses">No courses found.</p>
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
                      navigate(`/instructor/courses/${course.courseId}`)
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
