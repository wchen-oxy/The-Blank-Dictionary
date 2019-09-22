//set logo redirect
var url = window.location.pathname.split('/');
// window.location.hostname + '/' + url[1] + '/' + url[2];
var final_url = window.location.protocol + "//" +window.location.hostname + ':'+ window.location.port+ '/' + url[1] + '/' + url[2];
var isHome = false;

//IMPORTANT WHEN DEPLOYING MAKE SURE TO GET RID OF THE PORT NUMBER BELOW window.location.port;
//FIX ME
document.getElementById('home').href = final_url;

//if at home page, set default selected to bhutia_english
console.log(window.location.pathname);
if (window.location.pathname == "/d/Bhutia") {
	isHome = true;
	document.myform.setAttribute("action", "/d/Bhutia/bhutia_english/");
	document.getElementById('translation').selected = 'bhutia_english';
}
// get the inital translation direction and set the forms correct
var translation = window.location.pathname;
// console.log(translation);
if (translation != "/d/Bhutia"){
document.myform.setAttribute("action", translation);
document.getElementById('translation').value = translation.split('/')[3];}

//changing select
document
  .getElementById('translation')
  .addEventListener('change',  (event) => {
		var newURL = event.target.value;
		// console.log(newURL);
		//change the form action on click
		document.myform.setAttribute("action", "/d/Bhutia/" + newURL);
		// window.history.replaceState("/d/Bhutia/", "trans", translation.split('/')[3]);
		//change the url on select/option change
		console.log(final_url);
		// if (isHome === true ){
		// 	console.log("HOME");
		// 	window.history.pushState(final_url, "", "/d/Bhutia/" + newURL);
		// }
		// else{
		// 	console.log("NOPE");
		// 	window.history.replaceState(final_url, "", "/d/Bhutia/" + newURL);}
	});





// var store;
// try {
// 	var x = 'test_localstorage_available_' + Date.now();
// 	localStorage.setItem(x, x);
// 	var y = localStorage.getItem(x);
// 	localStorage.removeItem(x);
// 	if (x !== y) {throw new Error();}
// 	store = localStorage;
// 	console.log('available');
// }
// catch(e) {
// 	store = new MemoryStorage('my-cool-app');
// 	console.log('failed');
// }
//
// if ((store.getItem('todoData')) == undefined) {
// 	document.myform.setAttribute("action", "/d/Bhutia/bhutia_english/");
// 	store.setItem('todoData', 'bhutToEng');
// }
//
// $(document).ready(function(){
//   //sizes the window
// 	$('.header').height($(window).height());
//
// //storage that is either memory storage or local storage
//
// 	$('#translation').change(function() {
// 		store.setItem('todoData', this.value);
// 	                // console.log(this.value);
//
// 	            });
// 	if(store.getItem('todoData')){
// 		$('#translation').val(store.getItem('todoData'));
// 		console.log(store.getItem('todoData'));
// }
//
// $("select").change(function(event){
//     if (event.target.value == "bhutToEng") {
// 	    	document.myform.setAttribute("action", "/d/Bhutia/bhutia_english/");
// 	    	// alert('bhutToEng');
// 	    	store.setItem("storageName", "be");
//
//         }
//         if(event.target.value == "engToBhut") {
//         	document.myform.setAttribute("action", "/d/Bhutia/english_bhutia/");
//         		    	// alert('engToEng');
//           store.setItem("storageName", "eb");
//
//         }
//
//         if (event.target.value == "tibToBhut")  {
// 	        document.myform.setAttribute("action", "/d/Bhutia/tibetan_bhutia/");
// 	        store.setItem("storageName", "tb");
//       }
// });
//
// });



// //for loading proper translation url
// window.onload = function() {

//       var selected = document.createAttribute("selected");
//       if (localStorage.getItem("storageName")) {
//         var thing = localStorage.getItem("storageName");
//         var element = document.getElementById(thing);

//         if (thing == "be") {
//           document.myform.setAttribute("action","/entry/bhutia_english/");};
//           if (thing == "eb") {document.myform.setAttribute("action", "/entry/english_bhutia/");
//           element.setAttribute("selected", "selected");};
//           if (thing == "tb") {document.myform.setAttribute("action", "/entry/tibetan_bhutia/");
//           element.setAttribute("selected", "selected");
//         };}
//         console.log(document.getElementById("translation"));

//       }

//       //code that is only run when a change is detected
//       function changeEventHandler(event) {
//         if (event.target.value == "bhutToEng") {
//           document.myform.setAttribute("action", "/entry/bhutia_english/");
//           store.setItem("storageName", "be");

//         }
//         if(event.target.value == "engToBhut") {
//           document.myform.setAttribute("action", "/entry/english_bhutia/");
//           store.setItem("storageName", "eb");

//         }

//         if (event.target.value == "tibToBhut")  { document.myform.setAttribute("action", "/entry/tibetan_bhutia/");
//         store.setItem("storageName", "tb");
//       }
//     }
