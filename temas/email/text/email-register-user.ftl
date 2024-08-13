<#ftl output_format="plainText">
${realmName} has received a request for access on your behalf. 
If you did not request access, please ignore this comunicaction.

We will contact you for more information, please be patient.

The requestor has reported the following information:

* UserName: ${user.username} ${user.email}
* Name: ${user.firstName} ${user.lastName}
* Institution: ${user.attributes['institution']}
* License: ${user.attributes['license']}
* Intended Usage:
${user.attributes['usage']}