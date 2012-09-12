
function Entities(params) {
    this.entityUrl = params.url;
    this.entitiesId = params.id;
}

Entities.prototype.loadEntities = function (model) {
    var context = this;
    $.getJSON(context.entityUrl + '/' + model.name, function(data){
        resolveRefs(data);
        context.renderEntities(model, data);
    });
};

Entities.prototype.renderEntities = function(model, data) {
    var context = this;
    var html = "<table>";
    html += "<tr>";
    $.each(model.attributes['@items'], function(index, value){
        if (value.column == true) {
            html += "<th>" + value.description + "</th>";
        }
    });
    html += "</tr>";

    //TODO: only render columns in the model

    if (data['@items']) {
        $.each(data['@items'], function (index, entity) {
            html += "<tr>";
            $.each(model.attributes['@items'], function(index, value){
                if (value.column == true) {
                    var val = entity[value.name];
                    if (value.identity == true) {
                        html += "<td><a href='/service/" + model.name + "/" + val + "' target='_blank'>" + val + "</a></td>"
                    } else {
                        html += "<td>" + val + "</td>";
                    }
                }
            });
            html += "</tr>";
        });
    }

    html += "</table>";
    $(context.entitiesId).html(html);
};
