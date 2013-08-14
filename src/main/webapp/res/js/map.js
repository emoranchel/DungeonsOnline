$(function() {
  function addCharacter(data) {
    var iconDiv = createIconDiv(data);
    var tooltipDiv = createTooltipDiv(data);
    var initDiv = createInitiativeDiv(data);

    $("#map").append(iconDiv);
    $("body").append(tooltipDiv);
    $("#initiativeContent").append(initDiv);

    iconDiv.tooltip({
      content: function() {
        return $("#char-" + data.name + "-tooltip").html();
      },
      track: true
    });

    tooltipDiv.hide();

    var highlightFunction = function() {
      iconDiv.effect("bounce", {}, 500);
      initDiv.effect("bounce", {}, 500);
    };
    initDiv.click(highlightFunction);
    iconDiv.click(highlightFunction);
    iconDiv.mouseenter(highlightFunction);
  }

  function updateCharacter(data) {
    var dataDiv = $("#char-" + data.name + "-tooltip-content");
    var html = "<strong>" + data.name + "</strong><br>";
    if (data.hp) {
      html += "<strong>HP:" + data.hp + "/" + data.hpMax + "</strong><br>";
    }
    if (data.healingSurge) {
      html += "<strong>Healing Surges:" + data.healingSurge + "<br>";
    }
    dataDiv.html(html);
    dataDiv.append(
            jQuery("<div/>", {
      style: "height: 4px; " + getHpStyle(data),
      class: "hpBar"
    }));
    $("#char-" + data.name).data("charData", data);
    $("#char-" + data.name).attr("style", getIconStyle(data));
    $("#char-" + data.name + "-hpBar").attr("style", getHpStyle(data));
    $("#char-" + data.name + "-initHpBar").attr("style", getHpStyle(data));
  }

  function removeCharacter(data) {
    $("#char-" + data.name).remove();
    $("#char-" + data.name + "-tooltip").remove();
    $("#char-" + data.name + "-init").remove();
  }

  function updateInitiative(data) {
    var initDiv = $("#initiativeContent");
    for (i = 0; i < data.length; i++) {
      initDiv.append($("#char-" + data[i] + "-init"));
    }
  }

  function updateMap(data) {
    $("#map").attr("style",
            "background-image:url('" + data.url + "'); "
            + "width:" + (data.width * gridSize) + "px; "
            + "height:" + (data.height * gridSize) + "px; ");
    $(".char").each(function() {
      var charDiv = $(this);
      charDiv.attr("style", getIconStyle(charDiv.data("charData")));
    });
    recalculateMapContainer();
    $("#map").trigger("mapUpdated");
  }

  var loc = window.location, new_uri;
  if (loc.protocol === "https:") {
    new_uri = "wss:";
  } else {
    new_uri = "ws:";
  }
  new_uri += "//" + loc.host + "/dungeons-online/notifications";

  var websocket = new WebSocket(new_uri);
  websocket.onmessage = function(event) {
    var action = JSON.parse(event.data);
    if ("combatantAdded" === action.action) {
      addCharacter(action.object);
      updateCharacter(action.object);
    }
    if ("combatantUpdated" === action.action) {
      updateCharacter(action.object);
    }
    if ("combatantRemoved" === action.action) {
      removeCharacter(action.object);
    }
    if ("initiativeUpdated" === action.action) {
      updateInitiative(action.object);
    }
    if ("combatMapUpdated" === action.action) {
      updateMap(action.object);
    }
  };

  showCombatLog();
  showInitiative();

});
$(function() {

  var overlay = new MapOverlay();
  overlay.setup($("#map"));

  var mapPosition = {};

  $("#map").on("mapUpdated", function() {
    var mapElem = $(this);
    overlay.resize(mapElem.width(), mapElem.height());
    overlay.clear();
  });

  $("#map").click(function(event) {
    overlay.clear();
    mapPosition = getMapPosition(event);
    overlay.show($("#mapOptions"), $("#mapOptions_coordinates"), mapPosition);
  });

  $("#mapOptions_radius").click(function() {
    var total = parseInt($("#mapOptions_length").val());
    overlay.radius(total, gridSize, mapPosition.gridCenterX, mapPosition.gridCenterY);
    $("#mapOptions").hide();
  });

  $("#mapOptions_close").click(function() {
    $("#mapOptions").hide();
  });

  $("#mapOptions").click(function(event) {
    event.stopPropagation();
  });
});
