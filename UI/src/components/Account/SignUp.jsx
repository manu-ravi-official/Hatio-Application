import React, { useState } from "react";
import { TextField, Button, Paper, Typography, Box, Snackbar } from "@mui/material";
import { useNavigate, Link } from "react-router-dom";
import { styled } from "@mui/material/styles";
import { signup } from "../../api/Api";
const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(4),
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  maxWidth: 400,
  margin: `${theme.spacing(5)} auto`,
}));

const Signup = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const [usernameError, setUsernameError] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
    if (e.target.value.length < 3 || e.target.value.length > 20) {
      setUsernameError("Username must be between 3 and 20 characters.");
      return;
    } else {
      setUsernameError("");
    }
  };
  const handleEmailChange = (e) => {
    setEmail(e.target.value);
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(e.target.value)) {
      setEmailError("Invalid email format.");
    } else if (e.target.value.length > 255) {
      setEmailError("Email must be less than 255 characters.");
    } else {
      setEmailError("");
    }
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()+\-=_.,]).+$/;
    if (e.target.value.length < 8 || e.target.value.length > 24) {
      setPasswordError("Password must be between 8 and 24 characters.");
      return;
    } else if (!passwordRegex.test(e.target.value)) {
      setPasswordError(
        "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character."
      );
    } else {
      setPasswordError("");
    }
  };
  
  const handleSignup = async (event) => {
    event.preventDefault();

    try {
      await signup(username, email, password);
      navigate("/login");
    } catch (error) {
      console.error("Error signing up:", error);
      if (error.errorCode === "2000") {
        // Show popup message for user/email already exists
        setSnackbarOpen(true);
      }
    }
  };

  return (
    <StyledPaper elevation={2}>
      <Typography component="h1" variant="h5">
        Sign Up
      </Typography>
      <Box component="form" onSubmit={handleSignup} sx={{ mt: 1 }}>
        <TextField
          label="Username"
          variant="outlined"
          value={username}
          onChange={handleUsernameChange}
          margin="normal"
          required
          fullWidth
          helperText={usernameError}
          error={!!usernameError}
        />
        <TextField
          label="Email"
          variant="outlined"
          type="email"
          value={email}
          onChange={handleEmailChange}
          margin="normal"
          required
          fullWidth
          helperText={emailError}
          error={!!emailError}
        />
        <TextField
          label="Password"
          variant="outlined"
          type="password"
          value={password}
          onChange={handlePasswordChange}
          margin="normal"
          required
          helperText={passwordError}
          error={!!passwordError}
          fullWidth
        />
        <Button
          type="submit"
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 3, mb: 2 }}
        >
          Sign Up
        </Button>
        <Typography variant="body2">
          Already have an account?
          <Link to="/login" style={{ marginLeft: 5 }}>
            Login
          </Link>
        </Typography>
      </Box>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={() => setSnackbarOpen(false)}
        message="User or email already exists"
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
      />
    </StyledPaper>
  );
};

export default Signup;
