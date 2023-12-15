console.log("Hello World!");

//span id="ciao"
const tagHello = document.getElementById("ciao");
/*
* Alternativa: const tagHello = document.querySelector("#ciao");
*/
tagHello.innerHTML = "Hello World!"; //per scrivere nel contenuto dell'HTML

//class="anno"
//si tratta di un vettore degli item della classe "anno"
const tagYear = document.getElementsByClassName("anno")[0];
/*
* Alternative:
* - const tagYear = document.querySelector(".anno");
* - const tagYear = document.querySelectorAll(".anno")[0];
*/
tagYear.innerHTML = "2023";
