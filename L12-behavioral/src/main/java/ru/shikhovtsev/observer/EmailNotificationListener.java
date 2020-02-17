package ru.shikhovtsev.observer;

import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class EmailNotificationListener implements EventListener {
  private String email;

  @Override
  public void update(String eventType, File file) {
    System.out.println("Email to " + email + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
  }
}

