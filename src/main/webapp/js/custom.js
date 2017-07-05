/**
 * Created by nane on 7/1/17
 */
jQuery("#vcodeajax").click(function () {

    $.ajax({
        url: '/signup',
        type: 'POST',
        data: {
            'firstName': jQuery("input[name=firstName]").val(),
            'lastName': jQuery("input[name=lastName]").val(),
            'email': jQuery("#email").val(),
            'password': jQuery("#sgpass").val(),
            'confirmPass': jQuery("#sgpass2").val()
        }, statusCode: {
            409: function (response) {
                $('.content').after('<div id="alert" class="alert alert-info"><strong>Info!</strong>This email is already used!</div>').fadeIn("slow");
            },
            500: function (response) {
                $('.content').after('<div id="alert" class="alert alert-info"><strong>Info!</strong></div>').fadeIn("slow");
            },
            400: function (response) {
                $('.content').after('<div id="alert" class="alert alert-info"><strong>Info!</strong>Password does not match</div>').fadeIn("slow");
            }
        }, success: function () {
            jQuery("#afterajaxemail").val(jQuery("#email").val());
            $('#verify').modal('toggle');
        },
        error: function (errorThrow) {
            console.log('error')
        }
    });
});

