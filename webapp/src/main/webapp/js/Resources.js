
function Resources(params) {
    this.id = params.id;
    this.url = params.url;
    this.resources = undefined;
}

Resources.prototype.load = function () {
    var context = this;
    $.getJSON(context.url, function (data) {
        context.resources = data;
        context.render();
    });
};

Resources.prototype.render = function () {
    var html = "<table>";
    $.each(this.resources['@keys'], function (index, value) {
        html += "<tr><td>";
        html += "<a href='/service" + value + "' target='_blank'>" + value + "</a>"
        html += "</td></tr>";
    });
    html += "</table>";
    $(this.id).append(html);
};
