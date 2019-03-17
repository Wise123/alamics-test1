package org.alamics.model;

public abstract class AbstractTlvTag<T> {
  public int length;
  public T value;

  public abstract String toJson();
}
