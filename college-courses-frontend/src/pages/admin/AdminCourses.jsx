import Sidebar from "../../components/Sidebar";

export default function AdminCourses() {
  return (
    <div style={{ display: "flex" }}>
      <Sidebar />
      <div style={{ padding: "2rem", flex: 1 }}>
        <h1>All Courses</h1>
        <p>Here you can view, add, and manage courses.</p>
      </div>
    </div>
  );
}
