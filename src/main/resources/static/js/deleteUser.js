async function deleteUser(modal, id) {
    const userResponse = await http.fetch('/api/admin/users/' + id);
    const userJson = userResponse.json();

    modifyModal(modal, 'Delete');
    userJson.then(user => {
        modal.find('#id').val(user.id);
        modal.find('#firstName').val(user.firstName);
        modal.find('#lastName').val(user.lastName);
        modal.find('#email').val(user.email);
        modal.find('#multiSelect').append(user.roles.map((item) => new Option(item.role, item.id)));

    });

    $('#deleteUserButton').click(async function (e) {
        const bookResponse = await http.fetch('/api/admin/users/' + id, {
            method: 'DELETE'
        });
        if (bookResponse.status === 204) {
            viewAllUsers();
            $('#defaultModal').modal('hide');
        }
    });
}