<#import "template.ftl" as layout>
<@layout.mainLayout active='account' bodyClass='user'; section>

    <div class="row">
        <div class="col-md-10">
            <h2>${msg("editAccountHtmlTitle")}</h2>
        </div>
        <div class="col-md-2 subtitle">
            <span class="subtitle"><span class="required">*</span> ${msg("requiredFields")}</span>
        </div>
    </div>

    <form action="${url.accountUrl}" class="form-horizontal" method="post">

        <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">

        <#if !realm.registrationEmailAsUsername>
            <div class="form-group ${messagesPerField.printIfExists('username','has-error')}">
                <div class="col-sm-2 col-md-2">
                    <label for="username" class="control-label">${msg("username")}</label> <#if realm.editUsernameAllowed><span class="required">*</span></#if>
                </div>

                <div class="col-sm-10 col-md-10">
                    <input type="text" class="form-control" id="username" name="username" <#if !realm.editUsernameAllowed>disabled="disabled"</#if> value="${(account.username!'')}"/>
                </div>
            </div>
        </#if>

        <div class="form-group ${messagesPerField.printIfExists('email','has-error')}">
            <div class="col-sm-2 col-md-2">
            <label for="email" class="control-label">${msg("email")}</label> <span class="required">*</span>
            </div>

            <div class="col-sm-10 col-md-10">
                <input type="text" class="form-control" id="email" name="email" autofocus value="${(account.email!'')}"/>
            </div>
        </div>

        <div class="form-group ${messagesPerField.printIfExists('firstName','has-error')}">
            <div class="col-sm-2 col-md-2">
                <label for="firstName" class="control-label">${msg("firstName")}</label> <span class="required">*</span>
            </div>

            <div class="col-sm-10 col-md-10">
                <input type="text" class="form-control" id="firstName" name="firstName" value="${(account.firstName!'')}"/>
            </div>
        </div>

        <div class="form-group ${messagesPerField.printIfExists('lastName','has-error')}">
            <div class="col-sm-2 col-md-2">
                <label for="lastName" class="control-label">${msg("lastName")}</label> <span class="required">*</span>
            </div>

            <div class="col-sm-10 col-md-10">
                <input type="text" class="form-control" id="lastName" name="lastName" value="${(account.lastName!'')}"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-2 col-md-2">
                <label for="user.attributes.institution" class="control-label">Institution</label>
            </div>
            <div class="col-sm-10 col-md-10">
                <input type="text" class="form-control" id="user.attributes.institution" name="user.attributes.institution" value="${(account.attributes.institution!'')}"/>
            </div>
        </div>


        <div class="form-group">
            <div class="col-sm-2 col-md-2">
                <label for="user.attributes.usage" class="control-label">Intended Usage</label>
            </div>

                <div class="col-sm-10 col-md-10">
                <textarea type="text" class="form-control" id="user.attributes.usage" name="user.attributes.usage">${(account.attributes.usage!'')}</textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-2 col-md-2">
                <label for="user.attributes.license" class="control-label">License</label>
            </div>

            <div class="col-sm-10 col-md-10">
                <select class="form-control" id="user.attributes.license" name="user.attributes.license"
                    value="${(account.attributes.license!'')}">

                    <option value="no"
                    ${((account.attributes.license!'')=="no")?string('selected','')}>${kcSanitize(msg('license.no'))?no_esc}</option>
                    <option value="NonCommercial"
                    ${((account.attributes.license!'')=="NonCommercial")?string('selected','')}>${kcSanitize(msg('license.NonCommercial'))?no_esc}</option>
                    <option value="Commercial"
                    ${((account.attributes.license!'')=="Commercial")?string('selected','')}>${kcSanitize(msg('license.Commercial'))?no_esc}</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div id="kc-form-buttons" class="col-md-offset-2 col-md-10 submit">
                <div class="">
                    <#if url.referrerURI??><a href="${url.referrerURI}">${kcSanitize(msg("backToApplication")?no_esc)}</a></#if>
                    <button type="submit" class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="submitAction" value="Save">${msg("doSave")}</button>
                    <button type="submit" class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}" name="submitAction" value="Cancel">${msg("doCancel")}</button>
                </div>
            </div>
        </div>
    </form>

</@layout.mainLayout>
