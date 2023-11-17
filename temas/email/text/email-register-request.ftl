<#ftl output_format="plainText">
The User ${user.firstName} ${user.lastName} (${user.username} - ${user.email}), has requested access to ${realmName}.

Has reported the information:
* Institution: ${user.attributes['institution']}
* License: ${user.attributes['license']}
* Intended Usage:
${user.attributes['usage']}