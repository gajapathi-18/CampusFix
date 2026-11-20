import {
    Wrench,
    ClipboardList,
    ShieldCheck,
    ArrowRight,
    Clock,
    CheckCircle
} from "lucide-react";
import { Link } from "react-router-dom";

function Home() {
    const features = [
        {
            icon: <ClipboardList size={28} />,
            title: "Easy Complaint Submission",
            description:
                "Submit campus complaints with category, location and priority."
        },
        {
            icon: <Wrench size={28} />,
            title: "Smart Department Routing",
            description:
                "Complaints are automatically directed to the relevant department."
        },
        {
            icon: <ShieldCheck size={28} />,
            title: "Transparent Tracking",
            description:
                "Track complaint progress from submission to resolution."
        },
        {
            icon: <Clock size={28} />,
            title: "Automatic Escalation",
            description:
                "Unresolved high-priority complaints can be escalated automatically."
        }
    ];

    return (
        <div>
            <section className="hero">
                <div className="hero-content">
                    <span className="hero-tag">
                        SMART CAMPUS MANAGEMENT SYSTEM
                    </span>

                    <h1>
                        A Better Campus Starts
                        <span> With Your Voice.</span>
                    </h1>

                    <p>
                        CampusFix makes it easy for students and staff to report
                        campus issues, track complaints and get problems
                        resolved efficiently.
                    </p>

                    <div className="hero-buttons">
                        <Link to="/register" className="btn-primary">
                            Report an Issue <ArrowRight size={18} />
                        </Link>

                        <Link to="/login" className="btn-secondary">
                            Login
                        </Link>
                    </div>

                    <div className="hero-note">
                        <CheckCircle size={17} />
                        Simple. Transparent. Efficient.
                    </div>
                </div>

                <div className="hero-visual">
                    <div className="visual-card">
                        <div className="visual-icon">
                            <Wrench size={48} />
                        </div>
                        <h3>CampusFix</h3>
                        <p>Making campus maintenance smarter</p>
                        <div className="visual-status">
                            <span></span> Complaint management simplified
                        </div>
                    </div>
                </div>
            </section>

            <section className="features-section">
                <div className="section-heading">
                    <span>OUR FEATURES</span>
                    <h2>Everything Your Campus Needs</h2>
                    <p>
                        One platform to report, manage and resolve campus
                        maintenance issues.
                    </p>
                </div>

                <div className="features-grid">
                    {features.map((feature, index) => (
                        <div className="feature-card" key={index}>
                            <div className="feature-icon">
                                {feature.icon}
                            </div>
                            <h3>{feature.title}</h3>
                            <p>{feature.description}</p>
                        </div>
                    ))}
                </div>
            </section>

            <footer className="footer">
                <p>© 2026 CampusFix. Smart Campus Complaint Management.</p>
            </footer>
        </div>
    );
}

export default Home;
