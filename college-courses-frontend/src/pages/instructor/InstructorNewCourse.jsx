import React, { useState } from "react";
import axios from "axios";
import Sidebar from "../../components/Sidebar";
import "./InstructorNewCourse.css";

export default function InstructorNewCourse() {
  const [title, setTitle] = useState("");
  const [thumbnail, setThumbnail] = useState(null);
  const [thumbnailPreview, setThumbnailPreview] = useState("");
  const [uploadProgress, setUploadProgress] = useState(0);
  const [message, setMessage] = useState("");

  const handleThumbnailChange = (e) => {
    const file = e.target.files[0];
    setThumbnail(file);
    if (file) setThumbnailPreview(URL.createObjectURL(file));
  };

  const handleUpload = async () => {
    if (!title.trim()) {
      setMessage("⚠️ Please enter a course title!");
      return;
    }
    if (!thumbnail) {
      setMessage("⚠️ Please upload a course thumbnail!");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      if (!token) {
        setMessage("⚠️ Please login again. Token not found!");
        return;
      }

      const formData = new FormData();
      formData.append("title", title);
      formData.append("file", thumbnail);

      const res = await axios.post("http://localhost:8080/api/instructor/courses", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
        onUploadProgress: (progressEvent) => {
          const percent = Math.round((progressEvent.loaded * 100) / progressEvent.total);
          setUploadProgress(percent);
        },
      });

      setMessage("✅ Course created successfully!");
      setTitle("");
      setThumbnail(null);
      setThumbnailPreview("");
      setUploadProgress(0);
      console.log("Created Course:", res.data);
    } catch (error) {
      console.error("Error creating course:", error);
      if (error.response && error.response.status === 401) {
        setMessage("❌ Unauthorized: Please login again.");
      } else {
        setMessage("❌ Failed to create course.");
      }
    }
  };

  return (
    <div className="instructor-dashboardd">
      <Sidebar customClass="instructor-sidebar" />
      <div className="instructor-content">
        <div className="upload-container">
          <h1>🆕 Create New Course</h1>
          <p>Enter a course title and upload a thumbnail to get started.</p>

          <div className="upload-form">
            <label>📘 Course Title</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Enter course title"
            />

            <label>🖼️ Course Thumbnail</label>
            <input type="file" accept="image/*" onChange={handleThumbnailChange} />
            {thumbnailPreview && (
              <img
                src={thumbnailPreview}
                alt="Thumbnail Preview"
                className="thumbnail-preview"
              />
            )}

            {uploadProgress > 0 && uploadProgress < 100 && (
              <div className="progress-bar">
                <div className="progress" style={{ width: `${uploadProgress}%` }}></div>
              </div>
            )}

            <button onClick={handleUpload} className="upload-btn">
              Create Course
            </button>

            {message && <p className="upload-message">{message}</p>}
          </div>
        </div>
      </div>
    </div>
  );
}
