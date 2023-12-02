package hello.core.product.domain;

import hello.core.dataAccess.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Product extends Item {

  final String name;
  final int price;
}
