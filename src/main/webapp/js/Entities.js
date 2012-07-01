
function Entities(params) {
    this.entityUrl = params.url;
    this.entitiesId = params.id;
}

Entities.prototype.loadEntities = function (entityType) {
    var context = this;
    $.getJSON(context.entityUrl + entityType, function(data){
        context.renderEntities(data);
    });
};

Entities.prototype.renderEntities = function(data) {
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
