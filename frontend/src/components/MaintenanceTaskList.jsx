import { useState } from "react";
import { MapPin, CalendarDays } from "lucide-react";
import API from "../api/axiosConfig";

function MaintenanceTaskList({ tasks, staffId, onRefresh }) {
    const [updatingId, setUpdatingId] = useState(null);
    const [error, setError] = useState("");

    const updateStatus = async (complaintId, status) => {
        try {
            setUpdatingId(complaintId);
            setError("");

            await API.patch(`/complaints/${complaintId}/status`, {
                status,
                updatedByUserId: staffId
            });

            await onRefresh();
        } catch (err) {
            console.error("Status update error:", err.response?.data || err);
            setError(
                err.response?.data?.message ||
                "Could not update status. Check the backend response."
            );
        } finally {
            setUpdatingId(null);
        }
    };

    if (!tasks || tasks.length === 0) {
        return (
            <div className="maintenance-empty">
                <h3>No assigned tasks</h3>
                <p>New complaints assigned to you will appear here.</p>
            </div>
        );
    }

    return (
        <div className="maintenance-task-list">
            {error && <div className="maintenance-error">{error}</div>}
            {tasks.map((complaint, index) => {
                const id = complaint.id || complaint.complaintId || index;
                const status = complaint.status || "ASSIGNED";
                const terminal = ["RESOLVED", "REJECTED", "CLOSED"].includes(status);

                return (
                    <article className="maintenance-task" key={id}>
                        <div className="maintenance-task-heading">
                            <div>
                                <span className="maintenance-task-id">
                                    COMPLAINT #{id}
                                </span>
                                <h3>{complaint.title || "Campus issue"}</h3>
                            </div>

                            <span className={`maintenance-status status-${status.toLowerCase()}`}>
                                {status.replace("_", " ")}
                            </span>
                        </div>

                        <p className="maintenance-description">
                            {complaint.description || "No description provided."}
                        </p>

                        <div className="maintenance-task-meta">
                            <span>
                                <MapPin size={15} />
                                {complaint.location || "Location unavailable"}
                            </span>
                            <span>Category: {complaint.category || "N/A"}</span>
                            <span>Priority: {complaint.priority || "N/A"}</span>
                            <span>Department: {complaint.departmentName || "N/A"}</span>
                        </div>

                        <div className="maintenance-task-footer">
                            <span>
                                <CalendarDays size={15} />
                                {complaint.createdAt
                                    ? new Date(complaint.createdAt).toLocaleDateString()
                                    : "Date unavailable"}
                            </span>

                            {!terminal && (
                                <div className="maintenance-task-actions">
                                    {status === "ASSIGNED" && (
                                        <button
                                            disabled={updatingId === id}
                                            onClick={() => updateStatus(id, "IN_PROGRESS")}
                                        >
                                            {updatingId === id ? "Updating..." : "Start Work"}
                                        </button>
                                    )}

                                    {status === "IN_PROGRESS" && (
                                        <button
                                            disabled={updatingId === id}
                                            onClick={() => updateStatus(id, "RESOLVED")}
                                        >
                                            {updatingId === id ? "Updating..." : "Mark Resolved"}
                                        </button>
                                    )}
                                </div>
                            )}
                        </div>
                    </article>
                );
            })}
        </div>
    );
}

export default MaintenanceTaskList;
