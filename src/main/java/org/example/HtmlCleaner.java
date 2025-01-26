package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Класс для очистки и корректировки HTML-документа.
 */
public class HtmlCleaner {

  /**
   * Убираем ненужные для нашей задачи элементы
   * и упрощаем HTML для Markdown.
   */
  public void clean(Document document) {
    removeUnnecessaryTags(document);
    removeUnnecessaryAttributes(document);
    simplifyFigures(document);
    simplifyLinks(document);
    removeSrcset(document);
    removeHashFromHeadings(document);
  }

  /**
   * Удаляем ненужные блоки.
   */
  private void removeUnnecessaryTags(Document document) {
    document.select("head, header, script, style, footer, nav, svg, details").remove();
  }

  /**
   * Удаляем ненужные атрибуты.
   */
  private void removeUnnecessaryAttributes(Document document) {
    String[] attributesToRemove = {"class", "id", "style", "accesskey", "aria-label", "title"};

    // Удаляем атрибуты только у определенных тегов
    for (Element element : document.select("img, a, div")) {
      for (String attr : attributesToRemove) {
        element.removeAttr(attr);
      }
    }
  }

  /**
   * Упрощаем изображения: заменяем <figure> и <figcaption> на <img>.
   */
  private void simplifyFigures(Document document) {
    for (Element figure : document.select("figure")) {
      Element img = figure.selectFirst("img");
      if (img != null) {
        figure.replaceWith(img);
      }
    }
  }

  /**
   * Упрощаем ссылки: оставляем только href и текст.
   */
  private void simplifyLinks(Document document) {
    for (Element link : document.select("a")) {
      String href = link.attr("href");
      String text = link.text();
      link.replaceWith(new Element("a").attr("href", href).text(text));
    }
  }

  /**
   * Удаляем атрибут srcset у изображений.
   */
  private void removeSrcset(Document document) {
    for (Element img : document.select("img")) {
      img.removeAttr("srcset");
    }
  }

  /**
   * Удаляем решетки (#) из заголовков h2 и h3.
   */
  private void removeHashFromHeadings(Document document) {
    for (Element heading : document.select("h2, h3")) {
      String text = heading.text();
      if (text.endsWith("#")) {
        heading.text(text.substring(0, text.length() - 1)); // Убираем последний символ (#)
      }
    }
  }
}