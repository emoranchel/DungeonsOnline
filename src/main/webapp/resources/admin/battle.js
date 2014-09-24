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
  $(".char").each(function() {
    var charName = $(this).attr("data-char");
    var index = currentTargets.indexOf(charName);
    if (index >= 0) {
      $(this).effect("highlight",{color:"#9999FF"}, 150);
    }
  });

}, 500);