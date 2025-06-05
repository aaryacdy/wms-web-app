<%@include file="../layout/dashboard/admin-v2/header.jsp" %>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">

<div class="app-wrapper">

  <div class="app-content pt-3 p-md-3 p-lg-4">
    <div class="container-xl">
      <h1 class="app-page-title">About Us</h1>
      <hr class="mb-4">
      <div class="row g-4 settings-section">
        <div class="col-12 col-md-4">
          <h3 class="section-title">${not empty aboutUs ? "Edit" : "Create"} About Us</h3>
          <div class="section-intro">You can ${not empty aboutUs ? "edit" : "create" } about us from here. On the basis of about us you can post
            the blog.
          </div>
        </div>
        <div class="col-12 col-md-8">
          <div class="app-card app-card-settings shadow-sm p-4">

            <div class="app-card-body">
              <form action="${baseUrl}/about-us/save-update" method="post" id="formAboutUs"
                    class="settings-form">
                <input type="hidden" name="id" value="${aboutUs.id}">
                <div class="mb-3">
                  <label for="textEditor" class="form-label">Name</label>
                  <textarea type="text" id="textEditor" rows="5" name="message" maxlength="2500"
                            class="form-control" placeholder="Enter Description" required>${aboutUs.message}</textarea>
                </div>
                <button type="submit" class="btn app-btn-primary">${not empty aboutUs ? "Update" : "Save"}</button>
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
    $(document).ready(function () {
      $('#textEditor').summernote();
    });
  </script>
