import { Navigate } from "react-router-dom";
import { useUserData } from "./userService";

const ProtectedRoute = ({ element, requiredRole }) => {
    const { userRole } = useUserData();

    if (userRole === requiredRole) {
        return <Navigate to="/404" />;
    } else {
        return element;
    }
};

export default ProtectedRoute;
