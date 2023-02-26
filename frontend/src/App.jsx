import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Landing from "./components/landing/Landing";
import HexGrid from "./components/map/HexGrid";
import InputName from "./components/inputName/InputName";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Landing />}></Route>
        <Route path="/input" element={<InputName />}></Route>
        <Route path="/map" element={<HexGrid />}></Route>
      </Routes>
    </Router>
  );
}

export default App;
