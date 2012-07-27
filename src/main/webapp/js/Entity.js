/**
 * Manages the state of an entity
 * @param type
 * @param id
 * @constructor
 */
function Entity(type, id) {
    this.type = type;
    this.id = id;
}

Entity.prototype.setup = function () {
    var context = this;
    $('#main').tabs();
    $('#main input:text').attr('disabled', 'disabled');
    imageButton('.ui-image-button');
    this.setMode('.ui-mode-default');

    $('#butEdit').click(function () {
        $('#main input:text').removeAttr('disabled');
        context.setMode('.ui-mode-edit');
    });
    $('#butCancelEdit').click(function () {
        $('#main input:text').attr('disabled', 'disabled');
        context.setMode('.ui-mode-default');
    });
    $('#butSave').click(function () {
        context.save(context.readInputForm(), function () {
            $('#main input:text').attr('disabled', 'disabled');
            context.setMode('.ui-mode-default');
        });
    });
    $('#butDelete').click(function () {
        context.delete(function () {
            context.setMode('.ui-mode-default');
        });
    });
};

Entity.prototype.setMode = function (mode) {
    $('.ui-mode-item').hide();
    $(mode).show();
};

Entity.prototype.readInputForm = function () {
    var entity = {};
    $('#entity input:text').each(function (index, value) {
        var id = $(value).attr('id');
        entity[id] = $(value).val();
    });
    return entity;
};

Entity.prototype.save = function (entity, success) {
    var url = '/service/' + this.type + '/' + this.id;
    $.ajax({
        type:'POST',
        url:url,
        data:JSON.stringify(entity),
        contentType:'application/json',
        dataType:'json',
        success:success
    });
};

Entity.prototype.delete = function (success) {
    var url = '/service/' + this.type + '/' + this.id;
    $.ajax({
        type:'DELETE',
        url:url,
        success:success
    });
};


