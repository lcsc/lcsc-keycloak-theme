<#import "template.ftl" as layout>
<@layout.emailLayout>
<h1>User Validation Request</h1>
${realmName} has received a request for access on your behalf. <br>
If you did not request access, please ignore this comunicaction.<p>

We will contact you for more information, please be patient.<p>

The requestor has reported the following information:
<ul>
<li>UserName: <b>${user.username} - <a href="mailto:${user.email}">${user.email}</a> </b></li>
<li>Name: <b>${user.firstName} ${user.lastName}</b></li>
<li>Institution:<b> <em>${user.attributes['institution']}</em></b></li>
<li>License: <b><em>${user.attributes['license']}</em></b> </li>
<li>Intended Usage: <br>
<b><code>${user.attributes['usage']?replace("\n","<br>")?no_esc}</code><b></li>
</ul>
</@layout.emailLayout>