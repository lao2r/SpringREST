async function editUser(modal, id) {
    const userResponse = await http.fetch('/api/admin/users/' + id);
    const userJson = userResponse.json();
    const rolesResponse = await roleService.findAll();
    const rolesJson = rolesResponse.json();


    let passwordField = `<label for="password">Password</label>
                <input required class="form-control" id="password" placeholder="Password" type="password"
                       />`

    modifyModal(modal, 'Edit');
    onInputChange(modal);

    modal.find('#email').after(passwordField);

    userJson.then(user => {
        modal.find('#id').val(user.id);
        modal.find('#firstName').val(user.firstName).prop('disabled', false);
        modal.find('#lastName').val(user.lastName).prop('disabled', false);
        modal.find('#email').val(user.email).prop('disabled', false);
        modal.find('#password').val(user.password).prop('disabled', false);

        rolesJson.then(roles => {
            roles.forEach(role => {
                modal.find('#multiSelect').append(new Option(role.authority.substring(5), role.id)).prop('disabled', false);
            });
        });
    });


    $('#updateUserButton').click(async function (e) {
        let id = modal.find('#id').val().trim();
        let {firstName, lastName, email, password, listRoles} = getFormValues(modal);

        const roles = listRoles.map((role) => ({id: role.value, role: role.text, authority: role.text}));

        let data = {
            id: id,
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password,
            roles: roles,

        };

        const updateUserResponse = await http.fetch('/api/admin/users/' + id, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
        if (updateUserResponse.status === 200) {
            viewAllUsers();
            $('#defaultModal').modal('hide');
            $("#top-nav").load(" #top-nav > *");
            $("#user_info_table").load(" #user_info_table > *");
        }

    });
}