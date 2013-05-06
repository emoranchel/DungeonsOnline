$(function() {
  var mapElem = $("#map");
  var canvas = document.getElementById('mapOverlay');
  canvas.width = mapElem.width();
  canvas.height = mapElem.height();
  var context = canvas.getContext('2d');

  var mapPosition;

  mapElem.click(function(event) {
    context.clearRect(0, 0, canvas.width, canvas.height);
    mapPosition = getMapPosition(event);
    $("#mapOptionsCoordinates").html(mapPosition.gridX + "," + mapPosition.gridY);
    var mapOptions = $("#mapOptions");
    var xCoord = (mapPosition.pageX - 10);
    if ((xCoord + mapOptions.width() + 10) > $(document).width()) {
      xCoord = $(document).width() - mapOptions.width() - 10;
    }
    mapOptions.attr("style", "left:" + xCoord + "px; top:" + (mapPosition.pageY - 5) + "px;");
    mapOptions.show();
  });

  $("#mapOptionsRadiusButton").click(function() {
    var total = parseInt($("#mapOptionsLengthInput").val());
    var radius = mapPosition.halfGrid + (total * gridSize);
    context.beginPath();
    context.arc(mapPosition.gridCenterX, mapPosition.gridCenterY, radius, 0, 2 * Math.PI, false);
    context.fillStyle = 'rgba(200,255,230,0.25)';
    context.fill();
    context.lineWidth = 1;
    context.strokeStyle = '#003300';
    for (i = 0; i < (total + 1); i++) {
      strokeCircle(context, mapPosition.gridCenterX, mapPosition.gridCenterY, mapPosition.halfGrid + (i * gridSize));
    }
    $("#mapOptions").hide();
  });

  $("#mapOptionsCloseLink").click(function() {
    $("#mapOptions").hide();
  });

  $("#mapOptions").hide();
});

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

  var loc = window.location, new_uri;
  if (loc.protocol === "https:") {
    new_uri = "wss:";
  } else {
    new_uri = "ws:";
  }
  new_uri += "//" + loc.host+ "/DungeonsOnline/notifications";

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
  };

  showCombatLog();
  showInitiative();

});
