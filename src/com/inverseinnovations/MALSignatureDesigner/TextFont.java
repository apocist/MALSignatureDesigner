package com.inverseinnovations.MALSignatureDesigner;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class TextFont {
	Color color = Color.black;
	Font font;
	public TextFont(){
		this(null, "plain", 20, "FFFFFF");
	}
	public TextFont(String fontName, int size){
		this(fontName, "plain", size, "FFFFFF");
	}
	public TextFont(String fontName, int size, String rgbColor){
		this(fontName, "plain", size, rgbColor);
	}
	/**
	 * @param fontName
	 * @param style
	 * @param size
	 * @param rgbColor
	 */
	public TextFont(String fontName, String style, int size, String rgbColor){
		try{
			color = Color.decode(rgbColor);
		}
		catch(NumberFormatException numE){
			color = Color.decode("#FFFFFF");
		}
		int fontStyle = Font.PLAIN;
		if(style!= null){
			switch(style){
			case "bold":
				fontStyle = Font.BOLD;
				break;
			case "italic":
				fontStyle = Font.ITALIC;
				break;
			}
		}
		File file = new File(System.getProperty("user.dir") + "/fonts/" +fontName);
		if(file.isFile() && file.canRead()){//check if the font is in the font
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, file);
				font = font.deriveFont(fontStyle,size);
			}
			catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
		}
		else{
			font =  new Font(fontName,fontStyle,size);
		}
	}
	public Font getFont(){
		return font;
	}
	public Color getColor(){
		return color;
	}
}
