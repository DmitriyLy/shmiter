<#assign
isSecurityContext = securityContext??
>

<#if isSecurityContext>
    <#assign
    user = securityContext.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    >
</#if>