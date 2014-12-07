
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

public class SaveAndRender {

    public static void saveProgress(List<Image> images,
            Optional<Image> background, File dest) throws
            FileNotFoundException, IOException {
        BufferedImage cachedImage = null;
        try (ZipOutputStream out = new ZipOutputStream(
                new FileOutputStream(dest))) {
            for (int i = 0; i < images.size(); i++)
                cachedImage = zipImage(images.get(i), "Page_" + i, out,
                        cachedImage);
            if (background.isPresent())
                zipImage(background.get(), "background", out, cachedImage);
        }
    }

    private static BufferedImage zipImage(Image image, String fname,
            ZipOutputStream out, BufferedImage cachedImaged)
            throws IOException {
        ZipEntry entry = new ZipEntry(fname);
        out.putNextEntry(entry);
        cachedImaged = SwingFXUtils.fromFXImage(image, cachedImaged);
        ImageIO.write(cachedImaged, "png", out);
        out.closeEntry();
        return cachedImaged;
    }

    public static void renderGif(List<Image> images, Optional<Image> background,
            File dest, int speed, boolean loop) throws FileNotFoundException,
            IOException {
        BufferedImage cachedImage = overlay(images.get(0), background, null);
        try (GifSequenceWriter writer = new GifSequenceWriter(
                new FileImageOutputStream(dest), cachedImage.getType(), speed,
                loop)) {
            writer.writeToSequence(cachedImage);
            for (int i = 1; i < images.size(); i++) {
                cachedImage = overlay(images.get(i), background, cachedImage);
                writer.writeToSequence(cachedImage);
            }
        }
    }

    private static BufferedImage overlay(Image fg, Optional<Image> bg,
            BufferedImage cachedImage) {
        if (!bg.isPresent())
            return SwingFXUtils.fromFXImage(fg, cachedImage);
        cachedImage = SwingFXUtils.fromFXImage(bg.get(), cachedImage);
        Graphics2D g = cachedImage.createGraphics();
        g.drawImage(SwingFXUtils.fromFXImage(fg, null), 0, 0, null);
        g.dispose();
        return cachedImage;
    }
}
