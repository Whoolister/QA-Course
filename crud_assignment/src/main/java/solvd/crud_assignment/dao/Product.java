package solvd.crud_assignment.dao;

public class Product {
	private String name;
	private String manufacturer;
	private int stock;
	private double price;

	public Product(String name, String manufacturer, int amount, double price) {
		this.name = name;
		this.manufacturer = manufacturer;
		this.stock = amount;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public int getStock() {
		return stock;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Product [ Name: " + name + " | Manufacturer: " + manufacturer + " | Amount: " + stock + " | Price: "
				+ price + "]";
	}
}
