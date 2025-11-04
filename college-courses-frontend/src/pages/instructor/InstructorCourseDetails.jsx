import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import Sidebar from "../../components/Sidebar";
import "./InstructorCourseDetails.css";

export default function InstructorCourseDetails() {
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);
  const [newSubtopic, setNewSubtopic] = useState("");
  const [message, setMessage] = useState("");
  const [selectedSubtopic, setSelectedSubtopic] = useState(null);
  const [file, setFile] = useState(null);
  const [description, setDescription] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [selectedContent, setSelectedContent] = useState(null); // For viewing content
  const token = localStorage.getItem("token");
  const BASE_URL = "http://localhost:8080";

  useEffect(() => {
    fetchCourseDetails();
  }, [courseId]);

  const fetchCourseDetails = async () => {
    try {
      const res = await axios.get(`${BASE_URL}/api/instructor/courses/${courseId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCourse(res.data);
    } catch (err) {
      console.error("Error fetching course", err);
      setMessage("❌ Failed to load course details");
    }
  };

  const handleAddSubtopic = async () => {
    if (!newSubtopic.trim()) return;
    try {
      await axios.post(
        `${BASE_URL}/api/instructor/courses/${courseId}/subtopics`,
        { title: newSubtopic },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setNewSubtopic("");
      setMessage("✅ Subtopic added!");
      fetchCourseDetails();
    } catch (err) {
      console.error(err);
      setMessage("❌ Failed to add subtopic");
    }
  };

  const handleUploadContent = async (subtopicId) => {
    if (!file) {
      alert("Please select a file first!");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    if (description) formData.append("description", description);

    try {
      await axios.post(
        `${BASE_URL}/api/instructor/subtopics/${subtopicId}/upload`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      setMessage("✅ File uploaded successfully!");
      setShowModal(false);
      setFile(null);
      setDescription("");
      fetchCourseDetails();
    } catch (err) {
      console.error(err);
      setMessage("❌ Failed to upload content");
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
    <div className="instructor-dashboard">
      <Sidebar />
      <div className="course-details-content">
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
                  <div>
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

                  <button
                    onClick={() => {
                      setSelectedSubtopic(s);
                      setShowModal(true);
                    }}
                    className="upload-btn"
                  >
                    📤 Upload Content
                  </button>
                </div>
              ))}
            </div>
          ) : (
            <p className="no-subtopics">No subtopics yet.</p>
          )}

          <div className="add-subtopic">
            <input
              type="text"
              placeholder="New Subtopic Title"
              value={newSubtopic}
              onChange={(e) => setNewSubtopic(e.target.value)}
            />
            <button onClick={handleAddSubtopic}>➕ Add</button>
          </div>
        </section>

        {/* Upload modal */}
        {showModal && selectedSubtopic && (
          <div className="modal-overlay">
            <div className="upload-modal">
              <h3>Upload Content for: {selectedSubtopic.title}</h3>

              <input
                type="file"
                onChange={(e) => setFile(e.target.files[0])}
              />
              <textarea
                placeholder="Optional description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              ></textarea>

              <div className="modal-actions">
                <button
                  onClick={() => handleUploadContent(selectedSubtopic.id)}
                  className="submit-btn"
                >
                  Upload
                </button>
                <button
                  onClick={() => setShowModal(false)}
                  className="cancel-btn"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        )}

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
