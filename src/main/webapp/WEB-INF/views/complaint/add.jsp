<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/dashboard/user/header.jsp" %>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<div class="app-wrapper">

    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">
            <h1 class="app-page-title">Complaint</h1>
            <hr class="mb-4">
            <div class="row g-4 settings-section">
                <div class="col-12 col-md-4">
                    <h3 class="section-title">${not empty complaint ? "Edit" : "Add"} Complaint</h3>
                    <div class="section-intro">You can ${not empty complaint ? "edit" : "add" } complaint from here.
                    </div>
                </div>
                <div class="col-12 col-md-8">
                    <div class="app-card app-card-settings shadow-sm p-4">

                        <div class="app-card-body">
                            <form action="${baseUrl}/complaint/save-update" method="post" id="formComplaint"
                                  class="settings-form">
                                <input type="hidden" name="id" value="${complaint.id}">
                                <div class="mb-3">
                                    <label for="heading" class="form-label">Heading</label>
                                    <input type="text" id="heading" name="heading" class="form-control"
                                           id="setting-input-1"
                                           placeholder="Enter Heading.." value="${complaint.heading}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="textEditor" class="form-label">Description</label>
                                    <textarea type="text" id="textEditor" rows="10" name="description"
                                              class="form-control"
                                              placeholder="Enter Content">${complaint.description}</textarea>
                                </div>
                                <button type="submit"
                                        class="btn app-btn-primary">${not empty complaint ? "Update" : "Complaint"}</button>
                                <a href="${baseUrl}/complaint" class="btn btn-light">Back</a>
                            </form>
                        </div><!--//app-card-body-->
                    </div><!--//app-card-->
                </div>
            </div><!--//row-->
            <hr class="my-4">
        </div><!--//container-fluid-->
    </div><!--//app-content-->


    <%@include file="../layout/dashboard/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
    <script type="text/javascript">
        function validateStatus(instance) {
            let isChecked = $(instance).is(":checked");
            $(instance).val(isChecked ? 1 : 0);
        }

        $(document).ready(function () {
            $('#textEditor').summernote();
        });

    </script>

