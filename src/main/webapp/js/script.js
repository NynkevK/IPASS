$("#login").click(function(event){
    var data = $("#loginform").serialize();

    $.post("/ipass/restservices/authentication", data, function(response){
        window.sessionStorage.setItem("sessionToken", response);
        console.log(response);
        console.log("login succes");
    }).fail(function(jqXHR, textStatus, errorThrown){
        console.log(textStatus);
        console.log(errorThrown);
        console.log("login mislukt");
    });
});

function initPage() {
	$("#ledeneten_opgeven").click(function(){
		$("#ledeneten_opgeven_form").toggle();
	});

  $('#ledeneten_inzien').click(function(){
    $("#ledeneten_inzien_form").toggle();
  });
}

$(document).ready(initPage());
