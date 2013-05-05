package features;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentFeatures {
	String filename=null;
	HashMap<String, ArrayList<String>> tweets=null;
	public ContentFeatures(String input){
		filename=input;
		readbyUser();
	}
	
	private void readbyUser(){
		tweets=new HashMap<String, ArrayList<String>>();
		try {
			BufferedReader in=new BufferedReader(new FileReader(filename));
			String line=null;
			//int count=0;
			while((line=in.readLine())!=null){
				//System.out.println(++count);
				if(line.isEmpty()) continue;
				int i=0;
				for(char c : line.toCharArray())
					if(c!=' ') ++i;
					else break;
				if(i+3>line.length()) continue;
				String user=line.substring(0, i);
				String tweet=line.substring(i+3);
				if(tweets.containsKey(user)){
					ArrayList<String> list=tweets.get(user);
					list.add(tweet);
				}else{
					ArrayList<String> list=new ArrayList<String>();
					list.add(tweet);
					tweets.put(user, list);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean sameTweet(String tweet1, String tweet2){
		if(tweet1.length()!=tweet2.length()) return false;
		return tweet1.toLowerCase().equals(tweet2.toLowerCase());
	}
	
	private HashMap<String,Integer> duplicate(){
		HashMap<String,Integer> duplicates=new HashMap<String, Integer>();
		for(String user : tweets.keySet()){
			int dup=0;
			ArrayList<String> list=tweets.get(user);
			for(int i=0; i<list.size(); ++i)
				for(int j=i+1; j<list.size(); ++j){
					if(sameTweet(list.get(i),list.get(j)))
						dup++;
				}
			if(dup>0) duplicates.put(user, dup);
		}
		return duplicates;
	}
	
	private HashMap<String, ArrayList<String>> httpLinks(){
		HashMap<String,ArrayList<String>> links=new HashMap<String, ArrayList<String>>();
		Pattern p=Pattern.compile("http://[-._/?a-z0-9A-Z]+");
		Matcher m=null;
		for(String user : tweets.keySet()){
			ArrayList<String> list=tweets.get(user);
			ArrayList<String> linksList=new ArrayList<String>();
			for(String tweet : list){
				m=p.matcher(tweet);
				while(m.find())
					linksList.add(m.group());
			}
			if(linksList.size()>0) links.put(user, linksList);
		}
		return links;
	}
	
	public void getFeatures(){
		HashMap<String,Integer> dups=duplicate();
		System.out.println(dups.size());
		HashMap<String, ArrayList<String>> links=httpLinks();
		System.out.println(links.size());
	}
	
}
