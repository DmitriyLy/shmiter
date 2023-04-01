<#import "/macros/commons.ftlh" as commons>
<@commons.common>
<div>
    <h1>Messages</h1>
</div>

<@commons.buttons/>

<div style="margin: 20px">
    <form action="add-message" method="post">
        <input type="text" name="message" id="message" placeholder="Enter new message here">
        <input type="text" name="tag" id="tag" placeholder="Tag">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="submit" value="Create">
    </form>
</div>
<div style="margin: 20px">
    <form method="get" action="/messages">
        <input type="text" name="filterTag" id="filterTag" placeholder="Filtering tag" value="${filterTag}">
        <input type="submit" value="Filter">
    </form>
</div>
<div>
    <table style="border-width: 2px; border-style: solid">
        <thead>
        <tr style="border-width: 2px; border-style: solid">
            <th>Id</th>
            <th>Message</th>
            <th>Tag</th>
            <th>Author</th>
        </tr>
        </thead>
        <tbody>
        <#list messages as message>
            <tr style="border-width: 2px; border-style: solid">
                <td>${message.id}</td>
                <td>${message.message}</td>
                <td>${message.tag}</td>
                <td><strong>${message.authorName}</strong></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</@commons.common>