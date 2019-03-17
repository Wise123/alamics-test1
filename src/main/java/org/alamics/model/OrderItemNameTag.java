package org.alamics.model;

import org.alamics.model.enums.TagType;

public class OrderItemNameTag extends AbstractTlvTag<String> {
  public static int tagId = TagType.ORDER_ITEM_NAME;

  public OrderItemNameTag(int length, String value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    return "\"name\": " + value;
  }
}
