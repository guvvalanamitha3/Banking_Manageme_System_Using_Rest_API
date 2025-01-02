
            window.onload = function() {
                fetchPendingDetails();
            };
    
            function fetchPendingDetails() {
                fetch("http://localhost:1919/allpendingdetails")
                    .then(response => response.json())
                    .then(data => displayPendingUsers(data))
                    .catch(error => console.error("Error fetching pending users", error));
            }
    
            function displayPendingUsers(data) {
                const tableBody = document.querySelector("#userTable tbody");
                const userTable = document.getElementById("userTable");
    
                if (!data || !data.data || data.data.length === 0) {
                    alert("No pending users found");
                    return;
                }
    
                userTable.style.display = "table";
                tableBody.innerHTML = ""; 
    
                data.data.forEach(user => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.emailid}</td>
                        <td>${user.mobilenumber}</td>
                        <td>${user.address}</td>
                        <td>
                            <button class="action-btn accept-btn" onclick="acceptUser(${user.id})">Accept</button>
                            <button class="action-btn reject-btn" onclick="rejectUser('${user.emailid}')">Reject</button>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });
            }
    
            function acceptUser(userId) {
                fetch(`http://localhost:1919/acceptpending/${userId}`, {
                    method: "PUT"
                })
                .then(response => response.text())
                .then(message => {
                    alert(message);
                    fetchPendingDetails();
                })
                .catch(error => console.error("Error during Accept:", error));
            }    
            function rejectUser(userEmailid) {
                fetch(`http://localhost:1919/deleteuserdetails/${userEmailid}`, {
                    method: "DELETE"
                })
                .then(response => response.text())
                .then(message => {
                    alert(message);
                    fetchPendingDetails();
                })
                .catch(error => console.error("Error during Reject:", error));
            } 