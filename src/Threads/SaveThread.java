package Threads;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.ImageIO;

import net.jcip.annotations.ThreadSafe;

/**
 * 
 * SaveThread is an implementation of Runnable
 * that retrieves images from a queue generated
 * by a GrayscaleThread and saves
 * them to a local folder.
 * 
 * @author Zach Adam
 * @author John Cutsavage
 * @author Stefan Gurgurich
 *
 */
@ThreadSafe
public class SaveThread implements Runnable{

	private ArrayBlockingQueue<ImageInfo> save_queue;
	private boolean exit;

	/**
	 * Constructor for SaveThread. Takes an
	 * ArrayBlockingQueue to pull converted
	 * grayscale images from.
	 * 
	 * @param save_queue an ArrayBlockingQueue
	 * to pull new images to save to a local folder
	 */
	public SaveThread(ArrayBlockingQueue<ImageInfo> save_queue){
		this.save_queue = save_queue;
		this.exit = false;
	}

	/**
	 * Override of Runnable.run(). While the save
	 * queue contains more images, the method will
	 * continue to save the images to a local folder
	 * on the computer.
	 */
	public void run(){
		while(!exit){
			if (!save_queue.isEmpty()){		
				ImageInfo tmp = null;
				synchronized(save_queue){
					if (!save_queue.isEmpty()){
						try {
							tmp = save_queue.take();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (!tmp.equals(null)){
					saveImg(tmp);
				}
				else{
					this.exit = true;
				}
			}
		}

	}
	
	/**
	 * 
	 * Override of Runnable.stop(). Causes the current
	 * thread to stop, allowing it to finish processing 
	 * the current task before exiting
	 * 
	 * @throws InterruptedException thrown when
	 * the thread is interrupted
	 */
	public void stop() throws InterruptedException{
		this.exit = true;
		while(!save_queue.isEmpty()){
			ImageInfo tmp = null;
			synchronized(save_queue){
				if (!save_queue.isEmpty()){

					tmp = save_queue.take();

				}
			}
			if (!tmp.equals(null)){
				saveImg(tmp);
			}
	
		}
	}
	
	/**
	 * 
	 * Extracts a BufferedImage from a given
	 * ImageInfo object and saves the file to
	 * the user's computer.
	 * 
	 * @param tmp a temporary ImageInfo object
	 * to be saved to the computer
	 */
	private void saveImg(ImageInfo tmp){
		try {		
			File outputfile = new File("./ProcessedImgs/" + tmp.getName());
			ImageIO.write(tmp.getImg(), "jpg", outputfile);
			System.out.println("Saved: " + tmp.getName());

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
