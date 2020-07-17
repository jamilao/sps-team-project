var position = {lat: 40.7, lng: -74};
var pathList = new Array();

//Gets a set of latitude, longitude.
function grabPosition(userLat, userLng){
    position["lat"] = userLat;
    position["lng"] = userLng;
    console.log(position["lat"]);
    console.log(position["lng"]);
    localStorage.setItem("userLat", position["lat"]);
    localStorage.setItem("userLng", position["lng"]);
    document.getElementById('centerCoord').value = [userLat, userLng];
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
            console.log("true");
            pathList.splice(i, 1);
        }
    }
    localStorage.setItem("pathList", JSON.stringify(pathList));
    document.getElementById('pathCoords').value = JSON.stringify(pathList);
}

function clearPath(){
    pathList = new Array();
    localStorage.setItem("pathList", JSON.stringify(pathList));
    document.getElementById('pathCoords').value = JSON.stringify(pathList);
}

function givePath(){
    var path = JSON.parse(localStorage.getItem("pathList"));
    return path;
}