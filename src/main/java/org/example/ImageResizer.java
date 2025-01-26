package org.example;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Изменяет размер изображения,
 * если его ширина превышает MAX_WIDTH.
 */
@Slf4j
public class ImageResizer {

  private static final int MAX_WIDTH = 800;

  public static void resizeImageIfNeeded(Path imagePath) {
    File imageFile = imagePath.toFile();

    try {
      BufferedImage image = ImageIO.read(imageFile);
      if (image.getWidth() > MAX_WIDTH) {
        Thumbnails.of(imageFile)
          .width(MAX_WIDTH)
          .keepAspectRatio(true)
          .toFile(imageFile);
      }
    } catch (IOException e) {
      log.error("ошибка при изменении размера изображения {}", imageFile, e);
      throw new RuntimeException();
    }
  }
}