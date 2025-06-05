<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/dashboard/user/header.jsp" %>
<div class="app-wrapper">
    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">

            <div class="tab-content" id="orders-table-tab-content">
                <div class="tab-pane fade show active" id="orders-all" role="tabpanel" aria-labelledby="orders-all-tab">
                    <div class="app-card app-card-orders-table shadow-sm mb-5">
                        <div class="app-card-body">
                            <div class="table-responsive">
                                <table class="table app-table-hover mb-0 text-left">
                                    <thead>
                                    <tr>
                                        <th class="cell">S.N.</th>
                                        <th class="cell">Name</th>
                                        <th class="cell">Price</th>
                                        <th class="cell">Description</th>
                                        <th class="cell">Status</th>
                                        <th class="cell">Created By</th>
                                        <th class="cell">Created Date</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <% int index = 1;%>
                                    <c:forEach var="category" items="${categories}">
                                        <tr>
                                            <td class="cell"><%=index++%>.</td>
                                            <td class="cell">${category.name}</td>
                                            <td class="cell">${category.price}</td>
                                            <td class="cell">${category.description}</td>
                                            <td class="cell">
                                                <c:choose>
                                                    <c:when test="${category.status}">
                                                        Active
                                                    </c:when>
                                                    <c:otherwise>
                                                        Inactive
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="cell">${category.createdBy.name}</td>
                                            <td class="cell">${category.createdDate}</td>
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
    </div><!--//app-content-->
    <%@include file="../layout/dashboard/footer.jsp" %>