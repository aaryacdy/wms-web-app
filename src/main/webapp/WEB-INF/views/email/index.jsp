<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/dashboard/collector/header.jsp" %>
<%@page import="com.kathford.wms.util.Arrays" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.0.3/leaflet.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.0.3/leaflet.css"/>
<div class="app-wrapper">
    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">
            <div class="tab-content" id="orders-table-tab-content">
                <div class="tab-pane fade show active" id="orders-all" role="tabpanel" aria-labelledby="orders-all-tab">
                    <div class="app-card app-card-orders-table shadow-sm mb-5">
                        <div class="app-card-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="address" class="form-label">Address</label>
                                    <select class="form-control" name="address" id="address">
                                        <c:forEach var="waste" items="${wastes}">
                                            <option value="${waste.address}">${waste.address}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-6">
                                    <label for="type" class="form-label">Type</label>
                                    <select class="form-control" name="type" id="type">
                                        <c:forEach var="type" items="${Arrays.MAIL_TYPE}">
                                            <option value="${type}">${type}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="fromTime" class="form-label">From Time</label>
                                        <input type="text" id="fromTime" name="fromTime" class="form-control">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="toTime" class="form-label">To Time</label>
                                        <input type="text" id="toTime" name="toTime" class="form-control">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <button type="button" class="btn app-btn-primary" onclick="sendMail()">Send
                                        Message
                                    </button>
                                </div>
                            </div>

                        </div><!--//app-card-body-->
                    </div><!--//app-card-->
                </div><!--//tab-pane-->
            </div><!--//tab-content-->
        </div><!--//container-fluid-->
    </div><!--//app-content-->
    <%@include file="../layout/dashboard/footer.jsp" %>
    <script>
        let baseUrl = "${baseUrl}";

        function sendMail() {
            let address = $("#address :selected").val();
            let type = $("#type :selected").val();
            let fromTime = $("#fromTime").val();
            let toTime = $("#toTime").val();

            if (type === 'Collect') {
                if (!fromTime) {
                    alert("Please enter from time.");
                    return;
                }

                if (!toTime) {
                    alert("Please enter to time.");
                    return;
                }
            }

            let url = baseUrl + "/email/sent";
            $.post(url, {address, type, fromTime, toTime})
                .done(function (response) {
                    alert(response.message);
                    if(response.success) {
                        window.location.reload();
                    }
                })
                .fail(function () {
                    alert("Something went wrong.");
                })
                .always(function () {

                });

        }
    </script>