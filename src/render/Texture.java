package render;

import static org.lwjgl.opengl.GL13.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class Texture {

	private int width, id, height;
	private int[] pixelsRBGA;

	public Texture(String filename) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File("./etc/textures/" + filename));
			
			width = img.getWidth();
			height = img.getHeight();
			pixelsRBGA = getImgPixels(img);

			id = glGenTextures();
			loadBuffer(getBuffer());

		} catch (IOException e) {
			System.out.println(e);
			System.out.println("./etc/textures/" + filename);
		}
	}

	protected void finalize() throws Throwable {
		glDeleteTextures(id);
		super.finalize();
	}

	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}

	private int[] getImgPixels(BufferedImage img) {
		int[] pixelsRGBA = new int[width * height * 4]; // for for each r,g,b,alpha
		pixelsRGBA = img.getRGB(0, 0, width, height, null, 0, width); // get all pixels
		return pixelsRGBA;
	}

	private ByteBuffer getBuffer() {
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		int pixel;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixel = pixelsRBGA[i * height + j];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // RED
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
				
				buffer.put((byte) ((pixel >> 0) & 0xFF)); // BLUE
				
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
			}
		}
		buffer.flip();
		return buffer;
	}

	private void loadBuffer(ByteBuffer buffer) {
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	}

}
