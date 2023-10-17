import React, { useState } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import InputLabel from "@mui/material/InputLabel";
import { toast, Toaster } from "react-hot-toast";
import { userSignIn } from "../../service/apiService";

const defaultTheme = createTheme();

export default function SignUp() {
  const [age, setAge] = useState("");
  const [coutry, setCoutry] = useState("");

  const handleChangeCoutry = (event) => {
    setCoutry(event.target.value);
  };

  const handleChange = (event) => {
    setAge(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const nameValue = data.get("firstName");
    const lastNameValue = data.get("lastName");
    const usernameValue = data.get("username");
    const emailValue = data.get("email");
    const passwordValue = data.get("password");
    const passwordRegex =
      /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (
      nameValue.trim() === "" ||
      lastNameValue.trim() === "" ||
      usernameValue.trim() === ""
    ) {
      toast.error("Champ vide");
    } else if (!emailRegex.test(emailValue) || emailValue === "") {
      toast.error("Email non valide");
    } else if (
      passwordValue.length <= 8 &&
      !passwordRegex.test(passwordValue) &&
      passwordValue === ""
    ) {
      toast.error(
        "Le mot de passe doit comporter au moins 8 caractères et 1 caractère spécial, 1 chiffre, 1 majuscule."
      );
    } else {
      const formData = {
        name: nameValue,
        username: usernameValue,
        email: emailValue,
        password: passwordValue,
        age: age,
        coutry: coutry
      };

      try {
        const response = await userSignIn(formData);
        if (response.ok)
          toast.success("Inscription reussi")
        else {
          toast.error("Erreur de serveur reassayer plus tard")
        }
      } catch (error) {
        console.error(error);
      }
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Toaster position="top-center" reverseOrder={false} />
      <Container component="main" maxWidth="xs" className="sign_container">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign up
          </Typography>
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 3 }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  autoComplete="given-name"
                  name="firstName"
                  required
                  fullWidth
                  id="firstName"
                  label="Nom"
                  autoFocus
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  required
                  fullWidth
                  id="lastName"
                  label="Prenom"
                  name="lastName"
                  autoComplete="family-name"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  required
                  fullWidth
                  id="username"
                  label="Pseudo"
                  name="username"
                  autoComplete="family-name"
                />
              </Grid>
              <Grid item xs={6}>
                <InputLabel id="demo-simple-select-standard-label">
                  Age
                </InputLabel>
                <Select
                  labelId="demo-simple-select-standard-label"
                  id="demo-simple-select-standard"
                  value={age}
                  onChange={handleChange}
                  label="Age"
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  <MenuItem value={10}>18</MenuItem>
                  <MenuItem value={20}>19</MenuItem>
                  <MenuItem value={30}>20</MenuItem>
                </Select>
              </Grid>
              <Grid item xs={6}>
                <InputLabel id="demo-simple-select-standard-label">
                  Pays
                </InputLabel>
                <Select
                  labelId="demo-simple-select-standard-label"
                  id="demo-simple-select-standard"
                  value={coutry}
                  onChange={handleChangeCoutry}
                  label="Pays"
                >
                  <MenuItem value="France">
                    <em>None</em>
                  </MenuItem>
                  <MenuItem value={"France"}>France</MenuItem>
                  <MenuItem value={"Canada"}>Canada</MenuItem>
                  <MenuItem value={"Finlande"}>Finlande</MenuItem>
                </Select>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="Addresse Email"
                  name="email"
                  autoComplete="email"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  label="Mot de passe"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign Up
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
