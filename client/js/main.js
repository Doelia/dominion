
var host = 'localhost';
var port = 6020;


$(function(){

    $('#game').hide();
    $('#join').hide();

    

    function callback(e) {
        var e = window.e || e;

        if (e.target.tagName == 'IMG')
        {
            if (e.target.id != "HD")
                agrandir(e.target);
            else
                virerHD();
        }


    }

    if (document.addEventListener)
         document.addEventListener('contextmenu', callback, false);
    else
         document.attachEvent('contextmenu', callback);


});