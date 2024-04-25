import React from "react";
import { Modal, Box, Typography, Button } from "@mui/material";

function GlobalConfirmationModal({ open, onClose, onConfirm, message }) {
  return (
    <Modal
      open={open}
      onClose={onClose}
      aria-labelledby="global-confirmation-modal-title"
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
        <Typography
          id="global-confirmation-modal-title"
          variant="h6"
          component="h2"
        >
          Confirmation
        </Typography>
        <Typography>{message}</Typography>
        <Box sx={{ display: "flex", justifyContent: "space-between", mt: 2 }}>
          <Button variant="contained" onClick={onConfirm}>
            Confirm
          </Button>
          <Button onClick={onClose}>Cancel</Button>
        </Box>
      </Box>
    </Modal>
  );
}

export default GlobalConfirmationModal;
