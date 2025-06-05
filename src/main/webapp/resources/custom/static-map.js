$(function () {
    let map = L.map('map').setView([27.71136, 85.31828], 12.3);
    L.tileLayer('https://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
        maxZoom: 20,
        subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
    }).addTo(map);

    var myIcon = L.icon({
        iconUrl: 'https://img.icons8.com/ios-filled/48/000000/marker.png',
        iconSize: [30, 35],
        iconAnchor: [15, 27],
        popupAnchor: [0, -15]
    });

    let marker = '';
    map.on('click', function (e) {
        var myIcon = L.icon({
            iconUrl: 'https://img.icons8.com/ios-filled/48/000000/marker.png',
            iconSize: [30, 35],
            iconAnchor: [15, 27],
            popupAnchor: [0, -15]
        });
        map.removeLayer(marker);
        marker = L.marker([e.latlng.lat, e.latlng.lng], {
            icon: myIcon
        }).addTo(map);
        $('input[name="longitude"]').val(e.latlng.lat + ', ' + e.latlng.lng);
        $('input[name="location"]').val(e.latlng.lat + ', ' + e.latlng.lng);
    });

    $('#mapModal').on('show.bs.modal', function () {
        map.removeLayer(marker);
        setTimeout(function () {
            map.invalidateSize();
        }, 500);
    });
})

function viewOnMap(){
    $("#mapModal").modal('show');
}