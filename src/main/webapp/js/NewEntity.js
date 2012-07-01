function NewEntity(params) {
    this.button = params.button;
    this.id = params.id;
    this.exampleUrl = '/service/example';
    this.example = undefined;
    this.idPrefix = 'newEntity_';
}

NewEntity.prototype.setup = function () {
    var context = this;
    $(context.button).click(function(){
        context.submit(context.readInputForm());
    });
};

NewEntity.prototype.loadInputForm = function (val) {
    var context = this;
    $.getJSON(context.exampleUrl + val, function(data){
        context.renderInputForm(data);
    });
};

NewEntity.prototype.renderInputForm = function (data) {
    var context = this;
    var html = "<table>";
    context.example = data;
    $.each(data, function (key, value) {
        if (!(value instanceof Array)) {
            html += "<tr><td>" + key + "</td><td><input type='text' id='" + context.idPrefix + key + "'/></td></tr>";
        }
    });
    html += "</table>";
    $(context.id).html(html);
};

NewEntity.prototype.readInputForm = function () {
    var context = this;
    var entity = context.example;

    $(this.id + ' input:text').each(function(index, value) {
        var id = $(value).attr('id');
        id = id.substring(context.idPrefix.length);
        entity[id] = $(value).val();
    });

    return entity;
};

NewEntity.prototype.submit = function (entity) {
    var url = '/service' + $('#selResource').val();
    $.ajax({
        type: 'PUT',
        url: url,
        data: JSON.stringify(entity),
        contentType: 'application/json',
        dataType: 'json'
    });
};
