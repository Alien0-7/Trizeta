#include <LiquidCrystal_I2C.h>
#include <Wire.h>
#include <DHT.h>
#include "DHT.h"
#include <TaskScheduler.h>

#define DHTTYPE DHT11
#define TRIG_PIN 10
#define ECHO_PIN 9
#define SENSOR_PIN 3
#define AREAZIONE 2
#define Finestra 7

int StatoFinestra = digitalRead(Finestra);
int analogPin = A0;
float Co2 = 0;
float t = 0;
float h = 0;
String inputString = "";
boolean stringComplete = false;
String UUID = "";
Scheduler runner;

DHT dht(SENSOR_PIN, DHTTYPE);
LiquidCrystal_I2C lcd(0x27, 16, 2);

void lcdVisualizer() {
  StatoFinestra = digitalRead(Finestra);
  /* questi "Serial.println" danno fastidio al programma quando cerca di ottenere le varie misurazioni
  if (StatoFinestra == LOW) {
    Serial.println("Finestra CHIUSA");
  } else if (StatoFinestra == HIGH) {
    Serial.println("Finestra APERTA");
  }
  */

  t = dht.readTemperature();
  h = dht.readHumidity();
  Co2 = analogRead(analogPin);

  /* questi "Serial.println" danno fastidio al programma quando cerca di ottenere le varie misurazioni
  Serial.println(t);
  Serial.println(h);
  Serial.println(Co2);
  */

  // Regolazione sensore ultrasuoni
  digitalWrite(TRIG_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_PIN, LOW);

  unsigned long tempo = pulseIn(ECHO_PIN, HIGH);
  float distanza = 0.03438 * tempo / 2;

  if (distanza <= 70) {

    if (Co2 >= 93) {
      lcd.backlight();
      lcd.setCursor(0, 0);        // Impostiamo la prima riga
      lcd.print("QUALITA'ARIA ");  // Scriviamo sulla prima riga
      lcd.print(Co2);
      lcd.setCursor(0, 1);        // Impostiamo la seconda riga
      lcd.print("OTTIMA");       // Scriviamo sulla seconda riga
      delay(2500);
      lcd.clear();
      lcd.setCursor(0, 0);  // Impostiamo la prima riga
      lcd.print("TEMP");
      lcd.setCursor(0, 1);  // Impostiamo la prima riga
      lcd.print(t);
      lcd.setCursor(6, 0);  // Impostiamo la prima riga
      lcd.print("UMIDITA'");
      lcd.setCursor(6, 1);  // Impostiamo la prima riga
      lcd.print(h);
      lcd.setCursor(8, 1);  // Impostiamo la prima riga
      lcd.print("%");
      delay(2500);
      lcd.clear();
    } else if (Co2 <= 92) {

      lcd.backlight();
      lcd.setCursor(0, 0);        // Impostiamo la prima riga
      lcd.print("QUALITA'ARIA");  // Scriviamo sulla prima riga
      lcd.setCursor(0, 1);        // Impostiamo la seconda riga
      lcd.print("PESSIMA ");      // Scriviamo sulla seconda riga
      delay(2500);
      lcd.clear();
      lcd.setCursor(0, 0);  // Impostiamo la prima riga
      lcd.print("TEMP");
      lcd.setCursor(0, 1);  // Impostiamo la prima riga
      lcd.print(t);
      lcd.setCursor(6, 0);  // Impostiamo la prima riga
      lcd.print("UMIDITA'");
      lcd.setCursor(6, 1);  // Impostiamo la prima riga
      lcd.print(h);
      lcd.setCursor(8, 1);  // Impostiamo la prima riga
      lcd.print("%");
      delay(2500);
      lcd.clear();

    }

    else {
      delay(5000);
    }

  } else {
    lcd.clear();
    lcd.noBacklight();
    delay(5000);
  }


  // Per letture in background
  if (Co2 >= 93) {
    digitalWrite(AREAZIONE, LOW);
  } else if (Co2 <= 92) {
    digitalWrite(AREAZIONE, HIGH);
  }

  delay(100);
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
      t = dht.readTemperature();
      Serial.println(t);
    } else if (inputString.equals("getHumidity")) {
      h = dht.readHumidity();
      Serial.println(h);
    } else if (inputString.equals("getCo2")) {
      Co2 = analogRead(analogPin);
      Serial.println(Co2);
    } else {
      Serial.println("Errore");
    }

    // reset variabili
    inputString = "";
    stringComplete = false;
  }
}

Task task1(1000, TASK_FOREVER, &listener);
Task task2(1000, TASK_FOREVER, &lcdVisualizer);


void setup() {
  runner.init();
  runner.addTask(task1);
  task1.enable();

  runner.addTask(task2);
  task2.enable();

  lcd.init();
  dht.begin();
  pinMode(TRIG_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT);
  digitalWrite(TRIG_PIN, LOW);
  pinMode(AREAZIONE, OUTPUT);
  Serial.begin(9600);
}


void loop() {
  runner.execute();
}
