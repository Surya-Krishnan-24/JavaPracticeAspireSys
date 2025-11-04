import { createContext, useState, useEffect } from "react";
import jwt_decode from "jwt-decode";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [user, setUser] = useState(token ? jwt_decode(token) : null);

  const login = (jwt) => {
    localStorage.setItem("token", jwt);
    setToken(jwt);

    const decoded = jwt_decode(jwt);
    setUser(decoded);

    if (decoded.role) localStorage.setItem("role", decoded.role);
    if (decoded.full_name) localStorage.setItem("full_name", decoded.full_name);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("full_name");
    setToken(null);
    setUser(null);
  };

  useEffect(() => {
    if (token) {
      const decoded = jwt_decode(token);
      setUser(decoded);

      if (decoded.role) localStorage.setItem("role", decoded.role);
      if (decoded.full_name) localStorage.setItem("full_name", decoded.full_name);
    }
  }, [token]);

  return (
    <AuthContext.Provider value={{ token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
