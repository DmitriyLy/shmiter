<#import "/macros/commons.ftlh" as commons>
<#include "/macros/security.ftlh">
<@commons.common>
    <h1>Messages of ${messageAuthorName}</h1>
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Card title</div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card"></div>
            </div>
        </div>
    </div>
    <p></p>
    <p></p>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list messages as message>
            <div class="col">
                <div class="card">
                    <#if message.file?has_content>
                        <img src="/img/${message.file}" alt="No image" class="card-img-top">
                    </#if>
                    <div class="card-body">
                        <h5 class="card-title">${message.tag}</h5>
                        <p class="card-text">${message.message}</p>
                    </div>
                    <div class="card-footer">
                        <a class="text-body-secondary" href="/user-messages/${message.author.id}">${message.authorName}</a>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@commons.common>