package org.example.ai.common;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class ScreenshotTaker {

	private static int i=0;
	
	private static void take(int x, int y, int w, int h) {
		Robot r;
		try {
			r = new Robot();
			BufferedImage img = r.createScreenCapture(new Rectangle(x,y,w,h));
			
			File f = new File("images/image"+i+".png");
			ImageIO.write(img, "png", f);
			i++;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void take(Rectangle r) {
		take((int) r.getX(),(int) r.getY(),(int) r.getWidth(),(int) r.getHeight());
	}

}
