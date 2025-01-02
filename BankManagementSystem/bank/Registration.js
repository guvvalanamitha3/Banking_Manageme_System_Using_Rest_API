function userRegistration()
{
    const name=document.getElementById("name").value;
    const emailid=document.getElementById("emailid").value;
    const mobilenumber=document.getElementById("mobilenumber").value;
    const aadharnumber=document.getElementById("aadharnumber").value;
    const pannumber=document.getElementById("pannumber").value;
    const address=document.getElementById("address").value;
    const gender=document.getElementById("gender").value;
    const amount=document.getElementById("amount").value;
    const dateofbirth=document.getElementById("dateofbirth").value;
    const ifsccode=document.getElementById("ifsccode").value;

    const userdetails={
        name:name,
        emailid:emailid,
        mobilenumber:mobilenumber,
        aadharnumber:aadharnumber,
        pannumber:pannumber,
        address:address,
        gender:gender,
        amount:amount,
        dateofbirth:dateofbirth,
        ifsccode:ifsccode
    }
   console.log(name,emailid,mobilenumber,aadharnumber,pannumber,address,gender,amount,dateofbirth,ifsccode);
   console.log(userdetails);
    const response=fetch(`http://localhost:1919/userregistration`,{
        method:'POST',
        headers:{'Content-Type':'application/json'    
        },
        //It is used to convert the object into the JSON format.
    body:JSON.stringify(userdetails)
    });
    if(response.httpstatuscode==201)
    {
        // console.log(response);
        window.open("./homepage.html");
        //window.open("./Registration.html");

    }
    else
    {
        // alert(response.JSON());
        window.open("./Registration.html");
        //window.open("./homepage.html");
    }
}