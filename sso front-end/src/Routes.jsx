import React from "react";
import { Routes, Route } from "react-router-dom";
import ResourceFetcher from "./ResorceFetcher";
import Profile from "./Profile";

const AppRoutes = () => (
    <Routes>
      <Route path="/resource" element={<ResourceFetcher />} />
      <Route path="/profile" element={<Profile />} />
    </Routes>
  );
  
  export default AppRoutes;