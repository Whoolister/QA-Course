package solvd.crud_assignment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IDAO<Product> {
	private static Connection connection;

	private static PreparedStatement saveStatement;
	private static PreparedStatement getStatement;
	private static PreparedStatement updateStatement;
	private static PreparedStatement deleteStatement;

	public ProductDao() {
		try {
			if (connection == null) {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "MySQL8.0");

				Statement statement = connection.createStatement();
				statement.execute("CREATE DATABASE IF NOT EXISTS solvd;");
				statement.execute("USE solvd;");

				statement.execute("CREATE TABLE IF NOT EXISTS products (\r\n" + "	product_id INT AUTO_INCREMENT,\r\n"
						+ "	name VARCHAR(60) NOT NULL UNIQUE,\r\n" + "    manufacturer VARCHAR(20),\r\n"
						+ "    stock INT,\r\n" + "    price DOUBLE(6,2),\r\n" + "	PRIMARY KEY (product_id)\r\n"
						+ ");");

				saveStatement = connection.prepareStatement(
						"INSERT INTO products (name, manufacturer, stock, price) VALUES( ? , ? , ? , ? );");
				getStatement = connection.prepareStatement("SELECT * FROM products WHERE product_id = ? ;");
				updateStatement = connection.prepareStatement(
						"UPDATE products SET name = ? , manufacturer = ? , stock = ? , price = ? WHERE name = ?;");
				deleteStatement = connection.prepareStatement("DELETE FROM products WHERE name = ? ;");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Product input) {
		try {
			saveStatement.setString(1, input.getName());
			saveStatement.setString(2, input.getManufacturer());
			saveStatement.setInt(3, input.getStock());
			saveStatement.setDouble(4, input.getPrice());

			saveStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Product get(int id) {
		try {
			getStatement.setInt(1, id);

			ResultSet resultSet = getStatement.executeQuery();

			if (resultSet.next() != false) {
				return new Product(resultSet.getString("name"), resultSet.getString("manufacturer"),
						resultSet.getInt("stock"), resultSet.getDouble("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getAll() {
		ArrayList<Product> list = new ArrayList<>();

		try {
			ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM products;");

			while (resultSet.next()) {
				list.add(new Product(resultSet.getString("name"), resultSet.getString("manufacturer"),
						resultSet.getInt("stock"), resultSet.getDouble("price")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void update(Product target, Object[] parameters) {
		try {
			updateStatement.setString(1, parameters[0].toString());
			updateStatement.setString(2, parameters[1].toString());
			updateStatement.setInt(3, (int) parameters[2]);
			updateStatement.setDouble(4, (double) parameters[3]);

			updateStatement.setString(5, target.getName());

			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Product target) {
		try {
			deleteStatement.setString(1, target.getName());
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll() {
		try {
			connection.createStatement().executeUpdate("DELETE FROM products;");
			connection.createStatement().executeUpdate("ALTER TABLE products AUTO_INCREMENT = 1;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
