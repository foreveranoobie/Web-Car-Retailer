function setLanguage(){
    var selector = document.getElementsByName('lang')[0].value;
    var curl = window.location.href;
    curl = curl.replace("#", '');
    if(!curl.includes('?')){
        curl = curl + '?lang='+selector;
    } else {
        if(curl.lastIndexOf('?') == curl.length - 1){
            curl.substring(0, curl.length-1);
        }
        if(curl.includes('lang=')){
            curl = curl.replace(/lang=[a-z]{2}/, 'lang='+selector);
        } else {
            curl = curl + '&lang='+selector;
        }
    }
    window.open(curl, '_self');
}