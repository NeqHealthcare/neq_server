package eu.neq.mais.technicalservice;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageHandler {
	
	public static BufferedImage getScaledImage(BufferedImage originalImage, int IMG_MAX_WIDTH, int IMG_MAX_HEIGHT) {
		try {

			int IMG_WIDTH = IMG_MAX_WIDTH, IMG_HEIGHT = IMG_MAX_HEIGHT;

			if (originalImage.getWidth() > IMG_MAX_WIDTH
					|| originalImage.getHeight() > IMG_MAX_HEIGHT) {
				if (originalImage.getWidth() >= originalImage.getHeight()) {
					float factor = (float) IMG_MAX_WIDTH
							/ originalImage.getWidth();
					IMG_WIDTH = IMG_MAX_WIDTH;
					IMG_HEIGHT = Math.round(originalImage.getHeight() * factor);
				} else {
					float factor = (float) IMG_MAX_HEIGHT
							/ originalImage.getHeight();
					IMG_WIDTH = Math.round(originalImage.getWidth() * factor);
					IMG_HEIGHT = IMG_MAX_HEIGHT;
				}
			} else {
				IMG_WIDTH = originalImage.getWidth();
				IMG_HEIGHT = originalImage.getHeight();
			}
			BufferedImage resizedImage = new BufferedImage(IMG_WIDTH,
					IMG_HEIGHT, originalImage.getType());
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
			g.dispose();

			return resizedImage;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


}
