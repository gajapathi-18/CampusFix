import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    ClipboardList,
    CheckCircle,
    Clock,
    LogOut
} from "lucide-react";
import { useAuth } from "../context/useAuth";
import ComplaintForm from "../components/ComplaintForm";
import ComplaintList from "../components/ComplaintList";
import "../student.css";

function StudentDashboard() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();
    const [refreshKey, setRefreshKey] = useState(0);

    const refreshComplaints = () => {
        setRefreshKey((previous) => previous + 1);
    };

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    if (!user) {
        return (
            <div className="student-dashboard">
                Please login to access your dashboard.
            </div>
        );
    }

    return (
        <div className="student-dashboard">
            <header className="student-header">
                <div>
                    <span className="student-welcome">STUDENT PORTAL</span>
                    <h1>Hello, {user.name} 👋</h1>
                    <p>Manage your campus complaints from one place.</p>
                </div>

                <button className="logout-btn" onClick={handleLogout}>
                    <LogOut size={17} /> Logout
                </button>
            </header>

            <div className="student-stats">
                <div className="student-stat-card">
                    <ClipboardList />
                    <div>
                        <h3>My Complaints</h3>
                        <p>View all your reported issues</p>
                    </div>
                </div>

                <div className="student-stat-card">
                    <Clock />
                    <div>
                        <h3>Track Progress</h3>
                        <p>Monitor complaint updates</p>
                    </div>
                </div>

                <div className="student-stat-card">
                    <CheckCircle />
                    <div>
                        <h3>Resolution</h3>
                        <p>See resolved complaints</p>
                    </div>
                </div>
            </div>

            <ComplaintForm
                userId={user.id}
                onSuccess={refreshComplaints}
            />

            <ComplaintList
                userId={user.id}
                refreshKey={refreshKey}
            />
        </div>
    );
}

export default StudentDashboard;
