package solvd.rest_assignment.money_value;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import solvd.rest_assignment.utilities.MyLogger;

public class ValueFinder {
	public static final MyLogger LOG = new MyLogger(ValueFinder.class.getName());
	public static final RequestSpecification specification = RestAssured.given();

	@BeforeClass
	public void prepTests() {
		specification.baseUri("https://api-dolar-argentina.herokuapp.com/api");
		specification.config(RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));
	}

	@DataProvider(name = "bankAndCoin")
	public Object[][] provideBankAndCoin() {
		return new Object[][] { { Coin.DOLLAR, Bank.BBVA }, { Coin.EURO, Bank.GALICIA }, { Coin.REAL, Bank.NACION } };
	}

	@DataProvider(name = "bank")
	public Object[][] provideBank() {
		return new Object[][] { { Bank.BBVA }, { Bank.BANCOR }, { Bank.BIND } };
	}

	@DataProvider(name = "coin")
	public Object[][] provideCoin() {
		return new Object[][] { { Coin.DOLLAR }, { Coin.EURO }, { Coin.REAL } };
	}

	@Test(dataProvider = "bankAndCoin")
	public void retrieveBankAndCoin(Coin coin, Bank bank) {
		Value value = null;
		if (coin == Coin.DOLLAR) {
			value = retrieveValue(bank);
		} else if (bank.supports(coin)) {
			value = retrieveValue(coin, bank);
		} else {
			LOG.info("THIS COIN ISN'T SUPPORTED BY THIS BANK/PROVIDER" + System.lineSeparator());
		}
		LOG.info(value.toString());
	}

	@Test(dataProvider = "bank")
	public void retrieveBank(Bank bank) {
		Value value = null;
		for (Coin coin : Coin.values()) {
			if (coin == Coin.DOLLAR) {
				value = retrieveValue(bank);
			} else if (bank.supports(coin)) {
				value = retrieveValue(coin, bank);
			}
		}
		LOG.info(value.toString());
	}

	@Test(dataProvider = "coin")
	public void retrieveCoin(Coin coin) {
		Value value = null;
		for (Bank bank : Bank.values()) {
			if (coin == Coin.DOLLAR) {
				value = retrieveValue(bank);
			} else if (bank.supports(coin)) {
				value = retrieveValue(coin, bank);
			}
		}
		LOG.info(value.toString());
	}

	public static Value retrieveValue(Coin coin, Bank bank) {
		if (coin == Coin.DOLLAR) {
			return RestAssured.given().spec(specification).get("/" + bank).getBody().as(Value.class);
		} else if (bank.supports(coin)) {
			return RestAssured.given().spec(specification).get("/" + bank + "/" + coin).getBody().as(Value.class);
		}
		return null;
	}

	public static Value retrieveValue(Bank bank) {
		return RestAssured.given().spec(specification).log().all().get("/" + bank).getBody().as(Value.class);
	}
}