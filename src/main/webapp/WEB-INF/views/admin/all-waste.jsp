<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/dashboard/admin-v2/header.jsp" %>
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
                                    <div class="col-md-6">
                                        <label for="status" class="form-label">Status</label>
                                        <select class="form-control" name="status" id="status"
                                                onchange="populateAllWaste()">
                                            <option value="">All</option>
                                            <c:forEach var="status" items="${Arrays.STATUS}">
                                                <option value="${status}">${status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            ${message}
                            <div class="table-responsive mt-3">
                                <table id="tblWaste" class="table app-table-hover mb-0 text-left">
                                    <thead>
                                    <tr>
                                        <th class="cell">S.N.</th>
                                        <th class="cell">Heading</th>
                                        <th class="cell">Category</th>
                                        <th class="cell">Image</th>
                                        <th class="cell">Weight</th>
                                        <th class="cell">Price</th>
                                        <th class="cell">Status</th>
                                        <th class="cell">Longitude</th>
                                        <th class="cell">Approved At</th>
                                        <th class="cell">Created By</th>
                                        <th class="cell">Created At</th>
                                        <th class="cell">Description</th>
                                        <th class="cell">Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%-- <% int index = 1;%>
                                     <c:forEach var="waste" items="${wastes}">
                                         <tr>
                                             <td class="cell"><%=index++%>.</td>
                                             <td class="cell">${waste.name}</td>
                                             <td class="cell">${waste.wastageType.name}</td>
                                             <c:set var="imageSrc"
                                                    value="${baseUrl}/files/waste/${waste.image}"/>
                                             <td class="cell"><a href="${imageSrc}" target="_blank">
                                                 <image src="${imageSrc}" width="200" height="100"/>
                                             </a></td>
                                             <td class="cell">${waste.weight}</td>
                                             <td class="cell">${waste.price}</td>
                                             <td class="cell">${waste.longitude}</td>
                                             <td class="cell">${waste.description}</td>
                                             <td class="cell">${waste.createdBy.name}</td>
                                             <td class="cell">${waste.createdDate}</td>
                                             <td>
                                                 <a role="button" onclick="setDynamicMapAddress('${waste.longitude}')">Map</a>
                                             </td>
                                         </tr>
                                     </c:forEach>--%>
                                    </tbody>
                                </table>
                            </div><!--//table-responsive-->
                        </div><!--//app-card-body-->
                    </div><!--//app-card-->
                </div><!--//tab-pane-->
            </div><!--//tab-content-->
        </div><!--//container-fluid-->
    </div><!--//app-content-->
    <%@include file="../map/map-modal.jsp" %>
    <%@include file="../layout/dashboard/footer.jsp" %>
    <script src="${baseUrl}/resources/custom/waste.js"></script>
    <script src="${baseUrl}/resources/custom/dynamic-map.js"></script>
    <script>
        let module = "Admin";
        let baseUrl = "${baseUrl}";
        // $(document).ready(function () {
        $(() => {
            populateAllWaste()
        })
    </script>