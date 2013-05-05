package hdfs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSWriter {
	
	private String filename;
	private BufferedWriter bw;
	private boolean overwrite;
	
	public HDFSWriter(String filename){
		this.filename=filename;
		this.overwrite=true;
	}

	public HDFSWriter(String filename, boolean overwrite){
		this.filename=filename;
		this.overwrite=overwrite;
	}
	
	public void close(){
		closeWriter();
	}
	
	private boolean openWriter(){
		if(bw!=null) return true;
		Configuration conf=new Configuration();
		try{
			FileSystem 	fs=FileSystem.get(URI.create(filename),conf);
			OutputStream out=fs.create(new Path(filename),overwrite);
			bw=new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			bw=null;
			return false;
		}
		return true;
	}
	
	private boolean closeWriter(){
		if(bw==null) return true;
		try {
			bw.close();
			bw=null;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void newLine(){
		if(openWriter()){
			try { 
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void write(String str){
		if(openWriter()){
			try {
				bw.write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
