
function Model() {
    this.url = '/service/model';
    this.data = undefined;
}

Model.prototype.all = function() {
    return this.data['@items'];
};

Model.prototype.get = function(name) {
    var model = undefined;
    name = name.toLowerCase();
    $.each(this.data['@items'], function(key, value){
        if (value.name.toLowerCase() == name) {
            model = value;
        }
    });
    return model;
};

Model.prototype.keys = function() {
    return this.data['@keys'];
};

Model.prototype.load = function(callBack) {
    var context = this;
    $.getJSON(context.url, function(data){
        context.data = data;
        if (callBack) {
            callBack(data);
        }
    });
};
