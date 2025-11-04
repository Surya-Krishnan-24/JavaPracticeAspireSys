import { Link, useLocation, useNavigate } from "react-router-dom";
import "./Sidebar.css";
import {
  FaHome,
  FaUserGraduate,
  FaChalkboardTeacher,
  FaBook,
  FaSignOutAlt,
  FaUniversity,
  FaClipboardList,
} from "react-icons/fa";

export default function Sidebar() {
  const location = useLocation();
  const navigate = useNavigate();

  const role = localStorage.getItem("role");
  const fullName = localStorage.getItem("full_name");

  const linksByRole = {
    ROLE_ADMIN: [
      { name: "Home", path: "/admin", icon: <FaHome /> },
      { name: "Students", path: "/admin/students", icon: <FaUserGraduate /> },
      { name: "Instructors", path: "/admin/instructors", icon: <FaChalkboardTeacher /> },
      { name: "Courses", path: "/admin/courses", icon: <FaBook /> },
      { name: "Departments", path: "/admin/departments", icon: <FaUniversity /> },
    ],
    ROLE_STUDENT: [
      { name: "Home", path: "/student", icon: <FaHome /> },
      { name: "My Courses", path: "/student/courses", icon: <FaBook /> },
      { name: "All Courses", path: "/student/allcourses", icon: <FaClipboardList /> },
    ],
    ROLE_INSTRUCTOR: [
      { name: "Home", path: "/instructor", icon: <FaHome /> },
      { name: "My Courses", path: "/instructor/courses", icon: <FaBook /> },
      { name: "New Course", path: "/instructor/newcourse", icon: <FaChalkboardTeacher /> },
    ],
  };

  const links = linksByRole[role] || [];

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  const getSidebarTitle = () => {
    if (role === "ROLE_ADMIN") return "Admin";
    if (role === "ROLE_STUDENT" || role === "ROLE_INSTRUCTOR") {
      return fullName || "User";
    }
    return "User";
  };

  return (
    <div className="sidebar">
      <div className="sidebar-content">
        <h2 className="sidebar-title">{getSidebarTitle()}</h2>

        <nav className="sidebar-nav">
          {links.map((link) => (
            <Link
              key={link.name}
              to={link.path}
              className={`sidebar-link ${
                location.pathname === link.path ? "active" : ""
              }`}
            >
              {link.icon}
              {link.name}
            </Link>
          ))}
        </nav>
      </div>

      {/* Logout Button at bottom */}
      <button type="button" className="logout-btn" onClick={handleLogout}>
        <FaSignOutAlt />
        Logout
      </button>
    </div>
  );
}
