async function adminLogin() {
    const emailid=document.getElementById("emailid").value;
    const password=document.getElementById("password").value;
    const response=await fetch(`http://localhost:1919/adminlogin/${encodeURIComponent(emailid)}/${encodeURIComponent(password)}`,{
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
            window.open("./adminfunctionality.html");
        }
    }
    catch(error)
    {
        window.open("./adminlogin.html");
    }
}