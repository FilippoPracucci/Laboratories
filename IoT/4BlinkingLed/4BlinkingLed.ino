#define POT_PIN A5

#include "lib.h"

int i = 0;
int led_pins[4] =  {4, 5, 6, 7};
bool flag = true;

void setup() {
  Serial.begin(9600);
  for (int i = 0; i < 4; i++) {
    pinMode(led_pins[i], OUTPUT);
  }
  pinMode(POT_PIN, INPUT);
}

void loop() {
  int read = map(analogRead(POT_PIN), 0, 1023, 0, 2000);
  /* Didn't use the for in order to create a more responsive circuit */
  blink(led_pins[i],read);
  if (flag) {
    i++;
    if (i == 3) {
      flag = !flag;
    }
  } else {
    i--;
    if (i == 0) {
      flag = !flag;
    }
  }
}
