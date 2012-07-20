function Entity() {
}

Entity.prototype.setup = function () {
    var context = this;
    $('#main').tabs();
    $('#main input:text').attr('disabled', 'disabled');
    imageButton('.ui-image-button');
    this.setMode('.ui-mode-default');

    $('#butEdit').click(function () {
        $('#main input:text').removeAttr('disabled');
        context.setMode('.ui-mode-edit')
    });
    $('#butCancelEdit').click(function () {
        $('#main input:text').attr('disabled', 'disabled');
        context.setMode('.ui-mode-default')
    });
    $('#butSave').click(function () {
        $('#main input:text').attr('disabled', 'disabled');
        context.setMode('.ui-mode-default')
    });
    $('#butDelete').click(function () {
        context.setMode('.ui-mode-default')
    });
};

Entity.prototype.setMode = function (mode) {
    $('.ui-mode-item').hide();
    $(mode).show();
};
