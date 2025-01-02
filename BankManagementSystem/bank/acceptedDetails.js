window.onload = function() {
    fetchAcceptDetails();
};

function fetchAcceptDetails() {
    fetch("http://localhost:1919/allaccepteddetails")
        .then(response => response.json())
        .then(data => displayAcceptUsers(data))
        .catch(error => console.error("Error fetching pending users", error));
}

function displayAcceptUsers(data) {
    const tableBody = document.querySelector("#userTable tbody");
   // const userTable = document.getElementById("userTable");

    if (!data || !data.data || data.data.length === 0) {
        tableBody.innerHTML="<tr><td colspan='4'>No Accepted Users Found</td></tr>"
        return;
    }
    tableBody.innerHTML = ""; 

    data.data.forEach(user => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td contenteditable="true">${user.id}</td>
            <td contenteditable="true">${user.name}</td>
            <td contenteditable="true">${user.emailid}</td>
            <td contenteditable="true">${user.mobilenumber}</td>
            <td contenteditable="true">${user.address}</td>
            <td>
                <button class="action-btn reject-btn" onclick="updateUser()">Update</button>
            </td>
        `;
        tableBody.appendChild(row);

    });
}
async function updateUser(){
    const button=event.target;
   const row= button.parentElement.parentElement;
   const details=row.querySelectorAll("td");
   const userDetaiils={
    id:details[0].innerText.trim(),
    name:details[1].innerText.trim(),
    emailid:details[2].innerText.trim(),
    mobilenumber:details[3].innerText.trim(),
    address:details[4].innerText.trim()
   }
   const response=await fetch("http://localhost:1919/modifyuser",{
    method:'PUT',
    headers:{
        'Content-Type':'application/json'
    },
    body:JSON.stringify(userDetaiils)
   })

   const data=await response.json();
   if(data.httpstatuscode===202){
    alert(data.responsemsg)
    fetchAcceptDetails()
   }else{
    alert(data.responsemsg)
   }

}