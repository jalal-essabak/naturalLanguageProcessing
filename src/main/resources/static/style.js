$(document).ready(function(){
  $("#file-input").change(function(){
    var path=document.getElementById("file-input").value.split('\\');

    if(path.length==0){

      document.getElementById("file-label").innerHTML = "Veuillez choisir un fichier";      
    }
    else{
      var fileName=path[path.length-1];
      document.getElementById("file-label").innerHTML = fileName;
    }
  });
  
  $( "#submit" ).click(function() {
    var path=document.getElementById("file-input").value;
    if ($.trim($("textarea").val()) != "" && path.length!=0) {
      alert("vous ne pouvez pas entrer un texte et un document en même temps!");
      return false;
    }
    else if($.trim($("textarea").val()) == "" && path.length==0){
    	alert("Vous devez saisir un texte ou un document avant de valider");
        return false;
    }
    
  });
 
});