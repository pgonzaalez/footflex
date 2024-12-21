function iniciarMap(){
    var coord = {lat:41.5696852 ,lng: 1.9965175};
    var map = new google.maps.Map(document.getElementById('map'),{
      zoom: 10,
      center: coord
    });
    var marker = new google.maps.Marker({
      position: coord,
      map: map
    });
}