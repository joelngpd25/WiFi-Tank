#include <Servo.h>

Servo rightTrack;
Servo leftTrack;
int movement;
//Servo speed 80 - 110
void setup() {
  rightTrack.attach(7);
  leftTrack.attach(6);
  Serial.begin(9600);
  movement = 0;
}

void loop() {
  //Serial.println(movement);
    if(Serial.available()>0){
      int temp = Serial.read();
      int temp1 = (int) temp;
      Serial.println(temp1);
      if(temp1 >47 && temp1 < 53){
      if(temp == 48){
        movement=0;
      }else if(temp == 49){
        movement=1;
      }else if(temp == 50 ){
        movement=2;
      }
      }
      if(temp1 > 75 && temp1 < 180){
          if(movement == 1){
            if(temp1>127){
              leftTrack.write(80);
              rightTrack.write(95-((int)(((temp1-153))/26.0*15.0)));
            }else{
              rightTrack.write(110);
              leftTrack.write(95-((int)(((temp1-101))/26.0*15.0)));          
            }
          }else if(movement == 2){
            if(temp1>127){
              leftTrack.write(110);
              rightTrack.write(95+((int)(((temp1-153))/26.0*15.0)));
            }else{
              rightTrack.write(80);
              leftTrack.write(95+((int)(((temp1-101))/26.0*15.0)));
            }
          }else{
              leftTrack.write(95);
              rightTrack.write(95);
          }
      }
    }
}
