import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainLayout from "./layouts/MainLayout";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/*" element={<MainLayout />} />
          </Routes>
      </BrowserRouter>
  );
}

export default App;
