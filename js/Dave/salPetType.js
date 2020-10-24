var salPetType = document.getElementById("salPetType");

salPetType.addEventListener('load', function() {
    if (salPetType.value === "0") {
        salPetType.value = "0";
    } else if (salPetType.value === "1") {
        salPetType.innerText = "1";
    } else if (salPetType.value === 2) {
        salPetType.innerText = "貓";
    }});



 
