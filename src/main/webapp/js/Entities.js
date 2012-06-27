
function Entities() {
    this.rootUrl = '/service/resources/root';
    this.entityUrl = '/service';
    this.resourceDDId = '#selResource';
    this.entitiesId = '#entities';
}

Entities.prototype.setup = function () {
    var context = this;
    //Load dropdown content
    $.getJSON(context.rootUrl, function (data) {
        var html = "<option value='-1'>-- Select --</option>";
        $.each(data, function (index, value) {
            html += "<option value='" + value + "'>" + value + "</option>";
        });
        $(context.resourceDDId).append(html);
    });

    //Load example JSON and create UI form
    $('#selResource').change(function() {
        var val = $(this).val();
        if (val != -1) {
            $.getJSON(context.entityUrl + val, function(data){
                context.render(data);
            });
        }
    });
};

Entities.prototype.render = function(data) {
    var context = this;
    var html = "<table>";
    $.each(data, function (index, entity) {
        $.each(entity, function(key, value){
            if (!(value instanceof Array)) {
                html += "<tr><td>" + key + "</td><td>" + value + "</td></tr>";
            }
        });
    });
    html += "</table>";
    $(context.entitiesId).html(html);
};

