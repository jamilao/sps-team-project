var position = {lat: 40.7, lng: -74};

//Gets a set of latitude, longitude.
function grabPosition(userLat, userLng){
    position["lat"] = userLat;
    position["lng"] = userLng;
    console.log(position["lat"]);
    console.log(position["lng"]);
    localStorage.setItem("userLat", position["lat"]);
    localStorage.setItem("userLng", position["lng"]);
}

//Sets latitude and longitude.
function giveLatitude(){
    setLat = parseFloat(localStorage.getItem("userLat")); //TO-DO: Find a way to store values that isn't local.
    return setLat;
}
function giveLongitude(){
    setLng = parseFloat(localStorage.getItem("userLng"));
    return setLng;
}