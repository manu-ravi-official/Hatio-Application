import React, { useState, useEffect } from "react";
import { Button } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import AddProjectModal from "../Project/AddProjectModal";
import { projectList } from "../../api/Api";
import ProjectList from "../Project/ProjectList";
function Home() {
  const [projects, setProjects] = useState([]);
  const [openAddModal, setOpenAddModal] = useState(false);

  useEffect(() => {
    // Fetch initial project list
    const fetchProjects = async () => {
      try {
        const data = await projectList();
        setProjects(data);
      } catch (error) {
        console.error("Error fetching project list:", error);
      }
    };

    fetchProjects();
  }, []);

  const handleOpenAddModal = () => setOpenAddModal(true);
  const handleCloseAddModal = () => setOpenAddModal(false);

  // Callback function to update projects state after saving a project
  const handleSaveProject = async () => {
    try {
      const data = await projectList();
      setProjects(data);
    } catch (error) {
    }
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        marginTop: "10px",
      }}
    >
      <Button
        variant="contained"
        startIcon={<AddIcon />}
        onClick={handleOpenAddModal}
      >
        Add New Project
      </Button>
      <ProjectList projects={projects} />

      <AddProjectModal
        open={openAddModal}
        onClose={handleCloseAddModal}
        onSaveSuccess={handleSaveProject}
      />
    </div>
  );
}

export default Home;
