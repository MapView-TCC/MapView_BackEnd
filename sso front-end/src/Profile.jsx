import { useState, useEffect } from "react";
import axios from "axios";

const API_BASE_URL = "http://localhost:5173"; // Base URL para as requisições

const Profile = () => {
  const [userInfo, setUserInfo] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await axios.get(`${API_BASE_URL}/userinfo`); 
        const userData = response.data;

        setUserInfo(userData);
        localStorage.setItem("id_token", userData.id_token); // Armazena o Token no LocalStorage
        
      } catch (error) {
        console.error("Erro ao buscar informações do usuário:", error);
        setError("Erro ao buscar informações do usuário.");
      }
    };

    fetchUserInfo();
  }, []);

  const handleLogout = () => {
    window.location.href = 'https://login.microsoftonline.com/0ae51e19-07c8-4e4b-bb6d-648ee58410f4/oauth2/v2.0/logout'; // Redireciona para o Login (SSO)
  };

// Apenas renderiza as informações

  return (
    <div>
      {error && <p>{error}</p>}
      {userInfo ? (
        <div>
          <span>{userInfo.roles}</span>
          <h1>Profile</h1>
          <span style={{ fontWeight: "bold" }}> Tokens: </span>
          <ul>
            <li>
              <span style={{ fontWeight: "bold" }}>id_token</span>:{" "}
              <span> {userInfo.id_token} </span>
            </li>
            <li>
              <span style={{ fontWeight: "bold" }}>access_token</span>:{" "}
              <span> {userInfo.access_token} </span>
            </li>
          </ul>
          <div>
            <span style={{ fontWeight: "bold" }}> User Attributes: </span>
            <ul>
              {userInfo &&
                userInfo.user_attributes &&
                Object.entries(userInfo.user_attributes).map(([key, value]) => (
                  <li key={key}>
                    <span style={{ fontWeight: "bold" }}> {key} </span>:{" "}
                    <span> {value} </span>
                  </li>
                ))}
            </ul>
          </div>
      
          <button onClick={handleLogout}>Logout</button>
      

        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default Profile;