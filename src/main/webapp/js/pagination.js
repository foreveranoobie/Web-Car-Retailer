var inputParameters = ['checkbox_mark', 'checkbox_fuelType', 'checkbox_category', 'rangeMin_price', 'rangeMax_price', 'rangeMin_year', 'rangeMax_year',
'rangeMin_mileage', 'rangeMax_mileage', 'sort_order', 'limit_productsCount', 'lang'];

function getPage(page){
    var curl = window.location.href;
    var containsParameters = false;
    for (i = 0; i < inputParameters.length; i++) {
       if(curl.includes(inputParameters[i])){
           containsParameters = true;
           break;
       }
    }
    if(!containsParameters){
        curl = curl.replace(/[&#?]/, "");
        if(curl.includes('page_current=')){
            curl = curl.replace(/page_current=[0-9]*/, "?page_current="+page);
        } else {
            curl = curl + "?page_current="+page;
        }
    } else {
        if(curl.includes('page_current=')){
            curl = curl.replace(/page_current=[0-9]*/, "page_current="+page);
        }else{
            curl = curl + "&page_current="+page;
        }
    }
    window.open(curl, '_self');
}

function resetFilters(){
    window.open('/getProducts', '_self');
}