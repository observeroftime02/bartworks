/*
 * OpenSimplex Noise sample class.
 */

import com.github.bartimaeusnek.bartworks.util.noise.OpenSimplexNoise;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class OpenSimplexNoiseTest
{
	private static final int WIDTH = 512;
	private static final int HEIGHT = 512;
	private static final double FEATURE_SIZE = 256;

	public static void main(String[] args)
		throws IOException {
		
		OpenSimplexNoise noise = new OpenSimplexNoise();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < HEIGHT; y++)
		{
			for (int x = 0; x < WIDTH; x++)
			{
				double value = Math.tanh(noise.eval(x/FEATURE_SIZE, y / FEATURE_SIZE));
				int rgb = 0x000100 * (int)((value + 1) * 127.5);
				image.setRGB(x, y, rgb);
			}
		}
		ImageIO.write(image, "png", new File("noise.png"));
	}
}