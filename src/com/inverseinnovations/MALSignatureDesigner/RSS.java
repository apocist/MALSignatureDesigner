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

			anime.put("title", "Boku wa Tomodachi ga Sukunai - TV");
			anime.put("id", "10719");
			anime.put("description", "Plan to Watch - 0 of 12 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "Gintama - TV");
			anime.put("id", "918");
			anime.put("description", "Plan to Watch - 0 of 201 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "High School DxD 3rd Season - TV");
			anime.put("id", "24703");
			anime.put("description", "Plan to Watch - 0 of ? episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "Chuunibyou demo Koi ga Shitai! - TV");
			anime.put("id", "14741");
			anime.put("description", "Completed - 12 of 12 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "Baccano! - TV");
			anime.put("id", "2251");
			anime.put("description", "Completed - 13 of 13 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "K: Missing Kings - Movie");
			anime.put("id", "16904");
			anime.put("description", "Plan to Watch - 0 of 1 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "Durarara!! - TV");
			anime.put("id", "6746");
			anime.put("description", "Completed - 24 of 24 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "Kara no Kyoukai: Epilogue - Special");
			anime.put("id", "6954");
			anime.put("description", "Plan to Watch - 0 of 1 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "Fate/Prototype - OVA");
			anime.put("id", "12565");
			anime.put("description", "Completed - 1 of 1 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "Chuunibyou demo Koi ga Shitai!: Kirameki no... Slapstick Noel - Special");
			anime.put("id", "16934");
			anime.put("description", "Completed - 1 of 1 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);

			anime.put("title", "Chuunibyou demo Koi ga Shitai! Ren - TV");
			anime.put("id", "18671");
			anime.put("description", "Watching - 10 of 12 episodes");
			anime.put("pubDate", "Mon, 28 Jul 2014 18:22:30 -0700");
			items.add(anime);
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
