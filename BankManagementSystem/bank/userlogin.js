async function userLogin() {
    const emailid=document.getElementById("emailid").value;
    const password=document.getElementById("password").value;
    const response=await fetch(`http://localhost:1919/userlogin/${encodeURIComponent(emailid)}/${encodeURIComponent(password)}`,{
        method:'GET',
        headers:{
            'Content-Type':'application/json',
        },
    });
    const mydata=await response.json();
    console.log(response);
    console.log(mydata);
    try{
        if(mydata.httpstatuscode==202)
        {
           alert(mydata.responsemsg);
           window.open("./userloginhomepage.html");
        }
        else
        {
            alert(mydata.responsemsg);
            window.open("./HomePage.html");
        }
    }
    catch(error)
    {
       console.log("Error occured");
    }
}