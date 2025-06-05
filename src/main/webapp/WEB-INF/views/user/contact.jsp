<%--
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title> ${title} </title>
    <link type="text/css" rel="stylesheet" href="${baseUrl}/resources/other/user/css/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${baseUrl}/resources/other/user/css/material.css">
    <link rel="icon" href="${baseUrl}/resources/other/user/images/icon1.png">
    <link type="text/css" rel="stylesheet" href="${baseUrl}/resources/other/user/fonts/font.css">
</head>
<body id="_4">
<!-- navigation bar -->
<ul id="nav-bar">
    <a href="${baseUrl}/waste">
        <li>Request Service</li>
    </a>
    <c:choose>
        <c:when test="${empty sessionScope.username}">
            <a href="${baseUrl}/login">
                <li>Log In</li>
            </a>
        </c:when>
        <c:otherwise>
            <a href="${baseUrl}/logout">
                <li>Log Out</li>
            </a>
        </c:otherwise>
    </c:choose>
</ul>
<!-- content -->
<div id="content" class="clearfix">

    <div id="box-1">
        <div class="heading">
            <center>
                &lt;%&ndash;                <h1 class="logo"><div id="i">h</div><div id="cir">H/div><div id="ntro">ow To</div></h1>&ndash;%&gt;
                &lt;%&ndash;                <p id="tag-line">Where questions can be asked in the form of How To.</p>&ndash;%&gt;
            </center>
        </div>
    </div>
    <div id="box-2">
        <div id="text">
            <h1>Kathford International College.</h1>
            <p style="line-height: 20px;">
                ${aboutUs.message}
            </p>
        </div>
    </div>
</div>

<!-- Footer -->
<div id="footer">
    &copy; 2022 &bull; Kathford International College
</div>
</body>
</html>--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<head>
    <title>${title}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="../layout/login-sigup/header.jsp"></jsp:include>
</head>
<body>

<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <div id="text">
                <h1>Kathford International College.</h1>
                <p style="line-height: 20px;">
                    ${aboutUs.message}
                </p>
            </div>
            <div class="text-center p-t-115">
                <a href="${baseUrl}/dashboard">
                    Back
                </a>
            </div>
        </div>
    </div>
</div>
<div id="dropDownSelect1"></div>
<jsp:include page="../layout/login-sigup/footer.jsp"></jsp:include>
</body>
</html>


