import React from "react";
import { Routes, Route } from "react-router-dom";
import ResourceFetcher from "./ResorceFetcher";
import Profile from "./Profile";
import App from "./App";

const AppRoutes = () => (
    <Routes>
      <Route path="/app" element={<App/>} />
      <Route path="/resource" element={<ResourceFetcher />} />
      <Route path="/profile" element={<Profile />} />
    </Routes>
  );
  
  export default AppRoutes;