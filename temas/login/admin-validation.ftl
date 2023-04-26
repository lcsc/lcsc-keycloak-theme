
<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true; section>
    <#if section = "header">
        ${msg("adminVerifyEmailTittle")}
    <#elseif section = "form">
        <p class="instruction">${msg("adminVerifyEmailInstruction1",realm.name)}</p> 
    <#elseif section = "info">
        <p class="instruction">
            ${msg("adminVerifyEmailInstruction2")}
            <br/>
            <a href="${url.loginAction}">${msg("doClickHere")}</a> ${msg("adminVerifyEmailInstruction3")}
        </p>
    </#if>
</@layout.registrationLayout>