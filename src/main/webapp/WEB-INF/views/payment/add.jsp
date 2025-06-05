<%@include file="../layout/dashboard/collector/header.jsp" %>
<%@page import="com.kathford.wms.util.Arrays" %>
<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<div class="app-wrapper">

    <div class="app-content pt-3 p-md-3 p-lg-4">
        <div class="container-xl">
            <h1 class="app-page-title">Payment</h1>
            <hr class="mb-4">
            <div class="row g-4 settings-section">
                <div class="col-12 col-md-4">
                    <h3 class="section-title">Payment</h3>
                    <div class="section-intro">You can pay the money from here.</div>
                </div>
                <div class="col-12 col-md-8">
                    <div class="app-card app-card-settings shadow-sm p-4">

                        <div class="app-card-body">
                            <form action="${baseUrl}/payment/save-update" method="post" id="formPayment"
                                  class="settings-form" enctype="multipart/form-data">

                                <div class="mb-3">
                                    <label for="year" class="form-label">Year</label>
                                    <select class="form-control" name="year" id="year" required>
                                        <option value="">Select</option>
                                        <c:forEach var="year" items="${Arrays.YEAR}">
                                            <option ${year eq '2080' ? 'selected' : ''} value="${year}">${year}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="year" class="form-label">Month</label>
                                    <select class="form-control" name="month" id="month" required>
                                        <option value="">Select</option>
                                        <c:forEach var="month" items="${Arrays.MONTH}">
                                            <option ${month eq '3' ? 'selected' : ''} value="${month}">${month}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="year" class="form-label">Payment Method</label>
                                    <select class="form-control" name="paymentMethod" id="paymentMethod" required>
                                        <option value="">Select</option>
                                        <c:forEach var="paymentMethod" items="${Arrays.PAYMENT_METHOD}">
                                            <option ${paymentMethod eq 'Khalti' ? 'selected' : ''} value="${paymentMethod}">${paymentMethod}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="amount" class="form-label">Amount</label>
                                    <input type="number" id="amount" name="amount" value="100" class="form-control">
                                </div>

                                <button type="button" class="btn app-btn-primary" id="payment-button">Pay</button>
                                <a href="${baseUrl}/payment" class="btn btn-light">Back</a>
                            </form>
                        </div><!--//app-card-body-->
                    </div><!--//app-card-->
                </div>
            </div><!--//row-->
            <hr class="my-4">
        </div><!--//container-fluid-->
    </div>
    <!--//app-content-->
    <%@include file="../layout/dashboard/footer.jsp" %>
    <script src="https://khalti.s3.ap-south-1.amazonaws.com/KPG/dist/2020.12.17.0.0.0/khalti-checkout.iffe.js"></script>
    <script>
        //test_secret_key_b19c5f583dd148d2bd8e125cb11b39d9
        //test_public_key_d33106ff3d444c4fb6f123e01843778f

        let config = {
            "publicKey": "test_public_key_d33106ff3d444c4fb6f123e01843778f",
            // "publicKey": "live_public_key_242e882c8c2447f4a1da1d361f2e058c",
            "productIdentity": "1234579",
            "productName": "Dress",
            "amount": 10,
            "productUrl": "http://edigitalnepal.edu.np",
            "eventHandler": {
                onSuccess(payload) {
                    console.log(payload);
                    // finalizeTransaction(payload);
                    submitPaymentForm();
                },
                onError(error) {
                    console.log(error);
                },
                onClose() {
                    console.log('widget is closing');
                }

            },
            // one can set the order of payment options and also the payment options based on the order and items in the array
            paymentPreference: [
                // "MOBILE_BANKING",
                "KHALTI",
                // "EBANKING",
                // "CONNECT_IPS",
                // "SCT",
            ],
        };

        let checkout = new KhaltiCheckout(config);
        let btn = document.getElementById("payment-button");
        btn.onclick = function () {
            let finalAmount = Number($("#amount").val());
            checkout.show({amount: 100 * 100}); // 10 rs.
        }

        /*let payBtn = document.getElementById("pay-button");
        payBtn.onclick = function () {
            let finalAmount = Number($(".finalPrice").html());
            console.log(finalAmount + " finalAmount ");
            checkout.show({amount: finalAmount * 100}); // 10 rs.
        }*/

        function submitPaymentForm(){
            let formInstance = $("#formPayment");
            formInstance.submit();
        }

        function finalizeTransaction(payload) {
            performAdvancedRequest("payment/khalti/callback", payload)
                .then(function (response) {
                    const success = response.success;
                    if (success) {
                        console.log("Payment is successfully tested. Now you can generate live key for further acsess.");
                        alert("Payment is successfully tested. Now you can generate live key for further acsess.");
                    }
                });
        }

        function performAdvancedRequest(url, payload) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: url,
                    dataType: 'json',
                    contentType: 'application/json',
                    // method: POST,
                    method: "post",
                    data: JSON.stringify(payload)
                }).done((response) => {
                    resolve(response);
                }).fail((xhr, status, error) => {
                    alert("Something went wrong.", false);
                }).always(() => {
                });
            });
        }

    </script>
