package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.Scanner;

public class Test {
	
	public void process(String filename){
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
			String line;
			while((line=br.readLine())!=null){
				if(line.isEmpty()) continue;
				System.out.println(line);
				String name="";
				String message="";
				int index=line.indexOf(" - ");
				if(index<0) continue;
				String user=line.substring(0,index);
				String tweet=line.substring(index+3);
				System.out.println(tweet);
				if(!tweet.isEmpty()){
					Scanner scanner=new Scanner(tweet);
					scanner.useDelimiter(" ");
					name=scanner.next();
					if(name.equals("RT")){
						if(scanner.hasNext()) name=scanner.next();
						else name="";
						if(!name.isEmpty()) 
							name=name.substring(0,name.length()-1);
					}
					if(!name.startsWith("@")||name.startsWith("\"@")){
						message=name;
						name=null;
					}
					if(scanner.hasNext()){
						message+=scanner.nextLine();
					}
					scanner.close();
					//System.out.println(user+" + "+name+" + "+message);
				}		
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Test t=new Test();
		t.process("output.txt");
	}
	
}
