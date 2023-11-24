package hello.core.dataAccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MemoryRepository implements Repository {

  private static final Map<String, Item> store = new HashMap<>();

  @Override
  public Item save(Item item) {
    try {
      if (findById(item.getId()) != null) {
        // if exist, update item
        store.replace(item.getId(), item);
      } else {
        // if not exists, insert item
        String id = UUID.randomUUID().toString();
        item.setId(id);
        store.put(item.getId(), item);
      }
      return item;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public Item findById(String id) {
    return store.get(id);
  }

  @Override
  public List<Item> findAll() {
    return store.values().stream().toList();
  }

  @Override
  public void deleteById(String id) {
    store.remove(id);
  }
}
