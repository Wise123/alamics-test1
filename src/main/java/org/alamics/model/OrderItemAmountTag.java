package org.alamics.model;

import org.alamics.model.enums.TagType;

public class OrderItemAmountTag extends AbstractTlvTag<String> {
  public static int tagId = TagType.ORDER_ITEM_AMOUNT;

  public OrderItemAmountTag(int length, String value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    return "\"quantity\": " + value;
  }
}
