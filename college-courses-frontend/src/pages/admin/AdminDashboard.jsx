import { useState, useEffect } from "react";
import Sidebar from "../../components/Sidebar";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  Legend,
  ResponsiveContainer,
} from "recharts";
import { FaUniversity, FaUserTie, FaBook, FaUserGraduate } from "react-icons/fa";
import "./AdminDashboard.css";

export default function AdminDashboard() {
  const [stats, setStats] = useState({
    departments: 0,
    instructors: 0,
    students: 0,
    courses: 0,
  });

  const [studentsPerDept, setStudentsPerDept] = useState([]);
  const [studentsPerYear, setStudentsPerYear] = useState([]);
  const [instructorsPerDept, setInstructorsPerDept] = useState([]);

  useEffect(() => {
    fetch("http://172.24.219.181:8080/api/admin/dashboard", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch dashboard data");
        return res.json();
      })
      .then((data) => {
        setStats({
          departments: data.departmentCount,
          instructors: data.instructorCount,
          students: data.studentCount,
          courses: data.courseCount,
        });

        setStudentsPerDept(
          data.studentsPerDepartment?.map((item) => ({
            department: item.department,
            students: item.count,
          })) || []
        );

        setStudentsPerYear(
          data.studentsPerYear?.map((item) => ({
            year: item.year,
            students: item.count,
          })) || []
        );

        setInstructorsPerDept(
          data.instructorsPerDepartment?.map((item) => ({
            department: item.department,
            instructors: item.count,
          })) || []
        );
      })
      .catch((err) => console.error("Error loading dashboard:", err));
  }, []);

  return (
    <div className="admin-dashboard">
      <Sidebar />
      <div className="dashboard-content">
        <h1>Admin Dashboard</h1>
        <p>Welcome, Admin! Here’s the college summary.</p>

        {/* Summary Cards */}
        <div className="summary-cards">
          <div className="card">
            <FaUniversity />
            <span>
              <strong>{stats.departments}</strong>
              <small>Departments</small>
            </span>
          </div>
          <div className="card">
            <FaUserTie />
            <span>
              <strong>{stats.instructors}</strong>
              <small>Instructors</small>
            </span>
          </div>
          <div className="card">
            <FaBook />
            <span>
              <strong>{stats.courses}</strong>
              <small>Courses</small>
            </span>
          </div>
          <div className="card">
            <FaUserGraduate />
            <span>
              <strong>{stats.students}</strong>
              <small>Students</small>
            </span>
          </div>
        </div>

        {/* Charts */}
        <div className="chart-section">
          <h3>Students per Department</h3>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={studentsPerDept}>
              <CartesianGrid strokeDasharray="4 4" />
              <XAxis dataKey="department" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="students" fill="#b5df84ff" barSize={90} />
            </BarChart>
          </ResponsiveContainer>
        </div>

        <div className="chart-section">
          <h3>Students per Year</h3>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={studentsPerYear}>
              <CartesianGrid strokeDasharray="4 4" />
              <XAxis dataKey="year" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="students" fill="#8884d8"  barSize={70} />
            </BarChart>
          </ResponsiveContainer>
        </div>

        {instructorsPerDept.length > 0 && (
          <div className="chart-section">
            <h3>Instructors per Department</h3>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={instructorsPerDept}>
                <CartesianGrid strokeDasharray="4 4" />
                <XAxis dataKey="department" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="instructors" fill="#f5d085ff" barSize={70} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        )}
      </div>
    </div>
  );
}
