async function addUser() {

    let userCreateForm = $("#userCreateForm");
    userCreateForm.find('#multiSelect')
        .find('option')
        .remove()
        .end()

    const rolesResponse = await roleService.findAll();
    const rolesJson = rolesResponse.json();

    rolesJson.then(roles => {
        roles.forEach(role => {
            userCreateForm.find('#multiSelect').append(new Option(role.authority.substring(5), role.id));
        });
    });

    $('#create_user').click(async function (e) {
        e.preventDefault()

        let {firstName, lastName, email, password, listRoles} = getFormValues(userCreateForm);

        const roles = listRoles.map((role) => ({id: role.value}));
        let data = {
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password,
            roles: roles,
        };

        const userResponse = await http.fetch('/api/admin', {
            method: 'POST',
            body: JSON.stringify(data)
        });

        if (userResponse.status === 201) {
            viewAllUsers();
            $('#v-pills-tabContent a[href="#nav-home"]').trigger('click');

            $('#userCreateForm').find('input:text, input:password, select')
                .each(function () {
                    $(this).val('');
                });
        }
    });
}