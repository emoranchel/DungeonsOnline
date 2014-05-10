var overlay;
var mapPosition = {};
var mapActions = {
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

$(function() {
  overlay = new MapOverlay();
  overlay.setup($("#map"));
  $("#map").click(function(event) {
    hideAll(event);
    mapPosition = getMapPosition(event);
    overlay.show($("#mapOptions"), $("#mapOptions_coordinates"), mapPosition);
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
}

function hideAll(event) {
  overlay.clear();
}
