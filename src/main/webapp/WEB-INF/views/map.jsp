<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <title>MushroomFinder</title>
  <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=fetch,requestAnimationFrame,Element.prototype.classList,URL"></script>
  <script src="https://openlayers.org/en/v6.5.0/build/ol.js"></script>
  <link rel="stylesheet" href="https://openlayers.org/en/v6.5.0/css/ol.css" type="text/css">
</head>
<body>
  <div id="map" class="map" style= "width: 512px; height: 512px" ></div>

  <script>
var map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        }),
        new ol.layer.Vector({
            source: new ol.source.Vector()
        })
    ],
    view: new ol.View({
        center: ol.proj.fromLonLat([37.41, 8.82]),
        zoom: 4
    })
});

var markerSource = map.getLayers().getArray()[1].getSource();
var marker;
map.on('click', function(event) {
    var coordinates = event.coordinate;
    if (!marker) {
        marker = new ol.Feature({
            geometry: new ol.geom.Point(coordinates),
            name: 'Marker'
        });
        var style = new ol.style.Style({
            image: new ol.style.Icon({
                src: 'marker.png'
            })
        });
        marker.setStyle(style);
        markerSource.addFeature(marker);
    } else {
        marker.getGeometry().setCoordinates(coordinates);
    }
    //alert(marker.getGeometry().getCoordinates());
});
  </script>
</body>
</html>