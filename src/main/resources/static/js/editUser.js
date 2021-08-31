function editUserById(id) {
    fetch("/api/admin/users/" + id, {method: "GET", dataType: 'json',})
        .then((response) => {
            response.json().then((user) => {
                $('#id1').val(user.id);
                $('#firstName1').val(user.firstName);
                $('#lastName1').val(user.lastName);
                $('#email1').val(user.email);
                $('#password1').val(user.password);
                $('#role1').val(user.roles);

                console.log(user)
            })
        })
}

function update() {
    let roleList = () => {
        let array = []
        let options = document.querySelector('#roles1').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                let role = {id: options[i].value, name: options[i].text}
                array.push(role)
            }
        }
        return array;
    }

    let editUser = {
        id: document.getElementById("id1").value,
        firstName: document.getElementById("firstName1").value,
        lastName: document.getElementById("lastName1").value,
        email: document.getElementById("email1").value,
        password: document.getElementById("password1").value,
        roles: roleList()
    }
    console.log(editUser);

    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let request = new Request("api/admin/users", {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(editUser),
    });

    let userEditId = ($('#id1').val())
    console.log(userEditId)
    fetch(request).then(response => {
        response.json().then((userEdit) => {
            console.log(userEdit);
            userInfo.empty();
            allUser = allUser.map(user => user.id !== userEdit.id ? user : userEdit)
            console.log(allUser)
            allUser.forEach((user) => {
                addUserForTable(user)
            })
        })
        $('#modal-edit').modal('hide');
    });
}