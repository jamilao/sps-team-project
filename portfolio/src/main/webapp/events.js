async function fetchEvents(){
    const container = document.getElementById('events');
    console.log('Fetching events...');
    fetch('/data')
    .then(response => response.json())
    .then(events => {
        console.log(events);
        container.innerHTML = "";
        events.forEach((event)=> {
            console.log(event);
            var newDiv = document.createElement("div");
            newDiv.setAttribute("class","child");
            newDiv.setAttribute("id",event.key.id.toString());
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
            start.innerText = "Start time: " + event.start;
            newDiv.append(start);
            container.append(newDiv);
            console.log("Event added");
        });
    });
}
function fetchEvent(){
    var url = new URL(window.search.location);
    var params = url.searchParams;
    params.set("key","")
    const parameterKey = urlParams.get("key");
    fetch('/event')
}