<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section>
    <#if section = "header">
        ${msg("termsTitle")}
    <#elseif section = "form">
    <div id="kc-terms-text">
        <h2>Services</h2>
            <p>LCSC provides the SPEI datasets available for General Users (unregistered) under an Open Database license (see below).This is limited to historical data<sup>*</sup>.
            Registered Users have access to real time data<sup>*</sup>.</p>
        <div class="alert-warning ${properties.kcAlertClass!} pf-m-warning">
            <div class="pf-c-alert__icon">
                <span class="${properties.kcFeedbackWarningIcon!}"></span>
            </div>
            <div style="font-size: 10px;overflow-wrap: anywhere;display: flex;">
            <strong>*</strong>:  Real time and historical data definition depends on the specific dataset, but by default it means 3 weeks from the current.
            </div>
        </div>

        <h2>Licensing</h2></div>
        <div class="contentText">
            <p>The LCSC's datasets are made available under the
                <a href="http://opendatacommons.org/licenses/odbl/1.0/">
                    Open Database License</a> (ODbL 1.0). Any rights in individual contents of the database are
                licensed under the <a href="http://opendatacommons.org/licenses/dbcl/1.0/">
                    Database Contents License</a>.Registered Users can request a custom license. 
            </p>
            <p>What follows is a  human-readable summary of the ODbL 1.0 license.
                Please, read the full ODbL 1.0 license text for the exact terms that apply.
            </p>
                Users of the dataset are free to:
            <ul>
                <li><strong>Share</strong>: copy, distribute and use the database, either commercially or non-commercially.</li>
                <li><strong>Create</strong>: produce derivative works from the database.</li>
                <li><strong>Adapt</strong>: modify, transform and build upon the database.</li>
            </ul>
            Under the following conditions:
            <ul>
                <li><strong>Attribution</strong>: You must attribute any public use of the database, or works produced from the database, by citing one or more of the papers referenced in the References section below. For any use or redistribution of the database, or works produced from it, you must make clear to others the license of the original database.</li>
                <li><strong>Share-Alike</strong>: If you publicly use any adapted version of this database, or works produced from an adapted database, you must also offer that adapted database under the ODbL.</li>
        </div>
    <form class="form-actions" action="${url.loginAction}" method="POST">
        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="accept" id="kc-accept" type="submit" value="${msg("doAccept")}"/>
        <input class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}" name="cancel" id="kc-decline" type="submit" value="${msg("doDecline")}"/>
    </form>
    <div class="clearfix"></div>
    </#if>
</@layout.registrationLayout>
