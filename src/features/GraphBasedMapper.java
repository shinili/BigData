package features;

import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class GraphBasedMapper extends Mapper<LongWritable, Text, Text, ArrayWritable>{
	
	@Override
	public void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException{
		String line=value.toString();
		String name="";
		String message="";
		String[] pieces=line.split(" - ");
		String user=pieces[0];
		if(!pieces[1].isEmpty()){
			Scanner scanner=new Scanner(pieces[1]);
			scanner.useDelimiter(" ");
			name=scanner.next();
			if(name.equals("RT")){
				name=scanner.next();
				name=name.substring(0,name.length()-1);
				message="RT:";
			}
			message+=scanner.nextLine();
			if(!name.startsWith("@")){
				message=name+message;
				name="nil";
			}
			scanner.close();
		}
		ArrayWritable array=new ArrayWritable(Text.class);
		Text[] text=new Text[2];
		text[0]=new Text(name);
		text[1]=new Text(message);
		array.set(text);
		context.write(new Text(user), array);		
	}

}
