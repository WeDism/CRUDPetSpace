var deleteUser = function () {
    var button = this;
    $.ajax({
        url: window.location.href + '?user=' + button.value,
        async: false,
        type: "DELETE",
        success: function () {
            $(button).closest('tr').remove();
        }
    })
};
var init = function () {
    $('.deleteUserOfButton').on('click', deleteUser);
};
$(window).on('load', init);