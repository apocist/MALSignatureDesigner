package com.inverseinnovations.MALSignatureDesigner;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;

/**
 * Class holds all interent based methods
 */
public class Http {
	/**
 	 * Retrieves the anime history for inputted user
 	 * @param username
 	 * @param rssType rw
 	 * @return XML as String
 	 * @throws FailingHttpStatusCodeException
 	 * @throws MalformedURLException
 	 * @throws IOException
 	 */
 	public static String getAnimeList(String username, String rssType) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		Page page = webClient.getPage("http://myanimelist.net/rss.php?type="+rssType+"&u="+username);
		String content = page.getWebResponse().getContentAsString();
		webClient.closeAllWindows();
		if(content.startsWith("<?xml")){//grabbed the xml file
			return content;
		}
		return null;
	}
	/**
	 * Returns the Url of image for the animeId provided. The file type extension is not included.
	 * @param animeId
	 * @return null if not found
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String getAnimeImageUrl(int animeId) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		String theReturn = null;
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setThrowExceptionOnScriptError(false);//Will give alot of errors
		new IgnoreRandomJScripts(webClient);
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.SEVERE);//to ignore those errors
		try{
			Page page = webClient.getPage("http://myanimelist.net/anime.php?id="+animeId);
			if(page.isHtmlPage()){
				Pattern p = Pattern.compile("http://cdn.myanimelist.net/images/anime/([0-9]+)/([0-9]+).jpg");
				Matcher m = p.matcher(page.getWebResponse().getContentAsString());
				if(m.find()){
					if(m.groupCount() == 2){
						theReturn = "http://cdn.myanimelist.net/images/anime/"+m.group(1)+"/"+m.group(2);
					}
				}
			}
		}
		catch(com.gargoylesoftware.htmlunit.ScriptException e){}
		webClient.closeAllWindows();
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.ALL);
		return theReturn;
	}
	/**
	 * Downloads and saves Jpeg from url and stores as file
	 * @param url
	 * @param saveLoc
	 * @return true on success
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static boolean downloadImage(String url, String saveLoc) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		if(url != null && saveLoc != null){
			if(!url.isEmpty() && !saveLoc.isEmpty()){
				final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
				Page page = webClient.getPage(url);
				webClient.closeAllWindows();
				if(page.getWebResponse().getContentType().equals("image/jpeg")){//grabbed the xml file
					InputStream in = page.getWebResponse().getContentAsStream();
					OutputStream out; out = new FileOutputStream(new File(System.getProperty("user.dir") + saveLoc));
					IOUtils.copy(in,out);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Wrapper for WebClient, disbales slow JS ads when viewing webpage
	 */
	private static class IgnoreRandomJScripts extends FalsifyingWebConnection{
		public IgnoreRandomJScripts(WebClient webClient) throws IllegalArgumentException{
			super(webClient);
		}
		@Override
		public WebResponse getResponse(WebRequest request) throws IOException {
			WebResponse response=super.getResponse(request);
			if(response.getWebRequest().getUrl().toString().endsWith("dom-drag.js")){
				return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
			}
			else if(response.getWebRequest().getUrl().toString().endsWith("jquery.min.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("ga.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("google_ads.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("lidar.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("vce_st.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("post-widget.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("hover.v5.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("jquery.fancybox.pack.v1.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("forum.v12.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("animeinfo.v9.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("myanimelist.v18.js")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("4834_US.php")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().startsWith("http://n4403ad.doubleclick.net")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			else if(response.getWebRequest().getUrl().toString().endsWith("geo.php?dynamic=0&website_id=4834")){
	            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
	        }
			return super.getResponse(request);
		}
	}
	public static void uploadToFTP(String domain, String saveTo, String user, String password, BufferedImage image){
		FTPClient client = new FTPClient();
	    InputStream fis = null;

	    try {
	        client.connect(domain);
	        client.login(user, password);
	        client.setFileType(FTP.BINARY_FILE_TYPE);
	    	client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
	    	client.enterLocalPassiveMode();
	        fis = imageToStream(image);

	        client.storeFile(saveTo, fis);
	        fis.close();
	        client.logout();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static InputStream imageToStream(BufferedImage image){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(os.toByteArray());
	}
}
