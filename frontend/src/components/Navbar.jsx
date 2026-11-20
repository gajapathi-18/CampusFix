import { Link } from "react-router-dom";
import { GraduationCap } from "lucide-react";

function Navbar() {
    return (
        <nav className="navbar">
            <Link to="/" className="brand">
                <GraduationCap size={30} />
                <span>CampusFix</span>
            </Link>

            <div className="nav-links">
                <Link to="/">Home</Link>
                <Link to="/login">Login</Link>
                <Link to="/register" className="nav-register">
                    Get Started
                </Link>
            </div>
        </nav>
    );
}

export default Navbar;
