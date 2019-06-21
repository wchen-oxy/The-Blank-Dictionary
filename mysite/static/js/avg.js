var DB;
try {
    var x = 'test_localstorage_available_' + Date.now();
    localStorage.setItem(x, x);
    var y = localStorage.getItem(x);
    localStorage.removeItem(x);
    if (x !== y) {throw new Error();}
    DB = localStorage;
    console.log('available');
}
catch(e) {
    // DB = new MemoryStorage('my-cool-app');
    console.log('failed');
}

$(document).ready(function(){
  $('.header').height($(window).height());
});


$(function() {
    $('#translation').change(function() {
        localStorage.setItem('todoData', this.value);
    });
    if(localStorage.getItem('todoData')){
        $('#translation').val(localStorage.getItem('todoData'));
    }
});