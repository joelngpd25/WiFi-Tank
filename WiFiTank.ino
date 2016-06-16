/* Set these to your desired credentials. */
const char *ssid = "xxx";
const char *password = "xxx";
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
WiFiServer server(80);


void setup(void) {  
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  // Start TCP server.
  server.begin();
}

void loop() {
    WiFiClient client = server.available();
    if (client) {
    while (client.connected()) {
      if (client.available()) {
        Serial.print(client.read());
      }
    }
    client.stop();
  }
}
