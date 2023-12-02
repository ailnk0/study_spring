package hello.core.order.domain;

import hello.core.dataAccess.Item;
import hello.core.member.domain.Member;
import hello.core.product.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Order extends Item {

  final Member buyer;
  final Product product;
  int price = 0;
  int payment = 0;
}
