import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import StudentDashboard from "./pages/StudentDashboard";
import AdminDashboard from "./pages/AdminDashboard";
import MaintenanceDashboard from "./pages/MaintenanceDashboard";
import { AuthProvider } from "./context/AuthContext.jsx";

function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <Navbar />

                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />

                    <Route
                        path="/student/dashboard"
                        element={<StudentDashboard />}
                    />

                    <Route
                        path="/admin/dashboard"
                        element={<AdminDashboard />}
                    />

                    <Route
                        path="/maintenance/dashboard"
                        element={<MaintenanceDashboard />}
                    />
                </Routes>
            </AuthProvider>
        </BrowserRouter>
    );
}

export default App;
