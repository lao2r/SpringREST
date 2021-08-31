let userInfo = $('#tBody')
let allUser = []

getAllUser()

function getAllUser() {
    fetch("/api/admin/users").then((response) => {
        console.log(response.statusText + response.status)
        if (response.ok) {
            response.json().then((users) => {
                users.forEach((user) => {
                    console.log(user)
                    addUserForTable(user)
                    allUser.push(user)
                });
            });
            console.log(allUser)
        } else {
            console.error(response.statusText + response.status)
        }
    });
}

function addUserForTable(user) {
    userInfo.append(
        '<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.firstName + '</td>' +
        '<td>' + user.lastName + '</td>' +
        '<td>' + user.email + '</td>' +
        '<td>' + user.roles.map(roleUser => roleUser.name.substring(5)) + '</td>' +
        '<td>' +
        '<button onclick="editUserById(' + user.id + ')" class="btn btn-info edit-btn" data-toggle="modal" data-target="#modal-edit"' +
        '>Edit</button></td>' +
        '<td>' +
        '<button onclick="deleteUserById(' + user.id + ')" class="btn btn-danger" data-toggle="modal" data-target="#modal-delete"' +
        '>Delete</button></td>' +
        '</tr>'
    )
}