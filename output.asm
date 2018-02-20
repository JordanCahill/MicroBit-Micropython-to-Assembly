; File created using a python to assembly converter
INVBR R2, 9
INVBR R2, 11
INVBR R2, 12
CALL settingUpTimer
XOR R2, R2, R2 ; Clear R2
END


settingUpTimer:
XOR R1,R1,R1
SETBR R1, 0
SETBR R1, 2
SETBR R1, 3
SETBR R1, 4
SETBR R1, 5
SETBR R1, 6
MOVRSFR SFR2, R1
XOR R1,R1,R1
SETBR R1, 0
SETBR R1, 2
SETBR R1, 3
SETBR R1, 4
SETBR R1, 5
SETBR R1, 10
SETBSFR SFR0, 0 ; enable global interrupts
SETBSFR SFR0, 3 ; enable timer interrupt
SETBSFR SFR0, 6 ; down timer
SETBSFR SFR0, 4 ; enable timer. Include as last bit set
RET
