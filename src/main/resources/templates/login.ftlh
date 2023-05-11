<#import "/macros/commons.ftlh" as commons>

<@commons.common>
    <h1>Sing in</h1>
    <p></p>
    <#if authenticationException??>
        <div class="alert alert-danger" role="alert">
            ${authenticationException}
        </div>
    </#if>
    <form action="/login" method="post">
        <div class="row mb-3">
            <label for="usernameInput" class="col-sm-2 col-form-label">User Name</label>
            <div class="col-sm-6">
                <input type="text" name="username" id="usernameInput" class="form-control"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="passwordInput" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-6">
                <input type="password" name="password" id="passwordInput" class="form-control"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <a href="/registration" class="mx-2">Register here!</a>
        <button type="submit" class="btn btn-primary mx-2">Sign in</button>
    </form>
</@commons.common>