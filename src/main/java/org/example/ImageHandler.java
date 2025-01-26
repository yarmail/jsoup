package org.example;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Класс для работы с картинками
 */
@Slf4j
public class ImageHandler {

  private final String baseUrl;
  private final Path imageDir;

  public ImageHandler(String baseUrl, Path imageDir) {
    this.baseUrl = baseUrl;
    this.imageDir = imageDir;
  }

  public String downloadImage(Element img) {
    String src = img.attr("src");
    String imageName = extractImageName(src);

    URL imageUrl = createImageURL(src);
    Path imagePath = imageDir.resolve(imageName);
    try (InputStream in = imageUrl.openStream()) {
      Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);

      ImageResizer.resizeImageIfNeeded(imagePath);

      return imageName;
    } catch (IOException e) {
      log.error("Ошибка при загрузке изображения: {}", e.getMessage());
      log.info("imageUrl = {}", imageUrl);
      return null;
    }
  }

  private String extractImageName(String src) {
    if (src == null || src.isEmpty()) {
      return "";
    }
    int lastSlashIndex = src.lastIndexOf("/");
    return (lastSlashIndex != -1)
      ? src.substring(lastSlashIndex + 1)
      : src;
  }

  /**
   * Восстановление полного пути к картинке
   * если это необходимо
   */
  private URL createImageURL(String src) {
    try {
      return new URL(src.startsWith("http://")
        || src.startsWith("https://")
        ? src
        : baseUrl + "/" + src);
    } catch (MalformedURLException e) {
      log.error("Некорректный URL: {}", e.getMessage());
      return null;
    }
  }
}