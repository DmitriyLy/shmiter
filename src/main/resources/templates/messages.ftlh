<#import "/macros/commons.ftlh" as commons>
<@commons.common>
    <div>
        <h1>Messages</h1>
    </div>

    <div>
        <form action="add-message" method="post" enctype="multipart/form-data">
            <input type="text" name="message" id="message" placeholder="Enter new message here">
            <input type="text" name="tag" id="tag" placeholder="Tag">
            <input type="file" name="file" placeholder="Choose file....">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="submit" value="Create">
        </form>
    </div>
    <div>
        <form method="get" action="/messages">
            <input type="text" name="filterTag" id="filterTag" placeholder="Filtering tag" value="${filterTag}">
            <input type="submit" value="Filter">
        </form>
    </div>

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