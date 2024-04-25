import React, { useState } from "react";
import { Modal, Box, Typography, TextField, Button } from "@mui/material";
import { addProject } from "../../api/Api";

function AddProjectModal({ open, onClose, onSaveSuccess }) {
  const [title, setTitle] = useState("");
  const [error, setError] = useState("");
  const handleSave = async () => {
    if (title.length < 3 || title.length > 20) {
      setError("Project name must be between 3 and 20 characters.");
      return;
    }
    try {
      await addProject(title);
      onSaveSuccess();
      onClose();
      setTitle("");
      
    } catch (error) {
      console.error("Error adding project:", error);
    }
  };
  const handleInputChange = (e) => {
    const inputTitle = e.target.value;
    setTitle(inputTitle);

    if (inputTitle.length >= 3) {
      setError("");
    }
    if (inputTitle.length > 20) {
      setError("Project name must be between 3 and 20 characters.");
    }
  };
  return (
    <Modal
      open={open}
      onClose={onClose}
      aria-labelledby="add-project-modal-title"
    >
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: 400,
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
        }}
      >
        <Typography id="add-project-modal-title" variant="h6" component="h2">
          Add New Project
        </Typography>
        <TextField
          fullWidth
          label="Project Name"
          margin="normal"
          value={title}
          error={!!error}
          helperText={error}
          onChange={handleInputChange}
        />

        <Button variant="contained" onClick={handleSave}>
          Save Project
        </Button>
      </Box>
    </Modal>
  );
}

export default AddProjectModal;
