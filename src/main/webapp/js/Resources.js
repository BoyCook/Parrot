
function Resources(divId) {
    this.url = '/service/resources';
    this.divId = divId;
    this.resources = undefined;
}

Resources.prototype.render = function () {
    var context = this;
    $.getJSON(context.url, function (data) {
        context.resources = data;
        var html = "<table>";
        $.each(data, function (index, value) {
            html += "<tr><td>";
            html += "<a href='/service" + value + "' target='_blank'>" + value + "</a>"
            html += "</td></tr>";
        });
        html += "<table>";
        $(context.divId).append(html);
    });
};

function RootResources(divId) {
    Resources.call(this, divId);
    this.url = '/service/resources/root';
}

RootResources.prototype = new Resources();
RootResources.prototype.constructor = RootResources;
