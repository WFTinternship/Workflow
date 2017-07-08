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

$( document ).ready(function() {
    $(".edit-post").on("click", function(){
        $(this).removeClass('edit-post');
        $(this).addClass('save-post');
        $(this).html('<i class="fa fa-floppy-o" aria-hidden="true"></i><span>Save</span>');
        var editableInput = $("<input class='form-control editable edit-title' name='newTitle'/>");
        var editableText = $("<textarea class='form-control editable edit-content' rows='5' name='newContent' />");
        var Title = $(this).closest(".posttext").children('h2').text();
        var Content = $(this).closest(".posttext").children('p').text();
        $(this).parent().children("h2").replaceWith(editableInput);
        $(this).parent().children("p").replaceWith(editableText);
        $('.edit-title').val(Title);
        $('.edit-content').val(Content);

    });
    $(".save-post").on("click", function(){
        $(this).addClass('edit-post');
        $(this).removeClass('save-post');
        $(this).html('<i class="fa fa-pencil-square-o" aria-hidden="true"></i><span>Edit</span>');
        var confirmTitle= $("<h2 id='confirm-title'></h2>");
        var confirmContent = $("<p id='confirm-content'></p>");
        var confirmTitleValue = $(this).closest(".posttext").children('.edit-title').val();
        var confirmContentValue = $(this).closest(".posttext").children('.edit-content').val();
        $(this).closest(".edit-title").replaceWith(confirmTitle);
        $(this).closest(".edit-content").replaceWith(confirmContent);
        $('#confirm-title').val(confirmTitleValue);
        $('#confirm-content').val(confirmContentValue);

    });
});

