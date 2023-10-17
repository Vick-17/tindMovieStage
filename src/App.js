import { Routes, Route } from "react-router-dom";
import Swipe from "./page/Swipe";
import Message from "./page/Message";
import MovieRate from "./page/Rating_Movie";
import LoginPage from "./page/LoginPage";
import Profil from "./page/Profil";
import Prod from "./page/Test";


const App = () =>  {
  return (
    <Routes>
      <Route path="/" element={<Swipe />} />
      <Route path="/message" element={<Message />} />
      <Route path="/commentaire/:movieId" element={<MovieRate />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/profil" element={<Profil />} />
      <Route path="/dev" element={<Prod />} />
    </Routes>
  );
}

export default App;
