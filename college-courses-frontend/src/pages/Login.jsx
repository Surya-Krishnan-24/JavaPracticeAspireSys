import { useState, useContext } from "react";
import { AuthContext } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";
import "./Login.css"; // import the CSS file

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://172.24.219.181:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        alert("Invalid credentials");
        return;
      }

      const data = await response.json();
      const jwt = data.token || data.jwt || data.accessToken;

      if (!jwt) {
        alert("No token received from backend");
        return;
      }

      login(jwt);

      const payload = JSON.parse(atob(jwt.split(".")[1]));
      const role =
        payload.role ||
        payload.roles?.[0] ||
        payload.authorities?.[0] ||
        "";

      if (role.includes("ROLE_ADMIN")) navigate("/admin");
      else if (role.includes("ROLE_INSTRUCTOR")) navigate("/instructor");
      else if (role.includes("ROLE_STUDENT")) navigate("/student");
      else alert("Unknown role, staying on login");
    } catch (err) {
      console.error("Login error:", err);
      alert("Backend service is down");
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
        <h2>Login</h2>
        <div className="form-group">
          <label>Username</label>
          <input
            type="text"
            placeholder="Enter username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button className="login-button" type="submit">
          Login
        </button>
      </form>
    </div>
  );
}
