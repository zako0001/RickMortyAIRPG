
function fillTable(data, table) {
    Object.entries(data).forEach(entry => {
        const row = document.createElement("tr");
        table.append(row);
        const key = document.createElement("td");
        row.append(key);
        const value = document.createElement("td");
        row.append(value);
        key.innerText = entry[0];
        value.innerText = entry[1];
    });
}

fetch("http://localhost:8080/character")
    .then(response => response.json())
    .then(data => fillTable(data, document.querySelector("tbody.characterInfo")));

fetch("http://localhost:8080/location")
    .then(response => response.json())
    .then(data => fillTable(data, document.querySelector("tbody.locationInfo")));