package org.alamics.model;

import org.alamics.model.enums.TagType;

public class OrderItemTotalPriceTag extends AbstractTlvTag<Long> {
  public static int tagId = TagType.ORDER_ITEM_TOTAL_PRICE;

  public OrderItemTotalPriceTag(int length, long value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    return "\"sum\": " + value;
  }
}
