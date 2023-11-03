import { Routes, Route } from "react-router-dom";
import MovieRate from "./page/Rating_Movie";
import LoginPage from "./page/LoginPage";
import Profil from "./page/Profil";
import ChatRoom from "./components/Users/ChatRoom";
import Home from "./page/Home";
import ProtectedRoute from "./service/ProtectedRoute";
import NotFound from "./page/NotFound";


const App = () =>  {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/404" element={<NotFound />}/>
      <Route path="/message" element={<ChatRoom />} />
      <Route path="/commentaire/:movieId" element={<MovieRate />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/profil" element={<ProtectedRoute path="/profil" element={<Profil />} requiredRole="ROLE_USER" />} />
    </Routes>
  );
}

export default App;
