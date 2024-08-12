import React from "react";
import AppRoutes from "./Routes";
import { BrowserRouter as Router, Link } from 'react-router-dom';



const App = () => {
  const handleLogin = () => {
    window.location.href = 'http://localhost:5173/oauth2/authorization/azure'; // Redireciona para o Login (SSO)
  };

  return (
    <Router>
      <div>
        <nav>
          <button onClick={handleLogin}>Login</button>
          <Link to="/resource">Go to Resource</Link>
          <Link to="/profile">Go to Profile</Link>
        </nav>
        <AppRoutes />
      </div>
    </Router>
  );
};

export default App;
