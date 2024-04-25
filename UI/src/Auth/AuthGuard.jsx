import { Route, Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./AuthContext";

const AuthGuard = ({ children }) => {
  const { isLoggedIn } = useAuth();
  if (!isLoggedIn) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default AuthGuard;
