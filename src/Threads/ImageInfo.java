package Threads;
import java.awt.image.BufferedImage;

public class ImageInfo {
	
    private BufferedImage img;
    private String name;
	
	public ImageInfo(BufferedImage img, String name){
		this.img = img;
		this.name = name;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}
	
}
