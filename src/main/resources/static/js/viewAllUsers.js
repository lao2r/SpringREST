async function viewAllUsers() {
    $('#usersTable tbody').empty();
    const usersResponse = await http.fetch('/api/admin/users');
    const jsonUser = usersResponse.json();
    jsonUser.then(users => {
        users.forEach(user => {
            let userInfo = `$(<tr>
                        <th >${user.id}</th>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>                             
                        <td>${user.roles.map((item) => item.name.substring(5)).join(" ")}</td>                      
                            
                        <td><button class="btn btn-info " data-id="${user.id}" data-action="editUser" data-toggle="modal" data-target="#defaultModal">Edit</button></td>       
                        <td> <button class="btn btn-danger" data-id="${user.id}" data-action="deleteUser" data-toggle="modal" data-target="#defaultModal">Delete</button></td>      
                        
                    </tr>)`;
            $('#usersTable tbody').append(userInfo);
        });
    });
}