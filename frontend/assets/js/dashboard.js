function validateToken() {
  const token = getToken();

  fetch(`${API_BASE}/api/auth/validate`, {
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => {
    if (!res.ok) logout();
  });
}

function loadTransactions() {
  const token = getToken();

  fetch(`${API_BASE}/api/transactions`, {
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => res.json())
  .then(data => {
    let list = document.getElementById("txnList");
    list.innerHTML = "";

    data.forEach(t => {
      let li = document.createElement("li");
      li.innerText = `${t.type} - ₹${t.amount}`;
      list.appendChild(li);
    });
  });
}

function transfer() {
  const token = getToken();

  fetch(`${API_BASE}/api/transfer`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + token
    },
    body: JSON.stringify({
      toAccount: document.getElementById("toAccount").value,
      amount: document.getElementById("amount").value
    })
  })
  .then(res => res.json())
  .then(() => {
    alert("Transfer successful");
    loadTransactions();
  });
}