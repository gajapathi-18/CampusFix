import { useEffect, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import { LogOut, LayoutDashboard } from "lucide-react";
import { useAuth } from "../context/useAuth";
import API from "../api/axiosConfig";
import AdminStats from "../components/AdminStats";
import AdminComplaintList from "../components/AdminComplaintList";
import AdminAlerts from "../components/AdminAlerts";
import "../admin.css";

function AdminDashboard() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const [dashboard, setDashboard] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [refreshKey, setRefreshKey] = useState(0);

    useEffect(() => {
        const loadDashboard = async () => {
            setLoading(true);
            setError("");

            try {
                const response = await API.get("/dashboard/admin");
                setDashboard(response.data || {});
            } catch (err) {
                console.error("Dashboard loading error:", err.response?.data || err);
                setError("Unable to load dashboard statistics.");
            } finally {
                setLoading(false);
            }
        };

        const timer = setTimeout(loadDashboard, 0);
        return () => clearTimeout(timer);
    }, [refreshKey]);

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    if (!user || user.role !== "ADMIN") {
        return <Navigate to="/login" replace />;
    }

    return (
        <main className="admin-dashboard">
            <header className="admin-header">
                <div>
                    <span>ADMINISTRATION PORTAL</span>
                    <h1><LayoutDashboard size={30} /> Welcome, {user.name}</h1>
                    <p>Monitor campus complaints and maintenance activities.</p>
                </div>

                <button className="admin-logout" onClick={handleLogout}>
                    <LogOut size={17} /> Logout
                </button>
            </header>

            <section className="admin-overview">
                <div className="admin-section-heading">
                    <div>
                        <h2>Dashboard Overview</h2>
                        <p>Live summary from your CampusFix backend.</p>
                    </div>

                    <button
                        type="button"
                        onClick={() => setRefreshKey((previous) => previous + 1)}
                    >
                        Refresh Dashboard
                    </button>
                </div>

                {error && <div className="admin-error">{error}</div>}
                {loading ? (
                    <p>Loading dashboard statistics...</p>
                ) : (
                    <AdminStats data={dashboard} />
                )}
            </section>

            <AdminComplaintList
                adminId={user.id}
                refreshKey={refreshKey}
            />

            <AdminAlerts />
        </main>
    );
}

export default AdminDashboard;
