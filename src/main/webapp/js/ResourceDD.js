
function ResourceDD(params) {
    this.model = params.model;
    this.id = params.id;
    this.changeCallBack = params.changeCallBack;
}

ResourceDD.prototype.setup = function (data) {
    var context = this;
    $(context.id).change(function(){
        var val = $(this).val();
        if (val != -1) {
            context.changeCallBack(val);
        }
    });
    context.model = data;
    context.renderDD(data);
};

ResourceDD.prototype.renderDD = function (data) {
    var context = this;
    var html = "<option value='-1'>-- Select --</option>";
    $.each(data, function (key, value) {
        html += "<option value='" + key + "'>" + key + "</option>";
    });
    $(context.id).append(html);
};
