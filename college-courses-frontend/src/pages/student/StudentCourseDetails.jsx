import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import Sidebar from "../../components/Sidebar";
import "./StudentCourseDetails.css";

export default function StudentCourseDetails() {
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);
  const [message, setMessage] = useState("");
  const token = localStorage.getItem("token");
  const BASE_URL = "http://localhost:8080";
  const navigate = useNavigate();

  useEffect(() => {
    fetchCourseDetails();
  }, [courseId]);

  const fetchCourseDetails = async () => {
    try {
      const res = await axios.get(`${BASE_URL}/api/student/courses/${courseId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCourse(res.data);
    } catch (err) {
      console.error("Error fetching course details", err);
      setMessage("❌ Failed to fetch course details");
    }
  };

  const handleEnroll = async () => {
    try {
      await axios.post(
        `${BASE_URL}/api/student/courses/${courseId}/enroll`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMessage("✅ Successfully enrolled in the course!");
    } catch (err) {
      console.error("Enrollment failed", err);
      setMessage("❌ Enrollment failed");
    }
  };

  if (!course) {
    return (
      <div className="course-details-page">
        <Sidebar />
        <div className="course-details-content">
          <h1 className="course-details-title">Course Details</h1>
          {message ? <p className="course-status-message">{message}</p> : <p>Loading...</p>}
          <button onClick={() => navigate(-1)} className="course-back-btn">⬅ Back</button>
        </div>
      </div>
    );
  }

  return (
    <div className="course-details-page">
      <Sidebar />
      <div className="course-details-content">
        <h1 className="course-details-title">{course.title}</h1>
        <img
          src={course.thumbnailUrl ? `${BASE_URL}${course.thumbnailUrl}` : "/default-course.png"}
          alt={course.title}
          className="course-thumbnail"
        />
        
        {/* Display Instructor */}
        <p><strong>Instructor:</strong> {course.instructor && course.instructor.fullName ? course.instructor.fullName : "N/A"}</p>


        {/* Subtopics and their descriptions */}
        <div className="course-subtopics">
          <h2 className="course-subtopics-title">📄 Course Contents</h2>
          {course.subtopics && course.subtopics.length > 0 ? (
            course.subtopics.map((subtopic) => (
              <div key={subtopic.id} className="course-subtopic-item">
                <h3 className="course-subtopic-title">{subtopic.title}</h3>
                {subtopic.contents && subtopic.contents.length > 0 ? (
                  <ul className="course-subtopic-content-list">
                    {subtopic.contents.map((content) => (
                      content.description && (
                        <li key={content.id} className="course-subtopic-content-item">
                          {content.description}
                        </li>
                      )
                    ))}
                  </ul>
                ) : (
                  <p className="course-no-content">No content descriptions available for this subtopic.</p>
                )}
              </div>
            ))
          ) : (
            <p className="course-no-subtopics">No subtopics available for this course.</p>
          )}
        </div>

        {/* Back Button */}
        <button onClick={() => navigate(-1)} className="course-back-btn">⬅ Back</button>
      </div>
    </div>
  );
}
