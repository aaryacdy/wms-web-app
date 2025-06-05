<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en-gb">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!--[if lt IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <![endif]-->
    <title>${title}</title>
    <meta name="description" content="">
    <meta name="author" content="WebThemez">
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
    <script type="text/javascript" src="http://explorercanvas.googlecode.com/svn/trunk/excanvas.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${baseUrl}/resources/new-design/dashboard/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/resources/new-design/dashboard/css/isotope.css"
          media="screen"/>
    <link rel="stylesheet" href="${baseUrl}/resources/new-design/dashboard/js/fancybox/jquery.fancybox.css"
          type="text/css" media="screen"/>
    <link href="${baseUrl}/resources/new-design/dashboard/css/animate.css" rel="stylesheet" media="screen">
    <!-- Owl Carousel Assets -->
    <link href="${baseUrl}/resources/new-design/dashboard/js/owl-carousel/owl.carousel.css" rel="stylesheet">
    <link rel="stylesheet" href="${baseUrl}/resources/new-design/dashboard/css/styles.css"/>
    <!-- Font Awesome -->
    <link href="${baseUrl}/resources/new-design/dashboard/font/css/font-awesome.min.css" rel="stylesheet">
</head>

<body>
<header class="header">
    <div class="container">
        <nav class="navbar navbar-inverse" role="navigation">
            <div class="navbar-header">
                <button type="button" id="nav-toggle" class="navbar-toggle" data-toggle="collapse"
                        data-target="#main-nav"><span class="sr-only">Toggle navigation</span> <span
                        class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span></button>
                <a href="${baseUrl}/waste" class="navbar-brand scroll-top logo  animated bounceInLeft"><b></b>WMS</a>
            </div>
            <!--/.navbar-header-->
            <div id="main-nav" class="collapse navbar-collapse">
                <ul class="nav navbar-nav" id="mainNav">
                    <li class="active" id="firstLink"><a href="#home" class="scroll-link">Home</a></li>
                    <li><a href="#aboutUs" class="scroll-link">About Us</a></li>

                    <li><a href="#team" class="scroll-link">Management</a></li>
                    <%--                    <li><a href="${baseUrl}/login" class="scroll-link">Login</a></li>--%>
                    <li><a href="${baseUrl}/admin/login" class="scroll-link" target="_blank">Admin Login</a></li>
                    <%--                    <li><a href="${baseUrl}/collector/login" class="scroll-link" target="_blank">Collector Login</a></li>--%>
                    <%-- <c:choose>
                         <c:when test="${empty sessionScope.username}">
                             <li><a href="${baseUrl}/login" class="scroll-link" target="_blank">User Login</a></li>
                         </c:when>
                         <c:otherwise>
                             <li><a href="${baseUrl}/logout" class="scroll-link" target="_blank">Log Out</a></li>
                         </c:otherwise>
                     </c:choose>--%>
                    <%--                    <li><a href="${baseUrl}/signup" class="scroll-link" target="_blank">Signup</a></li>--%>
                    <%--                    <li><a href="#contactUs" class="scroll-link">About Us</a></li>--%>
                </ul>
            </div>
            <!--/.navbar-collapse-->
        </nav>
        <!--/.navbar-->
    </div>
    <!--/.container-->
</header>
<!--/.header-->
<div id="#top"></div>
<section id="home">
    <div class="banner-container">
        <div id="carousel" class="carousel slide carousel-fade" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carousel" data-slide-to="0" class="active"></li>
                <li data-target="#carousel" data-slide-to="1"></li>
                <li data-target="#carousel" data-slide-to="2"></li>
                <li data-target="#carousel" data-slide-to="3"></li>
                <li data-target="#carousel" data-slide-to="4"></li>
                <li data-target="#carousel" data-slide-to="5"></li>
                <li data-target="#carousel" data-slide-to="6"></li>
            </ol>
            <!-- Carousel items -->
            <div class="carousel-inner">
                <div class="active item"><img
                        src="${baseUrl}/resources/new-design/dashboard/images/slider-image/banner-bg.jpg"
                        alt="banner"/></div>
                <div class="item"><img
                        src="${baseUrl}/resources/new-design/dashboard/images/slider-image/banner-bg2.jpg"
                        alt="banner"/></div>
                <%--<div class="item"><img src="${baseUrl}/resources/new-design/dashboard/images/slider-image/banner-bg3.jpg"
                                       alt="banner"/></div>--%>
                <div class="item"><img
                        src="${baseUrl}/resources/new-design/dashboard/images/slider-image/banner-bg4.jpg"
                        alt="banner"/></div>
                <div class="item"><img
                        src="${baseUrl}/resources/new-design/dashboard/images/slider-image/banner-bg5.jpg"
                        alt="banner"/></div>
                <div class="item"><img
                        src="${baseUrl}/resources/new-design/dashboard/images/slider-image/banner-bg6.jpg"
                        alt="banner"/></div>
                <div class="item"><img
                        src="${baseUrl}/resources/new-design/dashboard/images/slider-image/banner-bg7.jpg"
                        alt="banner"/></div>
            </div>
            <!-- Carousel nav -->
            <a class="carousel-control left" href="#carousel" data-slide="prev">&lsaquo;</a>
            <a class="carousel-control right" href="#carousel" data-slide="next">&rsaquo;</a>
        </div>

    </div>

    <div class="container hero-text2">
        <div class="col-md-9">
            <h2>Waste Management System?</h1>
                <p>Here, you can request a service for your waste.
                    Our main goal is to provide our user to get a good service at home for their unmanaged wastes
                    of their house.
                    All the wastes here are checked and approved by admin before going to your house
                    to collect the wastes.</p>
        </div>
        <div class="col-md-3">
            <a class="btn btn-apply" href="${baseUrl}/waste" target="_blank"><i class="fa fa-search"></i>Request Service</a>
        </div>
    </div>
