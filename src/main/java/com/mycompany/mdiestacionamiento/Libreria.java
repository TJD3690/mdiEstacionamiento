
package com.mycompany.mdiestacionamiento;

public class Libreria {

//---------------------------------------
public Libreria() {
}
//---------------------------------------
public boolean IsNumeric(String CAD) {
boolean Sw;
int k,L;
char E;
    L = CAD.length();
    Sw = true;
    k = 0;
	while((k<=L-1)&&(Sw==true)) {
		E = CAD.charAt(k);
        Sw = (('0'<=E)&&(E<='9'));
        k++;
    }
    return Sw;
}
public boolean IsAlphabetic(String CAD) {
boolean Sw;
int k,L;
char E;
    L = CAD.length();
    Sw = true;
    k = 0;
	while((k<=L-1)&&(Sw==true)) {
		E = CAD.charAt(k);
        Sw = ((('A'<=E)&&(E<='Z'))||(('a'<=E)&&(E<='z')));
        k++;
    }
    return Sw;
}
//---------------------------------------
public void PrintMessage(String ErrorMessage, int Mode) {
	if(Mode==1) {
       System.out.println(ErrorMessage);
	}
	else if(Mode==2) {
		javax.swing.JOptionPane.showMessageDialog(null,ErrorMessage);
	}
	else {
	}
}
//---------------------------------------
public boolean Edad_Ok(int EDAD, int Mode) {
boolean Sw;
String ErrorMessage;
    Sw = false;
    if((18<=EDAD)&&(EDAD<=60)) {
        Sw = true;
    }
    else {
        PrintMessage("ERROR: Edad Incorrecta",Mode);
    }
    return Sw;
}
//---------------------------------------
public boolean Telefono_Ok(String CAD, int Mode) {
boolean Sw;
String ErrorMessage;
    Sw = false;
    if(CAD.length()==9) {
        if(IsNumeric(CAD)==true) {
            Sw = true;
        }
        else {
            PrintMessage("ERROR: El Telefono debe contener solamente digitos numericos",Mode);
         }
    }
    else {
        PrintMessage("ERROR: Longitud del Telefono debe ser de 9 numeros",Mode);
    }
    return Sw;
}
//---------------------------------------
public boolean DNI_Ok(String CAD, int Mode) {
boolean Sw;
String ErrorMessage;
    Sw = false;
    if(CAD.length()==8) {
        if(IsNumeric(CAD)==true) {
        	Sw = true;
        }
        else {
        	PrintMessage("ERROR: El DNI debe contener solamente digitos numericos",Mode);
         }
    }
    else {
        PrintMessage("ERROR: Longitud de DNI debe ser de 8 numeros",Mode);
    }
    return Sw;
}
//---------------------------------------
//---------------------------------------
//---------------------------------------
//---------------------------------------


}//class

