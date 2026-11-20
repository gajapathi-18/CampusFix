import { useState } from "react";
import API from "../api/axiosConfig";

function ComplaintForm({ userId, onSuccess }) {
    const [form, setForm] = useState({
        title: "",
        description: "",
        category: "Electrical",
        location: "",
        priority: "MEDIUM"
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            await API.post("/complaints", {
                ...form,
                userId
            });

            alert("Complaint submitted successfully!");

            setForm({
                title: "",
                description: "",
                category: "Electrical",
                location: "",
                priority: "MEDIUM"
            });

            onSuccess();
        } catch (err) {
            console.error("Complaint submission error:", err.response?.data || err);
            const data = err.response?.data;
            setError(
                typeof data === "string"
                    ? data
                    : data?.message || "Unable to submit complaint."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="student-card">
            <h2>Submit a Complaint</h2>
            <p className="student-muted">
                Tell us about the issue on your campus.
            </p>

            {error && <div className="student-error">{error}</div>}

            <form className="complaint-form" onSubmit={handleSubmit}>
                <label>Complaint Title</label>
                <input
                    name="title"
                    value={form.title}
                    onChange={handleChange}
                    placeholder="Example: Classroom fan not working"
                    required
                />

                <label>Description</label>
                <textarea
                    name="description"
                    value={form.description}
                    onChange={handleChange}
                    placeholder="Describe the problem..."
                    rows="4"
                    required
                />

                <label>Category</label>
                <select
                    name="category"
                    value={form.category}
                    onChange={handleChange}
                >
                    <option>Electrical</option>
                    <option>IT</option>
                    <option>Plumbing</option>
                    <option>Civil</option>
                    <option>Other</option>
                </select>

                <label>Location</label>
                <input
                    name="location"
                    value={form.location}
                    onChange={handleChange}
                    placeholder="Example: Block A, Room 203"
                    required
                />

                <label>Priority</label>
                <select
                    name="priority"
                    value={form.priority}
                    onChange={handleChange}
                >
                    <option value="LOW">Low</option>
                    <option value="MEDIUM">Medium</option>
                    <option value="HIGH">High</option>
                    <option value="URGENT">Urgent</option>
                </select>

                <button className="student-primary-btn" disabled={loading}>
                    {loading ? "Submitting..." : "Submit Complaint"}
                </button>
            </form>
        </div>
    );
}

export default ComplaintForm;
