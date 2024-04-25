import React, { createContext, useState, useContext, useEffect } from "react";

export const AuthContext = createContext({
  isLoggedIn: false,
  setIsLoggedIn: () => {},
  handleLogout: () => {}, // New function for logout
});
export const useAuth = () => {
  return useContext(AuthContext);
};
