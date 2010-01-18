package com.prabowomurti.guwekatejugeape;

/**
 *
 * @author Prabowo Murti <prabowo.murti AT gmail.com>
 */

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class GuwekatejugeapeCanvas extends Canvas{

	GuwekatejugeapeMidlet midlet;
	int w = getWidth();
	int h = getHeight();
	final int MARGIN_LEFT = 10,
			  MARGIN_TOP = 10,
			  MARGIN_RIGHT = 5;

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
		
		g.setFont (Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		
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
		g.drawString(num, MARGIN_LEFT, MARGIN_TOP, Graphics.LEFT | Graphics.TOP);
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

	public void writeWords(Graphics g, String word){
		if (countWord(word) <= 2){
			g.drawString(word, MARGIN_LEFT, 40, Graphics.LEFT | Graphics.TOP);
		}else {
			wrapText(g, word, MARGIN_LEFT, 40, w-MARGIN_LEFT-MARGIN_RIGHT);
		}
	}

	public void wrapText(Graphics g, String words, int xPosition, int yPosition, int lineWidth){
		int xPositionOriginal = xPosition;
		int wordWidth;
		int spaceLeftAtThisLine = lineWidth;
		int numOfWords = countWord(words);
		String[] splitWords = new String[numOfWords];

		//split words into splitWords (God, this line IS EXPENSIVE!)
		splitWords = split(words);

		for (int i=0 ; i<numOfWords ; i++){
			wordWidth = g.getFont().stringWidth(splitWords[i]);
			if (wordWidth <= spaceLeftAtThisLine){
				//write on the same line
				g.drawString(splitWords[i], xPosition, yPosition, Graphics.LEFT | Graphics.TOP);
				xPosition += wordWidth;
				spaceLeftAtThisLine = spaceLeftAtThisLine - wordWidth;
			}else {
				//write on the next line
				yPosition = yPosition + g.getFont().getHeight();//
				xPosition = xPositionOriginal;
				g.drawString(splitWords[i], xPosition, yPosition, Graphics.LEFT | Graphics.TOP);
				xPosition += wordWidth;
				spaceLeftAtThisLine = lineWidth - wordWidth;
			}
		}
	}

	public int countWord(String word){
		if (word.trim().length() == 0)
			return 0;
		if (word.trim().length() <= 2)
			return 1;

		//I waste 55 minutes just because I forget to change this var's value to 0. F***...
		int count = 1;//always get a space at the beginning of the word
		word = " " + word.trim();
		int searchStringPos = word.indexOf(" ", 1), startPos = 1;
		while (searchStringPos != -1){
			count ++;
			startPos = searchStringPos + 1; //1 == length of " "
			searchStringPos = word.indexOf(" ", startPos);
		}
		return count;
	}

	public String[] split(String word){
		int count = countWord(word);
		String[] result = new String[count];
		word = " " + word.trim();
		int startPos = 1;
		int searchPos;

		for (int i=0; i<count; i++){
			searchPos = word.indexOf(" ", startPos);
			result[i] = word.substring(startPos-1, (searchPos!=-1?searchPos:word.length()));
			startPos = searchPos + 1;
		}

		return result;
	}
	
	public void clearScreen(Graphics g) {
		//g.setGrayScale(221);//#CCCCCC
		//g.setColor(85, 50, 133);//#553285
		g.setColor(151, 104, 209);//#9768D1
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
