function openOrders(){
    $('div[name ="orders"]').show();
    $('iframe[name ="orders_frame"]').attr('src', '/getOrders');
    $('#mySidebar').hide();
}

function closeOrders(){
    $('div[name ="orders"]').hide();
}

function getDetails(orderId){
    //window.parent.document.getElementsByName("orders_frame")[0].src = "/getOrderProducts?order_id="+orderId;
    //window.parent.document.getElementsByName("returnArrow")[0].style.display = "initial";
    $("#order"+orderId).show();
}

function closeDetails(orderId){
    $("#order"+orderId).hide();
}

function previousFrame(){
    window.parent.document.getElementsByName("orders_frame")[0].src = '/getOrders';
    window.parent.document.getElementsByName("returnArrow")[0].style.display = "none";
}