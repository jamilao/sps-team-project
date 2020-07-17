var position = {lat: 40.7, lng: -74};
var pathList = new Array();

//Gets a set of latitude, longitude.
function grabPosition(userLat, userLng){
    position["lat"] = userLat;
    position["lng"] = userLng;
    localStorage.setItem("userLat", position["lat"]);
    localStorage.setItem("userLng", position["lng"]);
    var centerPos = {lat: userLat, lng: userLng};
    document.getElementById('centerCoord').value = JSON.stringify(centerPos);
}

//Sets latitude and longitude.
function giveLatitude(){
    setLat = parseFloat(localStorage.getItem("userLat"));
    return setLat;
}
function giveLongitude(){
    setLng = parseFloat(localStorage.getItem("userLng"));
    return setLng;
}

function storeCoords(lat, lng){
    var position = {lat: lat, lng: lng}; 
    pathList.push(position);
    localStorage.setItem("pathList", JSON.stringify(pathList));
    document.getElementById('pathCoords').value = JSON.stringify(pathList);
}

function removeCoord(lat, lng){
    var position = {lat: lat, lng: lng};
    for (i = 0; i < pathList.length; i++) {
        if ((position.lat == pathList[i].lat) && (position.lng == pathList[i].lng)){
            pathList.splice(i, 1);
        }
    }
    localStorage.setItem("pathList", JSON.stringify(pathList));
    document.getElementById('pathCoords').value = JSON.stringify(pathList);
}

function givePath(){
    var path = JSON.parse(localStorage.getItem("pathList"));
    return path;
}