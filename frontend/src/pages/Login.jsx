import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { LogIn, Mail, Lock } from "lucide-react";
import { useAuth } from "../context/useAuth";

function Login() {
    const { login } = useAuth();
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            const loggedInUser = await login(email, password);

            switch (loggedInUser.role) {
                case "ADMIN":
                    navigate("/admin/dashboard");
                    break;
                case "MAINTENANCE":
                    navigate("/maintenance/dashboard");
                    break;
                default:
                    navigate("/student/dashboard");
            }
        } catch (err) {
            setError(
                err.response?.data?.message ||
                err.response?.data ||
                "Login failed. Check your email and password."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-page">
            <div className="auth-card">
                <div className="auth-icon">
                    <LogIn size={30} />
                </div>

                <h1>Welcome Back</h1>
                <p className="auth-subtitle">
                    Login to your CampusFix account
                </p>

                {error && <div className="auth-error">{String(error)}</div>}

                <form onSubmit={handleSubmit}>
                    <label>Email Address</label>
                    <div className="auth-input">
                        <Mail size={19} />
                        <input
                            type="email"
                            placeholder="Enter your email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <label>Password</label>
                    <div className="auth-input">
                        <Lock size={19} />
                        <input
                            type="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button className="auth-button" disabled={loading}>
                        {loading ? "Logging in..." : "Login"}
                    </button>
                </form>

                <p className="auth-bottom">
                    Don't have an account? <Link to="/register">Register</Link>
                </p>
            </div>
        </div>
    );
}

export default Login;
