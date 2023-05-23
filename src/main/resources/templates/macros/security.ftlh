<#assign
isSecurityContext = securityContext??
>

<#if isSecurityContext>
    <#assign
    user = securityContext.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    email = user.getEmail() ! ''
    currentUserId = user.getId()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    currentUserId = -1
    >
</#if>