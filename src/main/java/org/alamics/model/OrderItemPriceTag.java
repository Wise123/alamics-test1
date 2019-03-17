package org.alamics.model;

import org.alamics.model.enums.TagType;

public class OrderItemPriceTag extends AbstractTlvTag<Long> {
  public static int tagId = TagType.ORDER_ITEM_PRICE;

  public OrderItemPriceTag(int length, long value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    return "\"price\": " + value;
  }
}
