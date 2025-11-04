// App.js
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./auth/AuthContext";

// Login
import Login from "./pages/Login";

// Admin Pages
import AdminDashboard from "./pages/admin/AdminDashboard";
import AdminStudents from "./pages/admin/AdminStudents";
import AdminInstructors from "./pages/admin/AdminInstructors";
import AdminCourses from "./pages/admin/AdminCourses";
import AdminDepartments from "./pages/admin/AdminDepartments";

// Student Pages
import StudentDashboard from "./pages/student/StudentDashboard";
import StudentMyCourses from "./pages/student/StudentMyCourses";
import StudentAllCourses from "./pages/student/StudentAllCourses";
import StudentCourseDetails from "./pages/student/StudentCourseDetails";
import CourseDetails from "./pages/student/CourseDetails"; // ✅ new import

// Instructor Pages
import InstructorDashboard from "./pages/instructor/InstructorDashboard";
import InstructorNewCourse from "./pages/instructor/InstructorNewCourse";
import InstructorCourses from "./pages/instructor/InstructorCourses";
import InstructorCourseDetails from "./pages/instructor/InstructorCourseDetails";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          {/* Public */}
          <Route path="/" element={<Login />} />

          {/* Admin Routes */}
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/admin/students" element={<AdminStudents />} />
          <Route path="/admin/instructors" element={<AdminInstructors />} />
          <Route path="/admin/departments" element={<AdminDepartments />} />
          <Route path="/admin/courses" element={<AdminCourses />} />

          {/* Student Routes */}
          <Route path="/student" element={<StudentDashboard />} />
          <Route path="/student/courses" element={<StudentMyCourses />} />
          <Route path="/student/courses/:courseId" element={<StudentCourseDetails />} />
          <Route path="/student/coursess/:courseId" element={<CourseDetails />} /> {/* ✅ new route */}
          <Route path="/student/allcourses" element={<StudentAllCourses />} />

          {/* Instructor Routes */}
          <Route path="/instructor" element={<InstructorDashboard />} />
          <Route path="/instructor/newcourse" element={<InstructorNewCourse />} />
          <Route path="/instructor/courses" element={<InstructorCourses />} />
          <Route path="/instructor/courses/:courseId" element={<InstructorCourseDetails />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
