package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

public class ImageWriterTest {
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
