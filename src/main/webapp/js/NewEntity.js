
function NewEntity() {
    this.rootUrl ='/service/resources/root';
    this.resourceDDId = '#selResource';
}

NewEntity.prototype.setup = function() {
    var context = this;
    $.getJSON(context.rootUrl, function (data) {
        var html = "<option value='-1'>-- Select --</option>";
        $.each(data, function (index, value) {
            html += "<option value='" + value + "'>" + value + "</option>";
        });
        html += "<table>";
        $(context.resourceDDId).append(html);
    });
};
