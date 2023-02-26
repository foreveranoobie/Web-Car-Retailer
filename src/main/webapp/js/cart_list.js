function openFrame(){
    $('div[name ="cart"]').show();
    $('iframe[name ="cart_frame"]').attr('src', '../pages/cart/cart.jsp');
    $('#mySidebar').hide();
}

function closeFrame(){
    $('div[name ="cart"]').hide();
}

function getOrderCart(){
    if($("#login").val() != ""){
      $('iframe[name ="makeOrder_frame"]').attr('src', 'order_cart.jsp');
      $('div[name ="cartOrder"]').show();
    } else {
        alert("You must be authorized before making an order");
    }
}

function closeOrderAndCart(){
    window.parent.document.getElementsByName("cartOrder")[0].style.display = "none";
    window.parent.parent.document.getElementsByName("cart")[0].style.display = "none";
}

function closeOrder(){
    $('div[name ="cartOrder"]').hide();
    $('iframe[name ="makeOrder_frame"]').attr("src", function(index, attr){ 
        return attr;
    });
}

function addProduct(productId){
    $.ajax({url: "/addProductToCart?product_id="+productId});
}

function incrementAmount(productId, price){
    $.ajax({url: "/incrementAmount?product_id="+productId,
    statusCode:{200: function(xhr){
        location.reload();
        }
    }});
}

function decrementAmount(productId, price){
    if(parseInt($('#span_'+productId).text()) > 1){
    $.ajax({url: "/decrementAmount?product_id="+productId,
    statusCode:{200: function(xhr){
        location.reload();
    }}});
}
}

function removeProduct(productId){
    $.ajax({url: "/removeFromCart?product_id="+productId,
    statusCode:{200: function(xhr){
        location.reload();
    }}});
}

function clearCart(){
    $.ajax({url: "/clearCart",
    statusCode:{200: function(xhr){
        location.reload();
    }}});
}

function getPayment(){
    if($("select[name='payment'] option:selected").val() === "Bank card"){
        $("#card_info").show();
    } else {
        $("#card_info").hide();
    }
}

function submitOrder(){
    var ableToSubmit = false;
    if($("select[name='payment'] option:selected").val() === "Cash payment"){
        $("input[name='cardNumber']").prop('disabled', true);
        ableToSubmit = checkName($("input[name='receiverName']").val());
    } else {
        var errors = 0;
        var expDate = $("input[name='month']").val() + '/' + $("input[name='year']").val();
        var functions = new Array(checkName, checkCardNumber, checkCVV, checkExpirationDate);
        var values = [$("input[name='receiverName']").val(), $("input[name='cardNumber']").val(),
        $("input[name='cvv']").val(), expDate];
        ableToSubmit = true;
        for (i = 0; i < functions.length; i++) {
            if(!functions[i](values[i])){
                errors++;
            }
        }
        if(errors != 0){
            ableToSubmit = false;
        }
    }
    $("input[name='cvv']").prop('disabled', true);
    $("input[name='month']").prop('disabled', true);
    $("input[name='year']").prop('disabled', true);
    return ableToSubmit;
}

function checkCardNumber(number){
    let reg = /^[0-9]{8,19}$/;
    if(number.match(reg)){
        error.style.display = "none";
        return true;
    }
    error.style.display = "initial";
    return false;
}

function checkCVV(cvv){
    let reg = /^[0-9]{3}$/;
    if(cvv.match(reg)){
        error.style.display = "none";
        return true;
    }
    error.style.display = "initial";
    return false;
}

function checkExpirationDate(date){
    let reg = /^[0-9]{2}\/[0-9]{2}$/;
    if(date.match(reg)){
        error.style.display = "none";
        return true;
    }
    error.style.display = "initial";
    return false;
}

function checkName(name){
   if(name != ""){
    document.getElementById("name_error").style.display = "none";
       return true;
   }
   document.getElementById("name_error").style.display = "initial";
   return false;
}