function MapOverlay() {
  this.canvasElement = document.createElement("canvas");
  this.canvas = $(this.canvasElement);
  this.canvas.attr("style", "position:absolute; top:0px; left:0px; background-color: rgba(0,0,0,0);");
  this.context = null;
  this.clear = function() {
    this.context.clearRect(0, 0, this.canvasElement.width, this.canvasElement.height);
  };
  this.resize = function(width, height) {
    this.canvasElement.width = width;
    this.canvasElement.height = height;
    this.context = this.canvasElement.getContext("2d");
  };
  this.radius = function(radius, gridSize, x, y) {
    var halfGrid = Math.floor(gridSize / 2);
    this.context.beginPath();
    this.context.arc(x, y, halfGrid + (radius * gridSize), 0, 2 * Math.PI, false);
    this.context.fillStyle = 'rgba(200,255,230,0.25)';
    this.context.fill();
    this.context.lineWidth = 1;
    this.context.strokeStyle = '#003300';
    for (i = 0; i < (radius + 1); i++) {
      this.context.beginPath();
      this.context.arc(x, y, halfGrid + (i * gridSize), 0, 2 * Math.PI, false);
      this.context.stroke();
    }
  };
  this.setup = function(mapElem) {
    mapElem.prepend(this.canvas);
    this.resize(mapElem.width(), mapElem.height());
    this.clear();
  };
  this.show = function(mapOptions, mapCoord, mapPosition) {
    if (mapCoord) {
      mapCoord.html(mapPosition.gridX + "," + mapPosition.gridY);
    }
    var style = ""
            + "font-size: 75%; "
            + "background-color: rgba(255,255,255,0.8); "
            + "border:1px solid #000000; "
            + "position:absolute; ";
    mapOptions.attr("style", style);
    var xCoord = (mapPosition.pageX - 10);
    if ((xCoord + mapOptions.width() + 10) > $(document).width()) {
      xCoord = $(document).width() - mapOptions.width() - 10;
    }
    style += ""
            + "left:" + xCoord + "px; "
            + "top:" + (mapPosition.pageY - 5) + "px; ";
    mapOptions.attr("style", style);
    mapOptions.show();

  };
}
