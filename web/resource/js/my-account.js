//need tab-toggle.js
addTabToggleEvent('post');
$('#tab-post').trigger('click');

//need multipart-form.js
$('#create-post-button').on('click', function(){
    if(validate()) sendFormData('create-post', new FormData($('#create-post-form')[0]));
    clearForm();
});

$('.delete-button').on('click', function(){
    console.log($(this).data('id'));
});
