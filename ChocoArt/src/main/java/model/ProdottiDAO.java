package model;

import java.util.List;

public interface ProdottiDAO {
	 Prodotto getProductbyId(int id);
	 List<Prodotto> getAllProducts();
	 void addProduct(Prodotto prodotto);
	 void updateProduct(Prodotto prodotto);
	 void deleteProduct(int id);

}
