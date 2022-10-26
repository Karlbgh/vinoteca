
function open_delete(id) {
    $.ajax({
        url: '/empleado/delete/show/' + id,
        success: function (data) {
            $('#paraelmodal').html(data);
            $('#delete-modal').modal('show');
        }
    });
}