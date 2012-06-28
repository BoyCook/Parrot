function NewEntity() {
    this.rootUrl = '/service/resources/root';
    this.exampleUrl = '/service/example';
    this.resourceDDId = '#selResource';
    this.newEntityId = '#newEntity';
    this.example = undefined;
    this.idPrefix = 'newEntity_';
}

NewEntity.prototype.setup = function () {
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
    $('#selResource').change(function () {
        var val = $(this).val();
        if (val != -1) {
            $.getJSON(context.exampleUrl + val, function(data){
                context.renderInputForm(data);
            });
        }
    });

    $('#subNew').click(function(){
        context.submit(context.readInputForm());
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
    $(context.newEntityId).html(html);
};

NewEntity.prototype.readInputForm = function () {
    var context = this;
    var entity = context.example;

    $(this.newEntityId + ' input:text').each(function(index, value) {
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
