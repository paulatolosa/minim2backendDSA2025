package manager;

import database.models.Item;
import java.util.List;

public interface ShopManager {
    List<Item> getItemsTienda();
    void comprarItem(String username, Integer itemId);
}

