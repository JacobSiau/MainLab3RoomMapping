
//  Rover 1 Control

//  Computer Engineering Lab

//  3/2/2019


//******************
//  Group Members:
//  James Paule
//  Cole Lewis
//  Brett Sanders
//  Jacob Siau

#include <msp430g2553.h>

long int Count = 0;
char PulseCount_R = 0;
char PulseCount_L = 0;
int PulseCountMax_R = 0;
int PulseCountMax_L = 0;
const int Max = 10000;
int Command = 0;
char ID = 3;
char Direction = 0x01;
char Go_R = 0;
char Go_L = 0;
char cam_angle=0;
char i = 0;
int main(void)
{
  WDTCTL = WDTPW + WDTHOLD;                 // Stop WDT
  if (CALBC1_1MHZ==0xFF)                    // If calibration constant erased
  {
    while(1);                               // do not load, trap CPU!!
  }
  DCOCTL = 0;                               // Select lowest DCOx and MODx settings
  BCSCTL1 = CALBC1_1MHZ;                    // Set DCO
  DCOCTL = CALDCO_1MHZ;
  P1SEL = BIT1 + BIT2 ;                     // P1.1 = RXD, P1.2=TXD
  P1SEL2 = BIT1 + BIT2 ;                    // P1.1 = RXD, P1.2=TXD
  UCA0CTL1 |= UCSSEL_2;                     // SMCLK
  UCA0BR0 = 104;                            // 1MHz 9600
  UCA0BR1 = 0;                              // 1MHz 9600
  UCA0MCTL = UCBRS0;                        // Modulation UCBRSx = 1
  UCA0CTL1 &= ~UCSWRST;                     // **Initialize USCI state machine**
  IE2 |= UCA0RXIE;                          // Enable USCI_A0 RX interrupt
  P1DIR |= 0xF9;                            // Set P1.7 (LED) to output direction
  P2DIR = BIT2+BIT3+BIT4+BIT5+BIT6;                            // set p2.0 and p2.1 to input direction
  P2REN = BIT0 + BIT1;
  P2OUT = BIT0 + BIT1;                            // enable pull down resistors
  P2IE |= BIT0 + BIT1;
  P2IES |= 0x00;
  P2IFG = 0;
  P2SEL = (P2SEL & BIT0);
  CCTL0 = CCIE;                             // CCR0 interrupt enabled
  CCR0 = 25000;
  TACTL = TASSEL_2 + MC_1;                  // SMCLK, upmode



  __bis_SR_register(GIE);       // General interrupts enabled

  while(1)
    {


        Count++;
       if(Count>Max)                   //LED flash control
       {
           P2OUT = P2OUT ^ BIT6;
           Count = 0;
       }
    }
}

#pragma vector = PORT2_VECTOR
__interrupt void Port_2(void)
{
    if(P2IFG & 0x01){
        PulseCount_R++;
        if(PulseCount_R > 127)
            PulseCount_R = 0;
    }

    if(P2IFG & 0x02){
        PulseCount_L++;
        if(PulseCount_L > 127)
            PulseCount_L = 0;
        //while (!(IFG2&UCA0TXIFG)); // USCI_A0 TX buffer ready?
         // UCA0TXBUF = 0x4A;
       // PulseLtransmit = PulseCount_L + 0x10;
    }



  P2IFG = 0;  // clear ALL the Int Flag bits on Port 1
}


#pragma vector = TIMER0_A0_VECTOR
__interrupt void Timer_A (void)
{
    while (!(IFG2&UCA0TXIFG)); // USCI_A0 TX buffer ready?
        UCA0TXBUF = PulseCount_R;

    while (!(IFG2&UCA0TXIFG)); // USCI_A0 TX buffer ready?
        UCA0TXBUF = 0x80 + PulseCount_L;

}


