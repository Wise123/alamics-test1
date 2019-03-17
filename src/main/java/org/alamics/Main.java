package org.alamics;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.alamics.util.TlvParser;
import org.apache.commons.io.IOUtils;

public class Main {

  /**
   * главный метод.
   *
   * @param args аргументы командной строки
   */
  public static void main(String[] args) {

    try (
        InputStream inputStream = new FileInputStream(args[0]);
        OutputStream outputStream = new FileOutputStream(args[1]);
    ) {

      TlvParser tlvParser = new TlvParser(inputStream);
      tlvParser.parse();

      outputStream.write(tlvParser.getJson().getBytes());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
