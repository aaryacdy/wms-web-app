<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
    <!-- Meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="description" content="Waste Management System">
    <meta name="author" content="Aarya Chaudhary Vs Balika Thapa">
    <link rel="shortcut icon" href="${baseUrl}/resources/bootstrap5/favicon.ico">

    <!-- FontAwesome JS-->
    <script defer src="${baseUrl}/resources/bootstrap5/plugins/fontawesome/js/all.min.js"></script>

    <!-- App CSS -->
    <link id="theme-style" rel="stylesheet" href="${baseUrl}/resources/bootstrap5/css/portal.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0"/>

</head>

<body class="app">
<header class="app-header fixed-top">
    <div class="app-header-inner">
        <div class="container-fluid py-2">
            <div class="app-header-content">
                <div class="row justify-content-between align-items-center">

                    <div class="col-auto">
                        <a id="sidepanel-toggler" class="sidepanel-toggler d-inline-block d-xl-none" href="#">
                            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30"
                                 role="img"><title>Menu</title>
                                <path stroke="currentColor" stroke-linecap="round" stroke-miterlimit="10"
                                      stroke-width="2" d="M4 7h22M4 15h22M4 23h22"></path>
                            </svg>
                        </a>
                    </div><!--//col-->
                    <div class="search-mobile-trigger d-sm-none col">
                        <i class="search-mobile-trigger-icon fas fa-search"></i>
                    </div><!--//col-->
                    <div class="app-utilities col-auto">
                        <div class="app-utility-item app-user-dropdown dropdown">
                            <a class="dropdown-toggle" id="user-dropdown-toggle" data-bs-toggle="dropdown" href="#"
                               role="button" aria-expanded="false"><img
                                    src="${baseUrl}/resources/bootstrap5/images/app-logo.svg" alt="user profile"></a>
                            <ul class="dropdown-menu" aria-labelledby="user-dropdown-toggle">
                                <li><a class="dropdown-item" href="${baseUrl}/user/reset-password" target="_blank">Reset
                                    Password</a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="${baseUrl}/logout">Log Out</a></li>
                            </ul>
                        </div><!--//app-user-dropdown-->
                    </div><!--//app-utilities-->
                </div><!--//row-->
            </div><!--//app-header-content-->
        </div><!--//container-fluid-->
    </div><!--//app-header-inner-->
    <div id="app-sidepanel" class="app-sidepanel">
        <div id="sidepanel-drop" class="sidepanel-drop"></div>
        <div class="sidepanel-inner d-flex flex-column">
            <a href="#" id="sidepanel-close" class="sidepanel-close d-xl-none">&times;</a>
            <div class="app-branding">
                <a class="app-logo" href="${baseUrl}/dashboard"><img class="logo-icon me-2"
                                                                     src="${baseUrl}/resources/bootstrap5/images/app-logo.svg"
                                                                     alt="logo"><span class="logo-text">WMS</span></a>

            </div><!--//app-branding-->
            <nav id="app-nav-main" class="app-nav app-nav-main flex-grow-1">
                <ul class="app-menu list-unstyled accordion" id="menu-accordion">
                    <li class="nav-item">
                        <a class="nav-link active" href="${baseUrl}/dashboard">
						        <span class="nav-icon">
						        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-house-door"
                                     fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                      <path fill-rule="evenodd"
                                            d="M7.646 1.146a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 .146.354v7a.5.5 0 0 1-.5.5H9.5a.5.5 0 0 1-.5-.5v-4H7v4a.5.5 0 0 1-.5.5H2a.5.5 0 0 1-.5-.5v-7a.5.5 0 0 1 .146-.354l6-6zM2.5 7.707V14H6v-4a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 .5.5v4h3.5V7.707L8 2.207l-5.5 5.5z"/>
                                      <path fill-rule="evenodd"
                                            d="M13 2.5V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
                                    </svg>
						         </span>
                            <span class="nav-link-text">Overview</span>
                        </a><!--//nav-link-->
                    </li><!--//nav-item-->

                    <li class="nav-item has-submenu">
                        <a class="nav-link submenu-toggle" href="#" data-bs-toggle="collapse"
                           data-bs-target="#category-3" aria-expanded="false" aria-controls="submenu-3">
						        <span class="nav-icon">
						       <span class="material-symbols-outlined">fact_check</span>
						         </span>
                            <span class="nav-link-text">All Category</span>
                            <span class="submenu-arrow">
		                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-down"
                                          fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                      <path fill-rule="evenodd"
                                            d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                                    </svg>
	                             </span><!--//submenu-arrow-->
                        </a><!--//nav-link-->

                        <div id="category-3" class="collapse submenu submenu-3b" data-bs-parent="#menu-accordion">
                            <ul class="submenu-list list-unstyled">
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/category/user">Your
                                    Category Price</a>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <%--Waste related menu--%>
                    <li class="nav-item has-submenu">
                        <a class="nav-link submenu-toggle" href="#" data-bs-toggle="collapse"
                           data-bs-target="#postsubmenu" aria-expanded="false">
						        <span class="nav-icon">
						        <span class="material-symbols-outlined">water_pump</span>
						         </span>
                            <span class="nav-link-text">Request Service</span>
                            <span class="submenu-arrow">
		                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-down"
                                          fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                      <path fill-rule="evenodd"
                                            d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                                    </svg>
	                             </span><!--//submenu-arrow-->
                        </a><!--//nav-link-->
                        <div id="postsubmenu" class="collapse submenu submenu" data-bs-parent="#menu-accordion">
                            <ul class="submenu-list list-unstyled">
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/waste/add">Add</a>
                                </li>
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/waste">View</a></li>
                            </ul>
                        </div>
                    </li><!--//nav-item-->

                    <%--Payment related menu--%>
                    <li class="nav-item has-submenu">
                        <a class="nav-link submenu-toggle" href="#" data-bs-toggle="collapse"
                           data-bs-target="#paymentSubMenu" aria-expanded="false">
						        <span class="nav-icon">
						        <span class="material-symbols-outlined">payments</span>
						         </span>
                            <span class="nav-link-text">Payment</span>
                            <span class="submenu-arrow">
		                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-down"
                                          fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                      <path fill-rule="evenodd"
                                            d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                                    </svg>
	                             </span><!--//submenu-arrow-->
                        </a><!--//nav-link-->
                        <div id="paymentSubMenu" class="collapse submenu submenu" data-bs-parent="#menu-accordion">
                            <ul class="submenu-list list-unstyled">
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/payment/to-user">View</a>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <%--compalint related menu--%>
                    <li class="nav-item has-submenu">
                        <a class="nav-link submenu-toggle" href="#" data-bs-toggle="collapse"
                           data-bs-target="#submenu-2" aria-expanded="false" aria-controls="submenu-2">
						        <span class="nav-icon">
						        <span class="material-symbols-outlined">library_add_check</span>
						         </span>
                            <span class="nav-link-text">Complaint</span>
                            <span class="submenu-arrow">
		                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-down"
                                          fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                      <path fill-rule="evenodd"
                                            d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                                    </svg>
	                             </span><!--//submenu-arrow-->
                        </a><!--//nav-link-->

                        <div id="submenu-2" class="collapse submenu submenu-2" data-bs-parent="#menu-accordion">
                            <ul class="submenu-list list-unstyled">
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/complaint/add">Complaint</a>
                                </li>
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/complaint/user">Your
                                    Complaint</a>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <%--feedback related menu--%>
                    <li class="nav-item has-submenu">
                        <a class="nav-link submenu-toggle" href="#" data-bs-toggle="collapse"
                           data-bs-target="#submenu-3" aria-expanded="false" aria-controls="submenu-3">
						        <span class="nav-icon">
						       <span class="material-symbols-outlined">fact_check</span>
						         </span>
                            <span class="nav-link-text">Feedback</span>
                            <span class="submenu-arrow">
		                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-down"
                                          fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                      <path fill-rule="evenodd"
                                            d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                                    </svg>
	                             </span><!--//submenu-arrow-->
                        </a><!--//nav-link-->

                        <div id="submenu-3" class="collapse submenu submenu-3b" data-bs-parent="#menu-accordion">
                            <ul class="submenu-list list-unstyled">
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/feedback/add">Feedback</a>
                                </li>
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/feedback/user">Your
                                    Feedback</a>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <%--contact us--%>
                    <li class="nav-item has-submenu">
                        <a class="nav-link submenu-toggle" href="#" data-bs-toggle="collapse"
                           data-bs-target="#submenu-contact" aria-expanded="false" aria-controls="submenu-contact">
						        <span class="nav-icon">
						        <span class="material-symbols-outlined">contacts</span>
						         </span>
                            <span class="nav-link-text">About Us</span>
                            <span class="submenu-arrow">
		                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-down"
                                          fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                      <path fill-rule="evenodd"
                                            d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                                    </svg>
	                             </span><!--//submenu-arrow-->
                        </a><!--//nav-link-->

                        <div id="submenu-contact" class="collapse submenu submenu-contact"
                             data-bs-parent="#menu-accordion">
                            <ul class="submenu-list list-unstyled">
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/contact-us">Contact
                                    Us</a></li>
                            </ul>
                        </div>
                    </li><!--//nav-item-->

                    <%--reset password--%>
                    <li class="nav-item has-submenu">
                        <a class="nav-link submenu-toggle" href="#" data-bs-toggle="collapse"
                           data-bs-target="#submenu-reset" aria-expanded="false" aria-controls="submenu-reset">
						        <span class="nav-icon">
						        <span class="material-symbols-outlined">lock_reset</span>
						         </span>
                            <span class="nav-link-text">Reset Password</span>
                            <span class="submenu-arrow">
		                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-down"
                                          fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                      <path fill-rule="evenodd"
                                            d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
                                    </svg>
	                             </span><!--//submenu-arrow-->
                        </a><!--//nav-link-->

                        <div id="submenu-reset" class="collapse submenu submenu-keyword"
                             data-bs-parent="#menu-accordion">
                            <ul class="submenu-list list-unstyled">
                                <li class="submenu-item"><a class="submenu-link" href="${baseUrl}/user/reset-password">Reset
                                    Password</a></li>
                            </ul>
                        </div>
                    </li><!--//nav-item-->

                </ul><!--//app-menu-->
            </nav><!--//app-nav-->
        </div><!--//sidepanel-inner-->
    </div><!--//app-sidepanel-->
</header>
<!--//app-header-->