/**
 * Created by nane on 7/1/17
 */
$(document).ready(function () {
// jQuery("#vcodeajax").click(function () {
//     jQuery("#loader").show();
//     $.ajax({
//         url: '/signup',
//         type: 'POST',
//         data: {
//             'firstName': jQuery("input[name=firstName]").val(),
//             'lastName': jQuery("input[name=lastName]").val(),
//             'email': jQuery("#email").val(),
//             'password': jQuery("#sgpass").val(),
//             'confirmPass': jQuery("#sgpass2").val()
//         }, statusCode: {
//             409: function (response) {
//                 $('.content').after('<div id="alert" class="alert alert-info"><strong>Info!</strong>This email is already used!</div>').fadeIn("slow");
//             },
//             500: function (response) {
//                 $('.content').after('<div id="alert" class="alert alert-info"><strong>Info!</strong></div>').fadeIn("slow");
//             },
//             400: function (response) {
//                 $('.content').after('<div id="alert" class="alert alert-info"><strong>Info!</strong>Password does not match</div>').fadeIn("slow");
//             }
//         }, success: function () {
//             jQuery("#afterajaxemail").val(jQuery("#email").val());
//             $('#verify').modal('toggle');
//             jQuery("#loader").hide();
//         },
//         error: function (errorThrow) {
//             jQuery("#loader").hide();
//             console.log('error')
//         }
//     });
// });
//

    $(document).on("click", ".edit-post", function () {
        $(this).removeClass('edit-post');
        $(this).addClass('save-post');
        $(this).after('<div class="notify"></div>');
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

    $(document).on("click", ".save-post", function () {
        var thishtml = $(this);
        var confirmTitleValue = $('.edit-title').val();
        var confirmContentValue = $('.edit-content').val();
        var confirmTitle = "<h2 id='confirm-title'>" + confirmTitleValue + "</h2>";
        var confirmContent = "<p id='confirm-content'>" + confirmContentValue + "</p>";

        $.ajax({
            url: '/edit-post',
            type: 'POST',
            data: {
                'postId': jQuery("input[name=postId]").val(),
                'title': confirmTitleValue,
                'content': confirmContentValue,
            }, statusCode: {
                500: function (data) {
                    console.log(65);
                    $('div.notify').html('<div id="alert" class="alert alert-info"><strong>Info!</strong> Sorry, your post was not updated. Please try again</div>').fadeIn("slow");
                }
            }, success: function (data) {
                $('div.notify').remove();
                $(thishtml).addClass('edit-post');
                $(thishtml).removeClass('save-post');
                $(thishtml).html('<i class="fa fa-pencil-square-o" aria-hidden="true"></i><span>Edit</span>');
                $(thishtml).siblings(".edit-title").remove();
                $(thishtml).siblings(".edit-content").remove();
                $(thishtml).parent().append(confirmTitle + " " + confirmContent);
            },
            error: function (data) {
                // $('.content').after('<div id="alert" class="alert alert-info"><strong>Info!</strong> Sorry, your post was not updated. Please try again</div>').fadeIn("slow");
            }
        });
    });
});


$(document).ready(function () {
    $(document).on("click", ".edit-answer", function () {
        $(this).removeClass('edit-answer');
        $(this).addClass('save-answer');
        $(this).after('<div class="notify"></div>');
        $(this).html('<i class="fa fa-floppy-o" aria-hidden="true"></i><span>Save</span>');
        var editableText = $("<textarea class='form-control editable edit-content' rows='5' name='newContent' />");
        var Content = $(this).closest(".posttext").children('p').text();
        $(this).parent().children("p").replaceWith(editableText);
        $('.edit-content').val(Content);
    });

    $(document).on("click", ".save-answer", function () {
        var thishtml = $(this);
        var confirmContentValue = $('.edit-content').val();
        var confirmContent = "<p id='confirm-content'>" + confirmContentValue + "</p>";

        $.ajax({
            url: '/edit-answer',
            type: 'POST',
            data: {
                'answerId': jQuery("input[name=answerId]").val(),
                'content': confirmContentValue,
            }, statusCode: {
                500: function (data) {
                    console.log(65);
                    $('div.notify').html('<div id="alert" class="alert alert-info"><strong>Info!</strong> Sorry, your post was not updated. Please try again</div>').fadeIn("slow");
                }
            }, success: function (data) {
                $('div.notify').remove();
                $(thishtml).addClass('edit-answer');
                $(thishtml).removeClass('save-answer');
                $(thishtml).html('<i class="fa fa-pencil-square-o" aria-hidden="true"></i><span>Edit</span>');
                $(thishtml).siblings(".edit-content").remove();
                $(thishtml).parent().append(confirmContent);
            },
            error: function (data) {
            }
        });
    });
});