async function loadUsers() {

    const response =
        await fetch(
            `${API_BASE}/api/users`
        );

    const users =
        await response.json();

    document.getElementById(
        "totalUsers"
    ).innerText =
        users.length;

    const table =
        document.getElementById(
            "usersTable"
        );

    table.innerHTML =
    `
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
    </tr>
    `;

    users.forEach(user => {

        table.innerHTML +=
        `
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName} ${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
        </tr>
        `;
    });
}

function goDashboard() {

    window.location.href =
        "dashboard.html";
}

function logout() {

    localStorage.clear();

    window.location.href =
        "login.html";
}

window.goDashboard = goDashboard;
window.logout = logout;

window.onload = () => {

    loadUsers();

};