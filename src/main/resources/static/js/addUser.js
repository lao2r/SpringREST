function addUser() {
    let roleList = () => {
        let array = []
        let options = document.querySelector('#roles0').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                let role = {id: options[i].value, name: options[i].text}
                array.push(role)
            }
        }
        return array;
    }

    let user = {
        firstName: document.getElementById("firstName0").value,
        lastName: document.getElementById("lastName0").value,
        email: document.getElementById("email0").value,
        password: document.getElementById("password0").value,
        roles: roleList()
    }

    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let request = new Request('/api/admin', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });
    console.log(user);

    fetch(request).then((response) => {
        response.json().then((userAdd) => {
            allUser.push(userAdd)
            addUserForTable(userAdd)
            console.log(userAdd)
        })

        console.log(allUser)
        $('#v-pills-tabContent a[href="#nav-users-table"]').trigger('click');
        userClearModal()
    })
}
