
package interfaces;

import entity.Client;
import entity.History;
import entity.Shoes;
import entity.Shop;
import java.util.List;

public interface Keeping {
    public void saveShoes(List<Shoes> shoes);
    public List<Shoes> loadShoes();
    
    public void saveClient(List<Client> client);
    public List<Client> loadClient();
    
    public void saveHistory(List<History> history);
    public List<History> loadHistory();
    
    public void saveShop(List<Shop> shop);
    public List<Shop> loadShop();
}
