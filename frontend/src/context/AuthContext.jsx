import { useState } from "react";
import API from "../api/axiosConfig";
import AuthContext from "./authContext";

export function AuthProvider({ children }) {
    const [user, setUser] = useState(() => {
        const savedUser = sessionStorage.getItem("campusfix_user");
        return savedUser ? JSON.parse(savedUser) : null;
    });

    const login = async (email, password) => {
        const response = await API.post("/users/login", {
            email,
            password
        });

        const userData = response.data;

        setUser(userData);
        sessionStorage.setItem("campusfix_user", JSON.stringify(userData));

        return userData;
    };

    const register = async (formData) => {
        const response = await API.post("/users/register", formData);
        return response.data;
    };

    const logout = () => {
        setUser(null);
        sessionStorage.removeItem("campusfix_user");
    };

    return (
        <AuthContext.Provider value={{ user, login, register, logout }}>
            {children}
        </AuthContext.Provider>
    );
}
