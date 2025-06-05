<%@include file="header.jsp" %>
<div class="app-wrapper">

    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">

            <h1 class="app-page-title">Overview</h1>
            <div class="app-card alert alert-dismissible shadow-sm mb-4 border-left-decoration" role="alert">
                <div class="inner">
                    <div class="app-card-body p-3 p-lg-4">
                        <h3 class="mb-3">Welcome, ${sessionScope.admin_username}!</h3>
                        <div class="row gx-5 gy-3">
                            <div class="col-12 col-lg-9">
                                <%--<div>Now you can request a waste service from here.</div>--%>
                            </div><!--//col-->
                        </div><!--//row-->
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div><!--//app-card-body-->
                </div><!--//inner-->
            </div><!--//app-card-->

            <div class="row g-4 mb-4">
                <div class="col-6 col-lg-3">
                    <div class="app-card app-card-stat shadow-sm h-100">
                        <div class="app-card-body p-3 p-lg-4">
                            <h4 class="stats-type mb-1">Total Request</h4>
                            <div class="stats-figure">${totalWaste}</div>
                        </div><!--//app-card-body-->
                        <a class="app-card-link-mask" href="${baseUrl}/admin/post"></a>
                    </div><!--//app-card-->
                </div><!--//col-->
            </div><!--//container-fluid-->
        </div><!--//app-content-->
        <!--//app-wrapper-->
        <%@include file="../footer.jsp" %>

