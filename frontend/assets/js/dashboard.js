 
function getToken() {
    return localStorage.getItem("token");
}

function loadUser() {

    const firstName =
        localStorage.getItem(
            "firstName"
        );

    const lastName =
        localStorage.getItem(
            "lastName"
        );

    const welcome =
        document.getElementById(
            "welcomeUser"
        );

    if (welcome) {

        welcome.innerText =
            "Welcome " +
            firstName +
            " " +
            lastName;
    }
}

async function loadBalance() {

    try {

        const account =
            localStorage.getItem(
                "accountNumber"
            );

        if (!account) {
            return;
        }

        const response =
            await fetch(
                `${API_BASE}/api/account/${account}`
            );

        const data =
            await response.json();

        document.getElementById(
            "balance"
        ).innerText =
            "₹" + data.balance;

    } catch (err) {

        console.log(err);

    }
}

async function loadTransactions() {

    try {

        const account =
            localStorage.getItem(
                "accountNumber"
            );

        if (!account) {
            return;
        }

        const response =
            await fetch(
                `${API_BASE}/api/transactions/history/${account}`,
                {
                    headers: {
                        Authorization:
                            `Bearer ${getToken()}`
                    }
                }
            );

        const data =
            await response.json();

        const list =
            document.getElementById(
                "txnList"
            );

        list.innerHTML = "";

        let income = 0;
        let expense = 0;

        data.forEach(tx => {

            const li =
                document.createElement(
                    "li"
                );

            li.innerHTML =
                `${tx.type} | ₹${tx.amount} | ${tx.createdAt}`;

            list.appendChild(li);

            if (
                tx.toAccount === account
            ) {
                income += Number(
                    tx.amount
                );
            }

            if (
                tx.fromAccount === account
            ) {
                expense += Number(
                    tx.amount
                );
            }

        });

        document.getElementById(
            "income"
        ).innerText =
            "₹" + income;

        document.getElementById(
            "expense"
        ).innerText =
            "₹" + expense;

    } catch (err) {

        console.log(err);

    }
}

async function transfer() {

    try {

        const response =
            await fetch(
                `${API_BASE}/api/transactions/transfer`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json",

                        Authorization:
                            `Bearer ${getToken()}`
                    },

                    body: JSON.stringify({

                        fromAccount:
                            localStorage.getItem(
                                "accountNumber"
                            ),

                        toAccount:
                            document.getElementById(
                                "toAccount"
                            ).value,

                        amount:
                            Number(
                                document.getElementById(
                                    "amount"
                                ).value
                            )
                    })
                }
            );

        const data =
            await response.json();

        if (!response.ok) {

            throw new Error(
                data.message ||
                "Transfer Failed"
            );
        }

        alert(
            data.message
        );

        document.getElementById(
            "toAccount"
        ).value = "";

        document.getElementById(
            "amount"
        ).value = "";

        loadBalance();

        loadTransactions();

    } catch (err) {

        alert(
            err.message
        );

    }
}

function loadProfile() {

    document.getElementById(
        "profileName"
    ).innerText =
        localStorage.getItem(
            "firstName"
        ) +
        " " +
        localStorage.getItem(
            "lastName"
        );

    document.getElementById(
        "profileEmail"
    ).innerText =
        localStorage.getItem(
            "email"
        );

    document.getElementById(
        "profileRole"
    ).innerText =
        localStorage.getItem(
            "role"
        );

    document.getElementById(
        "profileAccount"
    ).innerText =
        localStorage.getItem(
            "accountNumber"
        );
}

async function sendTransferOtp() {

    const mobile =
        localStorage.getItem(
            "mobile"
        );

    const response =
        await fetch(
            `${API_BASE}/api/transfer-otp/send?mobile=${mobile}`,
            {
                method: "POST"
            }
        );

    const message =
        await response.text();

    alert(message);
}

async function verifyAndTransfer() {

    const mobile =
        localStorage.getItem(
            "mobile"
        );

    const otp =
        document.getElementById(
            "transferOtp"
        ).value;

    const response =
        await fetch(
            `${API_BASE}/api/transfer-otp/verify?mobile=${mobile}&otp=${otp}`,
            {
                method: "POST"
            }
        );

    const verified =
        await response.json();

    if (!verified) {

        alert(
            "Invalid OTP"
        );

        return;
    }

    alert(
        "OTP Verified Successfully"
    );

    await transfer();

    document.getElementById(
        "transferOtp"
    ).value = "";
}

function openAdmin() {

    const role =
        localStorage.getItem(
            "role"
        );

    if(role !== "ADMIN") {

        alert(
            "Access Denied"
        );

        return;
    }

    window.location.href =
        "admin.html";
}

window.openAdmin = openAdmin;

function logout() {

    localStorage.clear();

    window.location.href =
        "login.html";
}
 
window.transfer = transfer;

window.logout = logout;

window.sendTransferOtp =
    sendTransferOtp;

window.verifyAndTransfer =
    verifyAndTransfer;
 
window.onload = () => {

    loadUser();

    loadProfile();

    loadBalance();

    loadTransactions();

};