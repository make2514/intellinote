function showPass(){
    
    var passField = document.getElementById('pass-input');
    
    if(passField.type == 'password'){
        passField.type = 'text';
    }else{
        passField.type = 'password';
    }
}

function toggleHidden() {
    var login = $('#container-login');
    var reg = $('#container-regis');
    
    if(login.hasClass('hidden') == false){
        reg.removeClass('hidden');
        login.addClass('hidden');
    }else{
        reg.addClass('hidden');
        login.removeClass('hidden');
    }
    
}

$(document).ready(function() {
    $('#pass-input-reg-rep').focusout(function() {
        var pass = $('#pass-input-reg').val();
        var passRep = $('#pass-input-reg-rep').val();
        
        if(passRep != pass) {
            alert('Passwords didn\'t match!')
        }
    })
})
