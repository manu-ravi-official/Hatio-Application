import axios from "axios";

const API_BASE_URL = "http://localhost:8080"; // Backend url

// Set authorization header globally
const setAuthorizationHeader = () => {
  const token = localStorage.getItem("token");
  if (token) {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } else {
    // If no token is found, remove the authorization header
    delete axios.defaults.headers.common["Authorization"];
  }
};

// Set authorization header after successful login
setAuthorizationHeader();

// Function to handle login
export const login = async (email, password) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/api/users/login`, {
      email,
      password,
    });
    const { token } = response.data;
    localStorage.setItem("token", token);
    setAuthorizationHeader();
    return token;
  } catch (error) {
    throw error.response.data;
  }
};

// Function to handle signup
export const signup = async (username, email, password) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/api/users/signup`, {
      username,
      email,
      password,
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Function to fetch project list
export const projectList = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/projects`);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Add a new project
export const addProject = async (title) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/projects`, {
      title,
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Add a new task
export const addTask = async ({ title }, projectId) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/task/${projectId}`, {
      title,
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Function to update task
export const editTask = async ({ title }, taskId) => {
  try {
    const response = await axios.put(`${API_BASE_URL}/task/${taskId}`, {
      title,
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Function to toggle status
export const taskStatusToggle = async (taskId) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/task/toggle/${taskId}`);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Function to delete task
export const deleteTask = async (taskId) => {
  try {
    const response = await axios.delete(`${API_BASE_URL}/task/${taskId}`);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Delete a project
export const deleteProject = async (projectId) => {
  try {
    const response = await axios.delete(
      `${API_BASE_URL}/projects/${projectId}`
    );
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Function to fetch project detail
export const projectDetail = async (projectId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/projects/${projectId}`);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Function to update project detail
export const editProject = async (projectId, { title }) => {
  try {
    const response = await axios.put(`${API_BASE_URL}/projects/${projectId}`, {
      title,
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// Function to download .md file
const downloadMdFile = (fileName, content) => {
  // Create a Blob object containing the file content
  const blob = new Blob([content], { type: "text/markdown" });

  // Create a temporary anchor element
  const link = document.createElement("a");
  link.href = window.URL.createObjectURL(blob);
  link.download = fileName;

  // Append the anchor element to the body and trigger a click event
  document.body.appendChild(link);
  link.click();

  // Cleanup
  window.URL.revokeObjectURL(link.href);
  document.body.removeChild(link);
};

// Function to update project detail
export const exportProject = async (projectId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/api/export/${projectId}`);
    downloadMdFile(response.data.fileName, response.data.content);
  } catch (error) {
    console.error("Error exporting project:", error);
    throw error.response.data;
  }
};

// redirect to gist
export const gotoGist = async (projectId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/api/export/${projectId}`);
    window.open(response.data.gistUrl, "_blank");
  } catch (error) {
    console.error("Error exporting project:", error);
    throw error.response.data;
  }
};
