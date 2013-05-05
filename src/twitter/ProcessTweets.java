package twitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProcessTweets {
	
	public void process(String input, String output){
		try {
			BufferedReader in=new BufferedReader(new FileReader(input));
			BufferedWriter out=new BufferedWriter(new FileWriter(output,false));
			String line=null;
			//out.write("Start Tweets:");
			while((line=in.readLine())!=null){
				if(line.isEmpty()) continue;
				line=line.replaceAll("[?]+", "?");
				line=line.replaceAll("[ ]+"," ");
				boolean good=true;
				for(char c : line.toCharArray()){
					if(c>0xFF){
						good=false;
						break;
					}
				}
				if(good){
					if(line.charAt(0)=='@') out.newLine();
					out.write(line);
				}
			}
			in.close();
			out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
