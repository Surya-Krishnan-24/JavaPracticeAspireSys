import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import "./AdminInstructors.css";

export default function AdminInstructors() {
  const [instructors, setInstructors] = useState([]);
  const [departments, setDepartments] = useState([]); 
  const [loading, setLoading] = useState(true);
  const [editingInstructor, setEditingInstructor] = useState(null);

  const [newInstructor, setNewInstructor] = useState({
    username: "",
    fullName: "",
    email: "",
    password: "",
    departmentId: ""
  });

  // ✅ Fetch instructors & departments on mount
  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) throw new Error("No token found. Please log in.");

        // Fetch instructors
        const resInstructors = await fetch("http://172.24.219.181:8080/api/admin/instructors", {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (!resInstructors.ok) throw new Error("Failed to fetch instructors.");
        const instructorsData = await resInstructors.json();
        setInstructors(instructorsData);

        // ✅ Fetch departments
        const resDepartments = await fetch("http://172.24.219.181:8080/api/admin/departments", {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (!resDepartments.ok) throw new Error("Failed to fetch departments.");
        const departmentsData = await resDepartments.json();
        setDepartments(departmentsData);
      } catch (err) {
        console.error("Error fetching data:", err.message);
        alert(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  // ✅ Add new instructor
  const handleAddInstructor = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");

      const res = await fetch("http://172.24.219.181:8080/api/admin/instructors", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newInstructor),
      });

      const text = await res.text();
      if (!res.ok) throw new Error(text || "Could not create instructor");

      const data = JSON.parse(text);
      setInstructors([...instructors, data]);
      setNewInstructor({
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

  // ✅ Delete instructor
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this instructor?")) return;

    try {
      const token = localStorage.getItem("token");
      const res = await fetch(`http://172.24.219.181:8080/api/admin/instructors/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!res.ok) {
        const errMsg = await res.text();
        throw new Error(errMsg || "Failed to delete instructor.");
      }

      setInstructors(instructors.filter((i) => i.instructorId !== id));
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  // ✅ Save edited instructor
  const handleEditSave = async () => {
    try {
      const token = localStorage.getItem("token");

      const payload = {
        username: editingInstructor.username,
        fullName: editingInstructor.fullName,
        email: editingInstructor.email,
        password: editingInstructor.password || null,
        departmentId: editingInstructor.departmentId || null
      };

      const res = await fetch(
        `http://172.24.219.181:8080/api/admin/instructors/${editingInstructor.instructorId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        }
      );

      const text = await res.text();
      if (!res.ok) throw new Error(text || "Failed to update instructor.");

      const updated = JSON.parse(text);
      setInstructors(
        instructors.map((i) => (i.instructorId === updated.instructorId ? updated : i))
      );
      setEditingInstructor(null);
    } catch (err) {
      alert("Error updating instructor: " + err.message);
    }
  };

  if (loading) return <div>Loading instructors...</div>;

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />
      <div className="admin-instructors-content">
        <h1>Manage Instructors</h1>
        <p>Create and manage instructor accounts</p>

        {/* ✅ Add Instructor Form */}
        <form className="add-instructor-form" onSubmit={handleAddInstructor}>
          <input
            type="text"
            placeholder="Username"
            value={newInstructor.username}
            onChange={(e) =>
              setNewInstructor({ ...newInstructor, username: e.target.value })
            }
            required
          />
          <input
            type="text"
            placeholder="Full Name"
            value={newInstructor.fullName}
            onChange={(e) =>
              setNewInstructor({ ...newInstructor, fullName: e.target.value })
            }
            required
          />
          <input
            type="email"
            placeholder="Email"
            value={newInstructor.email}
            onChange={(e) =>
              setNewInstructor({ ...newInstructor, email: e.target.value })
            }
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={newInstructor.password}
            onChange={(e) =>
              setNewInstructor({ ...newInstructor, password: e.target.value })
            }
            required
          />

          <select
            value={newInstructor.departmentId}
            onChange={(e) =>
              setNewInstructor({ ...newInstructor, departmentId: Number(e.target.value) })
            }
          >
            <option value="">Select Department</option>
            {departments.map((d) => (
              <option key={d.departmentId} value={d.departmentId}>
                {d.name} ({d.deptCode})
              </option>
            ))}
          </select>

          <button type="submit">Add Instructor</button>
        </form>

        {/* ✅ Instructors Table */}
        <table className="instructors-table">
          <thead>
            <tr>
             
              <th>Instructor ID</th>
              <th>Username</th>
              <th>Full Name</th>
              <th>Email</th>
              <th>Department</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {instructors.map((i) => (
              <tr key={i.instructorId}>
                
                <td>{i.instructorId}</td>
                <td>{i.user?.username || i.username || "N/A"}</td>
                <td>{i.fullName}</td>
                <td>{i.user?.email || i.email || "N/A"}</td>
                <td>{i.departmentName || "No Department"}</td>


                <td>
                  <div className="btn">
                  <button
                    onClick={() =>
                      setEditingInstructor({
                        ...i,
                        username: i.username || "",
                        email: i.email || "",
                        fullName: i.fullName || "",
                        password: "",
                        departmentId:
                          i.departmentId ||
                          departments.find((d) => d.name === i.departmentName)?.departmentId ||
                          "",
                      })
                    }
                  >
                    Edit
                  </button>


                  <button
                    className="delete-btn"
                    onClick={() => handleDelete(i.instructorId)}
                  >
                    Delete
                  </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* ✅ Edit Modal */}
        {editingInstructor && (
          <div className="modal">
            <div className="modal-content">
              <h3>Edit Instructor</h3>

              <input
                type="text"
                placeholder="Username"
                value={editingInstructor.username || ""}
                onChange={(e) =>
                  setEditingInstructor({ ...editingInstructor, username: e.target.value })
                }
              />

              <input
                type="text"
                placeholder="Full Name"
                value={editingInstructor.fullName || ""}
                onChange={(e) =>
                  setEditingInstructor({ ...editingInstructor, fullName: e.target.value })
                }
              />

              <input
                type="email"
                placeholder="Email"
                value={editingInstructor.email || ""}
                onChange={(e) =>
                  setEditingInstructor({ ...editingInstructor, email: e.target.value })
                }
              />

              <input
                type="password"
                placeholder="Leave blank to keep current password"
                value={editingInstructor.password || ""}
                onChange={(e) =>
                  setEditingInstructor({ ...editingInstructor, password: e.target.value })
                }
              />

              <select
                value={editingInstructor.departmentId || ""}
                onChange={(e) =>
                  setEditingInstructor({
                    ...editingInstructor,
                    departmentId: Number(e.target.value),
                  })
                }
              >
                <option value="">Select Department</option>
                {departments.map((d) => (
                  <option key={d.departmentId} value={d.departmentId}>
                    {d.name} ({d.deptCode})
                  </option>
                ))}
              </select>


              <div style={{ display: "flex", gap: "10px" }}>
                <button onClick={handleEditSave}>Save</button>
                <button onClick={() => setEditingInstructor(null)}>Cancel</button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
