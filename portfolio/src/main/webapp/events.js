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
            var date = document.createElement("h4");
            date.innerText = "Date: " + event.date;
            newDiv.append(date);
            container.append(newDiv);
            console.log("Event added");
        });
    });
}