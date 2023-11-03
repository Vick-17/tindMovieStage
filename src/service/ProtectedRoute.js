import { Navigate } from "react-router-dom";
import { useUserData } from "./userService";

const ProtectedRoute = ({ element, requiredRole }) => {
    const { userRole } = useUserData();

    if (userRole === requiredRole) {
        return element;
    } else {
        return <Navigate to="/404" />;
    }
};

export default ProtectedRoute;
