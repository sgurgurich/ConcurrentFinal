package Threads;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.ImageIO;

import net.jcip.annotations.ThreadSafe;


/**
 * 
 * DownloadThread is an implementation of
 * Runnable. As part of a Producer-Consumer model, 
 * an instance of DownloadThread downloads an image 
 * from the specified website(
 * http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/) 
 * and places it on a queue for a GrayScaleThread
 * to pull from.
 * 
 * @author Zach Adam
 * @author John Cutsavage
 * @author Stefan Gurgurich
 * 
 */
@ThreadSafe
public class DownloadThread implements Runnable{
	
	private String file_name;
	private ArrayBlockingQueue<ImageInfo> converter_queue;
	 
	/**
	 * 
	 * Constructor for the DownloadThread class. A file name
	 * is passed into DownloadThread to specify what image
	 * to download and then convert to grayscale. An
	 * ArrayBlockingQueue of ImageInfo objects is also
	 * passed in to provide the GrayscaleThread class
	 * images to convert.
	 * 
	 * @param file_name a String of the file name to download.
	 * @param converter_queue an ArrayBlockingQueue of ImageInfo
	 * objects used to 
	 */
	public DownloadThread(String file_name, ArrayBlockingQueue<ImageInfo> converter_queue){
		this.file_name = file_name;
		this.converter_queue = converter_queue;
	}
	
	/**
	 * Overrides Runnable.run(). A connection
	 * is established to http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/
	 * with the specified file_name, downloads the image,
	 * then places the image on the converter_queue for
	 * processing by the GrayscaleThread.
	 */
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
