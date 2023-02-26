function loginValidation(){
    var userLogin = $('input[name="login"]');
    var userPassword = $('input[name="password"]');
    var functions = [checkLogin, checkPassword];
    var variables = [userLogin, userPassword];
    var result = true;
    for (i = 0; i < functions.length; i++) {
        if(!validateJQ(functions[i], variables[i])){
            result = false;
        }
      }
    return result;
}

function validateJQ(func, element){
    if(func(element.val())){
            element.removeClass("error");
            return true;
        } else{
            element.addClass("error");
            return false;
    }
}

function checkLogin(login){
    let reg = /^[A-Za-z0-9_]{5,}$/;
    if(login.match(reg)){
        $('#login_error').textContent = "";
        return true;
    }else{
      $('#login_error').text("Your login has an incorrect form");
      $('#login_error').css({"color": "#c70039"});
      return false;
    }
}

function checkPassword(password){
    let reg = /^[A-Za-z0-9]{8,}$/;
    if(password.match(reg)){
        $('#password_error').textContent = "";
        return true;
    }else{
        $('#password_error').text("Your password has an incorrect form");
        $('#password_error').css({"color": "#c70039"});
        return false;
    }
}