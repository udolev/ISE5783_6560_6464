package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing Camera Class
 *
 * @author Natan Weis
 */
/**
 * Test method for
 * {@link Camera#constructRay(int, int, int, int)}.
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
                    testImage.writePixel(j, i, new Color(0, 0, 255));
                else
                    testImage.writePixel(j, i, new Color(196, 180, 84));
            }
        }
        testImage.writeToImage();
    }
}
