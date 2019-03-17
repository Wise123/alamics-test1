package org.alamics.model;

import org.alamics.model.enums.TagType;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemListTag extends AbstractTlvTag<List<OrderItemTag>> {

  public OrderItemListTag(int length, List<OrderItemTag> value) {
    this.length = length;
    this.value = value;
  }

  public String toJson() {
    return "\"items\": " + value.stream().map(AbstractTlvTag::toJson).collect(Collectors.toList());
  }
}
