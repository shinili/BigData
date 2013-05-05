package hdfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSReader {
	
	private String filename;
	private BufferedReader br;
	
	public HDFSReader(String filename){
		this.filename=filename;
	}
	
	public void close(){
		closeReader();
	}
	
	private boolean openReader(){
		if(br!=null) return true;
		Configuration conf=new Configuration();
		try{
			FileSystem 	fs=FileSystem.get(URI.create(filename),conf);
			InputStream in=fs.open(new Path(filename));
			br=new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			br=null;
			return false;
		}
		return true;
	}
	
	private boolean closeReader(){
		if(br==null) return true;
		try {
			br.close();
			br=null;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public String readLine(){
		if(openReader()){
			try {
				return br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
