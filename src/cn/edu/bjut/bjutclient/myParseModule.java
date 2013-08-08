package cn.edu.bjut.bjutclient;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class myParseModule {
	Document parseInfo;
	public static final String[] INFO = { "448", "449", "454", "448", "450",
			"451", "2822", "460" };
	public static final String[] TITLE = { "校发通知", "院部处通知", "院部处工作信息",
			"迎新离校工作通知", "科技动态 ", "海报", "公示公告", "教学通告" };

	public myParseModule(String html) {
		parseInfo = Jsoup.parse(html);
	}

	public ArrayList<String> getLink(String subject) {
		Elements tempElements = parseInfo.select("div#pf" + subject
				+ " > div.portletFrame > div.portletContent > ul#blf" + subject
				+ " > li > a.rss-title");
		ArrayList<String> strings = new ArrayList<String>();
		for (Element tempElement : tempElements) {
			strings.add(tempElement.attr("href"));
		}
		return strings;
	}

	public ArrayList<String> getTitle(String subject) {
		Elements tempElements = parseInfo.select("div#pf" + subject
				+ " > div.portletFrame > div.portletContent > ul#blf" + subject
				+ " > li > a.rss-title");
		ArrayList<String> strings = new ArrayList<String>();
		for (Element tempElement : tempElements) {
			strings.add(tempElement.attr("title"));
		}
		return strings;
	}

	public static String passageParser(String html) {
		Document temp = Jsoup.parse(html);
		HtmlToPlainText parser2 = new HtmlToPlainText();
		return (parser2.getPlainText(temp.select("div.bulletin-content").get(0)));
	}
}
