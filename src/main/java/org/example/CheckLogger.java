package org.example;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

/**
 * Проверка внутреннего состояния логгера
 * Подключить, если надо проверить
 */
@Slf4j
public class CheckLogger {

  public void startCheck() {
    LoggerContext ls = (LoggerContext) LoggerFactory.getILoggerFactory();
    StatusPrinter2 statusPrinter2 = new StatusPrinter2();
    statusPrinter2.print(ls);
  }
}