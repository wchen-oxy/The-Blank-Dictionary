$(document).ready(function(){
	var visible = "none";
  $("#show-hide").click(function(){
  	if (visible=="none") {
  		$("#dictionaries").show();
  		visible = "show";
  	}
  	else{
  		$("#dictionaries").hide();
  		visible = "none";
  	}
    
  });
});