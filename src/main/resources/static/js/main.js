$(function () {
    const url = window.location.href;
    if (url.includes('/user/')) {
        $('#v-pills-tab a[href="#v-pills-profile"]').tab('show');
        document.title = 'User Page';
    }
});

$(document).ready(function () {
    viewAllUsers();
    defaultModal();
    addUser();
})


$(function () {
    $('#v-pills-tabContent a[href="#nav-profile"]').on("click", function (e) {
        e.preventDefault();

    })
});

onInputChange('#userCreateForm');

const http = {
    fetch: async function (url, options = {}) {
        return await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });
    }
};

const roleService = {
    findAll: async () => {
        return await http.fetch('/api/roles');
    },
};


function defaultModal() {
    modal.modal({
        keyboard: true,
        backdrop: "static",
        show: false,
    }).on("show.bs.modal", function (event) {
        let button = $(event.relatedTarget);
        let id = button.data('id');
        let action = button.data('action');
        switch (action) {

            case 'deleteUser':
                deleteUser($(this), id);
                break;
            case 'editUser':
                editUser($(this), id);
                break;
        }
    }).on('hidden.bs.modal', function (event) {
        $(this).find('.modal-title').html('');
        $(this).find('.modal-body').html('');
        $(this).find('.modal-footer').html('');
    });
}

function modifyModal(modal, submitButtonName) {
    modal.find(modalTitle).html(submitButtonName + ' user');
    let userFormHidden = $('.userForm:hidden')[0];
    modal.find(modalBody).html($(userFormHidden).clone());
    let userForm = modal.find('.userForm');
    modal.find(userForm).show();
    dismissButton.html('Close');
    modal.find(modalFooter).append(dismissButton);
    if (submitButtonName === 'Delete') {
        modal.find(modalFooter).append(dangerButton);
        dangerButton.prop('id', 'deleteUserButton');
        dangerButton.html(submitButtonName);
    } else if (submitButtonName === 'Edit') {
        modal.find(modalFooter).append(primaryButton);
        primaryButton.prop('id', 'updateUserButton');
        primaryButton.html(submitButtonName);
    }
}


function getFormValues(userCreateForm) {
    let firstName = userCreateForm.find('#firstName').val().trim();
    let lastName = userCreateForm.find('#lastName').val().trim();
    let email = userCreateForm.find('#email').val().trim();
    let password = userCreateForm.find('#password').val().trim();
    let rolesId = userCreateForm.find('#multiSelect option:selected').map(function () {
        return this;
    }).get();

    return {firstName, lastName, email, password, listRoles: rolesId};
}

function onInputChange(form) {
    $(form).on('input', function () {
        $(form).find('input,select')
            .each(function () {
                $(form).find('#' + $(this).attr('id')).removeClass('is-invalid');
            });
    });
}