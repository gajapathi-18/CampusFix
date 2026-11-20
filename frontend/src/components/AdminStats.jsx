import {
    ClipboardList,
    Clock,
    CheckCircle,
    Users,
    AlertTriangle,
    Star
} from "lucide-react";

function AdminStats({ data }) {
    const stats = [
        {
            title: "Total Complaints",
            value: data?.totalComplaints ?? 0,
            icon: ClipboardList
        },
        {
            title: "Submitted",
            value: data?.submittedComplaints ?? 0,
            icon: Clock
        },
        {
            title: "Resolved",
            value: data?.resolvedComplaints ?? 0,
            icon: CheckCircle
        },
        {
            title: "High / Urgent",
            value: (data?.highPriorityComplaints ?? 0)
                + (data?.urgentPriorityComplaints ?? 0),
            icon: AlertTriangle
        },
        {
            title: "Active Users",
            value: data?.totalUsers ?? 0,
            icon: Users
        },
        {
            title: "Average Rating",
            value: Number(data?.averageRating ?? 0).toFixed(1),
            icon: Star
        }
    ];

    return (
        <div className="admin-stats-grid">
            {stats.map((item) => {
                const Icon = item.icon;

                return (
                    <div className="admin-stat-card" key={item.title}>
                        <div className="admin-stat-icon">
                            <Icon size={25} />
                        </div>
                        <div>
                            <p>{item.title}</p>
                            <h2>{item.value}</h2>
                        </div>
                    </div>
                );
            })}
        </div>
    );
}

export default AdminStats;
