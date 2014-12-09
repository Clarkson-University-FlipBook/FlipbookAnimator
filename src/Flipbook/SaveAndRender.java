package Flipbook;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

public class SaveAndRender {

    private static final String PREFIX = "Page_";

    /**
     * Saves a list of images to a zip file.
     *
     * @param images A List of Image objects to be saved
     * @param background An optional background image to be saved
     * @param dest the destination file
     * @throws FileNotFoundException
     * @throws IOException if the file cannot be written
     */
    public static void saveProgress(List<Image> images,
            Optional<Image> background, File dest) throws
            FileNotFoundException, IOException {
        BufferedImage cached = null;
        try (ZipOutputStream out = new ZipOutputStream(
                new FileOutputStream(dest))) {
            for (int i = 0; i < images.size(); i++)
                cached = zipImage(images.get(i), PREFIX + i + ".png", out, cached);
            if (background.isPresent())
                zipImage(background.get(), "background.png", out, cached);
        }
    }

    /**
     * Creates a ZipEntry for a single image and returns a cached copy of the
     * image.
     *
     * @param image the image to be zipped
     * @param fname the name of the ZipEntry
     * @param out the output stream to which to write the image
     * @param cachedImaged a cache that can be reused (to avoid unnecessary
     * reallocation)
     * @return the cached image
     * @throws IOException if the image cannot be written to the output stream
     */
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

    /**
     * Exports a sequence of images to an animated GIF file.
     *
     * @param images the frames that make up the animation
     * @param background an optional background that will accompany each frame
     * @param dest the destination file
     * @param speed the speed of the animation, in milliseconds per frame
     * @param loop whether to loop the animation
     * @throws FileNotFoundException
     * @throws IOException if the file cannot be written
     */
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

    /**
     * Overlays a frame on top of a background image, and returns the resulting
     * image. (Note, if there is no background, this method just returns a
     * BufferedImage version of the original frame.)
     *
     * @param fg the frame (or foreground).
     * @param bg the background
     * @param cachedImage a cache to avoid unnecessary allocations
     * @return a BufferedImage of the frame (on top of the background, if it is
     * given)
     */
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

    /**
     * Loads frames from a zip file.
     *
     * @param fname the zip file from which to read the frames
     * @return a List of Image objects (each containing a single frame)
     * @throws FileNotFoundException if the zip file cannot be found
     * @throws IOException if the file cannot be read
     */
    public static List<Image> load(String fname) throws
            FileNotFoundException, IOException {
        ArrayList<Image> images = new ArrayList<>();
        try (ZipInputStream in = new ZipInputStream(
                new FileInputStream(fname))) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.startsWith(PREFIX)) {
                    Image img = SwingFXUtils.toFXImage(ImageIO.read(in), null);
                    images.add(img);
                }
            }
        }
        return images;
    }
}
