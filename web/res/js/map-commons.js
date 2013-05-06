var gridSize = 30;
var gridPadding = 3;

function hideInitiative() {
  $("#initiative").hide();
  $("#showInitiative").show();
  recalculateMapContainer();
}
function showInitiative() {
  $("#initiative").show();
  $("#showInitiative").hide();
  recalculateMapContainer();
}
function hideCombatLog() {
  $("#combatLog").hide();
  $("#showCombatLog").show();
  recalculateMapContainer();
}
function showCombatLog() {
  $("#combatLog").show();
  $("#showCombatLog").hide();
  recalculateMapContainer();
}
$(function() {
  var mapElem = $("#map");
  var coordElem = jQuery("<div/>", {
    id: "coords"
  });
  $("body").append(coordElem);
  mapElem.mousemove(function(event) {
    var mapPosition = getMapPosition(event);
    coordElem.html(mapPosition.gridX + "," + mapPosition.gridY);
    coordElem.attr("style", "top: " + event.pageY + "px; left:" + (event.pageX + 20) + "px;");
  });
  mapElem.mouseenter(function() {
    coordElem.show();
  });
  mapElem.mouseleave(function() {
    coordElem.hide();
  });
  recalculateMapContainer();
});

function recalculateMapContainer() {
  var mapElem = $("#map");
  var initiativeElem = $("#initiative");
  var combatLogElem = $("#combatLog");
  var width = mapElem.width() + (initiativeElem.is(":visible") ? initiativeElem.width() : 0);
  var height = mapElem.height() + (combatLogElem.is(":visible") ? combatLogElem.height() : 0);
  $("#mapContainer").attr("style", "min-width: " + width + "px; min-height:" + height + "px;");
}

function getMapPosition(event) {
  var mapElem = $("#map");
  var mapX = event.pageX - mapElem.offset().left;
  var mapY = event.pageY - mapElem.offset().top;
  return getMapPositionXY(mapX, mapY);
}
function getMapPositionXY(x, y) {
  var mapElem = $("#map");
  var gridX = Math.floor(x / gridSize);
  var gridY = Math.floor(y / gridSize);
  var halfGrid = Math.floor(gridSize / 2);
  return {
    mapX: x,
    mapY: y,
    gridX: gridX + 1,
    gridY: gridY + 1,
    pageX: x+mapElem.offset().left,
    pageY: y+mapElem.offset().top,
    gridCenterX: (gridX * gridSize) + halfGrid,
    gridCenterY: (gridY * gridSize) + halfGrid,
    gridSize: gridSize,
    halfGrid: halfGrid
  };
}

function strokeCircle(context, x, y, radius) {
  context.beginPath();
  context.arc(x, y, radius, 0, 2 * Math.PI, false);
  context.stroke();
}