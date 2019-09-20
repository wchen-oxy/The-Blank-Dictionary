var store;
try {
	var x = 'test_localstorage_available_' + Date.now();
	localStorage.setItem(x, x);
	var y = localStorage.getItem(x);
	localStorage.removeItem(x);
	if (x !== y) {throw new Error();}
	store = localStorage;
	console.log('available');
}
catch(e) {
	store = new MemoryStorage('my-cool-app');
	console.log('failed');
}

if ((store.getItem('todoData')) == undefined) {
	document.myform.setAttribute("action", "/d/Bhutia/bhutia_english/");
	store.setItem('todoData', 'bhutToEng');
}

$(document).ready(function(){
  //sizes the window
	$('.header').height($(window).height());

//storage that is either memory storage or local storage

	$('#translation').change(function() {
		store.setItem('todoData', this.value);
	                // console.log(this.value);

	            });
	if(store.getItem('todoData')){
		$('#translation').val(store.getItem('todoData'));
		console.log(store.getItem('todoData'));
}

$("select").change(function(event){
    if (event.target.value == "bhutToEng") {
	    	document.myform.setAttribute("action", "/d/Bhutia/bhutia_english/");
	    	// alert('bhutToEng');
	    	store.setItem("storageName", "be");

        }
        if(event.target.value == "engToBhut") {
        	document.myform.setAttribute("action", "/d/Bhutia/english_bhutia/");
        		    	// alert('engToEng');

          	store.setItem("storageName", "eb");

        }

        if (event.target.value == "tibToBhut")  {
	        document.myform.setAttribute("action", "/d/Bhutia/tibetan_bhutia/");
	        store.setItem("storageName", "tb");
      }
});

});



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
