
function Entities(params) {
    this.entityUrl = params.url;
    this.entitiesId = params.id;
}

Entities.prototype.loadEntities = function (model) {
    var context = this;
    $.getJSON(context.entityUrl + '/' + model.name, function(data){
        context.renderEntities(model, data);
    });
};

Entities.prototype.renderEntities = function(model, data) {
    var context = this;
    var html = "<table>";
    html += "<tr>";
    $.each(model.attributes, function(index, value){
        if (value.column == true) {
            html += "<th>" + value.description + "</th>";
        }
    });
    html += "</tr>";

    //TODO: only render columns in the model
    $.each(data, function (index, entity) {
        html += "<tr>";
        $.each(entity, function(key, value){
            if (!(value instanceof Array)) {
                html += "<td>" + value + "</td>";
            }
        });
        html += "</tr>";
    });
    html += "</table>";
    $(context.entitiesId).html(html);
};
