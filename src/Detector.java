import features.ContentFeatures;
import twitter.ProcessTweets;


public class Detector {

	public static void main(String[] args){
		//ProcessTweets pt=new ProcessTweets();
		//pt.process("data/input.txt", "data/output.txt");
		ContentFeatures cf=new ContentFeatures("data/output.txt");
		cf.getFeatures();
	}
	
}
