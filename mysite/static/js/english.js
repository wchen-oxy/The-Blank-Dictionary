// get the inital translation direction and set the forms correct
var translation = window.location.pathname;
console.log(translation);
if (translation.toLowerCase() != "/d/english/"){
document.myform.setAttribute("action", translation);
document.getElementById('translation').value = translation.split('/')[3];
}


document
  .getElementById('translation')
  .addEventListener('change',  (event) => {
		var newURL = event.target.value;
		//change the form action on click
		document.myform.setAttribute("action", "/d/english/" + newURL);
		//change the url on select/option change
		console.log(final_url);
	});
