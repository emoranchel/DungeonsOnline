$(function() {
  $("#nextTurnButton").button().click(function() {
    $(".JSFendTurnCommandButton")[0].click();
  });
  $("#map").on("click", function(ev) {
    var x = Math.floor((ev.pageX - $(this).offset().left) / 30);
    var y = Math.floor((ev.pageY - $(this).offset().top) / 30);
    $(".JSFWorkspaceInputX").val(x);
    $(".JSFWorkspaceInputY").val(y);
  });
  $(".char").on("click", function() {
    var charName = $(this).attr("data-char");
    var currentTargets = $(".JSFWorkspaceTarget").val().split(",").filter(function(val) {
      return val.trim() !== "";
    });
    var index = currentTargets.indexOf(charName);
    if (index >= 0) {
      currentTargets.splice(index, 1);
    } else {
      currentTargets.push(charName);
    }
    $(".JSFWorkspaceTarget").val(currentTargets.join(","));
    return false;
  });
});

//Highlights
setInterval(function() {
  var currentTargets = $(".JSFWorkspaceTarget").val().split(",").filter(function(val) {
    return val.trim() !== "";
  });
  var currentSources = $(".JSFWorkspaceSource").val().split(",").filter(function(val) {
    return val.trim() !== "";
  });
  var currentTurn = $(".JSFWorkspaceTurn").val();
  $(".char").each(function() {
    var charName = $(this).attr("data-char");
    if (currentTargets.indexOf(charName) >= 0) {
      $(this).effect("highlight",{color:"#FF9999"}, 150);
    }
    if (currentSources.indexOf(charName) >= 0) {
      $(this).effect("highlight",{color:"#9999FF"}, 150);
    }
    if (currentTurn === charName) {
      $(this).effect("highlight",{color:"#99FF99"}, 150);
    }
  });

}, 500);