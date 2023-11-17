<#import "template.ftl" as layout>
<@layout.emailLayout>
<h1>User Validation Request</h1>
The user <b>${user.firstName} ${user.lastName}</b> (${user.username} - <a href="mailto:${user.email}">${user.email}</a>) has requested access to ${realmName}.<p>

Has reported the information:
<ul>
<li>Institution:<b> <em>${user.attributes['institution']}</em></b></li>
<li>License: <b><em>${user.attributes['license']}</em></b> </li>
<li>Intended Usage: <br>
<b><code>${user.attributes['usage']?replace("\n","<br>")?no_esc}</code><b></li>
</ul>
</@layout.emailLayout>