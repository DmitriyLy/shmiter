<#import "/macros/commons.ftlh" as commons>
<@commons.common>

    <h1>Registration</h1>
    <p></p>
    <#if message??>
        <div class="alert alert-danger" role="alert">
            ${message}
        </div>
    </#if>
    <form action="/registration" method="post">
        <div class="row mb-3">
            <label for="usernameInput" class="col-sm-2 col-form-label">New Username</label>
            <div class="col-sm-7">
                <input type="text" name="username" placeholder="Username" id="usernameInput"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="passwordInput" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-7">
                <input type="password" name="password" placeholder="Password" id="passwordInput"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="confirmPasswordInput" class="col-sm-2 col-form-label">Confirm Password</label>
            <div class="col-sm-7">
                <input type="password" name="confirmPassword" placeholder="Confirm password" id="confirmPasswordInput"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Register</button>
    </form>

</@commons.common>