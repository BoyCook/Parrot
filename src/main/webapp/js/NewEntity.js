function NewEntity(params) {
    this.button = params.button;
    this.id = params.id;
    this.idPrefix = 'newEntity_';
}

NewEntity.prototype.setup = function () {
    var context = this;
    $(context.button).click(function(){
        context.submit(context.readInputForm());
    });
};

NewEntity.prototype.renderInputForm = function (model) {
    var context = this;
    var html = "<table>";

    $.each(model.attributes, function (index, value) {
        if (value.column == true) {
            html += "<tr><td>" + value.description + "</td><td><input type='text' id='" + context.idPrefix + value.name + "'/></td></tr>";
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
