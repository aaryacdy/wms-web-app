<%@include file="../layout/dashboard/collector/header.jsp" %>
<%@page import="com.kathford.wms.util.Arrays" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                                    <label for="waste" class="form-label">Waste</label>
                                    <select class="form-control" name="wasteId" id="waste"
                                            onchange="validateWaste(this)"
                                            required>
                                        <option value="">Select</option>
                                        <c:forEach var="waste" items="${wastes}">
                                            <option data-weight="${waste.weight}"
                                                    data-pricekg="${waste.price}" ${param['id'] eq waste.id ? 'selected' : ''}
                                                    value="${waste.id}">${waste.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="paymentMethod" class="form-label">Payment Method</label>
                                    <select class="form-control" name="paymentMethod" id="paymentMethod"
                                            onchange="validateMethod()"
                                            required>
                                        <c:forEach var="paymentMethod" items="${Arrays.PAYMENT_METHOD}">
                                            <option ${paymentMethod eq 'Khalti' ? 'selected' : ''}
                                                    value="${paymentMethod}">${paymentMethod}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="weight" class="form-label">Weight</label>
                                            <input type="text" id="weight" name="weight" readonly
                                                   class="form-control">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="pricePerKg" class="form-label">Price Per Kg</label>
                                            <input type="number" id="pricePerKg" name="pricePerKg"
                                                   class="form-control" readonly>
                                        </div>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label for="amount" class="form-label">Amount</label>
                                    <input type="number" id="amount" name="amount" value="${param['amount']}" class="form-control" readonly>
                                </div>

                                <button type="button" class="btn app-btn-primary d-none" id="payment-button">Pay Via Khalti
                                </button>
                                <button type="button" class="btn app-btn-primary" id="cash-button"
                                        onclick="submitPaymentForm()">Pay
                                </button>
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

        $(()=>{
            validateMethod();
            /*setTimeout(()=>{
                submitPaymentForm();
            }, 500);*/
        })

        //test_secret_key_b19c5f583dd148d2bd8e125cb11b39d9
        //test_public_key_d33106ff3d444c4fb6f123e01843778f

        function validateMethod() {
            let paymentMethod = $("#paymentMethod :selected").val();
            let khaltiBtn = $("#payment-button");
            let cashPayBtn = $("#cash-button");
            if (paymentMethod === 'Cash') {
                cashPayBtn.removeClass("d-none");
                khaltiBtn.addClass("d-none");
            } else {
                cashPayBtn.addClass("d-none");
                khaltiBtn.removeClass("d-none");
            }
        }

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

        function submitPaymentForm() {
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

        function validateWaste(instance) {
            let wasteInstance = $("#waste :selected");
            // let wasteId = wasteInstance.val();
            let weight = wasteInstance.attr('data-weight');
            let price = wasteInstance.attr("data-pricekg");

            console.log({weight, price})

            $("#weight").val(weight);
            $("#pricePerKg").val(price);
            let finalWeigth = weight ? weight : 1;
            let finalAmount = finalWeigth * price;
            $("#amount").val(finalAmount);

        }
    </script>
