package org.alamics.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.alamics.model.*;
import org.alamics.model.enums.TagType;

public class TlvParser {
  InputStream inputStream;
  List<AbstractTlvTag> tags = new ArrayList<>();

  public TlvParser(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  /**
   * парсинг байтового потока.
   *
   * @throws IOException - ошибка при чтении байтов из потока
   */
  public void parse() throws IOException {
    while (inputStream.available() > 0) {
      try {
        byte[] bytetagId = inputStream.readNBytes(2);
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

          case TagType.ORDER_DATE_TIME: {
            if (length == 4) {
              byte[] value = inputStream.readNBytes(length);
              long decodedValue = UnsignedIntUtil.readUnsignedInt(value);
              tags.add(new OrderDateTimeTag(length, decodedValue));
            } else {
              tags.add(new OrderDateTimeTag(0, 0));
            }
            break;
          }

          case TagType.ORDER_NUMBER: {
            if (length > 8) {
              throw new RuntimeException("Order Number is too long");
            }
            if (length != 0) {
              byte[] value = inputStream.readNBytes(length);
              long decodedValue = UnsignedIntUtil.readUnsignedInt(value);
              tags.add(new OrderNumberTag(length, decodedValue));
            } else {
              tags.add(new OrderDateTimeTag(0, 0));
            }
            break;
          }

          case TagType.ORDER_CUSTOMER_NAME: {
            if (length != 0) {
              byte[] value = inputStream.readNBytes(length);

              tags.add(new OrderCustomerNameTag(length, new String(value, "CP866")));
            } else {
              tags.add(new OrderCustomerNameTag(0, ""));
            }
            break;
          }

          case TagType.ORDER_ITEM: {
            if (length != 0) {
              OrderItemListTag orderItemListTag = new OrderItemListTag(length, new ArrayList<>());
              while (inputStream.available() > 0){
                ItemsParser itemsParser = new ItemsParser(inputStream, length);
                itemsParser.parse();
                orderItemListTag.value.add(new OrderItemTag(length, itemsParser.tags));
              }
              tags.add(orderItemListTag);
            } else {
              tags.add(new OrderItemTag(0, new ArrayList<>()));
            }
            break;
          }


          default: {
            return;
            // throw new RuntimeException("invalid tag id");
          }

        }

      } catch (Exception e) {
        e.printStackTrace();
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
