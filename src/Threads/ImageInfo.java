package Threads;
import java.awt.image.BufferedImage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * 
 * ImageInfo is a simple data structure
 * used to keep track of a BufferedImage
 * object and the image's file name. This
 * class was created to be implemented with
 * DownloadThread, GrayscaleThread, and
 * SaveThread. GrayscaleThread will be
 * the only thread to reassign
 * the BufferedImage associated with the
 * instance of ImageInfo.
 * 
 * @author Zach Adam
 * @author John Cutsavage
 * @author Stefan Gurgurich
 *
 */
@ThreadSafe
public class ImageInfo {
	
    @GuardedBy("img")private BufferedImage img;
    private String name;
	
    /**
     * 
     * Constructor is ImageInfo. Takes a BufferedImage
     * and the image's file name and saves them to
     * the class' private fields.
     * 
     * @param img the stored BufferedImage
     * @param name the image's file name
     */
	public ImageInfo(BufferedImage img, String name){
		this.img = img;
		this.name = name;
	}

	/**
	 * 
	 * Getter method to retrieve
	 * img.
	 * 
	 * @return the class's img field
	 */
	public BufferedImage getImg() {
		return img;
	}

	/**
	 * 
	 * Setter method that changes img
	 * to a new BufferedImage. This method
	 * is synchronized on img so that a call
	 * to getImg() will return the correct
	 * latest img.
	 * 
	 * @param img the new Buffered image
	 * to set
	 */
	public void setImg(BufferedImage img) {
		synchronized(this.img){
			this.img = img;
		}
	}

	/**
	 * 
	 * Getter method to retrieve the image's
	 * file name
	 * 
	 * @return the class's name field
	 */
	public String getName() {
		return name;
	}
	
}
