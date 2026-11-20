import { useEffect, useState } from "react";
import { AlertTriangle, Star } from "lucide-react";
import API from "../api/axiosConfig";

function AdminAlerts() {
    const [escalations, setEscalations] = useState([]);
    const [feedback, setFeedback] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadData = async () => {
            try {
                const [escalationResponse, feedbackResponse] =
                    await Promise.all([
                        API.get("/escalations"),
                        API.get("/feedback")
                    ]);

                setEscalations(
                    Array.isArray(escalationResponse.data)
                        ? escalationResponse.data
                        : []
                );

                setFeedback(
                    Array.isArray(feedbackResponse.data)
                        ? feedbackResponse.data
                        : []
                );
            } catch (err) {
                console.error("Admin alerts error:", err.response?.data || err);
                setError("Some admin information could not be loaded.");
            }
        };

        loadData();
    }, []);

    return (
        <div className="admin-bottom-grid">
            <section className="admin-panel">
                <h2><AlertTriangle size={20} /> Escalations</h2>
                <p className="admin-muted">Complaints requiring attention.</p>

                {escalations.length === 0 && (
                    <div className="admin-empty">No escalations currently.</div>
                )}

                {escalations.map((item, index) => (
                    <div className="admin-alert-item" key={item.escalationId || item.id || index}>
                        <strong>
                            Complaint #{item.complaintId ?? item.complaint?.id ?? "N/A"}
                        </strong>
                        <p>{item.message || "Complaint escalated for attention."}</p>
                        <small>
                            {item.escalatedAt
                                ? new Date(item.escalatedAt).toLocaleString()
                                : "Date unavailable"}
                        </small>
                    </div>
                ))}
            </section>

            <section className="admin-panel">
                <h2><Star size={20} /> Student Feedback</h2>
                <p className="admin-muted">Recent ratings and comments.</p>

                {feedback.length === 0 && (
                    <div className="admin-empty">No feedback submitted yet.</div>
                )}

                {feedback.map((item, index) => (
                    <div className="admin-alert-item" key={item.id || index}>
                        <strong>
                            Complaint #{item.complaintId ?? item.complaint?.id ?? "N/A"}
                        </strong>
                        <p>Rating: {item.rating ?? "N/A"} / 5</p>
                        <p>{item.comment || "No comment provided."}</p>
                    </div>
                ))}
            </section>

            {error && <div className="admin-error">{error}</div>}
        </div>
    );
}

export default AdminAlerts;
