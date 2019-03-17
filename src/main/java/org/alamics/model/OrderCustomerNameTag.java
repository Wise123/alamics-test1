package org.alamics.model;

import org.alamics.model.enums.TagType;

public class OrderCustomerNameTag extends AbstractTlvTag<String> {
  public static int tagId = TagType.ORDER_CUSTOMER_NAME;

  public OrderCustomerNameTag(int length, String value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    return "\"customerName\": \"" + value + "\"";
  }
}
