var changeStateFriend = function () {
    var tr = $(this).closest('tr')[0];
    var path = $(this).closest('tbody')[0];
    $.ajax({
        url: 'http://' + window.location.host + path.dataset.pathForStateFriend + '?' + $.param({
            user_essence_id: tr.dataset.essenceId,
            state_friend: $(this).find(':selected')[0].value
        }),
        async: false,
        type: 'PUT',
        success: function () {
            alert('State friend changed');
        },
        error: function (xhr) {
            var select = $(tr).find('.action-friends')[0];
            var previewValue = select.dataset.previewValue;
            $(select).find('[value=' + previewValue + ']')[0].setAttribute('selected', true);
            var options = $(select).children();
            for (var i = 0; options.length; i++) {
                if (options[i].value == previewValue)
                    select.selectedIndex = i;
                break;
            }
            alert('Error' + xhr.status);
        }
    })
};
var setPreviewDataValue = function () {
    this.dataset.previewValue = $(this).find(':selected')[0].value;
};

var init = function () {
    $('.action-friends').on('change', changeStateFriend).on('click', setPreviewDataValue);
};
$(window).on('load', init);