package com.prabowomurti;

/**
 *
 * @author Prabowo Murti
 */

import javax.microedition.lcdui.*;

public class GuwekatejugeapeCanvas extends Canvas{

	int w = getWidth();
	int h = getHeight();

	String number;
	String words;

	public void keyPressed (int keyCode) {
	
	}

	public int write (Graphics g, int y, String s) {
		return 0;
	}

	public void paint (Graphics g) {
		
		clearScreen(g);
		createMenu(g);
		
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
		g.drawString("Menu", 0, h, Graphics.LEFT | Graphics.BOTTOM);
		g.drawString("Convert", w/2, h, Graphics.HCENTER | Graphics.BOTTOM);
		g.drawString("Clear", w, h, Graphics.RIGHT | Graphics.BOTTOM);
	}

	public String numberToWords(String num){
		
		String[] unitName = {"",
		"ribu",
		"juta",
		"milyar",
		"trilyun",
		"ribu trilyun",
		"juta trilyun",
		"milyar trilyun",
		"trilyun trilyun"};//whatever
		int unit = 0;

		String addedWords, packet;
		String words = "";
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

			//special case
			if (ones == 1 && tens == 0 && hundreds == 0 && (unit == 1 || unit == 5)){
				words = "seribu " + words + " ";
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
			words = addedWords + " " + unitName[unit] + " " + words + " ";
			unit ++;
		}

		return words;
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
