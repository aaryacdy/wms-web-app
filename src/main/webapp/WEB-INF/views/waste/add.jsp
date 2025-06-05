<%@include file="../layout/dashboard/user/header.jsp" %>
<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.0.3/leaflet.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.0.3/leaflet.css"/>
<div class="app-wrapper">

    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">
            <h1 class="app-page-title">Waste</h1>
            <hr class="mb-4">
            <div class="row g-4 settings-section">
                <div class="col-12 col-md-4">
                    <h3 class="section-title">Request Service</h3>
                    <div class="section-intro">You can request service from here.</div>
                </div>
                <div class="col-12 col-md-8">
                    <div class="app-card app-card-settings shadow-sm p-4">

                        <div class="app-card-body">
                            <form action="${baseUrl}/waste/save" method="post" id="formWaste"
                                  class="settings-form" enctype="multipart/form-data">

                                <div class="mb-3">
                                    <label for="name" class="form-label">Heading</label>
                                    <input type="text" id="name" name="name" class="form-control" minlength="2"
                                           maxlength="255"
                                           placeholder="Enter Wastage Heading.." required>
                                </div>

                                <div class="mb-3">
                                    <label for="categoryId" class="form-label">Category</label>
                                    <select class="form-control" name="categoryId" id="categoryId"
                                            onchange="fetchCategory(this)" required>
                                        <option value="">Select</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.id}">${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="weight" class="form-label">Weight</label>
                                    <input type="number" id="weight" name="weight" class="form-control">
                                </div>

                                <div class="mb-3">
                                    <label for="price" class="form-label">Price (Per Kg)</label>
                                    <input type="number" id="price" name="price" class="form-control" readonly>
                                </div>

                                <div class="mb-3">
                                    <label for="address" class="form-label">Address</label>
                                    <input type="text" id="address" name="address" class="form-control" required>
                                </div>

                                <div class="mb-3">
                                    <label for="longitude" class="form-label">Longitude</label>
                                    <input type="text" id="longitude" name="longitude" onkeyup="viewOnMap()" class="form-control" required>
                                </div>

                                <div class="mb-3">
                                    <label for="image">Image</label>
                                    <input type="file" class="form-control" id="image" name="attachment"
                                           class="img-thumbnail"
                                           accept="image/*" placeholder="Enter image">
                                </div>

                                <div class="mb-3">
                                    <label for="textEditor" class="form-label">Description</label>
                                    <textarea type="text" id="textEditor" rows="10" name="description"
                                              class="form-control" placeholder="Enter Content"></textarea>
                                </div>

                                <button type="submit" class="btn app-btn-primary">Save</button>
                                <a href="${baseUrl}/waste" class="btn btn-light">Back</a>
                            </form>
                        </div><!--//app-card-body-->

                    </div><!--//app-card-->
                </div>
            </div><!--//row-->
            <hr class="my-4">
        </div><!--//container-fluid-->
    </div>
    <!--//app-content-->
    <%@include file="../map/map-modal.jsp" %>
    <%@include file="../layout/dashboard/footer.jsp" %>
    <script src="${baseUrl}/resources/custom/static-map.js"></script>
    <script src="${baseUrl}/resources/custom/waste.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
    <script type="text/javascript">

        let baseUrl = "${baseUrl}";
        $(document).ready(function () {
            $('#textEditor').summernote();
        });

        function validateStatus(instance) {
            let isChecked = $(instance).is(":checked");
            $(instance).val(isChecked ? 1 : 0);
        }
    </script>