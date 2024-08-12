import React, { useEffect, useState } from "react";
import axios from "axios";

const API_BASE_URL = "http://localhost:5173"; // Base URL para as requisições

const ResourceFetcher = () => {
  const [data, setData] = useState(null);

  useEffect(() => {
    const fetchResource = async () => {
      const token = localStorage.getItem("id_token"); // Pegar o token anteriormente armazenado no localStorage

      if (token) {
        try {
          const response = await axios.get(`${API_BASE_URL}/resource`, { // Fazer a requisição passando o token no header
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });

          setData(response.data);
          
        } catch (error) {
          console.error(
            "Erro ao acessar recurso protegido:",
            error.response ? error.response.data : error.message
          );
        }
      } else {
        console.error("Token não encontrado no localStorage.");
      }
    };

    fetchResource();
  }, []);

  // Exibir Recurso Protegido

  return (
    <div>
      <h1>Recurso Protegido</h1>
      <div>
        <ul>
          <li>
            <span> {data} </span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default ResourceFetcher;
