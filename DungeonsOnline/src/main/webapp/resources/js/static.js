$(function () {

    $("#dialog").dialog({
        modal: true,
        autoOpen: false,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
    });

    $(window).resize(function () {
        $('.ui-dialog').css({
            'width': $(window).width() - 20,
            //'height': $(window).height(),
            'left': '0px',
            'top': '0px'
        });
    }).resize();

    function addCharacter(data) {
        var iconDiv = createIconDiv(data);
        var tooltipDiv = createTooltipDiv(data);
        var initDiv = createInitiativeDiv(data);

        $("#map").append(iconDiv);
        $("body").append(tooltipDiv);
        $("#initiativeContent").append(initDiv);

        iconDiv.tooltip({
            content: function () {
                return $("#char-" + data.name + "-tooltip").html();
            },
            track: true
        });

        tooltipDiv.hide();

        var highlightFunction = function () {
            iconDiv.effect("bounce", {}, 500);
            initDiv.effect("bounce", {}, 500);
        };

        initDiv.on("click", highlightFunction);
        initDiv.on("click", displayDetails(data.name, function () {
            $("#dialog").html("LOADING...");
            $("#dialog").dialog("open");
        }, function (charHtml) {
            $("#dialog").html(charHtml);
        }));
        iconDiv.click(highlightFunction);
        iconDiv.mouseenter(highlightFunction);
    }
    function updateCharacter(data) {
        var dataDiv = $("#char-" + data.name + "-tooltip-content");
        var html = "<div class=\"tooltip-name\">" + data.name + "</div>";
        if (data.hp) {
            html += "<div class=\"tooltip-hp\">HP:" + data.hp + "/" + data.hpMax + "</div>";
        }
        if (data.healingSurge) {
            html += "<div class=\"tooltip-hs\">Healing Surges:" + data.healingSurge + "</div>";
        }
        if (data.effects) {
            for (var i = 0; i < data.effects.length; i++) {
                var effect = data.effects[i];
                html += "<div class=\"tooltip-effect-" + (i % 2 === 0 ? "even" : "odd") + "\">";
                html += "<div class=\"tooltip-effect-name\">" + effect.source + "&gt;" + effect.target + "</div>";
                html += "<div class=\"tooltip-effect-effect\">" + effect.effect + "</div>";
                html += "<div class=\"tooltip-effect-duration\">" + effect.duration + "</div>";
                html += "</div>";
            }
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
        $(".char").each(function () {
            var charDiv = $(this);
            charDiv.attr("style", getIconStyle(charDiv.data("charData")));
        });
        recalculateMapContainer();
        $("#map").trigger("mapUpdated");
    }

    function updateLog(data) {
        $("#combatLogContent").append("<br>" + data.desc);
    }

    var loc = window.location;
    var new_uri;
    if (loc.protocol === "https:") {
        new_uri = "https:";
    } else {
        new_uri = "http:";
    }
    new_uri += "//" + loc.host + "/dungeons-online/rest/combat";


    $.ajax({
        type: "GET",
        url: new_uri
    }).done(function (actions) {
        actions.forEach(function (action) {
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
            if ("actionTaken" === action.action) {
                updateLog(action.object);
            }
        });
    });

    showCombatLog();
    showInitiative();

});
$(function () {

    var overlay = new MapOverlay();
    overlay.setup($("#map"));

    var mapPosition = {};

    $("#map").on("mapUpdated", function () {
        var mapElem = $(this);
        overlay.resize(mapElem.width(), mapElem.height());
        overlay.clear();
    });

    $("#map").click(function (event) {
        overlay.clear();
        mapPosition = getMapPosition(event);
        overlay.show($("#mapOptions"), $("#mapOptions_coordinates"), mapPosition);
    });

    $("#mapOptions_radius").click(function () {
        var total = parseInt($("#mapOptions_length").val());
        overlay.radius(total, gridSize, mapPosition.gridCenterX, mapPosition.gridCenterY);
        $("#mapOptions").hide();
    });

    $("#mapOptions_close").click(function () {
        $("#mapOptions").hide();
    });

    $("#mapOptions").click(function (event) {
        event.stopPropagation();
    });
});
