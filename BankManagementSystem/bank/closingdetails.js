window.onload = function() {
    fetchClosingDetails();
};

function fetchClosingDetails() {
    fetch("http://localhost:1919/allclosingdetails")
        .then(response => response.json())
        .then(data => displayClosedUsers(data))
        .catch(error => console.error("Error fetching pending users", error));
}

function displayClosedUsers(data) {
    const tableBody = document.querySelector("#userTable tbody");
   // const userTable = document.getElementById("userTable");

    if (!data || !data.data || data.data.length == 0) {
        tableBody.innerHTML="<tr><td colspan='4'>No Closed Users Found</td></tr>"
        return;
    }
    tableBody.innerHTML = ""; 

    data.data.forEach(user => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.emailid}</td>
            <td>${user.mobilenumber}</td>
        `;
        tableBody.appendChild(row);

    });
}
