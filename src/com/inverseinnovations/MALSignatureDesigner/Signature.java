package com.inverseinnovations.MALSignatureDesigner;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Signature{
	private BufferedImage sigImage;//public to allow edits by all
	public RSS rss;
	public Filter filter = new Filter();
	public boolean init = false;
	private String location = "";

	public Signature(){
	}

	public Signature(String location){
		this.location = location;
	}

	/**
	 * Adds an entire layer of a single color
	 * @param rgb
	 */
	public void addBackground(String rgb){
		addImage(makeBackground(rgb),0,0);
	}
	/**
	 * Places Anime Episodes on the image
	 * @param id the Anime order number
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align left, center, or right
	 * @param angdeg degrees to rotate clockwise
	 * @param style style 1: 3/54 2: 3 of 54 3: 3 - 54 4: 3 5: 54
	 */
	public void addEpisodes(int id,int x,int y, TextFont textFont, String align, int angdeg, int style){
		/*String text = rss.getEpisodes(id, style);
		addText(text,x,y, textFont,align, angdeg);*/
		addImage(makeEpisodes(id, x, y, textFont, align, angdeg, style),0,0);
	}
	/**
	 * Places image on the signature
	 * @param image
	 * @param x
	 * @param y
	 */
	public void addImage(BufferedImage image,int x, int y){
		Graphics2D g = getGraphics();
		g.drawImage(image, x,y, null);
		g.dispose();
	}
	/**
	 * Places image on the signature
	 * @param filename
	 * @param x
	 * @param y
	 */
	public void addImage(String filename,int x, int y){
		BufferedImage image = null;
		if((image = loadImage(filename)) != null){
			addImage(image, x, y);
		}
	}
	/**
	 * Places Selected Anime's viewing status on the image
	 * @param id
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align
	 * @param angdeg
	 */
	public void addStatus(int id, int x, int y, TextFont textFont, String align, int angdeg){
		addImage(makeStatus(id, x, y, textFont, align, angdeg),0,0);
	}
	/**
	 * Places text on the image
	 * @param text
	 * @param x
	 * @param y
	 * @param textFont
	 */
	public void addText(String text,int x,int y, TextFont textFont){
		addText(text,x,y,textFont,"left",0);
	}
	/**
	 * Places text on the image
	 * @param text
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align left, center, or right
	 * @param angdeg degrees to rotate clockwise
	 */
	public void addText(String text,int x,int y, TextFont textFont, String align, int angdeg){
		BufferedImage image = makeText(text, x, y, textFont, align, angdeg);
		addImage(image,0,0);
	}
	/**
	 * EXPERIMENTAL
	 * @param text
	 * @param x
	 * @param y
	 */
	public void addTextArc(String text,int x,int y){
		BufferedImage image = new BufferedImage(getHeight(), getWidth(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Font font = new Font("Courier", Font.PLAIN, 12);
		g.translate(20, 20);

		FontRenderContext frc = g.getFontRenderContext();

		GlyphVector gv = font.createGlyphVector(frc, text);
		int length = gv.getNumGlyphs();
			Rectangle2D testRec=new Rectangle2D.Double(0, 0, 700, 500);
			g.draw(testRec);
		for (int i = 0; i < length; i++) {
		Point2D p = gv.getGlyphPosition(i);
		AffineTransform at = AffineTransform.getTranslateInstance(p.getX(), p.getY());
		at.rotate((double) i / (double) (length - 1) * Math.PI / 3);

		Shape glyph = gv.getGlyphOutline(i);
		Shape transformedGlyph = at.createTransformedShape(glyph);
		g.fill(transformedGlyph);
		}

		addImage(image,x,y);

		g.dispose();
	}
	/**
	 * Places Date of update on image - Style To Be Changed
	 * @param id
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align
	 * @param angdeg
	 */
	public void addTime(int id,int x,int y, TextFont textFont, String align, int angdeg){
		addImage(makeTime(id, x, y, textFont, align, angdeg),0,0);
	}
	/**
	 * Places Anime Title on the image
	 * @param id the Anime order number
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align left, center, or right
	 * @param angdeg degrees to rotate clockwise
	 * @param airType if should display the tpye of show (TV/Movie/OVA)
	 * @param maxLength the maximum number of characters the title is
	 */
	public void addTitle(int id,int x,int y, TextFont textFont, String align, int angdeg, boolean airType, int maxLength){
		addImage(makeTitle(id, x, y, textFont, align, angdeg, airType, maxLength),0,0);
	}
	/**
	 * Converts the BufferedImage ColorModel to another
	 * @param src
	 * @param bufImgType
	 */
	@SuppressWarnings("unused")
	private BufferedImage convertColorModel(BufferedImage src, int bufImgType) {
	    BufferedImage img= new BufferedImage(src.getWidth(), src.getHeight(), bufImgType);
	    Graphics2D g2d= img.createGraphics();
	    g2d.drawImage(src, 0, 0, null);
	    g2d.dispose();
	    return img;
	}
	public void echo(String string){
		System.out.println(string);
	}
	/**
	 * Returns the direct Signature Graphics2D for editing, call dispose() when finished
	 */
	public Graphics2D getGraphics(){
		return sigImage.createGraphics();
	}
	/**
	 * Returns Signature height in pixels
	 */
	public int getHeight(){
		return sigImage.getHeight();
	}
	/**
	 * Returns the raw Signature Image
	 */
	public BufferedImage getSigImage(){
		return sigImage;
	}
	/**
	 * Returns Signature width in pixels
	 */
	public int getWidth(){
		return sigImage.getWidth();
	}
	public void initDemoSignature(int width, int height){
		sigImage = makeImage(width, height);
		rss = new RSS(null);
		init = true;
	}
	public void initSignature(String username, int width, int height){
		sigImage = makeImage(width, height);
		String animeList = "";
		boolean grabAnimeList = true;
		File file = new File(System.getProperty("user.dir") + location + "/cache/timeout");
		if(file.isFile() && file.canRead()){
			String timeout = "0";
			try {
				timeout = FileUtils.readFileToString(file);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			long timeMillis = System.currentTimeMillis() - Long.parseLong(timeout, 10);
			//System.out.println(TimeUnit.MILLISECONDS.toMinutes(timeMillis));
			if(TimeUnit.MILLISECONDS.toMinutes(timeMillis) < 12){
				File fileAnime = new File(System.getProperty("user.dir") + location + "/cache/anime.xml");
				if(fileAnime.isFile() && fileAnime.canRead()){
					grabAnimeList = false;
				}
			}
		}

		if(grabAnimeList){
			try {
				animeList = Http.getAnimeList(username, "rw");
				System.out.println("Grabbed Anime List");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				FileUtils.writeStringToFile(new File(System.getProperty("user.dir") + location + "/cache/timeout"), Long.toString(System.currentTimeMillis()));
				if(animeList == null){
					System.out.println("Anime List was null, must have encountered an error. Terminating...");
					System.exit(0);
				}
				FileUtils.writeStringToFile(new File(System.getProperty("user.dir") + location + "/cache/anime.xml"), animeList);
				System.out.println("Saved Anime List");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			animeList = FileUtils.readFileToString(new File(System.getProperty("user.dir") + location + "/cache/anime.xml"));
			rss = new RSS(animeList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		init = true;
	}
	/**
	 * Loads the anime thumbnails to be processed
	 * @param animeID
	 */
	public BufferedImage loadAnimeThumbnail(int animeID){
		BufferedImage image = null;
		File file = new File(System.getProperty("user.dir") + "/cache/thumbnails/"+rss.getAnimeId(animeID)+"t.jpg");
		if(file.isFile() && file.canRead()){//check if the font is in the font
			try {
				image = ImageIO.read(new File(System.getProperty("user.dir") + "/cache/thumbnails/"+rss.getAnimeId(animeID)+"t.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{//cache not found...download
			try {
				String url = Http.getAnimeImageUrl(Integer.parseInt(rss.getAnimeId(animeID)));
				if(Http.downloadImage(url+"t.jpg", "/cache/thumbnails/"+rss.getAnimeId(animeID)+"t.jpg")){//if downloaded
					image = ImageIO.read(new File(System.getProperty("user.dir") + "/cache/thumbnails/"+rss.getAnimeId(animeID)+"t.jpg"));
					System.out.println("Downloaded "+rss.getAnimeId(animeID)+".jpg");
				}
			}
			catch (NumberFormatException | FailingHttpStatusCodeException| IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}
	/**
	 * Loads a image from file
	 * @param filename
	 */
	public BufferedImage loadImage(String filename){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(System.getProperty("user.dir") + location + "/images/"+filename));
			//image = convertColorModel(image, BufferedImage.TYPE_INT_ARGB);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	/**
	 * Creates a BufferedImage of an entire layer of a single color
	 * @param rgb
	 */
	public BufferedImage makeBackground(String rgb){
		BufferedImage image = makeImage(getWidth(), getHeight());
		Graphics2D g = image.createGraphics();
		Color color;
		try{
			color = Color.decode(rgb);
		}
		catch(NumberFormatException e){
			color = Color.black;
		}
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.dispose();
		return image;
	}
	/**
	 * Creates BufferedImage of Anime Episodes
	 * @param id the Anime order number
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align left, center, or right
	 * @param angdeg degrees to rotate clockwise
	 * @param style style 1: 3/54 2: 3 of 54 3: 3 - 54 4: 3 5: 54
	 */
	public BufferedImage makeEpisodes(int id,int x,int y, TextFont textFont, String align, int angdeg, int style){
		String text = rss.getEpisodes(id, style);
		return makeText(text, x, y, textFont, align, angdeg);
	}
	/**
	 * Creates blank transparent BufferedImage
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage makeImage(int width, int height){
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	/**
	 * Creates BufferedImage Selected Anime's viewing status
	 * @param id
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align
	 * @param angdeg
	 */
	public BufferedImage makeStatus(int id, int x, int y, TextFont textFont, String align, int angdeg){
		String desc = rss.getStatus(id);
		return makeText(desc,x,y, textFont,align, angdeg);
	}
	/**
	 * Creates transparent image with text composited. Total image will be the same as the Signature.
	 * Allows text to be filtered, then later to added to Signature with addImage()
	 * @param text
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align left, center, or right
	 * @param angdeg degrees to rotate clockwise
	 */
	public BufferedImage makeText(String text,int x,int y, TextFont textFont, String align, double angdeg){
		BufferedImage image = makeImage(getWidth(), getHeight());
		if(text.length() > 0){
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			Font font = textFont.getFont();
			g.setFont(font);  //setting font of surface
			FontRenderContext frc = g.getFontRenderContext();
			TextLayout layout = new TextLayout(text, font, frc);
			//getting width & height of the text
			double sw = layout.getBounds().getWidth();
			//getting original transform instance
			AffineTransform saveTransform=g.getTransform();
			g.setColor(textFont.getColor());
			AffineTransform affineTransform = new AffineTransform();
			//rotate with the anchor point as the mid of the text
			int placeX = x;
			if(align.equals("center")){
				placeX = (int)(-sw/2)+x;
			}
			else if(align.equals("right")){
				placeX = (int) (x - sw);
			}
			affineTransform.rotate(Math.toRadians(angdeg), x, y);//at left, change for center/right
			g.setTransform(affineTransform);
			g.drawString(text,placeX,y);
			g.setTransform(saveTransform); //restoring original transform
			g.dispose();
		}
		return image;
	}
	/**
	 * Creates BufferedImage of Date of update on image - Style To Be Changed
	 * @param id
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align
	 * @param angdeg
	 */
	@SuppressWarnings("deprecation")
	public BufferedImage makeTime(int id,int x,int y, TextFont textFont, String align, int angdeg){
		Date date = rss.getDate(id);
		BufferedImage image = null;
		if(date != null){
			image = makeText(date.toLocaleString(),x,y, textFont,align, angdeg);
		}
		return image;
	}
	/**
	 * Creates BufferedImage of the Anime Title
	 * @param id the Anime order number
	 * @param x
	 * @param y
	 * @param textFont
	 * @param align left, center, or right
	 * @param angdeg degrees to rotate clockwise
	 * @param airType if should display the tpye of show (TV/Movie/OVA)
	 * @param maxLength the maximum number of characters the title is
	 */
	public BufferedImage makeTitle(int id, int x, int y, TextFont textFont, String align, int angdeg, boolean airType, int maxLength){
		String title = rss.getTitle(id);
		if(!airType){
			int subStringLoc = 0;
			if(title.endsWith(" - TV")){subStringLoc = 5;}
			else if(title.endsWith(" - Movie")){subStringLoc = 8;}
			else if(title.endsWith(" - OVA")){subStringLoc = 6;}
			else if(title.endsWith(" - Special")){subStringLoc = 10;}
			if(subStringLoc > 0){title = title.substring(0, (title.length()-subStringLoc));}
		}
		if(title.length() > maxLength){title = title.substring(0, maxLength)+"...";}
		return makeText(title,x,y, textFont,align, angdeg);
	}
	/**
	 * Creates a font for use in making text
	 * @param fontName System font name or filename in fonts folder
	 * @param size
	 */
	public TextFont newFont(String fontName, int size){
		return newFont(fontName, "plain", size, "FFFFFF");
	}
	/**
	 * Creates a font for use in making text
	 * @param fontName System font name or filename in fonts folder
	 * @param size
	 * @param rgbColor e.g. #23CD2F
	 */
	public TextFont newFont(String fontName, int size, String rgbColor){
		return newFont(fontName, "plain", size, rgbColor);
	}
	/**
	 * Creates a font for use in making text
	 * @param fontName System font name or filename in fonts folder
	 * @param style plain, bold, italic
	 * @param size
	 * @param rgbColor e.g. #23CD2F
	 */
	public TextFont newFont(String fontName, String style, int size, String rgbColor){
		return new TextFont(fontName, style, size, rgbColor, location);
	}
	/**
	 * Saves the signature as an image
	 * @param saveTo filename
	 */
	public void saveSignature(String saveTo){
		String fileType = "png";
		if(saveTo.endsWith(".jpg") || saveTo.endsWith(".jpeg")){
			fileType = "jpg";
		}
		else if(saveTo.endsWith(".bmp")){
			fileType = "bmp";
		}
		else if(saveTo.endsWith(".wbmp")){
			fileType = "wbmp";
		}
		else if(saveTo.endsWith(".gif")){
			fileType = "gif";
		}
		saveSignature(saveTo, fileType);
	}
	/**
	 * Saves the signature as an image
	 * @param saveTo filename
	 * @param fileType jpg,png,bmp, ect
	 */
	public void saveSignature(String saveTo, String fileType){
		String saveLoc = location;
		if(saveLoc.length()>0){saveLoc = saveLoc.substring(1) + "/";}
		try {
			File outputfile = new File(saveLoc + saveTo);
			ImageIO.write(sigImage, fileType, outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sets the entire signature image
	 * @param image
	 */
	public void setSigImage(BufferedImage image){
		this.sigImage = image;
	}
	/**
	 * Attempts to upload the current image to a FTP server
	 * @param domain
	 * @param saveTo path on server
	 * @param user
	 * @param password
	 */
	public void uploadToFTP(String domain, String saveTo, String user, String password){
		Http.uploadToFTP(domain, saveTo, user, password, sigImage);
		System.out.println("Attempted to uploaded image to "+saveTo);
	}
}
