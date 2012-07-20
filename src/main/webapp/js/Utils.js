/**
 * Create button with an image
 * @param elements
 */
function imageButton(elements) {
    $(elements).button();
    $(elements).each(function() {
        var label = $('#' + this.id + ' label').remove();
        $('#' + this.id).append(label);
    });
}
