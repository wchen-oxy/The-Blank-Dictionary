import { debounce } from "./debounce.js";

function returnLanguageType(translation) {
    const mappings = {
        bhutia_english_formal: "bhut_rom_formal",
        bhutia_english_informal: "bhut_rom_informal",
        bhutiascript_english_formal: "bhut_script_formal",
        bhutiascript_english_informal: "bhut_script_informal",
        english_bhutia_formal: "eng_trans",
        english_bhutia_informal: "eng_trans",
    };
    return mappings[translation];

}

function returnArray(translation, query) {
    let url = new URL(window.location.origin + "/api/bhutia/" + translation + '/');
    url.search = new URLSearchParams({ query: query }).toString();
    return fetch(url)
}
function autocomplete(inp, arr) {
    /*the autocomplete function takes two arguments,
    the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", debounce((inp) => {
        const query = inp.target.value;
        const translation = returnAction();
        returnArray(translation, query)
            .then(result => {
                console.log(translation);
                return result.json();
            })
            .then(result => {
                const suggestArray = result.map(value => value[returnLanguageType(translation)])
                console.log(suggestArray);
                return handleInput(inp, suggestArray);
            });
    }, 1000));
    /*execute a function presses a key on the keyboard:*/
    inp.addEventListener("keydown", function (e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            /*If the arrow DOWN key is pressed,
            increase the currentFocus variable:*/
            currentFocus++;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 38) { //up
            /*If the arrow UP key is pressed,
            decrease the currentFocus variable:*/
            currentFocus--;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 13) {
            /*If the ENTER key is pressed, prevent the form from being submitted,*/
            e.preventDefault();
            if (currentFocus > -1) {
                /*and simulate a click on the "active" item:*/
                if (x) x[currentFocus].click();
            }
        }
    });


    function handleInput(e, arr) {
        var a, b, i, val = e.target.value;
        /*close any already open lists of autocompleted values*/
        closeAllLists();
        if (!val) { return false; }
        currentFocus = -1;
        /*create a DIV element that will contain the items (values):*/
        a = document.createElement("DIV");
        a.setAttribute("id", e.target.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        a.setAttribute("style", "left:-76.61px; width:50%; margin:0 auto;")
        /*append the DIV element as a child of the autocomplete container:*/
        e.target.parentNode.appendChild(a);
        /*for each item in the array...*/
        for (i = 0; i < arr.length; i++) {
            console.log(arr[i].toUpperCase());
            /*check where the substring occurs first*/
            const startIndex = arr[i].indexOf(val);
            console.log(startIndex);
            console.log(arr[i].substr(startIndex, val.length))
            /*create a DIV element for each matching element:*/
            b = document.createElement("DIV");
            /*make the matching letters bold:*/
            // b.innerHTML = arr[i].substr(val.length);
            if (startIndex === 0) {
                b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
            }
            else {
                b.innerHTML = arr[i].substr(0, startIndex);
                b.innerHTML += "<strong>" + arr[i].substr(startIndex, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
            }

            /*insert a input field that will hold the current array item's value:*/
            b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
            /*execute a function when someone clicks on the item value (DIV element):*/
            b.addEventListener("click", function (e) {
                /*insert the value for the autocomplete text field:*/
                inp.value = e.target.getElementsByTagName("input")[0].value;
                /*close the list of autocompleted values,
                (or any other open lists of autocompleted values:*/
                closeAllLists();
            });
            a.appendChild(b);

        }
    }
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}
function returnAction() {
    return document.getElementById('translation').value
}
autocomplete(document.getElementById('bhutia-searchbar'), ["China", "Chinglish", "cc", "ce", "ci", "cb", "cz", "cap"])
