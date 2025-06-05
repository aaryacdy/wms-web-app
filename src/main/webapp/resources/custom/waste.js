function fetchCategory(instance) {
    let id = $(instance).val();
    let url = baseUrl + "/category/findById";
    $.post(url, {id})
        .done(function (response) {
            let success = response.success;

            if (success) {
                let data = response.body;
                $("#price").val(data.price);
            }
            // alert(response.message ? response.message : "");
        })
        .fail(function () {
            console.log("Something went wrong.");
        })
        .always(function () {

        });
}

function populateAllWaste() {
    let status = $("#status").val();
    let url = baseUrl + "/waste/filter-all";
    $.post(url, {status})
        .done(function (response) {
            let success = response.success;
            let rows = "";
            if (success) {
                let data = response.body;
                $.each(data, function (index, waste) {

                    let isCollector = module === "Collector";
                    let isAdmin = module === "Admin";
                    let isUser = module === "User";

                    let wastId = waste.id;
                    let actionBtn = "";

                    if (isUser) {
                        actionBtn = `<a class="" href="${baseUrl}/waste/${waste.id}" target="_blank">View</a>
                                                <a class="" href="${baseUrl}/waste/${waste.id}/edit"
                                                   target="_blank">Edit</a>
                                                <a role="button" onclick="setDynamicMapAddress('${waste.longitude}')">Map</a>
                                                <a class="" href="${baseUrl}/waste/${waste.id}/delete">Delete</a>`
                    }

                    if (isAdmin) {
                        actionBtn = `<a role="button" onclick="setDynamicMapAddress('${waste.longitude}')">Map</a>`;
                    }

                    if (isCollector) {
                        actionBtn = `<a role="button" onclick="setDynamicMapAddress('${waste.longitude}')">Map</a>
                        <a class="" href="${baseUrl}/waste/${waste.id}/approved"
                                                       target="_blank">Approve</a>
                        <a class="" href="${baseUrl}/waste/${waste.id}/rejected"
                                                       target="_blank">Reject</a>`;
                    }

                    let imageSrc = baseUrl + "/files/waste/" + waste.image;
                    rows += `<tr>
                                            <td class="cell">${index + 1}.</td>
                                            <td class="cell">${getValue(waste.name)}</td>
                                            <td class="cell">${getValue(waste.category.name)}</td>
                                            <td class="cell"><a href="${imageSrc}" target="_blank">
                                                <image src="${imageSrc}" width="200" height="100"/>
                                            </a></td>
                                            <td class="cell">${getValue(waste.weight)}</td>
                                            <td class="cell">${getValue(waste.price)}</td>
                                            <td class="cell">${getValue(waste.status)}</td>
                                            <td class="cell">${getValue(waste.longitude)}</td>
                                            <td class="cell">${getValue(waste.approvedDate)}</td>
                                            <td class="cell">${getValue(waste.createdBy.name)}</td>
                                            <td class="cell">${getValue(waste.createdDate)}</td>
                                            <td class="cell">${getValue(waste.description)}</td>
                                            <td>
                                              ${actionBtn}
                                            </td>
                                        </tr>`
                });
            }
            let dynamicContainerInstance = $("#tblWaste");
            dynamicContainerInstance.find('tbody').html(rows);
        })
        .fail(function () {
            alert("Something went wrong.");
        })
        .always(function () {

        });
}

function getValue(value = "", defaultValue = "") {
    return value && value !== "" ? value : defaultValue;

}