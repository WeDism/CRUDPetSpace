var doFriend = function (input, typeRequest, bool) {
    var tr = $(input).closest('tr')[0];
    var path = $(input).closest('tbody')[0];
    $.ajax({
        url: 'http://' + window.location.host + path.dataset.pathForEssenceFriend + '?' + $.param({user_essence_id: tr.dataset.essenceId}),
        async: false,
        type: typeRequest,
        success: function () {
            $(input).prop('checked', bool);
            bool ? alert('Friend requested') : alert('Delete request');
        },
        error: function (xhr) {
            $(input).prop('checked', !bool);
            alert('Error' + xhr.status);
        }
    })
};
var requestFriend = function (input) {
    doFriend(input, 'POST', true);
};

var removeRequestFriend = function (input) {
    doFriend(input, 'DELETE', false);
};

var friendController = function () {
    if (!this.checked)
        removeRequestFriend(this);
    else requestFriend(this);
};

var init = function () {
    $('.essence-friend-checkbox').on('change', friendController);
};
$(window).on('load', init);