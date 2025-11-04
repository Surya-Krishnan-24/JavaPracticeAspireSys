import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import Sidebar from "../../components/Sidebar";
import "./CourseDetails.css";

export default function CourseDetails() {
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);
  const [message, setMessage] = useState("");
  const [selectedContent, setSelectedContent] = useState(null); // For viewing content
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
      setMessage("❌ Failed to load course details");
    }
  };

  const getFileIcon = (fileUrl) => {
    const ext = fileUrl.split(".").pop().toLowerCase();
    if (["mp4", "webm", "mov"].includes(ext)) return "📹";
    if (["pdf", "doc", "docx"].includes(ext)) return "📄";
    if (["ppt", "pptx"].includes(ext)) return "📊";
    if (["jpg", "jpeg", "png", "gif"].includes(ext)) return "🖼️";
    return "📁"; // default
  };

  if (!course) return <div className="loading">Loading course details...</div>;

  return (
    <div className="course-details">
      <Sidebar />
      <div className="course-content">
        <h1 className="page-title">{course.title}</h1>
        {message && <p className="status-message">{message}</p>}

        <div className="course-header">
          <img
            src={course.thumbnailUrl ? `${BASE_URL}${course.thumbnailUrl}` : "/default-course.png"}
            alt={course.title}
          />
        </div>

        <section className="subtopics-section">
          <h3>Subtopics</h3>
          {course.subtopics?.length ? (
            <div className="subtopics-grid">
              {course.subtopics.map((s) => (
                <div key={s.id} className="subtopic-card">
                  <h4>{s.title}</h4>

                  <ul className="content-list">
                    {s.contents?.length ? (
                      s.contents.map((c) => (
                        <li
                          key={c.id}
                          className="content-item"
                          onClick={() => setSelectedContent(c)}
                        >
                          {getFileIcon(c.fileUrl)} {c.description || c.fileUrl.split("/").pop()}
                        </li>
                      ))
                    ) : (
                      <li className="no-content">No content yet.</li>
                    )}
                  </ul>
                </div>
              ))}
            </div>
          ) : (
            <p className="no-subtopics">No subtopics yet.</p>
          )}
        </section>

        {/* Content view modal */}
        {selectedContent && (
          <div
            className="modal-overlay"
            onClick={() => setSelectedContent(null)}
          >
            <div
              className="content-modal"
              onClick={(e) => e.stopPropagation()}
            >
              <h3>{selectedContent.description || selectedContent.fileUrl.split("/").pop()}</h3>

              {["mp4", "webm", "mov"].includes(selectedContent.fileUrl.split(".").pop().toLowerCase()) ? (
                <video
                  src={`${BASE_URL}${selectedContent.fileUrl}`}
                  controls
                  style={{ width: "100%", borderRadius: "8px" }}
                />
              ) : (
                <a
                  href={`${BASE_URL}${selectedContent.fileUrl}`}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="download-link"
                >
                  Download/View File
                </a>
              )}

              <button
                className="cancel-btn"
                onClick={() => setSelectedContent(null)}
              >
                Close
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
