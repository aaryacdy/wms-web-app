<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>

    <c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
    <!-- Meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="description" content="Reset Password">
    <meta name="author" content="Reset Password">
    <link rel="shortcut icon" href="${baseUrl}/resources/bootstrap5/favicon.ico">

    <!-- FontAwesome JS-->
    <script defer src="${baseUrl}/resources/bootstrap5/plugins/fontawesome/js/all.min.js"></script>

    <!-- App CSS -->
    <link id="theme-style" rel="stylesheet" href="${baseUrl}/resources/bootstrap5/css/portal.css">

</head>

<body class="app app-reset-password p-0">
<div class="row g-0 app-auth-wrapper">
    <div class="col-12 col-md-7 col-lg-6 auth-main-col text-center p-5">
        <div class="d-flex flex-column align-content-end">
            <div class="app-auth-body mx-auto">
                <div class="app-auth-branding mb-4"><a class="app-logo" href="${baseUrl}/dashboard"><img
                        class="logo-icon me-2" src="${baseUrl}/resources/bootstrap5/images/app-logo.svg" alt="logo"></a>
                </div>
                <h2 class="auth-heading text-center mb-4">Password Reset</h2>
                <div class="auth-form-container text-left">
                    ${message}
                    <form action="${baseUrl}/admin/reset-password" method="post" class="auth-form resetpass-form">
                        <div class="email mb-3">
                            <label class="sr-only" for="current-password">Current Password</label>
                            <input id="current-password" name="oldPassword" type="password" class="form-control"
                                   placeholder="Old Password" required="required">
                        </div><!--//form-group-->

                        <div class="email mb-3">
                            <label class="sr-only" for="new-password">New Password</label>
                            <input id="new-password" name="newPassword" type="password" class="form-control"
                                   placeholder="New Password" required="required">
                        </div><!--//form-group-->

                        <div class="email mb-3">
                            <label class="sr-only" for="confirm-password">Confirm Password</label>
                            <input id="confirm-password" name="confirmPassword" type="password" class="form-control"
                                   placeholder="Confirm Password" required="required">
                        </div><!--//form-group-->

                        <div class="text-center">
                            <button type="submit" class="btn app-btn-primary btn-block theme-btn mx-auto">Reset
                                Password
                            </button>
                        </div>
                    </form>

                </div><!--//auth-form-container-->


            </div><!--//auth-body-->
            <footer class="app-auth-footer">
                <div class="container text-center py-3">
                    <small class="copyright"><i class="fas fa-heart" style="color: #fb866a;"></i><a class="app-link"
                                                                                                    href="https://kathford.edu.np/"
                                                                                                    target="_blank">Kathford
                        International College</a></small>

                </div>
            </footer><!--//app-auth-footer-->
        </div><!--//flex-column-->
    </div><!--//auth-main-col-->
    <div class="col-12 col-md-5 col-lg-6 h-100 auth-background-col">
        <div class="auth-background-holder">
        </div>
        <div class="auth-background-mask"></div>
        <div class="auth-background-overlay p-3 p-lg-5">
            <div class="d-flex flex-column align-content-end h-100">
                <div class="h-100"></div>
                <div class="overlay-content p-3 p-lg-4 rounded">
                    <h5 class="mb-3 overlay-title">Wastage Management System</h5>
                </div>
            </div>
        </div><!--//auth-background-overlay-->
    </div><!--//auth-background-col-->
</div><!--//row-->
</body>
</html>


