package com.inverseinnovations.MALSignatureDesigner;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.jhlabs.image.*;
//TODO add Marble, Ripple
public class Filter {
	/**A filter which wraps an image around a circular arc.
	 * @param image
	 * @param angle the angle of the arc. default 0
	 * @param spreadAngle the spread angle of the arc. default 3.14
	 * @param radius the radius of the effect. default 10
	 * @param height the height of the arc. default 20
	 * @param centreX centre of the effect in the X direction as a proportion of the image size. default 0.5 (middle)
	 * @param centerY centre of the effect in the Y direction as a proportion of the image siz. default 0.5 (middle)
	 */
	public BufferedImage arc(BufferedImage image,double angle,double spreadAngle,double radius,double height,double centreX,double centerY){
		BufferedImage filteredImage = null;
		if(image != null){
			CircleFilter filter = new CircleFilter();
			filter.setAngle((float) angle);filter.setSpreadAngle((float) spreadAngle);filter.setRadius((float) radius);filter.setCentreX((float) centreX);filter.setHeight((float) height);
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately
	 * and a number of iterations can be given which allows an approximation to Gaussian blur.
	 * @param image image to filter
	 * @param hRadius the radius of blur
     * @param iterations the number of time to iterate the blur
	 */
	public BufferedImage blurGaussian(BufferedImage image, int radius, int iterations){
		BufferedImage filteredImage = null;
		if(image != null){
			BoxBlurFilter filter = new BoxBlurFilter();
			filter.setRadius(radius);
			filter.setIterations(iterations);
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately
	 * and a number of iterations can be given which allows an approximation to Gaussian blur.
	 * @param image image to filter
	 * @param hRadius the horizontal radius of blur
     * @param vRadius the vertical radius of blur
     * @param iterations the number of time to iterate the blur
	 */
	public BufferedImage blurGaussian(BufferedImage image, int hRadius, int vRadius, int iterations){
		BufferedImage filteredImage = null;
		if(image != null){
			BoxBlurFilter filter = new BoxBlurFilter(hRadius, vRadius, iterations);
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which averages the 3x3 neighbourhood of each pixel, providing a simple blur.
	 * @param image image to filter
	 */
	public BufferedImage blurSimple(BufferedImage image){
		BufferedImage filteredImage = null;
		if(image != null){
			AverageFilter filter = new AverageFilter();
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which performs a box blur with a different blur radius at each pixel.
	 * @param image
	 * @param radius the size of the blur.
	 * @param iterations the number of iterations the blur is performed
	 */
	public BufferedImage blurVariable(BufferedImage image, int radius){
		BufferedImage filteredImage = null;
		if(image != null){
			VariableBlurFilter filter = new VariableBlurFilter();
			filter.setRadius(radius);
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which performs a box blur with a different blur radius at each pixel.
	 * @param image
	 * @param hRadius the horizontal size of the blur.
	 * @param vRadius the vertical size of the blur.
	 * @param iterations the number of iterations the blur is performed
	 */
	public BufferedImage blurVariable(BufferedImage image, int hRadius, int vRadius, int iterations){
		BufferedImage filteredImage = null;
		if(image != null){
			VariableBlurFilter filter = new VariableBlurFilter();
			filter.setHRadius(hRadius);
			filter.setVRadius(vRadius);
			filter.setIterations(iterations);
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which simulates chrome.
	 * @param image image to filter
	 */
	public BufferedImage chrome(BufferedImage image){
		BufferedImage filteredImage = null;
		if(image != null){
			ChromeFilter filter = new ChromeFilter();
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**Draws imageToAdd on top of imageMain.
	 * To be used when creating a larger image and filtering together
	 * @param imageMain the image to alter
	 * @param imageToAdd the image being added
	 * @param x x-coord relative to imageMain
	 * @param y y-coord relative to imageMain
	 */
	public BufferedImage composite(BufferedImage imageMain, BufferedImage imageToAdd, int x, int y){
		Graphics2D g = imageMain.createGraphics();
		g.drawImage(imageToAdd, x,y, null);
		g.dispose();
		return imageMain;
	}
	/**A simple embossing filter.
	 * @param image image to filter
	 */
	public BufferedImage emboss(BufferedImage image){
		BufferedImage filteredImage = null;
		if(image != null){
			BumpFilter filter = new BumpFilter();
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which adds Gaussian blur to an image, producing a glowing effect.
	 * @param image
	 * @param amount the amount of glow. negatives result in darkening(inner shadow?)
	 * @return
	 */
	public BufferedImage glowInner(BufferedImage image, double amount){
		BufferedImage filteredImage = null;
		if(image != null){
			GlowFilter filter = new GlowFilter();
			filter.setAmount((float) amount);
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A Filter which produces the effect of looking into a kaleidoscope(Great for images/text with alpha).
	 * @param image
	 * @param sides the number of sides of the kaleidoscope. default 3
	 * @param radius the radius of the effect. default 0
	 * @param angle angle of the kaleidoscope. default 0
	 * @param angle2 secondary angle of the kaleidoscope. default 0
	 * @param centreX centre of the effect in the X direction as a proportion of the image size. default 0.5 (middle)
	 * @param centreY centre of the effect in the Y direction as a proportion of the image size. default 0.5 (middle)
	 * @return
	 */
	public BufferedImage kaleidoscope(BufferedImage image, int sides, double radius, double angle, double angle2,double centreX, double centreY){
		BufferedImage filteredImage = null;
		if(image != null){
			KaleidoscopeFilter filter = new KaleidoscopeFilter();
			filter.setSides(sides);filter.setRadius((float) radius);filter.setAngle((float) angle);filter.setAngle2((float) angle2);filter.setCentreX((float) centreX);filter.setCentreY((float) centreY);
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter thats sets the opacity(transparency) of an image
	 * @param image
	 * @param opacity 0-100% 100 being opaque
	 */
	public BufferedImage opacity(BufferedImage image,double opacity){
		BufferedImage filteredImage = null;
		if(image != null){
			filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = filteredImage.createGraphics();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (opacity/100)));
			g.drawImage(image, 0,0, null);
			g.dispose();
		}
		return filteredImage;
	}
	/**A filter which performs a perspective distortion on an image.
     * Coordinates are treated as if the image was a unit square, i.e. the bottom-right corner of the image is at (1, 1).
     * The filter maps the unit square onto an arbitrary convex quadrilateral or vice versa.
     * @param image the image to alter
     * @param x0 the new position of the top left corner
     * @param y0 the new position of the top left corner
     * @param x1 the new position of the top right corner
     * @param y1 the new position of the top right corner
     * @param x2 the new position of the bottom right corner
     * @param y2 the new position of the bottom right corner
     * @param x3 the new position of the bottom left corner
     * @param y3 the new position of the bottom left corner
     */
	public BufferedImage perspective(BufferedImage image, float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3){
		BufferedImage filteredImage = null;
		if(image != null){
			//place border to make transparent
			image = transparentBorder(image, 1);
			//sample the image to keep quality..X4?
			double scale = 4;
			image = rescale(image,scale);
			//distort the image
			PerspectiveFilter filter = new PerspectiveFilter((float) (x0*scale), (float) (y0*scale), (float) (x1*scale), (float) (y1*scale), (float) (x2*scale), (float) (y2*scale), (float) (x3*scale), (float) (y3*scale));//add points here
			filteredImage = filter.filter(image, null);
			//downsample back to orginal size
			filteredImage = rescale(filteredImage, 0.25);
		}
		return filteredImage;
	}
	/**Rescales the image based on mulitplicity. E.g. 0.5 = half size while 2 = double size
	 * @param image
	 * @param scale
	 * @return
	 */
	public BufferedImage rescale(BufferedImage image, double scale){
		int newWidth = (int)(image.getWidth()*scale);
		int newHeight = (int)(image.getHeight()*scale);
		Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage imageBuff = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imageBuff.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();
        return imageBuff;
	}
	/**Rotates the image by a number of degrees. Point of rotation altered by anchor point
	 * @param image
	 * @param angle degrees to rotate
	 * @param anchor center, left, right, top, bottom, topleft, topright, bottomleft, bottomright
	 */
	public BufferedImage rotate(BufferedImage image, double angle, String anchor){
		BufferedImage filteredImage = null;
		if(image != null){
			int width = image.getWidth();
			int height = image.getHeight();

			double anchorX = width / 2;
			double anchorY = height / 2;

			//rotate with the anchor point as the mid of the text
			switch(anchor){
			case "center":
				anchorX = width / 2;//center
				anchorY = height / 2;//center
				break;
			case "left":
				anchorX = 0;
				anchorY = height / 2;
				break;
			case "right":
				anchorX = width;
				anchorY = height / 2;
				break;
			case "top":
				anchorX = width / 2;
				anchorY = 0;
				break;
			case "bottom":
				anchorX = width / 2;
				anchorY = height;
				break;
			case "topleft":
				anchorX = 0;
				anchorY = 0;
				break;
			case "topright":
				anchorX = width;//right side
				anchorY = 0;//center
				break;
			case "bottomleft":
				anchorX = 0;//right side
				anchorY = height;//center
				break;
			case "bottomright":
				anchorX = width;//right side
				anchorY = height;//center
				break;
			default:
				anchorX = width / 2;//center
				anchorY = height / 2;//center
			}
			filteredImage = rotate(image, angle, anchorX, anchorY);
		}
		return filteredImage;
	}
	/**Rotates the image by a number of degrees. Point of rotation altered by anchor points
	 * @param image
	 * @param angle degrees to rotate
	 * @param anchorX
	 * @param anchorY
	 */
	public BufferedImage rotate(BufferedImage image, double angle, double anchorX, double anchorY){
		BufferedImage filteredImage = null;
		if(image != null){
			double rotationRequired = Math.toRadians(angle);

			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, anchorX, anchorY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			try{
				filteredImage = op.filter(image, null);
			}
			catch(java.awt.image.RasterFormatException e){
				System.out.println("RasterFormatException");
			}
		}
		return filteredImage;
	}
	/**A filter which draws a drop shadow based on the alpha channel of the image.
     * @param image
     */
	public BufferedImage shadow(BufferedImage image){
		BufferedImage filteredImage = null;
		if(image != null){
			ShadowFilter filter = new ShadowFilter();
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which draws a drop shadow based on the alpha channel of the image.
     * @param image
     * @param radius the radius of the shadow. default 5
     * @param xOffset the X offset of the shadow
     * @param yOffset the Y offset of the shadow
     * @param opacity the opacity of the shadow
     */
	public BufferedImage shadow(BufferedImage image, int radius, double xOffset, double yOffset, double opacity){
		BufferedImage filteredImage = null;
		if(image != null){
			ShadowFilter filter = new ShadowFilter(radius, (float) xOffset, (float) yOffset, (float) (opacity/100));
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter creates a smeared effect
	 * @param image
	 */
	public BufferedImage smear(BufferedImage image){
		BufferedImage filteredImage = null;
		if(image != null){
			SmearFilter filter = new SmearFilter();
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which solarizes an image. (Color changes)
	 * @param image
	 */
	public BufferedImage solarize(BufferedImage image){
		BufferedImage filteredImage = null;
		if(image != null){
			SolarizeFilter filter = new SolarizeFilter();
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**Generates a large sparkle effect.. experimental
	 * @param image
	 */
	public BufferedImage sparkle(BufferedImage image){
		BufferedImage filteredImage = null;
		if(image != null){
			SparkleFilter filter = new SparkleFilter();
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**A filter which simulates a lens placed over an image.
	 * @param image
	 */
	public BufferedImage sphere(BufferedImage image){
		BufferedImage filteredImage = null;
		if(image != null){
			SphereFilter filter = new SphereFilter();
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
	/**Creates a transparent border that wraps around an image
	 * @param image src
	 * @param borderSize size of border in pixels
	 */
	public BufferedImage transparentBorder(BufferedImage image, int borderSize){
		BufferedImage filteredImage = null;
		if(image != null){
			filteredImage = new BufferedImage(image.getWidth()+(borderSize*2), image.getHeight()+(borderSize*2), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = filteredImage.createGraphics();
			g.drawImage(image, borderSize,borderSize, null);
			g.dispose();
		}
		return filteredImage;
	}
	/**A filter which subtracts Gaussian blur from an image, sharpening it. (Great for after distorting)
	 * @param image
	 * @param amount effect in percentage 1-100+
	 * @return
	 */
	public BufferedImage unsharp(BufferedImage image, double amount){
		BufferedImage filteredImage = null;
		if(image != null){
			UnsharpFilter filter = new UnsharpFilter();
			filter.setAmount((float) (amount/100));
			filteredImage = filter.filter(image, null);
		}
		return filteredImage;
	}
}
