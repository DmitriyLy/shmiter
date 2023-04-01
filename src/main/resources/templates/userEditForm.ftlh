<#import "/macros/commons.ftlh" as commons>

<@commons.common>

<form action="/users" method="post">
    <div><label>ID: <input type="text" readonly name="id" value="${user.id}"></label></div>
    <div><label>Username: <input type="text" name="username" value="${user.username}"></label></div>
    <#list roles as role>
        <div><label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label></div>
    </#list>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div><input type="submit" value="Change"></div>
</form>

</@commons.common>