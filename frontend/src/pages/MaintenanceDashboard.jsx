import { useCallback, useEffect, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import {
    Wrench,
    ClipboardList,
    Clock,
    CheckCircle,
    LogOut,
    RefreshCw
} from "lucide-react";
import { useAuth } from "../context/useAuth";
import API from "../api/axiosConfig";
import MaintenanceTaskList from "../components/MaintenanceTaskList";
import "../maintenance.css";

function MaintenanceDashboard() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();
    const staffId = user?.id;

    const [dashboard, setDashboard] = useState({});
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const loadDashboard = useCallback(async () => {
        if (!staffId) return;

        setLoading(true);
        setError("");

        try {
            const [dashboardResponse, tasksResponse] = await Promise.all([
                API.get(`/maintenance/${staffId}/dashboard`),
                API.get(`/maintenance/${staffId}/tasks`)
            ]);

            const dashboardData = dashboardResponse.data || {};
            setDashboard(dashboardData);

            const taskData = tasksResponse.data;
            setTasks(
                Array.isArray(taskData)
                    ? taskData
                    : Array.isArray(taskData?.tasks)
                        ? taskData.tasks
                        : Array.isArray(dashboardData.tasks)
                            ? dashboardData.tasks
                            : []
            );
        } catch (err) {
            console.error("Maintenance dashboard error:", err.response?.data || err);
            setError(
                err.response?.data?.message ||
                "Unable to load your maintenance dashboard."
            );
        } finally {
            setLoading(false);
        }
    }, [staffId]);

    useEffect(() => {
        const timer = setTimeout(loadDashboard, 0);
        return () => clearTimeout(timer);
    }, [loadDashboard]);

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    if (!user || user.role !== "MAINTENANCE") {
        return <Navigate to="/login" replace />;
    }

    const assignedCount = dashboard.assignedTasks ?? tasks.filter(
        (task) => task.status === "ASSIGNED"
    ).length;
    const inProgressCount = dashboard.inProgressTasks ?? tasks.filter(
        (task) => task.status === "IN_PROGRESS"
    ).length;
    const resolvedCount = dashboard.resolvedTasks ?? tasks.filter(
        (task) => task.status === "RESOLVED"
    ).length;

    return (
        <main className="maintenance-dashboard">
            <header className="maintenance-header">
                <div>
                    <span className="maintenance-eyebrow">MAINTENANCE PORTAL</span>
                    <h1><Wrench size={30} /> Hello, {user.name}</h1>
                    <p>Manage your assigned campus maintenance tasks.</p>
                </div>

                <div className="maintenance-header-actions">
                    <button className="maintenance-refresh" onClick={loadDashboard}>
                        <RefreshCw size={17} /> Refresh
                    </button>
                    <button className="maintenance-logout" onClick={handleLogout}>
                        <LogOut size={17} /> Logout
                    </button>
                </div>
            </header>

            <section className="maintenance-stat-grid">
                <div className="maintenance-stat-card">
                    <ClipboardList />
                    <div>
                        <p>Assigned Tasks</p>
                        <h2>{assignedCount}</h2>
                    </div>
                </div>

                <div className="maintenance-stat-card">
                    <Clock />
                    <div>
                        <p>In Progress</p>
                        <h2>{inProgressCount}</h2>
                    </div>
                </div>

                <div className="maintenance-stat-card">
                    <CheckCircle />
                    <div>
                        <p>Resolved Tasks</p>
                        <h2>{resolvedCount}</h2>
                    </div>
                </div>
            </section>

            <section className="maintenance-panel">
                <div className="maintenance-panel-heading">
                    <div>
                        <h2>My Assigned Tasks</h2>
                        <p>Review complaints and update their progress.</p>
                    </div>
                </div>

                {loading && <p>Loading your tasks...</p>}
                {error && <div className="maintenance-error">{error}</div>}

                {!loading && !error && (
                    <MaintenanceTaskList
                        tasks={tasks}
                        staffId={user.id}
                        onRefresh={loadDashboard}
                    />
                )}
            </section>
        </main>
    );
}

export default MaintenanceDashboard;
