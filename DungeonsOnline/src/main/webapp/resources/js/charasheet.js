/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var charSheetTemplate;

$(function () {
  $.ajax({
    method: "GET",
    url: "hb-charSheet.html"
  }).done(function (msg) {
    charSheetTemplate = Handlebars.compile(msg);
  });
});

function displayDetails(charName, onloading, ondone) {
  return function () {
    onloading();
    $(window).resize();
    $.ajax({
      method: "GET",
      url: "rest/char/" + charName
    }).done(function (msg) {
      ondone(charSheetTemplate(msg));
      var wall = new Freewall("#powerContainer");
      wall.reset({
        animate: true,
        cellW: 240,
        cellH: 'auto',
        selector: '.card',
        onResize: function () {
          wall.fitWidth();
        }
      });
      wall.fitWidth();
    });
  };
}
