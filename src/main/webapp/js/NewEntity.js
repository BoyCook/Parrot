function NewEntity() {
    this.rootUrl = '/service/resources/root';
    this.exampleUrl = '/service/example';
    this.resourceDDId = '#selResource';
    this.newEntityId = '#newEntity';
    this.example = undefined;
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
};

NewEntity.prototype.renderInputForm = function (data) {
    var context = this;
    var html = "<table>";
    context.example = data;
    $.each(data, function (key, value) {
        if (!(value instanceof Array)) {
            html += "<tr><td>" + key + "</td><td><input type='text'/></td></tr>";
        }
    });
    html += "</table>";
    $(context.newEntityId).html(html);
};
