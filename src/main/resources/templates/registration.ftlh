<#import "/macros/commons.ftlh" as commons>
<@commons.common>

    <h1>Registration</h1>
    <p></p>
    <#if errorMessage??>
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </#if>
    <#if activationUrl??>
        <div class="alert alert-success" role="alert">
            Your activation URL is ${activationUrl}
        </div>
    </#if>
    <form action="/registration" method="post">
        <div class="row mb-3">
            <label for="usernameInput" class="col-sm-2 col-form-label">New Username</label>
            <div class="col-sm-7">
                <input type="text" name="username" placeholder="Username" id="usernameInput"
                       class="form-control ${(usernameValidationError?has_content)?string('is-invalid', '')}"/>
                <#if usernameValidationError?has_content>
                    <div class="invalid-feedback">
                        ${usernameValidationError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="row mb-3">
            <label for="passwordInput" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-7">
                <input type="password" name="password" placeholder="Password" id="passwordInput"
                       class="form-control ${(passwordValidationError?has_content)?string('is-invalid', '')}"/>
                <#if passwordValidationError?has_content>
                    <div class="invalid-feedback">
                        ${passwordValidationError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="row mb-3">
            <label for="passwordConfirmationInput" class="col-sm-2 col-form-label">Password Confirmation</label>
            <div class="col-sm-7">
                <input type="password" name="passwordConfirmation" placeholder="Password Confirmation" id="passwordConfirmationInput"
                       class="form-control ${(passwordConfirmationValidationError?has_content)?string('is-invalid', '')}"/>
                <#if passwordConfirmationValidationError?has_content>
                    <div class="invalid-feedback">
                        ${passwordConfirmationValidationError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="row mb-3">
            <label for="emailInput" class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-7">
                <input type="email" name="email" placeholder="Email" id="emailInput"
                       class="form-control ${(emailValidationError?has_content)?string('is-invalid', '')}"/>
                <#if emailValidationError?has_content>
                    <div class="invalid-feedback">
                        ${emailValidationError}
                    </div>
                </#if>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Register</button>
    </form>

</@commons.common>