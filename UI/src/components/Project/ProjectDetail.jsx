import React, { useState, useEffect } from "react";
import Tooltip from "@mui/material/Tooltip";
import {
  projectDetail,
  editProject,
  deleteTask,
  taskStatusToggle,
  exportProject,
  gotoGist,
} from "../../api/Api";
import { useParams, useNavigate } from "react-router-dom";
import VisibilityIcon from "@mui/icons-material/Visibility";
import GetAppIcon from "@mui/icons-material/GetApp";
import {
  Card,
  CardContent,
  Typography,
  IconButton,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  Checkbox,
  IconButton as DeleteIconButton,
  TextField,
  Button,
} from "@mui/material";

import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import AddTodoModal from "./AddTodoModal";
import GlobalConfirmationModal from "../Shared/GlobalConfirmationModal";

const ProjectDetail = () => {
  const { projectId } = useParams();
  const [project, setProject] = useState(null);
  const [editedTitle, setEditedTitle] = useState("");
  const [isEditing, setIsEditing] = useState(false);
  const [editTaskId, setEditTaskId] = useState(null);
  const [editTaskTitle, setEditTaskTitle] = useState(null);
  const [confirmationModalOpen, setConfirmationModalOpen] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  useEffect(() => {
    fetchProjectDetail();
    return () => {};
  }, [projectId]);

  const fetchProjectDetail = async () => {
    try {
      const projectData = await projectDetail(projectId);
      setProject(projectData);
      setEditedTitle(projectData.title);
    } catch (error) {
      console.error("Error fetching project detail:", error);
    }
  };
  const handleOpenAddTodoModal = () => {
    setIsAddTodoModalOpen(true);
  };
  const handleDeleteIconClick = (taskId) => {
    setEditTaskId(taskId);
    setConfirmationModalOpen(true);
  };
  const handleDeleteConfirmation = async () => {
    try {
      await deleteTask(editTaskId);
      fetchProjectDetail();
      setEditTaskId(null);
      setEditTaskTitle("");
    } catch (error) {
      console.error("Error deleting project:", error);
    } finally {
      setConfirmationModalOpen(false);
    }
  };
  const [isAddTodoModalOpen, setIsAddTodoModalOpen] = useState(false);
  const handleOpenEditTodoModal = (taskId, taskTitle) => {
    setEditTaskId(taskId);
    setEditTaskTitle(taskTitle);
    setIsAddTodoModalOpen(true);
  };
  const handleCloseAddTodoModal = () => {
    setEditTaskId(null);
    setEditTaskTitle("");
    setIsAddTodoModalOpen(false);
  };

  const handleBackClick = async (event) => {
    event.preventDefault();
    try {
      navigate("/home");
    } catch (error) {
      console.error("Error redirecti:", error);
    }
  };
  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleTitleChange = (event) => {
    setEditedTitle(event.target.value);

    if (event.target.value.length >= 3) {
      setError("");
    }
    if (event.target.value.length > 20) {
      setError("Project name must be between 3 and 20 characters.");
    }
  };

  const handleSubmit = async () => {
    if (editedTitle.length < 3 || editedTitle.length > 20) {
      setError("Project name must be between 3 and 20 characters.");
      return;
    }
    try {
      await editProject(projectId, {
        title: editedTitle,
      });
      setIsEditing(false);
      // Refresh project detail after editing
      const updatedProject = await projectDetail(projectId);
      setProject(updatedProject);
    } catch (error) {
      console.error("Error editing project:", error);
    }
  };
  const handleTodoChange = async (taskId) => {
    try {
      await taskStatusToggle(taskId);
      fetchProjectDetail();
    } catch (error) {
      console.error("Error updating status:", error);
    }
  };

  const handleExportClick = async () => {
    try {
      await exportProject(projectId);
    } catch (error) {
      console.error("Error exporting project:", error);
    }
  };

  const linktoGist = async () => {
    try {
      const response = await gotoGist(projectId);
    } catch (error) {
      console.error("Error exporting project:", error);
    }
  };

  if (!project) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <IconButton aria-label="back" href="/" onClick={handleBackClick}>
        <ArrowBackIcon />
      </IconButton>
      <Card>
        <CardContent
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Typography variant="body2" color="text.secondary">
            ID: {project.id}
          </Typography>
          {isEditing ? (
            <TextField
              fullWidth
              label="Title"
              value={editedTitle}
              onChange={handleTitleChange}
              error={!!error}
              helperText={error}
              sx={{ py: 2, mt: 2 }} // Adds vertical padding and margin
            />
          ) : (
            <>
              <Typography
                variant="h5"
                component="div"
                sx={{ display: "flex", alignItems: "center" }}
              >
                {project.title}
                <IconButton
                  aria-label="edit"
                  onClick={handleEditClick}
                  sx={{ ml: 2 }}
                >
                  <EditIcon />
                </IconButton>
              </Typography>
            </>
          )}
          <div
            sx={{
              display: "flex",
              justifyContent: "space-between",
              mt: 2,
              padding: "16px",
            }}
          >
            {" "}
            {isEditing ? (
              <Button
                variant="contained"
                onClick={handleSubmit}
                sx={{ width: "48%" }}
              >
                Submit
              </Button>
            ) : (
              <>
                <Button
                  variant="outlined"
                  onClick={handleOpenAddTodoModal}
                  sx={{ mr: 2 }}
                >
                  {" "}
                  Add Task
                </Button>
                <Button
                  variant="outlined"
                  startIcon={<GetAppIcon />}
                  onClick={handleExportClick}
                  sx={{ mr: 2 }}
                >
                  Export
                </Button>
                <Button
                  variant="outlined"
                  startIcon={<VisibilityIcon />}
                  onClick={linktoGist}
                >
                  View Gist
                </Button>
              </>
            )}
          </div>
        </CardContent>
      </Card>

      {/* Tasks */}
      <div style={{ display: "flex", width: "800px", margin: "0 auto" }}>
        <div style={{ width: "400px", padding: "16px" }}>
          <Typography variant="h6" gutterBottom>
            Pending Tasks
          </Typography>
          <List>
            {project.todos
              .filter((todo) => todo.status === "PENDING")
              .map((todo) => (
                <div key={todo.id}>
                  <Card variant="outlined" style={{ marginBottom: "10px" }}>
                    <div>
                      <ListItem>
                        <Tooltip title="Mark as Completed" placement="top">
                          <Checkbox
                            edge="start"
                            checked={todo.status !== "PENDING"}
                            disableRipple
                            onChange={() => handleTodoChange(todo.id)}
                          />
                        </Tooltip>
                        <ListItemText
                          primary={todo.title || "Untitled Todo"}
                          style={{ padding: "20px" }}
                        />

                        <ListItemSecondaryAction>
                          <IconButton
                            edge="end"
                            aria-label="edit"
                            onClick={() =>
                              handleOpenEditTodoModal(todo.id, todo.title)
                            }
                          >
                            <EditIcon />
                          </IconButton>
                          <DeleteIconButton
                            edge="end"
                            aria-label="delete"
                            onClick={() => handleDeleteIconClick(todo.id)}
                          >
                            <DeleteIcon />
                          </DeleteIconButton>
                        </ListItemSecondaryAction>
                      </ListItem>
                    </div>
                  </Card>
                </div>
              ))}
          </List>
        </div>
        <div style={{ width: "400px", flex: 1, padding: "16px" }}>
          <Typography variant="h6" gutterBottom>
            Completed Tasks
          </Typography>
          <List>
            {project.todos
              .filter((todo) => todo.status === "COMPLETED")
              .map((todo) => (
                <div key={todo.id}>
                  <Card variant="outlined" style={{ marginBottom: "10px" }}>
                    <div>
                      <ListItem>
                        <Tooltip title="Mark as Pending" placement="top">
                          <Checkbox
                            edge="start"
                            checked={todo.status !== "PENDING"}
                            disableRipple
                            onChange={() => handleTodoChange(todo.id)}
                          />
                        </Tooltip>
                        <ListItemText
                          primary={todo.title || "Untitled Todo"}
                          style={{ padding: "20px" }}
                        />

                        <ListItemSecondaryAction>
                          <IconButton
                            edge="end"
                            aria-label="edit"
                            onClick={() =>
                              handleOpenEditTodoModal(todo.id, todo.title)
                            }
                          >
                            <EditIcon />
                          </IconButton>
                          <DeleteIconButton
                            edge="end"
                            aria-label="delete"
                            onClick={() => handleDeleteIconClick(todo.id)}
                          >
                            <DeleteIcon />
                          </DeleteIconButton>
                        </ListItemSecondaryAction>
                      </ListItem>
                    </div>
                  </Card>
                </div>
              ))}
          </List>
        </div>
      </div>

      <AddTodoModal
        open={isAddTodoModalOpen}
        onClose={handleCloseAddTodoModal}
        projectId={project.id}
        taskId={editTaskId}
        initialTitle={editTaskTitle}
        onSaveSuccess={fetchProjectDetail}
      />
      <GlobalConfirmationModal
        open={confirmationModalOpen}
        onClose={() => setConfirmationModalOpen(false)}
        onConfirm={handleDeleteConfirmation}
        message="Are you sure you want to delete this project?"
      />
    </div>
  );
};

export default ProjectDetail;
