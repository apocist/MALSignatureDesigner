package com.inverseinnovations.MALSignatureDesigner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RSS {
	ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
	public RSS(String rss){
		if(rss != null){
			rss.replaceAll("\n", "");
			rss.replaceAll("\t", "");

			Pattern p = Pattern.compile("<item>(.*?)</item>", Pattern.DOTALL);
			Matcher m = p.matcher(rss);
			Pattern titleParse = Pattern.compile("<title>(.*?)</title>", Pattern.DOTALL);
			//Pattern linkParse = Pattern.compile("<link>(.*?)</link>", Pattern.DOTALL);
			//Pattern linkParse = Pattern.compile("<link>http://myanimelist.net/anime/([0-9]+)/", Pattern.DOTALL);
			Pattern linkParse = Pattern.compile("<link>/anime/([0-9]+)/", Pattern.DOTALL);
			Pattern descParse = Pattern.compile("<description>(.*?)</description>", Pattern.DOTALL);
			Pattern timeParse = Pattern.compile("<pubDate>(.*?)</pubDate>", Pattern.DOTALL);
			while(m.find()){
				HashMap<String, String> anime = new HashMap<String, String>();
				Matcher animeItem = titleParse.matcher(m.group(1));
				if(animeItem.find()){
					anime.put("title", animeItem.group(1).trim());
				}
				animeItem = linkParse.matcher(m.group(1));
				if(animeItem.find()){
					anime.put("id", animeItem.group(1).trim());
				}
				animeItem = descParse.matcher(m.group(1));
				if(animeItem.find()){
					anime.put("description", animeItem.group(1).trim());
				}
				animeItem = timeParse.matcher(m.group(1));
				if(animeItem.find()){
					anime.put("pubDate", animeItem.group(1).trim());
				}
				items.add(anime);
			}
		}
		else{//if empy make a dummy RSS
			HashMap<String, String> anime = new HashMap<String, String>();
			anime.put("title", "Accel World - TV");
			anime.put("id", "11759");
			anime.put("description", "Watching - 5 of 24 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			HashMap<String, String> anime2 = new HashMap<String, String>();
			anime2.put("title", "Boku wa Tomodachi ga Sukunai - TV");
			anime2.put("id", "10719");
			anime2.put("description", "Plan to Watch - 0 of 12 episodes");
			anime2.put("pubDate", "Mon, 28 Jul 2014 18:21:30 -0700");
			items.add(anime2);

			HashMap<String, String> anime3 = new HashMap<String, String>();
			anime3.put("title", "Gintama - TV");
			anime3.put("id", "918");
			anime3.put("description", "Plan to Watch - 0 of 201 episodes");
			anime3.put("pubDate", "Mon, 28 Jul 2014 17:21:10 -0700");
			items.add(anime3);

			HashMap<String, String> anime4 = new HashMap<String, String>();
			anime4.put("title", "High School DxD 3rd Season - TV");
			anime4.put("id", "24703");
			anime4.put("description", "Plan to Watch - 0 of ? episodes");
			anime4.put("pubDate", "Mon, 28 Jul 2014 17:20:30 -0700");
			items.add(anime4);

			HashMap<String, String> anime5 = new HashMap<String, String>();
			anime5.put("title", "Chuunibyou demo Koi ga Shitai! - TV");
			anime5.put("id", "14741");
			anime5.put("description", "Completed - 12 of 12 episodes");
			anime5.put("pubDate", "Mon, 28 Jul 2014 17:20:30 -0700");
			items.add(anime5);

			HashMap<String, String> anime6 = new HashMap<String, String>();
			anime6.put("title", "Baccano! - TV");
			anime6.put("id", "2251");
			anime6.put("description", "Completed - 13 of 13 episodes");
			anime6.put("pubDate", "Mon, 28 Jul 2014 16:19:30 -0700");
			items.add(anime6);

			HashMap<String, String> anime7 = new HashMap<String, String>();
			anime7.put("title", "K: Missing Kings - Movie");
			anime7.put("id", "16904");
			anime7.put("description", "Plan to Watch - 0 of 1 episodes");
			anime7.put("pubDate", "Mon, 28 Jul 2014 16:22:30 -0700");
			items.add(anime7);

			HashMap<String, String> anime8 = new HashMap<String, String>();
			anime8.put("title", "Durarara!! - TV");
			anime8.put("id", "6746");
			anime8.put("description", "Completed - 24 of 24 episodes");
			anime8.put("pubDate", "Mon, 28 Jul 2014 15:22:30 -0700");
			items.add(anime8);

			HashMap<String, String> anime9 = new HashMap<String, String>();
			anime9.put("title", "Kara no Kyoukai: Epilogue - Special");
			anime9.put("id", "6954");
			anime9.put("description", "Plan to Watch - 0 of 1 episodes");
			anime9.put("pubDate", "Mon, 28 Jul 2014 15:22:30 -0700");
			items.add(anime9);

			HashMap<String, String> anime10 = new HashMap<String, String>();
			anime10.put("title", "Fate/Prototype - OVA");
			anime10.put("id", "12565");
			anime10.put("description", "Completed - 1 of 1 episodes");
			anime10.put("pubDate", "Mon, 28 Jul 2014 14:22:30 -0700");
			items.add(anime10);

			HashMap<String, String> anime11 = new HashMap<String, String>();
			anime11.put("title", "Chuunibyou demo Koi ga Shitai!: Kirameki no... Slapstick Noel - Special");
			anime11.put("id", "16934");
			anime11.put("description", "Completed - 1 of 1 episodes");
			anime11.put("pubDate", "Mon, 28 Jul 2014 12:22:30 -0700");
			items.add(anime11);

			HashMap<String, String> anime12 = new HashMap<String, String>();
			anime12.put("title", "Chuunibyou demo Koi ga Shitai! Ren - TV");
			anime12.put("id", "18671");
			anime12.put("description", "Watching - 10 of 12 episodes");
			anime12.put("pubDate", "Mon, 28 Jul 2014 11:22:30 -0700");
			items.add(anime12);
		}
	}
	/**
	 * Gets Anime Link to MyAnimeList
	 * @param num order number
	 */
	public String getAnimeId(int num){
		return items.get(num-1).get("id");
	}
	/**
	 * Returns parsed Date object from getTime for reading
	 * @param num order number
	 */
	public Date getDate(int num){
		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
		Date date = null;
		try {
			date = formatter.parse(getTime(num));
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * Gets the raw Anime Description from the RSS feed (Watching - 2 of 16 episodes)
	 * @param num order number
	 */
	public String getDesc(int num){
		return items.get(num-1).get("description");
	}
	/**
	 * Gets the Episode on of currently selected Anime formatted with style
	 * @param num order number
	 * @param style style 1: 3/54 2: 3 of 54
	 * @return
	 */
	public String getEpisodes(int num, int style){
		String desc = getDesc(num);
		String episode = "?";
		String total = "?";
		Pattern p = Pattern.compile(" - ([0-9]{1,3}) of ([0-9]{1,3})", Pattern.DOTALL);
		Matcher m = p.matcher(desc);
		if(m.find()){
			episode = m.group(1);
			total = m.group(2);
		}
		String text = episode+"/"+total;;
		switch(style){
		case 1:
			text = episode+"/"+total;
			break;
		case 2:
			text = episode+" of "+total;
			break;
		}
		return text;
	}
	/**
	 * Gets the raw Anime Time when it was updated from the RSS feed
	 * @param num order number
	 */
	public String getTime(int num){
		return items.get(num-1).get("pubDate");
	}
	/**
	 * Gets the raw Anime title from the RSS feed
	 * @param num order number
	 */
	public String getTitle(int num){
		return items.get(num-1).get("title");
	}
	/**
	 * Gets the current watching status of selected anime
	 * @param num order number
	 */
	public String getStatus(int num){
		String desc = getDesc(num);
		String text = "";
		if(desc.startsWith("W")){text = "Watching";}
		else if(desc.startsWith("P")){text = "To Be Watched";}
		else if(desc.startsWith("C")){text = "Finished";}
		else if(desc.startsWith("D")){text = "Dropped";}
		else if(desc.startsWith("O")){text = "Stopped Watching";}
		else if(desc.startsWith("R")){text = "Re-Watching";}
		return text;
	}
}
