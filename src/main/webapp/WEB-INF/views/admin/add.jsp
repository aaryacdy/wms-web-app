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
            <form action="${baseUrl}/admin/signup/save" method="post" class="login100-form validate-form">
					<span class="login100-form-title p-b-26">
						Admin Signup
					</span>
                <span class="login100-form-title p-b-48">
						<i class="zmdi zmdi-font"></i>
					</span>

                <div class="wrap-input100 validate-input">
                    <input class="input100" type="text" name="name" minlength="3" maxlength="50" autocomplete="off" required>
                    <span class="focus-input100" data-placeholder="Name"></span>
                </div>

                <div class="wrap-input100 validate-input">
                    <input class="input100" type="text" name="username" minlength="3" maxlength="20" autocomplete="off"
                           required>
                    <span class="focus-input100" data-placeholder="Enter Username"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Valid email is: a@b.c">
                    <input class="input100" type="email" name="email" autocomplete="off" required>
                    <span class="focus-input100" data-placeholder="Email"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Enter password">
						<span class="btn-show-pass">
							<i class="zmdi zmdi-eye"></i>
						</span>
                    <input class="input100" type="password" name="password" autocomplete="off" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one  number and one uppercase and lowercase letter, and at least 8 or more characters" required>
                    <span class="focus-input100" data-placeholder="Password"></span>
                </div>

                <div class="row">
                    <%--<div class="col-md-6">
                        <div class="wrap-input100 validate-input">
                            <input class="input100" type="number" name="phoneNo" minlength="6" maxlength="10">
                            <span class="focus-input100" data-placeholder="Phone No."></span>
                        </div>
                    </div>--%>

                    <div class="col-md-12">
                        <div class="wrap-input100 validate-input">
                            <input class="input100" type="number" name="mobileNo" minlength="10" maxlength="10"
                                   autocomplete="off" required>
                            <span class="focus-input100" data-placeholder="Mobile No."></span>
                        </div>
                    </div>

                </div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn">
                            Signup
                        </button>
                    </div>
                </div>
                <div class="text-center p-t-115">
						<span class="txt1">
							Have an account?
						</span>
                    <a href="${baseUrl}/admin/login">
                        Login
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>
<div id="dropDownSelect1"></div>
<jsp:include page="../layout/login-sigup/footer.jsp"></jsp:include>
</body>
</html>