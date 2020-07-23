async function fetchEvents(){
    const container = document.getElementById('events');
    container.innerHTML = "";
    console.log('Fetching events...');
    fetch('/data')
    .then(response => response.json())
    .then(events => {
        console.log(events);
        events.forEach((event)=> {
            console.log(event);
            const eventID = event.key.id.toString();
            var newDiv = document.createElement("div");
            newDiv.setAttribute("class","child");
            newDiv.setAttribute("id",eventID);
            var imgContainer = document.createElement("div");   
            imgContainer.setAttribute("class","img-container");
            var image = document.createElement("img");
            image.setAttribute("src","/images/imgicon.png");
            imgContainer.append(image);
            newDiv.append(imgContainer);
            imgContainer.onclick = function(){      
                window.location.href = "/event.html?id=" + eventID;
            }
            var name = document.createElement("h4");
            name.innerText = event.eventName;
            newDiv.append(name);
            var location = document.createElement("h4");
            location.innerText = "Location: " + event.location;
            newDiv.append(location);
            var start = document.createElement("h4");
            start.innerText = "When: " + event.start;
            newDiv.append(start);
            container.append(newDiv);
            console.log("Event added");
        });
    });
}

// This method successfully checks a password and displays the edit page.
// TO-DO: Fix bug where the first event loaded/clicked in events.html has its data persist (i.e. password, displayed info).
function checkPassword(){
    const Http = new XMLHttpRequest();
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    const url = "/event?id=" + id;
    var event = null;
    Http.responseType = 'json';
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function(){
        if(this.readyState==4 && this.status==200){
            event = (Http.response)[0];
            var password = document.getElementById('password').value;
            if (password === event.password){
                localStorage.setItem("eventName", event.eventName);
                localStorage.setItem("location", event.location);
                localStorage.setItem("start", event.start);
                localStorage.setItem("end", event.end);
                localStorage.setItem("description", event.description);
                localStorage.setItem("key", JSON.stringify(event.key));
                localStorage.setItem("centerCoord", event.centerCoord);
                localStorage.setItem("pathList", event.pathCoords);
                document.getElementById('main').innerHTML='<object style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; width: 100%; height: 100%;" type="text/html" data="organizereventedit.html"></object>';
            }
            else{
                alert("Incorrect password.");
            }
        }
    }
}

// TO-DO: Clear local storage after saving event. Saved event should replace existing datastore entry.
function fillForm(){
    document.getElementsByName('key')[0].value=localStorage.getItem("key");
    document.getElementsByName('eventName')[0].value=localStorage.getItem("eventName");
    document.getElementsByName('location')[0].value=localStorage.getItem("location");
    var start = new Date(localStorage.getItem("start")).toISOString().substring(0, 16);
    var end = new Date(localStorage.getItem("end")).toISOString().substring(0, 16);
    document.getElementsByName('start')[0].value=start;
    document.getElementsByName('end')[0].value=end;
    document.getElementsByName('description')[0].value=localStorage.getItem("description");
    window.onbeforeunload = function() {
        localStorage.clear();
    }
}

function displayEvent(){
    console.log("Calling displayEvent()");
    const Http = new XMLHttpRequest();
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    const url = "/event?id=" + id;
    console.log(url);
    var event = null;
    Http.responseType = 'json';
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function(){
        if(this.readyState==4 && this.status==200){
            event = (Http.response)[0];
            console.log(event);
            console.log('HTTP request complete');
            const container = document.getElementById('main');
            container.innerHTML = "";
            var newDiv = document.createElement("div");
            newDiv.setAttribute("class","child");
            var imgContainer = document.createElement("div");   
            imgContainer.setAttribute("class","img-container");
            var image = document.createElement("img");
            image.setAttribute("src","/images/imgicon.png");
            imgContainer.append(image);
            newDiv.append(imgContainer);
            var name = document.createElement("h4");
            name.innerText = event.eventName;
            newDiv.append(name);
            var location = document.createElement("h4");
            location.innerText = "Location: " + event.location;
            newDiv.append(location);
            var start = document.createElement("h4");
            start.innerText = "When: " + event.start;
            newDiv.append(start);
            container.append(newDiv);
            var passwordDiv = document.createElement("div");
            passwordDiv.innerHTML = "<input type='text' id='password' placeholder='Password'/><button type='button' onclick='checkPassword()'>Edit Event</button>";
            container.append(passwordDiv);
            console.log("Event added");
        }
    }
}

// Check that start and end times are valid.
function validateForm(){
    var start = new Date(document.getElementsByName('start')[0].value);
    var end = new Date(document.getElementsByName('end')[0].value);
    if (start >= end){
        alert("Start time cannot be after or equal to end time!");
        return false;
    }
    localStorage.clear();
    return true;
}