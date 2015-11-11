
// Includes
#include <math.h>

typedef struct {
    short int force;
    double pos;
} data_hapkit;

data_hapkit hapkit_tx;

// Pin declares
int pwmPin = 5; // PWM output pin for motor 1
int dirPin = 8; // direction output pin for motor 1
int sensorPosPin = A2; // input pin for MR sensor

// Position tracking variables
int updatedPos = 0;     // keeps track of the latest updated value of the MR sensor reading
int rawPos = 0;         // current raw reading from MR sensor
int lastRawPos = 0;     // last raw reading from MR sensor
int lastLastRawPos = 0; // last last raw reading from MR sensor
int flipNumber = 0;     // keeps track of the number of flips over the 180deg mark
int tempOffset = 0;
int rawDiff = 0;
int lastRawDiff = 0;
int rawOffset = 0;
int lastRawOffset = 0;
const int flipThresh = 700;  // threshold to determine whether or not a flip over the 180 degree mark occurred
boolean flipped = false;

// Kinematics variables
double xh = 0;           // position of the handle [m]

// Force output variables
double force = 0;           // force at the handle
double Tp = 0;              // torque of the motor pulley
double duty = 0;            // duty cylce (between 0 and 255)
unsigned int output = 0;    // output command to the motor


double lastXh = 0;     //last x position of the handle
double vh = 0;         //velocity of the handle
double lastVh = 0;     //last velocity of the handle
double lastLastVh = 0; //last last velocity of the handle

int counterMort = 0;
double OFFSET = 980;
double OFFSET_NEG = 15;

double b;
double m =  0.0133;

int k_spring = 50; // define the stiffness of a virtual spring in N/m
int feedback_on = 1;
int gain_multiplier = 1;
const int lengthInputBuffer = 4; //need to flush out the ENTIRE transmission! Otherwise it gets super confused
char serialInputBuffer[lengthInputBuffer]; 
int sendInterval = 0;

// --------------------------------------------------------------
// Setup function -- NO NEED TO EDIT
// --------------------------------------------------------------
void setup() 
{
  // Set up serial communication
  Serial.begin(9600);
  
  // Set PWM frequency 
  setPwmFrequency(pwmPin,1); 
  
  // Input pin
  pinMode(sensorPosPin, INPUT); // set MR sensor pin to be an input

  // Output pins
  pinMode(pwmPin, OUTPUT);  // PWM pin for motor A
  pinMode(dirPin, OUTPUT);  // dir pin for motor A
  
  // Initialize motor 
  analogWrite(pwmPin, 0);     // set to not be spinning (0/255)
  digitalWrite(dirPin, LOW);  // set direction
  
  // Initialize position valiables
  lastLastRawPos = analogRead(sensorPosPin);
  lastRawPos = analogRead(sensorPosPin);
  
   b = (lastRawPos-OFFSET_NEG)*m;
   flipNumber = 0;
}

