
function ResourceDD(params) {
    this.url = params.url;
    this.id = params.id;
    this.changeCallBack = params.changeCallBack;
    this.data = undefined;
}

ResourceDD.prototype.setup = function () {
    var context = this;
    $(context.id).change(function(){
        var val = $(this).val();
        if (val != -1) {
            context.changeCallBack(val);
        }
    });
    context.getData(function (data) {
        context.renderDD(data);
    });
};

ResourceDD.prototype.getData = function (callBack) {
    var context = this;
    $.getJSON(context.url, function (data) {
        if (callBack) {
            callBack(data);
        }
    });
};

ResourceDD.prototype.renderDD = function (data) {
    var context = this;
    var html = "<option value='-1'>-- Select --</option>";
    $.each(data, function (key, value) {
        html += "<option value='" + key + "'>" + key + "</option>";
    });
    $(context.id).append(html);
};
