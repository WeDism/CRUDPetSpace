var deleteUser = function () {
    var tr = this.closest('tr');
    $.ajax({
        url: window.location.href + '?' + $.param({user: tr.dataset.essenceId}),
        async: false,
        type: "DELETE",
        success: function () {
            tr.remove();
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
};
var changeRoleUserEssence = function () {
    var tr = this.closest('tr');
    $.ajax({
        url: window.location.href + '?' + $.param({user: tr.dataset.essenceId, role: $(this).find(':selected')[0].value}),
        async: false,
        type: "PUT",
        success: function () {
            alert('Role Essence is updated');
        },
        error: function (xhr) {
            var previewValue = $(tr).find('.user-essence-roles')[0].dataset.previewValue;
            $(tr).find('[value=' + previewValue + ']')[0].setAttribute('selected', true);
            var select = $(tr).find('.user-essence-roles')[0];
            var options = $(select).children();
            for (var i = 0; options.length; i++) {
                if (options[i].value == previewValue)
                    select.selectedIndex = i;
                break;
            }
            alert(xhr.status);
        }
    })
};
var setPreviewDataValue = function () {
    this.dataset.previewValue = $(this).find(':selected')[0].value;
};

var init = function () {
    $('.delete-user-of-button').on('click', deleteUser);
    $('.user-essence-roles').on('change', changeRoleUserEssence).on('click', setPreviewDataValue);
};
$(window).on('load', init);