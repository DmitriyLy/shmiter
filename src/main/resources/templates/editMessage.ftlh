<#import "/macros/commons.ftlh" as commons>
<#include "/macros/security.ftlh">
<@commons.common>
    <div>
        <h1>Message Edit form</h1>
    </div>

    <p></p>
    <p></p>

    <div>
        <form action="/save-message-changes" method="post" enctype="multipart/form-data">
            <div class="row g-3 align-items-center">
                <div class="col-auto">
                    <input type="text" name="message" id="message" placeholder="Enter new message here"
                           class="form-control" value="${message.message}">
                </div>
                <div class="col-auto">
                    <input type="text" name="tag" id="tag" placeholder="Tag" class="form-control" value="${message.tag}">
                </div>
                <div class="col-auto">
                    <input type="file" name="file" placeholder="Choose file....">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${message.id}"/>
        </form>
    </div>

    <p></p>
    <p></p>

    <div class="row row-cols-1 row-cols-md-3 g-4">
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
    </div>
</@commons.common>