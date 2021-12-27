package solvd.crud_assignment;

import solvd.crud_assignment.dao.Product;
import solvd.crud_assignment.dao.ProductDao;

public class Runner {
	private static final ProductDao productDB = new ProductDao();

	public static void main(String[] args) {
		productDB.deleteAll();

		Product actionProduct = new Product("Monopoly", "Hasbro", 10001, 2999.99);
		productDB.save(new Product("Girl Talk", "Hasbro", 79, 0.0));
		productDB.save(new Product("Sorry", "Hasbro", 27, 1499.99));
		productDB.save(new Product("Star Wars: The Sith", "LEGO", 504, 129.99));
		productDB.save(new Product("Emma's Dalmatian Cube", "LEGO", 99, 9.99));
		productDB.save(new Product("Heartlake City's Cafeteria", "LEGO", 1337, 299.99));
		productDB.save(new Product("1000 Piece Aladdin Puzzle", "Disney", 1050, 15.99));
		productDB.save(new Product("Chewbacca Lunchbox", "Disney", 0, 8.0));
		productDB.save(new Product("Frozen 2 Maze", "Disney", 38, 12.99));
		productDB.save(new Product("Basic Hypersphere Stadium", "Beyblade", 2, 14.99));
		productDB.save(new Product("Glide Roktavor R6", "Beyblade", 69, 69.69));

		productDB.save(actionProduct);
		productDB.getAll().forEach(product -> System.out.println(product.toString()));

		System.out.println(
				System.lineSeparator() + "==================================================" + System.lineSeparator());

		productDB.delete(actionProduct);
		productDB.getAll().forEach(product -> System.out.println(product.toString()));

		System.out.println(
				System.lineSeparator() + "==================================================" + System.lineSeparator());

		System.out.println(productDB.get(5));

		ProductDao.close();
	}
}
