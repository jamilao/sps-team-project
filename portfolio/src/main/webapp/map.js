var position = {lat: 40.7, lng: -74};
var pathList = new Array();

//Gets a set of latitude, longitude.
function grabPosition(userLat, userLng){
    position["lat"] = userLat;
    position["lng"] = userLng;
    var centerPos = {lat: userLat, lng: userLng};
    document.getElementById('centerCoord').value = JSON.stringify(centerPos);
}

//Sets latitude and longitude.
function giveLatitude(){
    setLat = parseFloat((JSON.parse(localStorage.getItem("centerCoord"))).lat);
    return setLat;
}
function giveLongitude(){
    setLng = parseFloat((JSON.parse(localStorage.getItem("centerCoord"))).lng);
    return setLng;
}

function storeCoords(lat, lng){
    var position = {lat: lat, lng: lng}; 
    pathList.push(position);
    document.getElementById('pathCoords').value = JSON.stringify(pathList);
}

function removeCoord(lat, lng){
    var position = {lat: lat, lng: lng};
    for (i = 0; i < pathList.length; i++) {
        if ((position.lat == pathList[i].lat) && (position.lng == pathList[i].lng)){
            pathList.splice(i, 1);
        }
    }
    document.getElementById('pathCoords').value = JSON.stringify(pathList);
}

function givePath(){
    var path = JSON.parse(localStorage.getItem("pathList"));
    return path;
}