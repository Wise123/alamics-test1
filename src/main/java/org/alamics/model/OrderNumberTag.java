package org.alamics.model;

import org.alamics.model.enums.TagType;

public class OrderNumberTag extends AbstractTlvTag<Long> {
  public static int tagId = TagType.ORDER_NUMBER;

  public OrderNumberTag(int length, long value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    return "\"orderNumber\": " + value;
  }
}
