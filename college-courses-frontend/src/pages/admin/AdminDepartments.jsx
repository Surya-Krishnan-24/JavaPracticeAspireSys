import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import "./AdminDepartments.css";

export default function AdminDepartments() {
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editingDept, setEditingDept] = useState(null);
  const [newDept, setNewDept] = useState({ name: "", deptCode: "" });

  // Fetch all departments
  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) throw new Error("No token found. Please log in.");

        const res = await fetch("http://172.24.219.181:8080/api/admin/departments", {
          headers: { Authorization: `Bearer ${token}` },
        });

        if (!res.ok) throw new Error("Failed to fetch departments.");

        const data = await res.json();
        setDepartments(data);
      } catch (err) {
        console.error("Error:", err.message);
        alert(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchDepartments();
  }, []);

  // Add new department
  const handleAddDept = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");

      const res = await fetch("http://172.24.219.181:8080/api/admin/departments", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newDept),
      });

      const text = await res.text();
      if (!res.ok) throw new Error(text || "Could not create department");

      const data = JSON.parse(text);
      setDepartments([...departments, data]);
      setNewDept({ name: "", deptCode: "" });
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  // Delete department
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this department?")) return;

    try {
      const token = localStorage.getItem("token");
      const res = await fetch(`http://172.24.219.181:8080/api/admin/departments/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!res.ok) {
        const errMsg = await res.text();
        throw new Error(errMsg || "Failed to delete department.");
      }

      setDepartments(departments.filter((d) => d.departmentId !== id));
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  // Save edited department
  const handleEditSave = async () => {
    try {
      const token = localStorage.getItem("token");

      const res = await fetch(
        `http://172.24.219.181:8080/api/admin/departments/${editingDept.departmentId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(editingDept),
        }
      );

      const text = await res.text();
      if (!res.ok) throw new Error(text || "Failed to update department.");

      const updated = JSON.parse(text);
      setDepartments(
        departments.map((d) => (d.departmentId === updated.departmentId ? updated : d))
      );
      setEditingDept(null);
    } catch (err) {
      alert("Error updating department: " + err.message);
    }
  };

  if (loading) return <div>Loading departments...</div>;

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />

      <div className="admin-departments-content">
        <h1>Manage Departments</h1>
        <p>Create and manage academic departments</p>

        {/* Add Department Form */}
        <form className="add-department-form" onSubmit={handleAddDept}>
          <input
            type="text"
            placeholder="Department Name"
            value={newDept.name}
            onChange={(e) => setNewDept({ ...newDept, name: e.target.value })}
            required
          />
          <input
            type="text"
            placeholder="Department Code"
            value={newDept.deptCode}
            onChange={(e) => setNewDept({ ...newDept, deptCode: e.target.value })}
            required
          />
          <button type="submit">Add Department</button>
        </form>

        {/* Departments Table */}
        <table className="departments-table">
          <thead>
            <tr>
              <th>Department ID</th>
              <th>Department Name</th>
              <th>Code</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {departments.map((d) => (
              <tr key={d.departmentId}>
                <td>{d.departmentId}</td>
                <td>{d.name}</td>
                <td>{d.deptCode}</td>
                <td style={{ display: "flex", gap: "10px" }}>
                  <button onClick={() => setEditingDept({ ...d })}>Edit</button>
                  <button className="delete-btn" onClick={() => handleDelete(d.departmentId)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Edit Modal */}
        {editingDept && (
          <div className="modal">
            <div className="modal-content">
              <h3>Edit Department</h3>

              <input
                type="text"
                placeholder="Department Name"
                value={editingDept.name || ""}
                onChange={(e) =>
                  setEditingDept({ ...editingDept, name: e.target.value })
                }
              />
              <input
                type="text"
                placeholder="Department Code"
                value={editingDept.deptCode || ""}
                onChange={(e) =>
                  setEditingDept({ ...editingDept, deptCode: e.target.value })
                }
              />

              <div style={{ display: "flex", gap: "20px" }}>
                <button onClick={handleEditSave}>Save</button>
                <button onClick={() => setEditingDept(null)}>Cancel</button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
