<#include "/macros/security.ftlh">
<#import "/macros/commons.ftlh" as commons>
<@commons.common>

    <h1>User: ${name}</h1>
    <p></p>
    <#if errorMessage??>
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </#if>
    <#if successMessage??>
        <div class="alert alert-success" role="alert">
            ${successMessage}
        </div>
    </#if>
    <form action="/users/profile" method="post">
        <div class="row mb-3">
            <label for="emailInput" class="col-sm-2 col-form-label">New email</label>
            <div class="col-sm-7">
                <input type="text" name="email" placeholder="Email" id="emailInput" value="${email!''}"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="passwordInput" class="col-sm-2 col-form-label">New password</label>
            <div class="col-sm-7">
                <input type="password" name="password" placeholder="Password" id="passwordInput"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>

</@commons.common>