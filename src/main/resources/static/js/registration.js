(function() {
    $(".showPassImg").click(function (e) {
        var clickedInput = $(e.currentTarget).parent().find('input');
        var inputType = clickedInput.attr('type');
        if (inputType == 'password') {
            clickedInput.attr('type', 'text');
        } else {
            clickedInput.attr('type', 'password');
        }
    });
}())