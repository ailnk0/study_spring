package hello.core.dataAccess;

import java.util.List;

public interface Repository {

  Item save(Item item);

  Item findById(String id);

  List<Item> findAll();

  void deleteById(String id);
}
