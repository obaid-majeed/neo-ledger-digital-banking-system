function setToken(token) {
    localStorage.setItem("token", token);
}

async function login() {

    try {

        const response = await fetch(
            `${API_BASE}/api/auth/login`,
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    email:
                        document.getElementById("email").value,

                    password:
                        document.getElementById("password").value
                })
            }
        );

        const data = await response.json();

        if (!response.ok) {

            throw new Error(
                data.message || "Login Failed"
            );
        }

        setToken(data.token);

        localStorage.setItem(
            "email",
            data.email
        );

        const profileResponse =
            await fetch(
                `${API_BASE}/api/users/profile?email=${data.email}`
            );

        const profile =
            await profileResponse.json();

        localStorage.setItem(
            "userId",
            profile.id
        );

		localStorage.setItem(
		    "role",
		    profile.role
		);

		
		localStorage.setItem(
		    "firstName",
		    profile.firstName
		);

		localStorage.setItem(
		    "lastName",
		    profile.lastName
		);
		
		localStorage.setItem(
		    "mobile",
		    profile.mobile
		);

        const accountResponse =
            await fetch(
                `${API_BASE}/api/account/email/${data.email}`
            );

        const accounts =
            await accountResponse.json();

        if (accounts.length > 0) {

            localStorage.setItem(
                "accountNumber",
                accounts[0].accountNumber
            );

            localStorage.setItem(
                "accountType",
                accounts[0].accountType
            );
        }

        alert(
            "Login Successful"
        );

        window.location.href =
            "dashboard.html";

    }

    catch (err) {

        alert(
            err.message
        );
    }
}

window.login = login;