<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/dashboard/admin-v2/header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.0.3/leaflet.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.0.3/leaflet.css"/>
<div class="app-wrapper">
    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">

            <div class="tab-content" id="orders-table-tab-content">
                <div class="tab-pane fade show active" id="orders-all" role="tabpanel" aria-labelledby="orders-all-tab">
                    <div class="app-card-body">
                        ${message}
                        <div class="table-responsive">
                            <table class="table app-table-hover mb-0 text-left">
                                <thead>
                                <tr>
                                    <th class="cell">S.N.</th>
                                    <th class="cell">Name</th>
                                    <th class="cell">Username</th>
                                    <th class="cell">Email</th>
                                    <th class="cell">Address</th>
                                    <th class="cell">Mobile No.</th>
<%--                                    <th class="cell">Location</th>--%>
                                    <th class="cell">Created Date</th>
                                    <th class="cell">Block</th>
                                    <th class="cell">Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% int index = 1;%>
                                <c:forEach var="collector" items="${collectors}">
                                    <tr>
                                        <td class="cell"><%=index++%>.</td>
                                        <td class="cell">${collector.name}</td>
                                        <td class="cell">${collector.username}</td>
                                        <td class="cell">${collector.email}</td>
                                        <td class="cell">${collector.address}</td>
                                        <td class="cell">${collector.mobileNo}</td>
<%--                                        <td class="cell">${collector.longitude}</td>--%>
                                        <td class="cell">${collector.createdDate}</td>
                                        <td class="cell">
                                            <c:choose>
                                                <c:when test="${collector.blocked}">
                                                    Blocked
                                                </c:when>
                                                <c:otherwise>
                                                    Unblocked
                                                </c:otherwise>
                                            </c:choose></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${collector.blocked}">
                                                    <a class="" href="${baseUrl}/collector/${collector.id}/unblock"
                                                       target="_blank">Unblock</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="" href="${baseUrl}/collector/${collector.id}/block"
                                                       target="_blank">Block</a>
                                                </c:otherwise>
                                            </c:choose>
                                            <%--<a class="" href="${baseUrl}/collector/${collector.id}/edit"
                                               target="_blank">Edit</a>--%>
<%--                                            <a role="button" onclick="setDynamicMapAddress('${collector.longitude}')">Map</a>--%>
                                            <a class="" href="${baseUrl}/collector/${collector.id}/delete">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div><!--//table-responsive-->
                    </div><!--//app-card-body-->
                </div><!--//app-card-->
            </div><!--//tab-pane-->
        </div><!--//tab-content-->
    </div><!--//container-fluid-->
</div>
<!--//app-content-->
<%@include file="../map/map-modal.jsp" %>
<%@include file="../layout/dashboard/footer.jsp" %>
<script src="${baseUrl}/resources/custom/dynamic-map.js"></script>

