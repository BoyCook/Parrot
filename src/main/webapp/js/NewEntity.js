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
    $('#selResource').change(function() {
        var val = $(this).val();
        if (val != -1) {
            $.getJSON(context.exampleUrl + val, function (data) {
                var html = "<table>";
                context.example = data;
                $.each(data, function (key, value) {
                    if (value != undefined && !(value instanceof Array)) {
                        html += "<tr><td>" + key + "</td></tr>";
                    }
                });
                html += "</table>";
                $(context.newEntityId).append(html);
            });
        }
    });
};

