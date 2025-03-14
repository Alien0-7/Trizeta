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
      //! println non print => ln sta per "line", cioè che va a caporiga che è il metodo che usiamo per comunicare che è la fine del messaggio
      Serial.println(10);
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