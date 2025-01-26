package org.example;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Основной класс парсинга
 */
@Slf4j
public class HtmlParser {

  private final String url;
  private final Path imageDirName;
  private final Path resultFileName;

  public HtmlParser(String url, Path imageDirName, Path resultFileName) {
    this.url = url;
    this.imageDirName = imageDirName;
    this.resultFileName = resultFileName;
  }

  public void startParsing() {
    ProgressReporter reporter = new ProgressReporter(5);
    reporter.start();

    try {
      Document document = Jsoup.connect(url).get();

      HtmlCleaner htmlCleaner = new HtmlCleaner();
      htmlCleaner.clean(document);
      reporter.updateProgress();

      Path imageDir = createImageDirectory();
      reporter.updateProgress();

      ImageHandler imageHandler = new ImageHandler(url, imageDir);
      Elements images = document.select("img");

      for (Element img: images) {
        String imageName = imageHandler.downloadImage(img);
        if (imageName != null) {
          Path imagePath = imageDir.resolve(imageName);
          String formattedPath = "./" + imagePath.toString().replace("\\", "/");
          img.attr("src", formattedPath);
        }
      }
      reporter.updateProgress();

      writeFile(document);
      reporter.updateProgress();
      reporter.finish();
    } catch (IOException e) {
      log.error("Ошибка при парсинге HTML: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }


  /**
   * создаем папку для хранения изображений
   */
  private Path createImageDirectory() {
    try {
      return Files.createDirectories(imageDirName);
    } catch (IOException e) {
      log.error("Ошибка при создании директории изображений: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  /**
   * записываем результат в файл
   */
  private void writeFile(Document document) {
    try {
      Files.writeString(resultFileName, document.outerHtml(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error("Ошибка при записи файла: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }
}

/*
Примечание. При необходимости можно написать так:
      Document document = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
        .header("Accept-Language", "en-US,en;q=0.9")
        .header("Referer", "https://example.com")
        .get();
*/
