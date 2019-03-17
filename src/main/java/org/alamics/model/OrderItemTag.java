package org.alamics.model;

import org.alamics.model.enums.TagType;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemTag extends AbstractTlvTag<List<AbstractTlvTag>> {
  public static int tagId = TagType.ORDER_ITEM;

  public OrderItemTag(int length, List<AbstractTlvTag> value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    String resultString = value.stream().map(AbstractTlvTag::toJson).collect(Collectors.toList()).toString();
    return "{" + resultString.substring(1, resultString.length()-2) + "}";
  }
}
