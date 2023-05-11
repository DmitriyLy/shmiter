<#import "/macros/commons.ftlh" as commons>
<@commons.common>
    <div>
        <h1>Messages</h1>
    </div>

    <div>
        <form action="messages" method="post" enctype="multipart/form-data">
            <div class="row g-3 align-items-center">
                <div class="col-auto">
                    <input type="text" name="message" id="message" placeholder="Enter new message here" class="form-control ${(messageFiledError?has_content)?string('is-invalid', '')}">
                    <#if messageFiledError?has_content>
                    <div class="invalid-feedback">
                        ${messageFiledError}
                    </div>
                    </#if>
                </div>
                <div class="col-auto">
                    <input type="text" name="tag" id="tag" placeholder="Tag" class="form-control">
                </div>
                <div class="col-auto">
                    <input type="file" name="file" placeholder="Choose file....">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </div>

    <p></p>
    <p></p>

    <div>
        <form method="get" action="/messages">
            <div class="row g-3 align-items-center">
                <div class="col-auto">
                    <input type="text" name="filterTag" id="filterTag" placeholder="Filtering tag" value="${filterTag}" class="form-control">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Filter</button>
                </div>
            </div>
        </form>
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
                        <small class="text-body-secondary">${message.authorName}</small>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@commons.common>