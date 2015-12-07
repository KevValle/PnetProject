function getEstudiante(myId){
	var myUrl =	"http://localhost:8080/PnetProject/demo/tfg/estudiante/"+ myId;
	$.ajax({
	type: "GET",
	url: myUrl,
	success: function(data){
	$("#result").html(data); },
	error:function(res){
	alert("ERROR "+ res.statusText); }
	});
}