package org.example;

/**
 * Класс для отчетов по проделанной работе
 *  5 шагов: очистка, создание директории,
 *  загрузка изображений,
 *  запись файла, завершение
 */
public class ProgressReporter {
  private int totalSteps;
  private int currentStep;

  public ProgressReporter(int totalSteps) {
    this.totalSteps = totalSteps;
    this.currentStep = 0;
  }

  public void start() {
    System.out.println("Начало работы программы ....");
  }

  public void updateProgress() {
    currentStep++;
    int percent = (int) ((currentStep / (double) totalSteps) * 100);
    System.out.print("\rВыполнено - " + percent + "%");
  }

  public void finish() {
    System.out.print("\rРабота программы завершена.");
  }
}