import React, { useContext, useEffect, useState } from "react";
import { TextField, Button, Paper, Typography, Box,Snackbar } from "@mui/material";
import { useNavigate, Link } from "react-router-dom";
import { styled } from "@mui/material/styles";
import { login } from "../../api/Api";
import { AuthContext } from "../../Auth/AuthContext";
const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(4),
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  maxWidth: 400,
  margin: `${theme.spacing(5)} auto`,
}));

const Login = () => {
  const [email, setEmail] = useState("");
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const [snackbarOpen, setSnackbarOpen] = useState(false);

  useEffect(() => {
    if (isLoggedIn) {
      navigate("/home");
    }
  }, [isLoggedIn]);
  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      await login(email, password);
      setIsLoggedIn(true);
      navigate("/home");
    } catch (error) {
      if (error.errorCode === "2003") {
        // Show popup message forincorrect login
        setSnackbarOpen(true);
      }
    }
  };

  return (
    <StyledPaper elevation={2}>
      <Typography component="h1" variant="h5">
        Login
      </Typography>
      <Box component="form" onSubmit={handleLogin} sx={{ mt: 1 }}>
        <TextField
          label="Email"
          variant="outlined"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          margin="normal"
          required
          fullWidth
        />
        <TextField
          label="Password"
          variant="outlined"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          margin="normal"
          required
          fullWidth
        />
        <Button
          type="submit"
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 3, mb: 2 }}
        >
          Login
        </Button>
        <Typography variant="body2">
          Don't have an account?
          <Link to="/signup" style={{ marginLeft: 5 }}>
            Sign up
          </Link>
        </Typography>
      </Box>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={() => setSnackbarOpen(false)}
        message="Email or password incorrect"
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
      />
    </StyledPaper>
  );
};

export default Login;