</section>
<section id="aboutUs">
    <div class="container">
        <div class="heading text-center">
            <!-- Heading -->
            <h2>About Us</h2>
            <p class="aboutUsMessage"></p>
        </div>
    </div>
</section>
<section id="team" class="page-section">
    <div class="container">
        <div class="heading text-center">
            <!-- Heading -->
            <h2>Management</h2>
            <p>We have the team of two employee only till now.</p>
        </div>
        <!-- Team Member's Details -->
        <div class="team-content">
            <div class="row">
                <div class="col-md-3 col-sm-6 col-xs-12">
                    <!-- Team Member -->
                    <div class="team-member pDark">
                        <!-- Image Hover Block -->
                        <div class="member-img">
                            <!-- Image  -->
                            <img class="img-responsive" src="${baseUrl}/resources/new-design/dashboard/images/arya.jpg"
                                 alt=""></div>
                        <!-- Member Details -->
                        <div class="team-title">
                            <h4>Aarya Chaudhary</h4>
                            <!-- Designation -->
                            <span class="pos">Student</span>
                        </div>
                        <%--<div class="team-socials"> <a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i class="fa fa-google-plus"></i></a> <a href="#"><i class="fa fa-twitter"></i></a> <a href="#"><i class="fa fa-dribbble"></i></a> <a href="#"><i class="fa fa-github"></i></a> </div>--%>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 col-xs-12">
                    <!-- Team Member -->
                    <div class="team-member pDark">
                        <!-- Image Hover Block -->
                        <div class="member-img">
                            <!-- Image  -->
                            <img class="img-responsive text-center"
                                 src="${baseUrl}/resources/new-design/dashboard/images/balika.jpg" alt=""></div>
                        <!-- Member Details -->
                        <h4>Balika Thapa</h4>
                        <!-- Designation -->
                        <span class="pos">Student</span>
                        <%--                        <div class="team-socials"> <a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i class="fa fa-google-plus"></i></a> <a href="#"><i class="fa fa-twitter"></i></a> <a href="#"><i class="fa fa-dribbble"></i></a> <a href="#"><i class="fa fa-github"></i></a> </div>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--/.container-->
</section>
<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <div class="col">
                    <h4>About us</h4>
                    <ul>
                        <li>Kathford International College of Engineering and Management</li>
                        <li>Phone: 98563257894</li>
                        <li>Email: <a href="mailto:aarya.chaudhary.bca76@kathford.edu.np" title="Email Us">aarya.chaudhary.bca76@kathford.edu.np</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col-md-3 ml-4">
                <div class="col col-social-icons">
                    <h4>Follow us</h4>
                    <a href="https://www.facebook.com/aarya.chaudhary.9480/"><i class="fa fa-facebook"></i></a>
                    <a href="https://accounts.google.com/signin/v2/identifier?ltmpl=mobNH&flowName=GlifWebSignIn&flowEntry=ServiceLogin"><i
                            class="fa fa-google-plus"></i></a>
                    <a href="https://www.youtube.com/channel/UCa17MkNPvlwewpqLwjCnzZQ"><i
                            class="fa fa-youtube-play"></i></a>
                    <a href="https://www.linkedin.com/in/aarya-chaudhary-88a435177/"><i class="fa fa-linkedin"></i></a>
                    <a href="https://twitter.com/login"><i class="fa fa-twitter"></i></a>
                    <a href="https://www.pinterest.com/"><i class="fa fa-pinterest"></i></a>
                </div>
            </div>
        </div>

    </div>

</footer>
<!--/.page-section-->
<section class="copyright">
    <div class="container">
        <div class="row">
            <a href="https://kathford.edu.np/" target="_blank">
                <div class="col-sm-12 text-center"> Kathford International College of Engineering and Management
            </a></div>
        <a href="${baseUrl}/terms-and-condition" target="_blank">
            <div class="col-sm-12 text-center"> Terms and Conditions
        </a></div>
    </div>
    <!-- / .row -->
    </div>
</section>
<a href="#top" class="topHome"><i class="fa fa-chevron-up fa-2x"></i></a>

<!--[if lte IE 8]>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script><![endif]-->
<script src="${baseUrl}/resources/new-design/dashboard/js/modernizr-latest.js"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/jquery.isotope.min.js" type="text/javascript"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/fancybox/jquery.fancybox.pack.js"
        type="text/javascript"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/jquery.nav.js" type="text/javascript"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/jquery.fittext.js"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/waypoints.js"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/custom.js" type="text/javascript"></script>
<script src="${baseUrl}/resources/new-design/dashboard/js/owl-carousel/owl.carousel.js"></script>
<script>
    let message = '${aboutUs.message}';
    $(() => {
        $(".aboutUsMessage").html(message);
    })

</script>
</body>
</html>

