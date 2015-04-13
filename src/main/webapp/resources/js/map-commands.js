$(function () {
  $("#commandPowerList").hide();
  $("#commandPowers").on("click", function () {
    $("#commandPowerList").toggle();
  });
  $(".power").each(function () {
    var card = $(this).find(".detailCard");
    card.dialog({autoOpen: false});
    $(this).on("click", function () {
      if (card.dialog("isOpen")) {
        card.dialog("close");
      } else {
        card.dialog("open");
      }
    });
  });
  $(".power").tooltip({
    position: {my: "right bottom"}
  });
});