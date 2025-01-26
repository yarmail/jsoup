package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Точка входа в программу
 * <br>
 * Парсим ссылку - статическую html страницу.
 * Текст сохраняем в файл, а картинки - в папку в корне проекта.
 * Также меняем ссылки на картинки в тексте на локальные
 *
 */
public class Main {
  private final static String URL = "https://ardent101.github.io/posts/kerberos_theory";
  private final static Path IMAGE_DIR_NAME = Paths.get("images");
  private final static Path RESULT_FILE_NAME = Paths.get("result.html");

  public static void main(String[] args) {

    HtmlParser htmlParser = new HtmlParser(URL, IMAGE_DIR_NAME, RESULT_FILE_NAME);
    htmlParser.startParsing();
  }
}