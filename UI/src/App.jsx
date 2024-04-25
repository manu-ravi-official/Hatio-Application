import { useState,useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import Login from "./components/Account/Login";
import SignUp from "./components/Account/SignUp";
import Home from "./components/Home/Home";
import ProjectDetail from "./components/Project/ProjectDetail";
import Header from "./components/Shared/Header";
import AuthGuard from "./Auth/AuthGuard";
import { AuthContext } from "./Auth/AuthContext";
function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('token')); // Check for token initially

  useEffect(() => {
    const handleStorageChange = () => {
      setIsLoggedIn(!!localStorage.getItem('token'));
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);
  const handleLogout = async () => {
    try {
      localStorage.removeItem('token');
      setIsLoggedIn(false); // Update isLoggedIn state after logout
      // Optionally handle redirect to login page here
    } catch (error) {
      console.error('Error logging out:', error);
      // Handle error if needed
    }
  };
  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>
      <BrowserRouter>
        {/* Header is now conditionally rendered based on isLoggedIn */}
        {isLoggedIn && <Header logoutCallback={handleLogout} />}


        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />

          {/* Protected routes with AuthGuard */}
          <Route
            path="/home"
            element={<AuthGuard><Home /></AuthGuard>} // Ensure authentication for Home
          />
          <Route
            path="/projects/:projectId"
            element={<AuthGuard><ProjectDetail /></AuthGuard>} // Ensure authentication for ProjectDetail
          />
        </Routes>
      </BrowserRouter>
    </AuthContext.Provider>
  );
}

export default App;