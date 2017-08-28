package Threads;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.ImageIO;

public class SaveThread implements Runnable{
    
	private ArrayBlockingQueue<BufferedImage> save_queue;

	// takes a target image
	public SaveThread(ArrayBlockingQueue<BufferedImage> save_queue){
		this.save_queue = save_queue;
	}

	// saves the file and removes it from 'converted list'
	public void run(){
		while(true){
			if (!save_queue.isEmpty()){
				
				long startTime = System.currentTimeMillis();  // Start timing
				BufferedImage tmp;
				try {

					tmp = save_queue.take();
					File outputfile = new File("C:/Users/sgurgurich/Desktop/saved.jpg");
					ImageIO.write(tmp, "jpg", outputfile);


				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				long endTime = System.currentTimeMillis();  // Start timing

				long final_time = endTime - startTime;
				System.out.println(final_time);
			}
		}
	}
	
	// overloaded to stop this thread if there's an issue
	public void stop(){
		
	}
	
}
