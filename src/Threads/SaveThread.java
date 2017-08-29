package Threads;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.ImageIO;

public class SaveThread implements Runnable{

	private ArrayBlockingQueue<ImageInfo> save_queue;
	private boolean exit;

	// takes a target image
	public SaveThread(ArrayBlockingQueue<ImageInfo> save_queue){
		this.save_queue = save_queue;
		this.exit = false;
	}

	// saves the file and removes it from 'converted list'
	public void run(){
		while(!exit){
			if (!save_queue.isEmpty()){
				try {
					ImageInfo tmp;
					tmp = save_queue.take();
					File outputfile = new File("./ProcessedImgs/" + tmp.getName());
					ImageIO.write(tmp.getImg(), "jpg", outputfile);
					System.out.println("Saved: " + tmp.getName());

				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
	}
	
	// overloaded to stop this thread if there's an issue
	public void stop(){
		this.exit = true;
	}
	
}
