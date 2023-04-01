<#import "/macros/commons.ftlh" as commons>

<@commons.common>
    <div>
        <h1>Users list</h1>
    </div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Roles</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td><#list user.roles as role> ${role}<#sep>, </#list></td>
                    <td>
                        <a href="/users/${user.id}">Edit</a>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
</@commons.common>