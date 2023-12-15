const result = document.querySelector("div");

//document.querySelectorAll("input")[0].addEventListener("click", function() {
document.querySelector("input[value = 'Testo uppercase']").addEventListener("click", function() {
    let text = result.innerHTML;
    text = text.toUpperCase();
    result.innerHTML = text;
});

document.querySelectorAll("input")[1].addEventListener("click", function() {
    let text = result.innerHTML;
    text = text.toLowerCase();
    result.innerHTML = text;
});

//document.querySelector("input:last-of-type").addEventListener("click", function() {
document.querySelector("input[value = 'Testo substring']").addEventListener("click", function() {
    let text = result.innerHTML;
    //text = text.substring(5, text.length) + text.substring(0, 5);
    text = text.slice(5, text.length) + text.slice(0, 5);
    result.innerHTML = text;
})
