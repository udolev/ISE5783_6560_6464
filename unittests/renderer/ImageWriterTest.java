package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing ImageWriter Class
 *
 * @author Natan Weis
 */
public class ImageWriterTest {
    /**
     * Test method for
     * {@link ImageWriter#writeToImage()}
     * and {@link ImageWriter#writePixel(int, int, Color)}.
     * the function tests the projects ability to create a grid with different colors.
     */
    @Test
    void testWriteImage() {
        int nX = 801;
        int nY = 501;
        ImageWriter testImage = new ImageWriter("test", nX, nY);
        for (int j = 0; j < nX; ++j) {
            for (int i = 0; i < nY; ++i) {
                if (j % 50 == 0 || i % 50 == 0)
                    testImage.writePixel(j, i, new Color(java.awt.Color.YELLOW));
                else
                    testImage.writePixel(j, i, new Color(java.awt.Color.BLUE));
            }
        }
        testImage.writeToImage();
    }
}
