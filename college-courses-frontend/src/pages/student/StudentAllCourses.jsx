import React, { useEffect, useState } from "react";
import axios from "axios";
import Sidebar from "../../components/Sidebar";
import { useNavigate } from "react-router-dom";
import "./StudentAllCourses.css";

export default function StudentAllCourses() {
  const [courses, setCourses] = useState([]);
  const [message, setMessage] = useState("");
  const token = localStorage.getItem("token");
  console.log("Token:", token); // Check if it's retrieved properly
  const BASE_URL = "http://localhost:8080";
  const navigate = useNavigate();

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      const res = await axios.get(`${BASE_URL}/api/student/allcourses`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCourses(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error("Error fetching courses", err);
      setCourses([]);
      setMessage("❌ Failed to fetch courses");
    }
  };

  const handleEnroll = async (courseId) => {
    try {
      const res = await axios.post(
        `${BASE_URL}/api/student/courses/${courseId}/enroll`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMessage("✅ Successfully enrolled!");
      fetchCourses();  // Re-fetch courses to update the enroll status
    } catch (err) {
      console.error("Enrollment failed", err);
      setMessage("❌ Enrollment failed");
    }
  };

  return (
    <div className="student-all-courses-dashboard">
      <Sidebar />
      <div className="student-all-courses-content">
        <h1 className="student-all-courses-page-title">📚 Available Courses</h1>

        {message && <p className="student-all-courses-status-message">{message}</p>}

        {courses.length === 0 ? (
          <p className="student-all-courses-no-courses">No courses available right now.</p>
        ) : (
          <div className="student-all-courses-grid">
            {courses.map((course) => (
              <div key={course.courseId} className="student-all-courses-card">
                <div className="student-all-courses-image-wrapper">
                  <img
                    src={
                      course.thumbnailUrl
                        ? `${BASE_URL}${course.thumbnailUrl}`
                        : "/default-course.png"
                    }
                    alt={course.title}
                    className="student-all-courses-thumb"
                  />
                </div>

                <div className="student-all-courses-info">
                  <h3>{course.title}</h3>
                  {/* Display Instructor Full Name */}
                  <p className="student-all-courses-instructor">
                    by {course.instructor ? course.instructor.fullName : "N/A"}
                  </p>
                  <div>
                    <button
                      onClick={() =>
                        navigate(`/student/courses/${course.courseId}`)
                      }
                      className="student-all-courses-view-btn"
                    >
                      View Details
                    </button>

                    {/* Disable Enroll Button if Already Enrolled */}
                    {course.enrolled ? (
                      <button className="student-all-courses-enrolled-btn" disabled>
                        Already Enrolled
                      </button>
                    ) : (
                      <button
                        onClick={() => handleEnroll(course.courseId)}
                        className="student-all-courses-enroll-btn"
                      >
                        Enroll
                      </button>
                    )}
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
