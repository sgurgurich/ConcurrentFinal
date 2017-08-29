package Threads;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.concurrent.ArrayBlockingQueue;

public class GrayscaleThread implements Runnable{

	private ArrayBlockingQueue<ImageInfo> converter_queue;
	private ArrayBlockingQueue<ImageInfo> save_queue;
	private boolean exit;

	// takes an image
	public GrayscaleThread(ArrayBlockingQueue<ImageInfo> converter_queue, ArrayBlockingQueue<ImageInfo> save_queue){
		this.converter_queue = converter_queue;
		this.save_queue = save_queue;
		this.exit = false;
	}

	// if there's something on the converter_queue, take it off, grayscale it, and add it do the save_queue
	public void run(){
		while(!exit){		
			if (!converter_queue.isEmpty()){
				processImg();
			}
		}
	}
	
	// overloaded to stop this thread if there's an issue
	public void stop(){
		this.exit = true;
		while(!converter_queue.isEmpty()){
			processImg();
		}
	}
	
	private synchronized void processImg(){
		try {

			ImageInfo tmp = converter_queue.take();
			BufferedImage img = tmp.getImg();
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
            try{
            	img = op.filter(img, null);
            	tmp.setImg(img);
            	save_queue.put(tmp);
            }
            catch(NullPointerException ne){
            	System.out.println("Attempt to process image file unsuccessful. File extension not valid.");
            }

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


