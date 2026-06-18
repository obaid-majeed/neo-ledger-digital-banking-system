const API_BASE = "http://localhost:8080";

function setToken(token) {
    localStorage.setItem("token", token);
}

function getToken() {
    return localStorage.getItem("token");
}

function setAccount(accountNumber) {
    localStorage.setItem("accountNumber", accountNumber);
}

function getAccount() {
    return localStorage.getItem("accountNumber");
}

/*function logout() {

    localStorage.clear();

    window.location.href = "login.html";
}

window.logout = logout;*/

function logout() {

    localStorage.clear();

    window.location.href =
        "login.html";
}

window.logout = logout;

window.onload = () => {

    const btn =
        document.getElementById(
            "logoutBtn"
        );

    if(btn){

        btn.addEventListener(
            "click",
            logout
        );

    }

};
