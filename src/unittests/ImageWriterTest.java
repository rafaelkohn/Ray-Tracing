package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import renderer.*;

class ImageWriterTest {

	@Test
	void test() {
		ImageWriter imageWriter = new ImageWriter("Grid Test", 500, 500, 500, 500);
		for (int i = 0; i < 500; i += 10) {
			for(int j =0;j<500;j++) {
				imageWriter.writePixel(i,j,255,255,255);
				imageWriter.writePixel(j,i,255,255,255);
			}
		}
		imageWriter.writeToimage();
	}

}