// --------------------------------------------------------------
// Main Loop
// --------------------------------------------------------------
void loop()
{
  
  //*************************************************************
  //*** Section 1. Compute position in counts (do not change) ***  
  //*************************************************************

  // Get voltage output by MR sensor
  rawPos = analogRead(sensorPosPin);  //current raw position from MR sensor
 
  rawPos = rawPos-OFFSET_NEG;
  // Calculate differences between subsequent MR sensor readings
  rawDiff = rawPos - lastRawPos;          //difference btwn current raw position and last raw position
  lastRawDiff = rawPos - lastLastRawPos;  //difference btwn current raw position and last last raw position
  rawOffset = abs(rawDiff);
  lastRawOffset = abs(lastRawDiff);
  
  // Update position record-keeping vairables
  lastLastRawPos = lastRawPos;
  lastRawPos = rawPos;
  
  // Keep track of flips over 180 degrees
  if((lastRawOffset > flipThresh) && (!flipped)) { // enter this anytime the last offset is greater than the flip threshold AND it has not just flipped
    if(lastRawDiff > 0) {        // check to see which direction the drive wheel was turning
      flipNumber--;              // cw rotation 
    } else {                     // if(rawDiff < 0)
      flipNumber++;              // ccw rotation
    }
    flipped = true;            // set boolean so that the next time through the loop won't trigger a flip
  } else {                        // anytime no flip has occurred
    flipped = false;
  }
   updatedPos = rawPos + flipNumber*OFFSET; // need to update pos based on what most recent offset is 

 
  //*************************************************************
  //*** Section 2. Compute position in meters *******************
  //*************************************************************

  // Define kinematic parameters you may need
     double rh = 0.075;   //[m]
     double ts = m*updatedPos - b; // Compute the angle of the sector pulley (ts) in degrees based on updatedPos
     double tsRAD= ts*3.14/180;
  // Step 2.7: xh = ?; 
     double xh= rh*tsRAD; // Compute the position of the handle (in meters) based on ts (in radians)
  

  // Lab 4 Step 2.3: compute filtered handle velocity (2nd-order filter)
    vh = -(.95*.95)*lastLastVh + 2*.95*lastVh + (1-.95)*(1-.95)*(xh-lastXh)/.0001;
    lastXh = xh;
    lastLastVh = lastVh;
    lastVh = vh;
  
  //*************************************************************
  //*** Section 3. Assign a motor output force in Newtons *******  
  //*************************************************************
 
  // ADD YOUR CODE HERE
  // Define kinematic parameters you may need
    
     double rp = 0.00475;   //[m]
     double rs = 0.075;   //[m] 
  
    force = k_spring*xh;

    Tp = force*rh*rp/rs; //Torque that you want the motor to output


  //*************************************************************
  //*** Section 4. Force output (do not change) *****************
  //*************************************************************
  
  // Determine correct direction for motor torque
  if(force < 0) { 
    digitalWrite(dirPin, HIGH);
  } else {
    digitalWrite(dirPin, LOW);
  }

  // Compute the duty cycle required to generate Tp (torque at the motor pulley)
  duty = sqrt(abs(Tp)/0.0183); // the constant is 0.0125 for the Mabuchi motor and 0.03 for the Maxon A-max motor)

  // Make sure the duty cycle is between 0 and 100%
  if (duty >= 1) {            
    duty = 1;
    //Serial.println("MAX!");
  } else if (duty < 0) { 
    duty = 0;
  }  

  // Turn on/off feedback
  output = (int)(duty* 255);   // convert duty cycle to output signal
  if (feedback_on == 0) {
    output = 0;
  }
  
  // Modify force based on gain variable
  if (gain_multiplier == 2) {
    output = output * 2;
  } else if (gain_multiplier == 3) {
    output = output * 3;
  }
  analogWrite(pwmPin,output);  // output the signal
  
   
  //*************************************************************
  //*** Section 5. Send data to Processing      *****************
  //*************************************************************
  
  hapkit_tx.force = 12;
  hapkit_tx.pos = xh;
  
  sendInterval++;
  
//  unsigned long start, finished, elapsed;
//  
//  start=millis();
  if(sendInterval > 6){
  sendInterval = 0;
  send_float(xh);
  }
//  finished=millis();
//  elapsed=finished-start;
//  Serial.print(elapsed);
//  Serial.println(" milliseconds elapsed");
}

//************************************************************
// courtesy of http://stackoverflow.com/questions/3270967/how-to-send-float-over-serial
//************************************************************
void send_float (float arg)
{
  // get access to the float as a byte-array:
  byte * data = (byte *) &arg; 

  // write the data to the serial
  Serial.write (data, sizeof (arg));
  Serial.write (225);
}

//*************************************************************
//*** Section 6. Send data to Processing      *****************
//*************************************************************
void serialEvent() {
  while (Serial.available()) {
     // read the incoming buffer:
      Serial.readBytesUntil(225,serialInputBuffer,lengthInputBuffer);
      k_spring = serialInputBuffer[0];
      feedback_on = serialInputBuffer[1];
      gain_multiplier = serialInputBuffer[2];
  }
  
}

// --------------------------------------------------------------
// Function to set PWM Freq -- DO NOT EDIT
// --------------------------------------------------------------
void setPwmFrequency(int pin, int divisor) {
  byte mode;
  if(pin == 5 || pin == 6 || pin == 9 || pin == 10) {
    switch(divisor) {
      case 1: mode = 0x01; break;
      case 8: mode = 0x02; break;
      case 64: mode = 0x03; break;
      case 256: mode = 0x04; break;
      case 1024: mode = 0x05; break;
      default: return;
    }
    if(pin == 5 || pin == 6) {
      TCCR0B = TCCR0B & 0b11111000 | mode;
    } else {
      TCCR1B = TCCR1B & 0b11111000 | mode;
    }
  } else if(pin == 3 || pin == 11) {
    switch(divisor) {
      case 1: mode = 0x01; break;
      case 8: mode = 0x02; break;
      case 32: mode = 0x03; break;
      case 64: mode = 0x04; break;
      case 128: mode = 0x05; break;
      case 256: mode = 0x06; break;
      case 1024: mode = 0x7; break;
      default: return;
    }
    TCCR2B = TCCR2B & 0b11111000 | mode;
  }
}

