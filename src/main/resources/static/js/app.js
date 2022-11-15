
function open_delete(id) {
    $.ajax({
        url: '/empleado/delete/show/' + id,
        success: function (data) {
            $('#paraelmodal').html(data);
            $('#delete-modal').modal('show');
        }
    });
}

function filtrarVino(){
    let precioVino = $('#textoFiltroVino').val();
    $.ajax({
        url: '/vino/lista/filtro/',
        data: {
            filtro:precioVino
        },
        success: function (data) {
            $('#tablaVinos').html(data);
        }
    });
}

function cambiarImagen(){
    let input = document.getElementById("file");
    let fReader = new FileReader();
    fReader.readAsDataURL(input.files[0]);
    fReader.onloadend = function(event){
        let img = document.getElementById("imgVino");
        img.src = event.target.result;
    }
}