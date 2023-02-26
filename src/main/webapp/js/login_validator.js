function registerValidation(){
    var userLogin = document.registration.login;
    var userPassword = document.registration.password;
    var functions = [checkLogin, checkPassword];
    var variables = [userLogin, userPassword];
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