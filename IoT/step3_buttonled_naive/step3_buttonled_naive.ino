#define BUTTON_PIN 2  
#define LED_PIN 4  

int count;
bool already_pressed;

void setup() {
  pinMode(LED_PIN, OUTPUT);      
  pinMode(BUTTON_PIN, INPUT);     
  Serial.begin(9600);
  count = 0;
  already_pressed = false;
}

void loop() {
  int buttonState = digitalRead(BUTTON_PIN);
  if (buttonState == HIGH) {     
    digitalWrite(LED_PIN, HIGH);
    //Serial.println("Pressed");
    if (!already_pressed) {
      count++;
      Serial.println(String("Count = ") + count);
      already_pressed = true;
      //delay(50);  nel caso il pulsante non sia buono, per evitare bouncing(1 click, ma varie micro oscillazioni HIGH-LOW prima di stabilizzarsi; la misura dipende dal pulsante)
    }
  } else {
    digitalWrite(LED_PIN, LOW);
    already_pressed = false; 
  }
}
