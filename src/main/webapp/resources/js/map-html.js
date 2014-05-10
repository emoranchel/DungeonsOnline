function createIconDiv(data) {
  var iconDiv = jQuery("<div/>", {
    title: data.name,
    id: "char-" + data.name,
    style: getIconStyle(data),
    class: "char"
  });
  iconDiv.data("charData", data);
  var hpDiv = jQuery("<div/>", {
    id: "char-" + data.name + "-hpBar",
    style: getHpStyle(data),
    class: "hpBar"
  });
  iconDiv.append(hpDiv);
  return iconDiv;
}

function createTooltipDiv(data) {
  var tooltipContainerDiv = jQuery("<div/>", {
    id: "char-" + data.name + "-tooltip"
  });
  var tooltipDiv = jQuery("<div/>", {
    class: "charTooltip"
  });
  tooltipDiv.append(jQuery("<img/>", {
    class: "charTooltipImage",
    src: data.medium
  }));
  var tooltipContentDiv = jQuery("<div/>", {
    id: "char-" + data.name + "-tooltip-content",
    class: "charTooltipContent"
  });
  tooltipContentDiv.html("<Strong>" + data.name + "</Strong><br>");
  tooltipDiv.append(tooltipContentDiv);
  tooltipContainerDiv.append(tooltipDiv);
  return tooltipContainerDiv;
}

function createInitiativeDiv(data) {
  var initDiv = jQuery("<div/>", {
    style: "background-image:url('" + data.image + "&type=initiative')",
    class: "initiativePortrait",
    id: "char-" + data.name + "-init"
  });
  initDiv.html("<div id=\"char-" + data.name + "-initHpBar\" style=\"" + getHpStyle(data) + "\" class=\"initiativeHpBar\"></div>" +
          "<div class=\"initiativeName\">" + data.name + "</div>");
  return initDiv;
}

function getIconStyle(data) {
  return  "background-image: url('" + data.image + "&type=icon'); " +
          "top: " + ((data.y * gridSize) + gridPadding) + "px; " +
          "left: " + ((data.x * gridSize) + gridPadding) + "px; " +
          "width: " + ((data.width * gridSize) - (gridPadding * 2)) + "px; " +
          "height: " + ((data.height * gridSize) - (gridPadding * 2)) + "px; ";
}

function getHpStyle(data) {
  return  "width: " + data.hpp + "%; " +
          "background: " + data.hpColor + "; ";
}


