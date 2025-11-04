import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import "./AdminStudents.css";

export default function AdminStudents() {
  const [students, setStudents] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editingStudent, setEditingStudent] = useState(null);
  const [newStudent, setNewStudent] = useState({
    username: "",
    fullName: "",
    email: "",
    password: "",
    departmentId: ""
  });

  // ✅ Fetch students & departments
  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) throw new Error("No token found. Please log in.");

        const [studentRes, deptRes] = await Promise.all([
          fetch("http://172.24.219.181:8080/api/admin/students", {
            headers: { Authorization: `Bearer ${token}` }
          }),
          fetch("http://172.24.219.181:8080/api/admin/departments", {
            headers: { Authorization: `Bearer ${token}` }
          })
        ]);

        if (!studentRes.ok || !deptRes.ok)
          throw new Error("Failed to fetch data.");

        const studentData = await studentRes.json();
        const deptData = await deptRes.json();

        setStudents(studentData);
        setDepartments(deptData);
      } catch (err) {
        console.error("Error fetching data:", err.message);
        alert(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  // ✅ Add new student
  const handleAddStudent = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");

      const res = await fetch("http://172.24.219.181:8080/api/admin/students", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(newStudent)
      });

      const text = await res.text();
      if (!res.ok) throw new Error(text || "Could not create student");

      const data = JSON.parse(text);
      setStudents([...students, data]);
      setNewStudent({
        username: "",
        fullName: "",
        email: "",
        password: "",
        departmentId: ""
      });
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  // ✅ Delete student
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this student?")) return;

    try {
      const token = localStorage.getItem("token");
      const res = await fetch(`http://172.24.219.181:8080/api/admin/students/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` }
      });

      if (!res.ok) {
        const errMsg = await res.text();
        throw new Error(errMsg || "Failed to delete student.");
      }

      setStudents(students.filter((s) => s.studentId !== id));
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  // ✅ Save edited student
  const handleEditSave = async () => {
    try {
      const token = localStorage.getItem("token");

      const payload = {
        username: editingStudent.username,
        fullName: editingStudent.fullName,
        email: editingStudent.email,
        password: editingStudent.password || null, // only update if provided
       departmentId: editingStudent.departmentId || null
      };

      const res = await fetch(
        `http://172.24.219.181:8080/api/admin/students/${editingStudent.studentId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify(payload)
        }
      );

      const text = await res.text();
      if (!res.ok) throw new Error(text || "Failed to update student.");

      const updated = JSON.parse(text);
      setStudents(students.map((s) => (s.studentId === updated.studentId ? updated : s)));
      setEditingStudent(null);
    } catch (err) {
      alert("Error updating student: " + err.message);
    }
  };

  if (loading) return <div>Loading students...</div>;

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />
      <div className="admin-students-content">
        <h1>Manage Students</h1>
        <p>Create and manage student accounts</p>

        {/* ✅ Add Student Form */}
        <form className="add-student-form" onSubmit={handleAddStudent}>
          <input
            type="text"
            placeholder="Username"
            value={newStudent.username}
            onChange={(e) => setNewStudent({ ...newStudent, username: e.target.value })}
            required
          />
          <input
            type="text"
            placeholder="Full Name"
            value={newStudent.fullName}
            onChange={(e) => setNewStudent({ ...newStudent, fullName: e.target.value })}
            required
          />
          <input
            type="email"
            placeholder="Email"
            value={newStudent.email}
            onChange={(e) => setNewStudent({ ...newStudent, email: e.target.value })}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={newStudent.password}
            onChange={(e) => setNewStudent({ ...newStudent, password: e.target.value })}
            required
          />
          <select
            value={newStudent.departmentId}
            onChange={(e) =>
              setNewStudent({ ...newStudent, departmentId: Number(e.target.value) })
            }
          >
            <option value="">No Department</option>
            {departments.map((d) => (
              <option key={d.departmentId} value={d.departmentId}>
                {d.name}
              </option>
            ))}
          </select>
          <button type="submit">Add Student</button>
        </form>

        {/* ✅ Students Table */}
        <table className="students-table" cellPadding="10px">
          <thead>
            <tr>
              <th>Student ID</th>
              <th>Username</th>
              <th>Full Name</th>
              <th>Email</th>
              <th>Department</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {students.map((s) => (
              <tr key={s.studentId}>
                <td>{s.studentId}</td>
                <td>{s.user?.username || s.username || "N/A"}</td>
                <td>{s.fullName}</td>
                <td>{s.user?.email || s.email || "N/A"}</td>
                <td>{s.departmentName || "No Department"}</td>
                <td>
                  <div class="btn">
                  <button
                    onClick={() =>
                        setEditingStudent({
                        ...s,
                        username: s.username || "",
                        email: s.email || "",
                        fullName: s.fullName || "",
                        departmentId: departments.find((d) => d.name === s.departmentName)?.departmentId || "",
                        departmentName: s.departmentName || "No Department",
                        password: ""
                        })

                    }
                    >
                    Edit
                    </button>

                  <button className="delete-btn" onClick={() => handleDelete(s.studentId)}>
                    Delete
                  </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* ✅ Edit Modal */}
        {editingStudent && (
          <div className="modal">
            <div className="modal-content">
              <h3>Edit Student</h3>

              <input
                type="text"
                placeholder="Username"
                value={editingStudent.username || ""}
                onChange={(e) =>
                  setEditingStudent({ ...editingStudent, username: e.target.value })
                }
              />

      <input
        type="text"
        placeholder="Full Name"
        value={editingStudent.fullName || ""}
        onChange={(e) =>
          setEditingStudent({ ...editingStudent, fullName: e.target.value })
        }
      />

      <input
        type="email"
        placeholder="Email"
        value={editingStudent.email || ""}
        onChange={(e) =>
          setEditingStudent({ ...editingStudent, email: e.target.value })
        }
      />

      <input
        type="password"
        placeholder="Leave blank to keep current password"
        value={editingStudent.password || ""}
        onChange={(e) =>
          setEditingStudent({ ...editingStudent, password: e.target.value })
        }
      />

      <select
        value={
            editingStudent.department?.departmentId?.toString() ??
            editingStudent.departmentId?.toString() ??
            ""
            }
            onChange={(e) => {
            const selectedId = e.target.value ? Number(e.target.value) : null;
            const selectedDept = departments.find((d) => d.departmentId === selectedId);
            setEditingStudent({
                ...editingStudent,
                departmentId: selectedId,
                departmentName: selectedDept ? selectedDept.name : "No Department"
            });
            }}

        >

        <option value="">No Department</option>
        {departments.map((d) => (
          <option key={d.departmentId} value={d.departmentId.toString()}>
            {d.name}
          </option>
        ))}
      </select>

      <div style={{ display: "flex", gap: "10px" }}>
        <button onClick={handleEditSave}>Save</button>
        <button onClick={() => setEditingStudent(null)}>Cancel</button>
      </div>
    </div>
  </div>
)}

      </div>
    </div>
  );
}
