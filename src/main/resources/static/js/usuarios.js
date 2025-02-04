// Call the dataTables jQuery plugin
$(document).ready(function() {
  CargarUsuarios();

  $('#usuarios').DataTable();
  actualizarEmailDelUsuario();
});
function actualizarEmailDelUsuario(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email
}

async function CargarUsuarios(){
    const request = await fetch('api/usuarios', {
      method: 'GET',
      headers: getHeaders()

    });
    const usuarios = await request.json();



    let ListadoHtml='';
    for(let usuario of usuarios){

        let telefonoTexto = usuario.telefono== null? '-': usuario.telefono;
        let botonEliminar= '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
        let usuarioHtml = '<tr>\<td>'+usuario.id+'</td><td>'+usuario.nombre+' '+usuario.apellido+'</td><td>'
            +usuario.email+'</td><td>'+telefonoTexto
            +'</td><td>'+botonEliminar+'</td></tr>';
        ListadoHtml +=usuarioHtml;

    }

    document.querySelector('#usuarios tbody').outerHTML= ListadoHtml

}
function getHeaders(){
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}

async function eliminarUsuario(id){

    if(!confirm('Desea eliminar este ususario?')){
        return;
    }
    const request = await fetch('api/usuarios/'+id, {
        method: 'DELETE',
        headers: getHeaders()

    });

location.reload()

}
