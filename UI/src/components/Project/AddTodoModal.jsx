import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Modal, Box, Typography, TextField, Button } from "@mui/material";
import { addTask, editTask } from "../../api/Api";

function AddTodoModal({
  open,
  onClose,
  projectId,
  onSaveSuccess,
  taskId,
  initialTitle,
}) {
  const [title, setTitle] = useState(initialTitle || "");
  const [currentTaskId, setCurrentTaskId] = useState(taskId);
  const [error, setError] = useState("");

  useEffect(() => {
    setTitle(initialTitle || "");
    setCurrentTaskId(taskId);
  }, [initialTitle, taskId]);

  const handleSave = async () => {
    if (title.length < 3 || title.length > 20) {
      setError("Task name must be between 3 and 20 characters.");
      return;
    }
    try {
      if (currentTaskId) {
        await editTask({ title }, currentTaskId);
      } else {
        await addTask({ title }, projectId);
      }
      onClose();
      onSaveSuccess();
      setTitle("");
      setCurrentTaskId(null);
      onSaveSuccess();
    } catch (error) {
      console.error("Error saving task:", error);
    }
  };
  const handleTitleChange = (event) => {
    setTitle(event.target.value);

    if (event.target.value.length >= 3) {
      setError("");
    }
    if (event.target.value.length > 20) {
      setError("Task name must be between 3 and 20 characters.");
    }
  };
  return (
    <Modal open={open} onClose={onClose} aria-labelledby="add-task-modal-title">
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
        <Typography id="add-task-modal-title" variant="h6" component="h2">
          {currentTaskId ? "Edit Task" : "Add New Task"}
        </Typography>
        <TextField
          fullWidth
          label="Task Name"
          margin="normal"
          value={title}
          onChange={handleTitleChange}
          error={!!error}
          helperText={error}
        />
        <Button variant="contained" onClick={handleSave}>
          {taskId ? "Save Changes" : "Save Task"}
        </Button>
      </Box>
    </Modal>
  );
}

export default AddTodoModal;
