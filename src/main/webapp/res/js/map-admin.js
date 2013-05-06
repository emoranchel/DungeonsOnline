$(function() {
  $("#map").click(function(event) {
    event.stopPropagation();
    $('.options').hide();
    $("#map-options").dialog("open");
    var mapPosition = getMapPosition(event);
    $(".map-options-x").val(mapPosition.gridX - 1);
    $(".map-options-y").val(mapPosition.gridY - 1);
  });

  var rebindAjax = function rebindAjax(data) {
    if (data.status === "success") {
      rebind();
    }
  };


  rebind();

  try {
    jsf.ajax.addOnEvent(rebindAjax);
  } catch (err) {
  }
});

function rebind() {
  $(".initiativePortrait").off("click", highlightChar);
  $(".initiativePortrait").on("click", highlightChar);

  $(".char").each(function() {
    var charDiv = $(this);
    charDiv.on("click", charDivClick);
    charDiv.tooltip({
      items: "div",
      track: true,
      content: function() {
        return $("#char-" + charDiv.data("char") + "-tooltip").html();
      }
    });
    $("#char-" + charDiv.data("char") + "-tooltip").hide();
    charDiv.draggable({
      grid: [30, 30],
      distance: 20,
      stop: function(event, ui) {
        event.stopPropagation();
        var name = ui.helper.attr("data-char");
        $('.options').hide();
        var mapPosition = getMapPositionXY(ui.position.left, ui.position.top);
        $('#moveForm\\:optionX').val(mapPosition.gridX - 1);
        $("#moveForm\\:optionY").val(mapPosition.gridY - 1);
        $("#moveForm\\:optionSelect").val(name);
        $("#moveForm\\:actionMove").click();
      }
    });
  });


}

function highlightChar(event) {
  event.stopPropagation();
  var name = $(this).attr("data-char");
  $("#char-" + name).effect("bounce", {}, 500);
  $("#char-" + name + "-init").effect("bounce", {}, 500);
}

function charDivClick(event) {
  hideAll();
  mapPosition = getMapPosition(event);
  highlightChar(event);
  var charDiv = $(this);
  var name = charDiv.attr("data-char");
  var optionsDiv = $("#char-" + name + "-options");
  optionsDiv.attr("style", ""
          + "top:" + charDiv.offset().top + "px;"
          + "left:" + charDiv.offset().left + "px;"
          );
  optionsDiv.show();
}

function hideAll(event) {
  if (event) {
    event.stopPropagation();
  }
  overlay.clear();
  $(".options").hide();
  $(".combatantOptions").hide();
  $("#mapOptions").hide();
}

var mapPosition = {};
mapActions = {
  radius: function(elem, event) {
    hideAll(event);
    var value = parseInt($(elem).parent().children("input[name=radius]").val());
    overlay.radius(value, gridSize, mapPosition.gridCenterX, mapPosition.gridCenterY);
  },
  path: function(elem, event) {
    hideAll(event);
  },
  sight: function(elem, event) {
    hideAll(event);
  }
};


var overlay;
$(function() {
  overlay = new MapOverlay();
  overlay.setup($("#map"));

  $("#map").click(function(event) {
    hideAll(event);
    mapPosition = getMapPosition(event);
    overlay.show($("#mapOptions"), $("#mapOptions_coordinates"), mapPosition);
  });
});