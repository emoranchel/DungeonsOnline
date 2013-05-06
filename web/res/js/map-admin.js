$(function() {
  $("#map").click(function(event) {
    event.stopPropagation();
    $('.options').hide();
    $("#map-options").dialog("open");
    var mapPosition = getMapPosition(event);
    $(".map-options-x").val(mapPosition.gridX - 1);
    $(".map-options-y").val(mapPosition.gridY - 1);
  });

  hideCombatLog();
  showInitiative();

  $(document).tooltip({
    track: true
  });

  rebind();
});

function rebind() {
  $(".initiativePortrait").off("click", highlightChar);
  $(".initiativePortrait").on("click", highlightChar);

  $(".char").off("click", highlightChar);
  $(".char").on("click", highlightChar);
  $(".char").draggable({
    grid: [30, 30],
    stop: function(event, ui) {
      var name = ui.helper.attr("data-char");
      event.stopPropagation();
      $('.options').hide();
      var mapPosition = getMapPositionXY(ui.position.left, ui.position.top);
      $(".map-options-x").val(mapPosition.gridX - 1);
      $(".map-options-y").val(mapPosition.gridY - 1);
      $(".map-options-select").val(name);
      $(".map-options-moveBtn").click();
    }
  });

  $(".options").dialog({
    autoOpen: false,
    closeOnEscape: true,
    draggable: true,
    modal: true
  });
  $(".tabs").tabs();
  $(".char").off("click", charDivClick);
  $(".char").on("click", charDivClick);

  $(".options").off("click", optionsLinkClick);
  $(".options").on("click", optionsLinkClick);
}

function highlightChar() {
  var name = $(this).attr("data-char");
  $("#char-" + name).effect("bounce", {}, 500);
  $("#char-" + name + "-init").effect("bounce", {}, 500);
}

function charDivClick(event) {
  event.stopPropagation();
//  var charDiv = $(this);
  var name = $(this).attr("data-char");
  $(".options").dialog("close");
  $("#char-" + name + "-options").dialog("open");
  $("#char-" + name + "-tabs").tabs("load", "char-" + name + "-tabs-main");
}
function optionsLinkClick(event) {
  event.stopPropagation();
}

