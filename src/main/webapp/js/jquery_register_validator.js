function registerValidation(){
    var userLogin = $('input[name="login"]');
    var userPassword = $('input[name="password"]');
    var firstName = $('input[name="fName"]');
    var lastName = $('input[name="lName"]');
    var email = $('input[name="email"]');
    var functions = [checkLogin, checkPassword, checkFirstName, checkLastName, checkEmail];
    var variables = [userLogin, userPassword, firstName, lastName, email];
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

function checkFirstName(fName){
    let reg = /^[A-Z][a-z]*$/;
    if(fName.match(reg)){
        $('#fname_error').text("");
        return true;
    } else{
        $('#fname_error').text("Incorrect first name value");
        $('#fname_error').css({"color": "#c70039"});
        return false;
    }
}

function checkLastName(lName){
    let reg = /^[A-Z][a-z]*$/;
    if(lName.match(reg)){
        $('#lname_error').text("");
        return true;
    } else{
        $('#lname_error').text("Incorrect last name value");
        $('#lname_error').css({"color": "#c70039"});
        return false;
    }
}

 function checkEmail(email){
     let reg = /^[A-Za-z0-9]+@[A-Za-z0-9]+\.[a-z]+$/;
     if(email.match(reg)){
        $('#email_error').text("");
         return true;
     } else {
        $('#email_error').text("Incorrect email value");
        $('#email_error').css({"color": "#c70039"});
        return false;
     }
 }