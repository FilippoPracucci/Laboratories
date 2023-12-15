function Computer(processore, disco, ram) {
    this.processore = processore;
    this.disco = disco;
    this.ram = ram;
}

Computer.prototype.infoComputerConsole = function() {
    console.log("Processore : " + this.processore +
        "\nDisco : " + this.disco +
        "\RAM : " + this.ram
    );
}

Computer.prototype.infoComputerDOM = function(id) {
    document.getElementById(id).innerHTML = 
        "<p>Processore:" + this.processore + "</p>" +
        "<p>Disco:" + this.disco + "</p>" +
        "<p>Ram:" + this.ram + "</p>";
}
//alternativa (migliore)
class Computer2 {
    constructor(processore, disco, ram) {
        this.processore = processore;
        this.disco = disco;
        this.ram = ram;
    }

    infoComputerConsole = function() {
        console.log("Processore : " + this.processore +
            "\nDisco : " + this.disco +
            "\RAM : " + this.ram
        );
    }

    infoComputerDOM = function(id) {
        document.getElementById(id).innerHTML = 
            "<p>Processore:" + this.processore + "</p>" +
            "<p>Disco:" + this.disco + "</p>" +
            "<p>Ram:" + this.ram + "</p>";
    }
}

const mioPC = new Computer("i7", "512GB", "16GB");
mioPC.infoComputerConsole();
mioPC.infoComputerDOM("miopc");

const mioPC2 = new Computer2("i5", "256GB", "8GB");
mioPC2.infoComputerConsole();
mioPC2.infoComputerDOM("miopc2");
