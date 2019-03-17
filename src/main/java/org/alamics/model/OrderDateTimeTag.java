package org.alamics.model;

import org.alamics.model.enums.TagType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class OrderDateTimeTag extends AbstractTlvTag<Long> {
  public static int tagId = TagType.ORDER_DATE_TIME;

  public OrderDateTimeTag(int length, long value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    TimeZone gmtTime = TimeZone.getTimeZone("GMT");
    dateFormat.setTimeZone(gmtTime);

    return "\"dateTime\": " + dateFormat.format(new Date(value*1000));
  }
}
