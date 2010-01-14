package com.prabowomurti.guwekatejugeape;

/**
 *
 * @author Prabowo Murti
 */

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class GuwekatejugeapeCanvas extends Canvas{

	GuwekatejugeapeMidlet midlet;
	int w = getWidth();
	int h = getHeight();
	int keyCode;

	String number = "";
	int numberLength = 0;
	int maxNumberLength = 18;
	String words;

	public GuwekatejugeapeCanvas (MIDlet midlet){
		this.midlet = (GuwekatejugeapeMidlet) midlet;
	}

	public void keyPressed (int keyCode) {
		if (keyCode != 0 &&
				keyCode != 42 &&
				keyCode != 35 &&
				((keyCode >= -8 && keyCode <= -5) || (keyCode >= 48 && keyCode <= 57))){
			this.keyCode = keyCode;
			repaint();
		}
	}

	protected void paint (Graphics g) {
		
		clearScreen(g);
		createMenu(g);

		
		switch (keyCode){
			case 0:
				return;
			case -6://soft left key pressed
				//showMenu(g);
				midlet.exitMidlet();
				break;
			case -7://soft right key pressed
				clearNumber(g);
				break;
			case -8: //clear key pressed
				eraseNumber(g);
				break;
			case -5://select key pressed
				writeNumber(g, this.number);
				if (this.number.equals("")){
					writeWords(g, "Simon said... number");
				}else {
					writeWords(g, numberToWords(this.number));
				}
				break;
			default:
				if (this.numberLength == 0 && keyCode == 48)
					break;
				if (this.numberLength >= this.maxNumberLength){
					writeNumber(g, this.number);
					writeWords(g, "Slow down, you're doing too much");
					break;
				}
				this.numberLength ++;
				this.number = this.number + getKeyName(keyCode);
				writeNumber (g, this.number);
				break;
		}
	}

	public void writeNumber (Graphics g, String num) {
		g.drawString(num, 10, 20, Graphics.LEFT | Graphics.TOP);
	}

	public void eraseNumber (Graphics g){
		if (this.numberLength > 0){
			this.number = this.number.substring(0, this.number.length()-1);
			this.numberLength --;
			writeNumber (g, this.number);
		}
	}
	public void clearNumber(Graphics g) {
		this.number = "";
		this.numberLength = 0;
		clearScreen(g);
		createMenu(g);
	}

	public void writeWords(Graphics g, String w){
		g.drawString(w, 10, 60, Graphics.LEFT | Graphics.TOP);
	}
	
	public void clearScreen(Graphics g) {
		g.setGrayScale(221);//#CCCCCC
		g.fillRect (0, 0, w, h);
	}

	public void createMenu(Graphics g) {
		//create box around menu
		g.setGrayScale(128);
		g.fillRect(0, h-20, w, 20);
		
		g.setGrayScale(0);
		g.drawString("Exit", 0, h, Graphics.LEFT | Graphics.BOTTOM);
		g.drawString("Convert", w/2, h, Graphics.HCENTER | Graphics.BOTTOM);
		g.drawString("Clear", w, h, Graphics.RIGHT | Graphics.BOTTOM);
	}

	public void showMenu(Graphics g) {
		
	}

	public String numberToWords(String num){
		
		String[] unitName = {"",
		"ribu",
		"juta",
		"milyar",
		"trilyun",
		"ribu trilyun"};//whatever
		int unit = 0;

		String addedWords, packet;
		String word = "";
		int lengthNum = num.length();
		int hundreds, tens, ones;

		if (lengthNum % 3 != 0)
			num = "00" + num;

		//God, I can't find a way to reverse a string in
		//JME better than this expensive way
		num = new StringBuffer(num).reverse().toString();

		for (int i=0; i<lengthNum ; i=i+3){
			addedWords = "";
			packet = num.substring(i, i+3);
			hundreds = Integer.parseInt(packet.substring(2, 3));
			tens = Integer.parseInt(packet.substring(1, 2));
			ones = Integer.parseInt(packet.substring(0, 1));

			//special cases
			if (ones == 1 && tens == 0 && hundreds == 0 && (unit == 1 || unit == 5)){
				word = "se" + unitName[unit] + " " + word + " ";
				unit ++;
				continue;
			}else if (ones == 0 && tens == 0 && hundreds == 0){
				unit ++;
				continue;
			}

			//hundreds
			if (hundreds == 1){
				addedWords = "seratus ";
			}else if (hundreds > 1){
				addedWords = getNumName(hundreds) + " ratus ";
			}

			//tens and ones
			if (tens == 0){
				addedWords = addedWords + getNumName(ones);
			}else if (tens == 1){
				if (ones == 0){
					addedWords = addedWords + "sepuluh";
				}else if (ones == 1){
					addedWords = addedWords + "sebelas";
				}else {
					addedWords = addedWords + getNumName(ones) + " belas";
				}
			}else {
				addedWords = addedWords + getNumName(tens) + " puluh ";
				if (ones != 0){
					addedWords = addedWords + getNumName(ones);
				}
			}

			//depend on unit
			word = addedWords + " " + unitName[unit] + " " + word + " ";
			unit ++;
		}

		return word;
	}

	public String getNumName (int num){
		switch (num) {
			case 0:
				return "";
			case 1:
				return "satu";
			case 2:
				return "dua";
			case 3:
                return "tiga";
			case 4:
				return "empat";
			case 5:
                return "lima";
			case 6:
                return "enam";
			case 7:
				return "tujuh";
			case 8:
				return "delapan";
			case 9:
                return "sembilan";
			default:
				return "";
		}
	}


}
