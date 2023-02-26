function registerValidation(){
    var userLogin = document.registration.login;
    var userPassword = document.registration.password;
    var firstName = document.registration.fName;
    var lastName = document.registration.lName;
    var email = document.registration.email;
    var functions = [checkLogin, checkPassword, checkFirstName, checkLastName, checkEmail];
    var variables = [userLogin, userPassword, firstName, lastName, email];
    var result = true;
    for (i = 0; i < functions.length; i++) {
        if(!validateJS(functions[i], variables[i])){
            result = false;
        }
      }
    return result;
}

function validateJS(func, element){
    if(func(element.value)){
            element.className = "reg";
            return true;
        } else{
            element.className = "error";
            return false;
    }
}

function checkLogin(login){
    let reg = /^[A-Za-z0-9_]{5,}$/;
    if(login.match(reg)){
        document.getElementById('login_error').textContent = "";
        return true;
    }else{
      document.getElementById('login_error').textContent = "Your login has an incorrect form";
      document.getElementById('login_error').style.color = "#c70039";
      return false;
    }
}

function checkPassword(password){
    let reg = /^[A-Za-z0-9]{8,}$/;
    if(password.match(reg)){
        document.getElementById('password_error').textContent = "";
        return true;
    }else{
        document.getElementById('password_error').textContent = "Your password has an incorrect form";
        document.getElementById('password_error').style.color = "#c70039";
        return false;
    }
}

function checkFirstName(fName){
    let reg = /^[A-Z][a-z]*$/;
    if(fName.match(reg)){
        document.getElementById('fname_error').textContent = "";
        return true;
    } else{
        document.getElementById('fname_error').textContent = "Incorrect first name value";
        document.getElementById('fname_error').style.color = "#c70039";
        return false;
    }
}

function checkLastName(lName){
    let reg = /^[A-Z][a-z]*$/;
    if(lName.match(reg)){
        document.getElementById('lname_error').textContent = "";
        return true;
    } else{
        document.getElementById('lname_error').textContent = "Incorrect last name value";
        document.getElementById('lname_error').style.color = "#c70039";
        return false;
    }
}

 function checkEmail(email){
     let reg = /^[A-Za-z0-9]+@[A-Za-z0-9]+\.[a-z]+$/;
     if(email.match(reg)){
        document.getElementById('email_error').textContent = "";
         return true;
     } else {
        document.getElementById('email_error').textContent = "Incorrect email value";
        document.getElementById('email_error').style.color = "#c70039";
         return false;
     }
 }