<#import "/macros/commons.ftlh" as commons>
<@commons.common>

<h1>Registration</h1>
<p></p>
<#if message??>
<div style="margin: 20px; font-size: 30px; color: crimson">
    ${message}
</div>
</#if>
<form action="/registration" method="post">
    <div><label> New Username : <input type="text" name="username" placeholder="Username"/> </label></div>
    <div><label> Password: <input type="password" name="password" placeholder="Password"/> </label></div>
    <div><label> Confirm Password: <input type="password" name="confirmPassword" placeholder="Confirm password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div><input type="submit" value="Register"/></div>
</form>

</@commons.common>