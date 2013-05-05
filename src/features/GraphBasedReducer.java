package features;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class GraphBasedReducer extends Reducer<Text, ArrayWritable, Text, MapWritable>{
	
	@Override
	public void reduce(Text key, Iterable<ArrayWritable> values, Context context) throws IOException, InterruptedException{
		//Map<String,String> map=new HashMap<String,String>();
		MapWritable map=new MapWritable();
		for(ArrayWritable array : values){
			Text[] text=(Text[]) array.get();
			//map.put(text[0].toString(), text[1].toString());
			map.put(text[0], text[1]);
		}
		context.write(key, map);

	}

}
