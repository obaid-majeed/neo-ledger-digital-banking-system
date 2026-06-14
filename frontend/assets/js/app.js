const API_BASE = "http://localhost:8080";

// TOKEN CHECK
function getToken() {
  return localStorage.getItem("token");
}

function setToken(token) {
  localStorage.setItem("token", token);
}

function logout() {
  localStorage.removeItem("token");
  window.location.href = "login.html";
}