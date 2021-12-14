package solvd.rest_assignment.money_value;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import solvd.rest_assignment.utilities.MyLogger;

import static io.restassured.RestAssured.given;

public class ValueFinder {
	public static final MyLogger LOG = new MyLogger(ValueFinder.class.getName());
	private static final RequestSpecification spec = given();


	@BeforeClass
	public void prepTests() {
		spec.baseUri("https://api-dolar-argentina.herokuapp.com/api");
		spec.config(RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));
		spec.log().all();
	}

	@DataProvider(name = "bankAndCoin", parallel = true)
	public Object[][] provideBankAndCoin() {
		return new Object[][] { { Coin.DOLLAR, Bank.BBVA }, { Coin.EURO, Bank.GALICIA }, { Coin.REAL, Bank.NACION } };
	}

	@DataProvider(name = "bank", parallel = true)
	public Object[][] provideBank() {
		return new Object[][]  { {Bank.BBVA}, {Bank.BANCOR}, {Bank.BIND} };
	}

	@DataProvider(name = "coin", parallel = true)
	public Object[][] provideCoin() {
		return new Object[][] { {Coin.DOLLAR}, {Coin.EURO}, {Coin.REAL} };
	}

	@Test(dataProvider = "bankAndCoin")
	public void retrieveBankAndCoin(Coin coin, Bank bank) {
		LOG.info(coin.name() + " at bank: " + bank.name());
		Value value = null;
		if (coin == Coin.DOLLAR) {
			value = getValue(bank);
		} else if (coin == Coin.EURO || coin == Coin.REAL) {
			if (bank.supports(coin)) {
				value = getValue(coin, bank);
			} else {
				Assert.fail("THIS COIN ISN'T SUPPORTED BY THIS BANK/PROVIDER" + System.lineSeparator());
			}
		}
		Assert.assertTrue(value.getCompra()!=0,"El valor de la compra no puede ser cero!");
		Assert.assertTrue(value.getVenta()!=0, "El valor de la venta no puede ser cero!");
		LOG.info(value.toString());
	}

	@Test(dataProvider = "bank")
	public void retrieveBank(Bank bank) {
		for (Coin coin : Coin.values()) {
			Value value = null;
			if (coin == Coin.DOLLAR) {
				value =getValue(bank);
			} else if (bank.supports(coin)) {
				value = getValue(bank);
			}
			Assert.assertTrue(value.getCompra()!=0,"El valor de la compra no puede ser cero!");
			Assert.assertTrue(value.getVenta()!=0, "El valor de la venta no puede ser cero!");
			LOG.info(value.toString());
		}
	}

	@Test(dataProvider = "coin")
	public void retrieveCoin(Coin coin) {
		for (Bank bank : Bank.values()) {
			Value value = null;
			if (coin == Coin.DOLLAR) {
				value =getValue(bank);
			} else if (bank.supports(coin)) {
				value = getValue(bank);
			}
			Assert.assertTrue(value.getCompra()!=0,"El valor de la compra no puede ser cero!");
			Assert.assertTrue(value.getVenta()!=0, "El valor de la venta no puede ser cero!");
			LOG.info(value.toString());
		}
	}

	public Value getValue(Coin coin, Bank bank){
		if (coin == Coin.DOLLAR) {
			return given().spec(spec).get(String.format("/%s", bank)).getBody().as(Value.class);
		} else if (bank.supports(coin)) {
			return given().spec(spec).get(String.format("/%s/%s", coin, bank)).getBody().as(Value.class);
		}
		return null;
	}

	public Value getValue(Bank bank){
		return given().spec(spec).get( "/" + bank).getBody().as(Value.class);
	}

	public Value getDolarOficial(){
		return given().spec(spec).log().all().get( "/dolaroficial").getBody().as(Value.class, ObjectMapperType.GSON);
	}

}