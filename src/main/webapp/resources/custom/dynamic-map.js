let map;
function setDynamicMapAddress(latLongitude = "") {

    if(!containsNumber(latLongitude)){
        alert("Please set valid latitude and longitude.");
        return;
    }

    let splitedAddress = latLongitude ? latLongitude.split(",") : "";

    let latitude = splitedAddress ? splitedAddress[0] : 27.71136;
    let longitude = splitedAddress ? splitedAddress[1] : 85.31828;

    map = L.map('map').setView([latitude, longitude], 12.3);
    L.tileLayer('https://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
        maxZoom: 20,
        subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
    }).addTo(map);

    L.marker([latitude, longitude]).addTo(map)
        .bindPopup('Exact Location.<br> Easily customizable.')
        .openPopup();

    // Zoom the map to the marker's location
    map.setView([latitude, longitude], 13);

    $('#mapModal').on('show.bs.modal', function () {
        // map.removeLayer(marker);
        setTimeout(function () {
            map.invalidateSize();
        }, 500);
    });
    $("#mapModal").modal('show');

}

$('#mapModal').on('hidden.bs.modal', function () {
    // Destroy the map
    map.remove();
});

function containsNumber(str) {
    return !isNaN(parseFloat(str));
}
