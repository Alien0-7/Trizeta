String inputString = "";
boolean stringComplete = false;

void setup() {
  Serial.begin(9600);
}

void listener() {
    while (Serial.available()) {
    char inChar = (char)Serial.read();

    if (inChar == '\n') {
      stringComplete = true;
      inputString.trim();
      break;

    } else {
      inputString += inChar;
    }
    
  }
  if (stringComplete) {
    if (inputString.equals("getTemperature")) {
      Serial.print(10); //!! print normale non println
    }

    // reset variabili
    inputString = "";
    stringComplete = false;
  }
}

void loop() {
  listener();

  // qui il resto del codice
}




