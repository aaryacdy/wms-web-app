<%@include file="../layout/dashboard/user/header.jsp" %>
<div class="app-wrapper">

    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">
            <h1 class="app-page-title">View Service</h1>
            <hr class="mb-4">
            <div class="row g-4 settings-section">
                <div class="col-12 col-md-4">
                    <h3 class="section-title"> View Service</h3>
                    <div class="section-intro">You can view request service from here.</div>
                </div>
                <div class="col-12 col-md-8">
                    <div class="app-card app-card-settings shadow-sm p-4">

                        <div class="app-card-body">
                            <div class="mb-3">
                                <label class="form-label">Name : </label> ${waste.name}
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Weight : </label> ${waste.weight}
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Price : </label> ${waste.price}
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Address : </label> ${waste.address}
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Longitude : </label> ${waste.longitude}
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Category : </label> ${waste.category.name}
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Description : </label> ${waste.description}
                            </div>

                            <div class="mb-3">
                                <label>Image</label>
                                <a href="${baseUrl}/files/waste/${waste.image}" target="_blank">
                                    <img src="${baseUrl}/files/waste/${waste.image}" alt="${waste.name}"
                                         class="img-fluid">
                                </a>
                            </div>
                            <div class="mb-3">
                                <label>Status : </label> ${waste.status}
                            </div>
                        </div><!--//app-card-body-->
                    </div><!--//app-card-->
                </div>
            </div><!--//row-->
            <hr class="my-4">
        </div><!--//container-fluid-->
    </div>
    <!--//app-content-->