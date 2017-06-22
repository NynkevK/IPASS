role = undefined;

$("#login").click(function(event){
    var data = $("#loginform").serialize();

    $.post("/ipass/restservices/authentication", data, function(response){
        window.sessionStorage.setItem("sessionToken", response);
        console.log(response);
        console.log("login succes");
        username = data.split("&")[0].split("=")[1];
        console.log("Username = " + username);   
        $.get("/ipass/restservices/leden/" + username, function(data){
    		role = data.role;
    		return role;
    	});
    }).fail(function(jqXHR, textStatus, errorThrown){
        console.log(textStatus);
        console.log(errorThrown);
        console.log("login mislukt");
    });
});

$("#show").click(function(event) {
	if (role == "admin") {
		$.get("/ipass/restservices/aanmeldingen/" + datumOmzetten($("#date").val()), function(data){
			$.each(data, function(i, aanmelding){
				$("#aanmeldingen_tabel").append(
						"<tr>" + 
						"<td>" + aanmelding.lidnaam + "</td>" + 
						"<td>" + aanmelding.gerechtnaam + "</td>" +
						"</tr>");
			});		
			console.log("data toegevoegd");
		});
	} else {		
		console.log("mislukt");
	}
});

$("#kies_datum").click(function(event){
	if (role == "admin" || role == "user") {
		$.get("/ipass/restservices/gerechten/" + datumOmzetten($("#datum_opgeven_ledeneten").val()), function(data){
			$.each(data, function(i, gerecht){
				$("#opgeven_ledeneten").append(
						"<label>" + gerecht.name + "&nbsp;<input type=\"radio\" name=\"gerecht\" value=\"" + gerecht.name + "\"></label><br>");
			});
		});
	} else if (role == undefined){
		console.log("mislukt");
	}
});

function datumOmzetten(oudeDatum){
	datumArray = oudeDatum.split("-");
	
	jaar = datumArray[0];
	maand = datumArray[1];
	dag = datumArray[2];
	
	nieuweDatum = dag + "-" + maand + "-" + jaar;
	
	console.log(nieuweDatum);
	return nieuweDatum;	
}

$("#opslaan").click(function(event){
	var data = {"lidnummer" : $("#lidnummer_input").val(), "gerechtId" : $('input[name=gerecht]:checked').val(), "datum" : datumOmzetten($("#datum_opgeven_ledeneten").val())};
    var JSONdata = JSON.stringify(data);
    console.log(JSONdata);
    
//    $.post("/ipass/restservices/aanmeldingen", JSONdata, function(response){
//    	console.log("succesvol aangemeld");
//    });
});