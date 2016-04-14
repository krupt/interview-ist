<#import "partial/input.ftl" as input>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#macro headerPanel>
<nav id="authPanel" class="navbar navbar-default navbar-fixed-top" role="navigation"
     xmlns="http://www.w3.org/1999/html">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-left">
            <li>
                <a class="navbar-brand" href="/">
                    <i class="fa fa-home fa-2x"></i>&nbsp;IST-Project
                </a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <@security.authorize access="isAuthenticated()" var="authenticated">
                <li class="user-nav">
                    <a href="/user/info"><i class="fa fa-user fa-2x text-success"></i><span id="current_user_description"><@security.authentication property="principal.description" /></span></a>
                </li>
            </@security.authorize>
            <#if authenticated>
                <li>
                    <form action="/logout" method="post">
                        <button class="logout-btn text-danger"><i class="fa fa-sign-out fa-2x"></i>ВЫХОД</button>
                        <@security.csrfInput />
                    </form>
                </li>
            </#if>
        </ul>
    </div>
</nav>
</#macro>
<base href="${rq.getContextPath()}/" />
<link rel="stylesheet" href="/resources/css/bootstrap-3.3.5.min.css" />
<link rel="stylesheet" href="/resources/css/font-awesome-4.5.0.min.css" />
<link rel="stylesheet" href="/resources/css/animate-3.0.0.min.css" />
<link rel="stylesheet" href="/resources/css/style.css" />
<script src="/resources/js/jquery-2.1.4.min.js"></script>
<script src="/resources/js/bootstrap-3.3.5.min.js"></script>
<script src="/resources/js/bootstrap-notify-3.1.3.min.js"></script>
<script src="/resources/js/util.js"></script>
