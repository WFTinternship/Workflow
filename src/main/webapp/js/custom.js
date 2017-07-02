/**
 * Created by nane on 7/1/17.
 */
jQuery("#vcodeajax").click(function () {
    var objFormData = new FormData();
    var objFile = jQuery("#avatar")[0].files[0];
    objFormData.append('userfile', objFile);
    jQuery.ajax({
        type: 'POST',
        url: '/signup',
        data: {
            'action': 'radiopl_ajax_request',
            'firstName': jQuery("input[name=firstName]").val(),
            'lastName': jQuery("input[name=lastName]").val(),
            'email': jQuery("#email").val(),
            'password': jQuery("#sgpass").val(),
            'confirmPass': jQuery("#sgpass2").val(),
            'avatar': objFormData
        },
        processData: false,
        success: function (data) {
            alert('success');

        },
        error: function (errorThrown) {
            console.log('ajaxing')
        }
    });
});