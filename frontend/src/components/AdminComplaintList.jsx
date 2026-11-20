import { useCallback, useEffect, useState } from "react";
import API from "../api/axiosConfig";

function AdminComplaintList({ refreshKey, adminId }) {
    const [complaints, setComplaints] = useState([]);
    const [status, setStatus] = useState("");
    const [priority, setPriority] = useState("");
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [staffIds, setStaffIds] = useState({});

    const fetchComplaints = useCallback(async () => {
        setLoading(true);
        setError("");

        try {
            const params = {};
            if (status) params.status = status;
            if (priority) params.priority = priority;

            const response = await API.get("/admin/complaints", { params });
            setComplaints(Array.isArray(response.data) ? response.data : []);
        } catch (err) {
            console.error("Admin complaints error:", err.response?.data || err);
            setError("Unable to load complaints.");
        } finally {
            setLoading(false);
        }
    }, [status, priority]);

    useEffect(() => {
        const timer = setTimeout(fetchComplaints, 0);
        return () => clearTimeout(timer);
    }, [fetchComplaints, refreshKey]);

    const updatePriority = async (complaintId, newPriority) => {
        try {
            await API.patch(`/admin/complaints/${complaintId}/priority`, {
                priority: newPriority
            });
            await fetchComplaints();
        } catch (err) {
            console.error("Priority update error:", err.response?.data || err);
            setError("Unable to update priority. Check the backend response.");
        }
    };

    const assignComplaint = async (complaintId) => {
        const maintenanceUserId = Number(staffIds[complaintId]);

        if (!maintenanceUserId || maintenanceUserId < 1) {
            setError("Enter a valid maintenance staff user ID.");
            return;
        }

        try {
            await API.post(`/complaints/${complaintId}/assign`, {
                maintenanceUserId,
                assignedByUserId: adminId
            });

            setError("");
            await fetchComplaints();
        } catch (err) {
            console.error("Assignment error:", err.response?.data || err);
            setError("Unable to assign complaint. Check the staff ID and backend response.");
        }
    };

    return (
        <section className="admin-panel">
            <div className="admin-panel-heading">
                <div>
                    <h2>Complaint Management</h2>
                    <p>Review, prioritize and assign campus complaints.</p>
                </div>
            </div>

            <div className="admin-filters">
                <select
                    aria-label="Filter by status"
                    value={status}
                    onChange={(e) => setStatus(e.target.value)}
                >
                    <option value="">All Statuses</option>
                    <option value="SUBMITTED">Submitted</option>
                    <option value="ASSIGNED">Assigned</option>
                    <option value="IN_PROGRESS">In Progress</option>
                    <option value="RESOLVED">Resolved</option>
                    <option value="REJECTED">Rejected</option>
                    <option value="CLOSED">Closed</option>
                </select>

                <select
                    aria-label="Filter by priority"
                    value={priority}
                    onChange={(e) => setPriority(e.target.value)}
                >
                    <option value="">All Priorities</option>
                    <option value="LOW">Low</option>
                    <option value="MEDIUM">Medium</option>
                    <option value="HIGH">High</option>
                    <option value="URGENT">Urgent</option>
                </select>

                <button type="button" onClick={fetchComplaints}>Refresh</button>
            </div>

            {loading && <p>Loading complaints...</p>}
            {error && <div className="admin-error">{error}</div>}

            {!loading && !error && complaints.length === 0 && (
                <div className="admin-empty">No complaints found.</div>
            )}

            {!loading && complaints.map((complaint) => (
                <article className="admin-complaint" key={complaint.id}>
                    <div className="admin-complaint-top">
                        <div>
                            <h3>{complaint.title}</h3>
                            <span>Complaint #{complaint.id}</span>
                        </div>
                        <span className={`admin-status ${(complaint.status || "").toLowerCase()}`}>
                            {complaint.status}
                        </span>
                    </div>

                    <p className="admin-description">{complaint.description}</p>

                    <div className="admin-complaint-info">
                        <span>Category: {complaint.category}</span>
                        <span>Location: {complaint.location}</span>
                        <span>Department: {complaint.departmentName || "Not assigned"}</span>
                        <span>Priority: {complaint.priority}</span>
                        <span>Student: {complaint.userName || "Student"}</span>
                    </div>

                    <div className="admin-actions">
                        <div>
                            <label htmlFor={`priority-${complaint.id}`}>Change Priority</label>
                            <select
                                id={`priority-${complaint.id}`}
                                value={complaint.priority}
                                onChange={(e) => updatePriority(complaint.id, e.target.value)}
                                disabled={["RESOLVED", "REJECTED", "CLOSED"].includes(complaint.status)}
                            >
                                <option value="LOW">Low</option>
                                <option value="MEDIUM">Medium</option>
                                <option value="HIGH">High</option>
                                <option value="URGENT">Urgent</option>
                            </select>
                        </div>

                        {!["RESOLVED", "REJECTED", "CLOSED"].includes(complaint.status) && (
                            <div>
                                <label htmlFor={`staff-${complaint.id}`}>Maintenance Staff User ID</label>
                                <div className="admin-assign-control">
                                    <input
                                        id={`staff-${complaint.id}`}
                                        type="number"
                                        min="1"
                                        placeholder="Enter staff ID"
                                        value={staffIds[complaint.id] || ""}
                                        onChange={(e) => setStaffIds({
                                            ...staffIds,
                                            [complaint.id]: e.target.value
                                        })}
                                    />
                                    <button
                                        type="button"
                                        onClick={() => assignComplaint(complaint.id)}
                                    >
                                        Assign
                                    </button>
                                </div>
                            </div>
                        )}
                    </div>
                </article>
            ))}
        </section>
    );
}

export default AdminComplaintList;
