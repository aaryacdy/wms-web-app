<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/dashboard/admin-v2/header.jsp" %>
<div class="app-wrapper">
    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">
            <div class="tab-content" id="orders-table-tab-content">
                <div class="tab-pane fade show active" id="orders-all" role="tabpanel" aria-labelledby="orders-all-tab">
                    <div class="app-card app-card-orders-table shadow-sm mb-5">
                        <div class="app-card-body">
                            ${message}
                            <div class="table-responsive mt-3">
                                <table class="table app-table-hover mb-0 text-left">
                                    <thead>
                                    <tr>
                                        <th class="cell">S.N.</th>
                                        <th class="cell">Waste Heading</th>
                                        <th class="cell">Payment Method</th>
                                        <th class="cell">Amount</th>
                                        <th class="cell">Status</th>
                                        <th class="cell">Created By</th>
                                        <th class="cell">Created Date</th>
                                        <th class="cell">Approved /Rejected By</th>
                                        <th class="cell">Approved /Rejected Date</th>
                                        <%--                                        <th class="cell">Action</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <% int index = 1;%>
                                    <c:forEach var="payment" items="${payments}">
                                        <tr>
                                            <td class="cell"><%=index++%>.</td>
<%--                                            <td class="cell">${payment.year}</td>--%>
                                            <td class="cell">${payment.waste.name}</td>
                                            <td class="cell">${payment.paymentMethod}</td>
                                            <td class="cell">${payment.amount}</td>
                                            <td class="cell">${payment.status}</td>
                                            <td class="cell">${payment.createdBy.name}</td>
                                            <td class="cell">${payment.createdDate}</td>
                                            <td class="cell">${not empty payment.approvedOrRejectedBy ? payment.approvedOrRejectedBy.name : ""}</td>
                                            <td class="cell">${payment.approveOrRejectedDate}</td>
                                                <%--<td>
                                                    <c:choose>
                                                        <c:when test="${payment.status eq 'Pending'}">
                                                            <a class="" href="${baseUrl}/payment/${payment.id}/approve"
                                                               target="_blank">Approve</a>
                                                            <a class="" href="${baseUrl}/payment/${payment.id}/reject"
                                                               target="_blank">Reject</a>
                                                        </c:when>
                                                        <c:when test="${payment.status eq 'Approved'}">
                                                            <a class="" href="${baseUrl}/payment/${payment.id}/reject"
                                                               target="_blank">Reject</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a class="" href="${baseUrl}/payment/${payment.id}/approve"
                                                               target="_blank">Approve</a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>--%>
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

