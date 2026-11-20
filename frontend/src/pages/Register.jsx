import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { UserPlus } from "lucide-react";
import { useAuth } from "../context/useAuth";

function Register() {
    const { register } = useAuth();
    const navigate = useNavigate();

    const [form, setForm] = useState({
        name: "",
        email: "",
        password: "",
        phone: "",
        role: "STUDENT",
        departmentId: ""
    });

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        const requestData = {
            name: form.name,
            email: form.email,
            password: form.password,
            phone: form.phone,
            role: form.role,
            departmentId: form.departmentId
                ? Number(form.departmentId)
                : null
        };

        try {
            await register(requestData);
            alert("Registration successful! Please login.");
            navigate("/login");
        } catch (err) {
            console.error("Registration error:", err.response?.data || err);

            const data = err.response?.data;

            let message = "Registration failed.";

            if (typeof data === "string") {
                message = data;
            } else if (data?.message) {
                message = data.message;
            } else if (data?.error) {
                message = data.error;
            } else if (data) {
                message = JSON.stringify(data);
            } else if (err.message) {
                message = err.message;
            }

            setError(message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-page">
            <div className="auth-card register-card">
                <div className="auth-icon">
                    <UserPlus size={30} />
                </div>

                <h1>Create Account</h1>
                <p className="auth-subtitle">
                    Join the CampusFix community
                </p>

                {error && <div className="auth-error">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <label>Full Name</label>
                    <input
                        className="auth-field"
                        name="name"
                        placeholder="Enter your name"
                        value={form.name}
                        onChange={handleChange}
                        required
                    />

                    <label>Email Address</label>
                    <input
                        className="auth-field"
                        type="email"
                        name="email"
                        placeholder="Enter your email"
                        value={form.email}
                        onChange={handleChange}
                        required
                    />

                    <label>Password</label>
                    <input
                        className="auth-field"
                        type="password"
                        name="password"
                        placeholder="Create a password"
                        value={form.password}
                        onChange={handleChange}
                        minLength={8}
                        required
                    />

                    <label>Phone Number</label>
                    <input
                        className="auth-field"
                        type="tel"
                        name="phone"
                        placeholder="Enter your phone number"
                        value={form.phone}
                        onChange={handleChange}
                    />

                    <label>Account Type</label>
                    <select
                        className="auth-field"
                        name="role"
                        value={form.role}
                        onChange={handleChange}
                    >
                        <option value="STUDENT">Student</option>
                        <option value="STAFF">Staff</option>
                    </select>

                    <label>Department ID (optional)</label>
                    <input
                        className="auth-field"
                        type="number"
                        name="departmentId"
                        placeholder="Enter department ID if applicable"
                        value={form.departmentId}
                        onChange={handleChange}
                        min="1"
                    />

                    <button className="auth-button" disabled={loading}>
                        {loading ? "Creating Account..." : "Register"}
                    </button>
                </form>

                <p className="auth-bottom">
                    Already have an account? <Link to="/login">Login</Link>
                </p>
            </div>
        </div>
    );
}

export default Register;
