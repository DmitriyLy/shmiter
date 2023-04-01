<#macro common>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Shmiter</title>
    </head>
    <body>
    <#nested>
    </body>
</html>
</#macro>

<#macro buttons>
    <div>
        <form action="/logout" method="post">
            <input type="submit" value="Sign Out"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form>
    </div>
    <di>
        <a href="/users">Users list</a>
    </di>
</#macro>