package ru.sfu.waffflezz.rkis8.utils;

import org.springframework.stereotype.Component;

@Component
public class Logger {
  public static void log(Object o) {
    System.out.println("LOG " + o);
  }

  public static void log(Object o, String so) {
    System.out.println("LOG " + so + " -> " + o);
  }
}
