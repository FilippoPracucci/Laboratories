#include "lib.h"
#include <arduino.h>

void blink(int pin, int delayTime) {
  digitalWrite(pin, HIGH);
  delay(delayTime);
  digitalWrite(pin, LOW);
}