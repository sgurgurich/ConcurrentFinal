package Threads;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.ImageIO;

public class DownloadThread implements Runnable{
	
	private String url_str;
	private ArrayBlockingQueue<BufferedImage> converter_queue;
	 
	// takes a target url and the converter queue
	public DownloadThread(String url_str, ArrayBlockingQueue<BufferedImage> converter_queue){
		this.url_str = url_str;
		this.converter_queue = converter_queue;
	}
	
	// downloads the file and adds it to converter_queue as a BufferedImage
	public void run(){
		BufferedImage img = null;
		try {
			URL url = new URL(url_str);
			img = ImageIO.read(url);
			converter_queue.put(img);
		} catch (IOException e) {
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		    //e.printStackTrace();
		}

	}
	
	// overloaded to stop this thread if there's an issue
	public void stop(){
		
	}

}
