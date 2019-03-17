package org.alamics.util;

import org.alamics.model.*;
import org.alamics.model.enums.TagType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsParser {
  InputStream inputStream;
  List<AbstractTlvTag> tags = new ArrayList<>();
  int maxLength;

  public ItemsParser(InputStream inputStream, int maxLength) {
    this.inputStream = inputStream;
    this.maxLength = maxLength;
  }

  /**
   * парсинг байтового потока.
   *
   * @throws IOException - ошибка при чтении байтов из потока
   */
  public void parse() throws IOException {
    int usedBytesAmount = 0;
    while (inputStream.available() > 0 || usedBytesAmount < maxLength) {

      byte[] bytetagId = inputStream.readNBytes(2);
      usedBytesAmount += 2;
      System.out.println(Arrays.toString(bytetagId));
      byte[] fullBytetagId = new byte[4];
      fullBytetagId[0] = bytetagId[0];
      fullBytetagId[1] = bytetagId[1];
      fullBytetagId[2] = 0;
      fullBytetagId[3] = 0;

      int tagId = ByteBuffer
          .wrap(fullBytetagId)
          .order(ByteOrder.LITTLE_ENDIAN)
          .getInt();
      System.out.println(tagId);


      byte[] byteLength = inputStream.readNBytes(2);
      usedBytesAmount += 2;

      byte[] fullByteLength = new byte[4];
      fullByteLength[0] = byteLength[0];
      fullByteLength[1] = byteLength[1];
      fullByteLength[2] = 0;
      fullByteLength[3] = 0;
      int length = ByteBuffer
          .wrap(fullByteLength)
          .order(ByteOrder.LITTLE_ENDIAN)
          .getInt();

      switch (tagId) {

        case TagType.ORDER_ITEM_NAME: {
          if (length != 0) {
            byte[] value = inputStream.readNBytes(length);
            usedBytesAmount += length;

            tags.add(new OrderItemNameTag(length, new String(value, "CP866")));
          } else {
            tags.add(new OrderItemNameTag(0, ""));
          }
          break;
        }

        case TagType.ORDER_ITEM_PRICE: {
          if(length > 8) {
            throw new RuntimeException("Order Number is too long");
          }
          if (length != 0) {
            byte[] value = inputStream.readNBytes(length);
            usedBytesAmount += length;
            long decodedValue = UnsignedIntUtil.readUnsignedInt(value);
            tags.add(new OrderItemPriceTag(length, decodedValue));
          } else {
            tags.add(new OrderItemPriceTag(0, 0));
          }
          break;
        }

        case TagType.ORDER_ITEM_AMOUNT: {
          byte[] pointPositionBytes = inputStream.readNBytes(1);
          usedBytesAmount += 1;
          byte[] fullPointPositionBytes = new byte[4];
          fullPointPositionBytes[0] = byteLength[0];
          fullPointPositionBytes[1] = 0;
          fullPointPositionBytes[2] = 0;
          fullPointPositionBytes[3] = 0;
          int pointPosition = ByteBuffer
              .wrap(fullPointPositionBytes)
              .order(ByteOrder.LITTLE_ENDIAN)
              .getInt();


          if (length != 0) {
            byte[] valueBytes = inputStream.readNBytes(length - 1);
            usedBytesAmount += length - 1;
            String valueString = Long.toString(UnsignedIntUtil.readUnsignedInt(valueBytes));
            if (pointPosition != 0 && pointPosition < valueString.length() - 1) {

              valueString = valueString.substring(0, pointPosition)
                  + ","
                  + valueString.substring(pointPosition);
            }
            tags.add(new OrderItemAmountTag(length, valueString));
          } else {
            tags.add(new OrderItemAmountTag(0, ""));
          }
          break;
        }

        case TagType.ORDER_ITEM_TOTAL_PRICE: {
          if (length != 0) {
            byte[] value = inputStream.readNBytes(length);
            usedBytesAmount += length;
            long decodedValue = UnsignedIntUtil.readUnsignedInt(value);
            tags.add(new OrderItemTotalPriceTag(length, decodedValue));
          } else {
            tags.add(new OrderItemTotalPriceTag(0, 0));
          }
          break;
        }

        default: {
          return;
          // throw new RuntimeException("invalid tag id");
        }
      }
    }
  }

  /**
   * получить json из структуры тегов.
   *
   * @return json
   */
  public String getJson() {
    String result = "{";
    for (int i = 0; i < tags.size(); i++) {
      result += tags.get(i).toJson();
      if(i < tags.size() - 1) {
        result += ", ";
      }
    }
    result += "}";
    return result;
  }
}
