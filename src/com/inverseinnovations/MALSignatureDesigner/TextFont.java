package com.inverseinnovations.MALSignatureDesigner;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class TextFont implements Serializable{
	private static final long serialVersionUID = 1L;
	Color color = Color.black;
	Font font;
	String fontName = "";
	String style = "";
	String hexcolor;
	int size;
	public TextFont(){
		this(null, "plain", 20, "FFFFFF", "");
	}
	public TextFont(String fontName, int size, String location){
		this(fontName, "plain", size, "FFFFFF", location);
	}
	public TextFont(String fontName, int size, String rgbColor, String location){
		this(fontName, "plain", size, rgbColor, location);
	}
	/**
	 * @param fontName
	 * @param style
	 * @param size
	 * @param rgbColor
	 */
	public TextFont(String fontName, String style, int size, String rgbColor, String location){
		this.fontName = fontName;
		this.size = size;
		this.style = style;
		try{
			color = Color.decode(rgbColor);
			this.hexcolor = rgbColor;
		}
		catch(NumberFormatException numE){
			color = Color.decode("#000000");
			this.hexcolor = "#000000";
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
		File file = new File(System.getProperty("user.dir") + location + "/fonts/" +fontName);
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
	public String getFontname(){
		return fontName;
	}
	public int getSize(){
		return size;
	}
	public String getStyle(){
		return style;
	}
	public String getHexColor(){
		return hexcolor;
	}
	public Color getColor(){
		return color;
	}
}
