
function Resources(params) {
    this.id = params.id;
    this.url = params.url;
    this.resources = undefined;
}

Resources.prototype.render = function () {
    var context = this;
    $.getJSON(context.url, function (data) {
        context.resources = data;
        var html = "<table>";
        $.each(data, function (key, value) {
            html += "<tr><td>";
            html += "<a href='/service" + key + "' target='_blank'>" + key + "</a>"
            html += "</td></tr>";
        });
        html += "</table>";
        $(context.id).append(html);
    });
};
