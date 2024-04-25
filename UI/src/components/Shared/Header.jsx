import React from "react";
import { AppBar, Toolbar, Typography, Button } from "@mui/material";

function Header({ logoutCallback }) {
  const handleLogout = async () => {
    try {
      localStorage.removeItem("token");
      logoutCallback("/login");
    } catch (error) {
      console.error("Error logging out:", error);
    }
  };

  return (
    <AppBar position="sticky">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          My App
        </Typography>
        <Button
          color="inherit"
          sx={{
            borderRadius: "unset",
            padding: "0.6em 1.2em",
            fontSize: "inherit",
          }}
          onClick={handleLogout}
        >
          Logout
        </Button>
      </Toolbar>
    </AppBar>
  );
}

export default Header;
