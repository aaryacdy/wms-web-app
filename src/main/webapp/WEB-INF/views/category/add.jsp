<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/dashboard/admin-v2/header.jsp" %>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<div class="app-wrapper">

    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">
            <h1 class="app-page-title">Category</h1>
            <hr class="mb-4">
            <div class="row g-4 settings-section">
                <div class="col-12 col-md-4">
                    <h3 class="section-title">${not empty category ? "Edit" : "Create"} Category</h3>
                    <div class="section-intro">You can ${not empty category ? "edit" : "create" } category from here. On
                        the basis of category you can post
                        the blog.
                    </div>
                </div>
                <div class="col-12 col-md-8">
                    <div class="app-card app-card-settings shadow-sm p-4">

                        <div class="app-card-body">
                            <form action="${baseUrl}/category/save-update" method="post" id="formCategory"
                                  class="settings-form">
                                <input type="hidden" name="id" value="${category.id}">
                                <div class="mb-3">
                                    <label for="name" class="form-label">Name</label>
                                    <input type="text" id="name" name="name" class="form-control" id="setting-input-1"
                                           placeholder="Enter Category Name.." value="${category.name}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="name" class="form-label">Price</label>
                                    <input type="number" id="price" name="price" class="form-control"
                                           placeholder="Enter Price" value="${category.price}">
                                </div>

                                <div class="mb-3">
                                    <label for="textEditor" class="form-label">Description</label>
                                    <textarea type="text" id="textEditor" rows="10" name="description"
                                              class="form-control"
                                              placeholder="Enter Description">${category.description}</textarea>
                                </div>

                                <div class="mb-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="status" value="1"
                                               id="status"
                                               onchange="validateStatus(this)" ${category.status ? "checked" : (empty category ? "checked" : "")}>
                                        <label class="form-check-label" for="status">
                                            Status
                                        </label>
                                    </div>
                                </div>
                                <button type="submit"
                                        class="btn app-btn-primary">${not empty category ? "Update" : "Save"}</button>
                                <a href="${baseUrl}/category" class="btn btn-light">Back</a>
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

