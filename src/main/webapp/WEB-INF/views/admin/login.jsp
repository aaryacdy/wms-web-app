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
            <form action="${baseUrl}/admin/login/validate-login" method="post" class="login100-form validate-form">
					<span class="login100-form-title p-b-26">
						Admin Login
					</span>
                <span class="login100-form-title p-b-48">
						<i class="zmdi zmdi-font"></i>
					</span>

                ${message}
                <div class="wrap-input100 validate-input">
                    <input class="input100" type="text" name="username" autocomplete="off">
                    <span class="focus-input100" data-placeholder="Username"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Enter password">
						<span class="btn-show-pass">
							<i class="zmdi zmdi-eye"></i>
						</span>
                    <input class="input100" type="password" name="password" autocomplete="off">
                    <span class="focus-input100" data-placeholder="Password"></span>
                </div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn">
                            Login
                        </button>
                    </div>
                </div>

                <c:if test="${not isSuperUser}">
                    <div class="text-center p-t-115">
						<span class="txt1">
							Don’t have an account?
						</span>

                        <a href="${baseUrl}/admin/signup">
                            Sign Up
                        </a>
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</div>


<div id="dropDownSelect1"></div>
<jsp:include page="../layout/login-sigup/footer.jsp"></jsp:include>
</body>
</html>
