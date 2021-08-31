function deleteUserById(id) {
    fetch("/api/admin/users/" + id, {method: "GET", dataType: 'json',})
        .then((response) => {
            response.json().then((user) => {
                $('#id2').val(user.id)
                $('#firstName2').val(user.firstName)
                $('#lastName2').val(user.lastName)
                $('#email2').val(user.email)
                $('#password2').val(user.password)
                $('#roles2').val(user.roles)
            })
        })
}

function remove() {
    let userId = ($('#id2').val());
    console.log(userId)
    fetch("/api/admin/users/" + userId, {method: "DELETE"})
        .then((response) => {
            userInfo.empty()
            allUser = allUser.filter(user => user.id !== Number(userId))
            console.log(allUser)

            allUser.forEach((user) => {
                addUserForTable(user)
            })
            $('#modal-delete').modal('hide');
        })
}