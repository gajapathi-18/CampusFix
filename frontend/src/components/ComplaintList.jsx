import { useCallback, useEffect, useState } from "react";
import { RefreshCw } from "lucide-react";
import API from "../api/axiosConfig";

function ComplaintList({ userId, refreshKey }) {
    const [complaints, setComplaints] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const fetchComplaints = useCallback(async () => {
        try {
            const response = await API.get(`/complaints/user/${userId}`);
            setComplaints(Array.isArray(response.data) ? response.data : []);
            setError("");
        } catch (err) {
            console.error("Complaint loading error:", err.response?.data || err);
            setError("Unable to load your complaints.");
        } finally {
            setLoading(false);
        }
    }, [userId]);

    useEffect(() => {
        const timer = setTimeout(fetchComplaints, 0);
        return () => clearTimeout(timer);
    }, [fetchComplaints, refreshKey]);

    const refreshComplaints = () => {
        setLoading(true);
        fetchComplaints();
    };

    const getStatusClass = (status) => {
        return `status-badge status-${(status || "submitted").toLowerCase()}`;
    };

    if (loading) return <div className="student-card">Loading complaints...</div>;

    return (
        <div className="student-card">
            <div className="complaint-list-heading">
                <div>
                    <h2>My Complaints</h2>
                    <p className="student-muted">
                        Track the progress of your reported issues.
                    </p>
                </div>

                <button
                    type="button"
                    className="refresh-btn"
                    onClick={refreshComplaints}
                    title="Refresh complaints"
                >
                    <RefreshCw size={18} />
                </button>
            </div>

            {error && <div className="student-error">{error}</div>}

            {!error && complaints.length === 0 && (
                <div className="empty-state">
                    <h3>No complaints yet</h3>
                    <p>Your submitted complaints will appear here.</p>
                </div>
            )}

            <div className="complaint-items">
                {complaints.map((complaint) => (
                    <div className="complaint-item" key={complaint.id}>
                        <div className="complaint-item-top">
                            <div>
                                <h3>{complaint.title}</h3>
                                <span className="complaint-id">
                                    Complaint #{complaint.id}
                                </span>
                            </div>

                            <span className={getStatusClass(complaint.status)}>
                                {(complaint.status || "SUBMITTED").replace("_", " ")}
                            </span>
                        </div>

                        <p className="complaint-description">
                            {complaint.description}
                        </p>

                        <div className="complaint-meta">
                            <span>Category: {complaint.category}</span>
                            <span>Location: {complaint.location}</span>
                            <span>Priority: {complaint.priority}</span>
                            <span>Department: {complaint.departmentName || "Pending"}</span>
                        </div>

                        <p className="complaint-date">
                            Submitted: {complaint.createdAt
                                ? new Date(complaint.createdAt).toLocaleString()
                                : "Date unavailable"}
                        </p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ComplaintList;
