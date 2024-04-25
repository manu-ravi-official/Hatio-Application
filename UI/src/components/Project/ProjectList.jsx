import React, { useState, useEffect } from "react";
import {
  List,
  Card,
  ListItem,
  ListItemText,
  IconButton,
  Divider,
} from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import DeleteIcon from "@mui/icons-material/Delete";
import GlobalConfirmationModal from "../Shared/GlobalConfirmationModal";
import { projectList, deleteProject } from "../../api/Api";
import { useNavigate } from "react-router-dom";

const ProjectList = ({ projects }) => {
  const navigate = useNavigate();
  const [projectData, setProjectData] = useState([]);
  const [selectedProjectId, setSelectedProjectId] = useState(null);
  const [confirmationModalOpen, setConfirmationModalOpen] = useState(false);

  useEffect(() => {
    setProjectData(projects);
  }, [projects]);

  const fetchProjects = async () => {
    try {
      const data = await projectList();
      setProjectData(data);
    } catch (error) {
      console.error("Error fetching project list:", error);
    }
  };

  const handleDeleteIconClick = (projectId) => {
    setSelectedProjectId(projectId);
    setConfirmationModalOpen(true);
  };

  const handleDeleteConfirmation = async () => {
    try {
      await deleteProject(selectedProjectId);
      fetchProjects();
    } catch (error) {
      console.error("Error deleting project:", error);
    } finally {
      setConfirmationModalOpen(false);
    }
  };
  const handleViewProject = (projectId) => {
    navigate(`/projects/${projectId}`);
  };
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <List>
        {projectData.map((project) => (
          <div key={project.id}>
            <Card variant="outlined" style={{ marginBottom: "10px" }}>
              <ListItem>
                <ListItemText
                  primary={project.title}
                  secondary={`ID: ${project.id}`}
                />
                <div>
                  <IconButton
                    aria-label="view"
                    onClick={() => handleViewProject(project.id)}
                  >
                    <VisibilityIcon />
                  </IconButton>
                  <IconButton
                    aria-label="delete"
                    onClick={() => handleDeleteIconClick(project.id)}
                  >
                    <DeleteIcon />
                  </IconButton>
                </div>
              </ListItem>
            </Card>
            <Divider />
          </div>
        ))}
      </List>

      <GlobalConfirmationModal
        open={confirmationModalOpen}
        onClose={() => setConfirmationModalOpen(false)}
        onConfirm={handleDeleteConfirmation}
        message="Are you sure you want to delete this project?"
      />
    </div>
  );
};

export default ProjectList;
