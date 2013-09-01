package cn.edu.bjut.bjutclient;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class jwParseModule {
	private Document parseInfo;

	public jwParseModule(String html) {
		parseInfo = Jsoup.parse(html);
	}

	public String getLink(String subject) {
		return null;
	}

	public ArrayList<String> getTitle(String subject) {
		return null;
	}

	public ArrayList<ArrayList<String>> getTable() {
		Elements step1 = parseInfo.select("table#Table1 > tbody > tr");
		Elements step2;
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < 7; i++)
			result.add(new ArrayList<String>());
		for (int i = 1; i < 7 ; i += 1) {
			step2 = step1.get(i*2).select("td");
			for (int j = 1 + (i % 2); j < 8 + (i % 2); j++) {
				if (step2.get(j).text().length() > 1) {
					result.get(j-1-(i % 2)).add(step2.get(j).text());
				} else {
					result.get(j-1-(i % 2)).add("无课程");
				}
			}
		}
		return result;

	}

}
