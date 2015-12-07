//Mostrar estudiante por apellido.
function getEstudiante(myId){
	var myUrl =	"http://localhost:8080/PnetProject/demo/tfg/estudiante/"+ myId;
	$.ajax({
	type: "GET",
	url: myUrl,
	success: function(data){
	$("#result").html(data); },
	error:function(res){
		$("#result").html("Sin resultados."); 
	}
	});
}

//Mostrar todos
function getAll(){
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "http://localhost:8080/PnetProject/demo/tfg/all",
		success: function(data){
		var myStudient="";
		$.each(data,function(i,data2){
	      myStudient += "<br>Alumno: "+data2._sNombre+" "+ data2._sApellidos+"<br>"
	      +"DNI: "+ data2._sDNI+"<br>"+"Titulo del TFG: "+ data2._sTitulo+"<br>"
	      +"Tutor1: "+ data2._sTutor1+"<br>"
	      +"Tutor2: "+ data2._sTutor2+"<br>"
	      +"Presentado: "+ data2._bEstado+"<br>"
	      +"Fecha de presentacion: "+ data2._ldtFecha+"<br>"
	      +"Calificacion: "+ data2._dCalificacion+"<br>_____________________<br>";
	    
		$("#result").html(myStudient);})},
		error:function(res){
		alert("ERROR "+ res.statusText); }
		});
}
//Añadir estudiante sin formulario
/*
function addEstudiante(){
	$.ajax( {
	type:"POST",
	url:"http://localhost:8080/PnetProject/demo/tfg/addEstudiante",
	contentType:"application/json",
	dataType:"json",
	data:JSON.stringify({"_sDNI":"12342645H","_sNombre":"Juan","_sApellidos":"Castaño Gonzalez",
		"_sTitulo":"Titulo de prueba3","_sTutor1":"Raul"}),
	success:function(data){
	$("#result2").html("Proyecto añadido"); },
	error:function(res){
		$("#result2").html("No se pudo agregar"); 
		}
	});
}
*/

//Añadir estudiante con formulario
function addEstudiante(){
	var dni = $("#textDNI").val();
	var nombre = $("#textNombre").val();
	var apellidos = $("#textApellidos2").val();
	var titulo = $("#textTitulo").val();
	var tutor1 = $("#textTutor1").val();
	var tutor2 = $("#textTutor2").val();
	$.ajax({
	type: "POST",
	url:"http://localhost:8080/PnetProject/demo/tfg/addEstudiante",
	contentType : "application/json",
	dataType : "json",
	data : JSON.stringify({"_sDNI":dni,"_sNombre":nombre,"_sApellidos":apellidos, "_sTitulo":titulo,"_sTutor1":tutor1,"_sTutor2":tutor2}),
		success:function(data){
			$("#result2").html("Proyecto añadido");
		},
		error:function(res){
			$("#result2").html("No se pudo agregar"); 
		}
	}) ;
}

//Borrar estudiante
function deleteEstudiante(myId)
{
	$.ajax({
		type: "DELETE",
		url:"http://localhost:8080/PnetProject/demo/tfg/delete/"+myId,
		success:function(data){
			$("#result2").html(data); },
		error:function(res){
			$("#result2").html("No se pudo Eliminar");}
	});
}
//modificar estudiante sin formulario
/*
function updateEstudiante(myId){
	$.ajax( {
	type:"PUT",
	url:"http://localhost:8080/PnetProject/demo/tfg/update/"+myId,
	contentType:"application/json",
	data:JSON.stringify({"_sDNI":"12345678H","_sNombre":"prueba","_sApellidos":"prueba",
		"_sTitulo":"Titulo de prueba4","_sTutor1":"prueba","_sTutor2":"prueba2","_bEstado":true,"_ldtFecha":null,
		"_dCalificacion":5.0}),
	success:function(data){
	$("#result3").html(data); },
	error:function(res){
		alert("ERROR "+ res.statusText);
		}
	});
}
*/

//modificar estudiante con formulario
function updateEstudiante(myId){
	var titulo = $("#texttitulo3").val();
	var estado = $("#textEstado3").val();
	var fecha = $("#textFecha3").val();
	var calificacion = $("#textCalificacion3").val();
	$.ajax({
	type: "PUT",
	url:"http://localhost:8080/PnetProject/demo/tfg/update/"+myId,
	contentType : "application/json",
	data : JSON.stringify({"_sTitulo":titulo,"_bEstado":estado,"_ldtFecha":fecha,"_dCalificacion":calificacion}),
		success:function(data){
			$("#result3").html("Proyecto actualizado");
		},
		error:function(res){
			$("#result3").html("No se pudo actualizar"); 
		}
	}) ;
}
