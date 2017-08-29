package Threads;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.ImageIO;



public class DownloadThread implements Runnable{
	
	private String file_name;
	private ArrayBlockingQueue<ImageInfo> converter_queue;
	 
	// takes a target url and the converter queue
	public DownloadThread(String file_name, ArrayBlockingQueue<ImageInfo> converter_queue){
		this.file_name = file_name;
		this.converter_queue = converter_queue;
	}
	
	// downloads the file and adds it to converter_queue as a BufferedImage
	public void run(){
		
		BufferedImage img = null;
		try {
			
			String url_str = "http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/" + file_name;			
			URL url = new URL(url_str);
			
			img = ImageIO.read(url);
			ImageInfo temp = new ImageInfo(img, file_name);
			converter_queue.put(temp);
		} catch (IOException e) {
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		    //e.printStackTrace();
		}

		
	}
	


}
