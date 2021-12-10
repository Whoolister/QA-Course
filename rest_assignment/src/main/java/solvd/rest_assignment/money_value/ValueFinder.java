package money_value;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import utilities.MyLogger;

public class ValueFinder {
	public static final MyLogger LOG = new MyLogger(ValueFinder.class.getName());

	@BeforeClass
	public void prepTests() {
		RestAssured.baseURI = "https://api-dolar-argentina.herokuapp.com/api";
	}

	@DataProvider(name = "bankAndCoin")
	public Object[][] provideBankAndCoin() {
		return new Object[][] { { Coin.DOLLAR, Bank.BBVA }, { Coin.EURO, Bank.GALICIA }, { Coin.REAL, Bank.NACION } };
	}

	@DataProvider(name = "bank")
	public Object[] provideBank() {
		return new Object[] { Bank.BBVA, Bank.BANCOR, Bank.BIND };
	}

	@DataProvider(name = "coin")
	public Object[] provideCoin() {
		return new Object[] { Coin.DOLLAR, Coin.EURO, Coin.REAL };
	}

	@Test(dataProvider = "bankAndCoin")
	public void retrieveBankAndCoin(Coin coin, Bank bank) {
		LOG.info(coin.name() + " at bank: " + bank.name());

		if (coin == Coin.DOLLAR) {
			LOG.info(RestAssured.get("/" + bank.toString()).getBody().asString() + System.lineSeparator());
		} else if (coin == Coin.EURO || coin == Coin.REAL) {
			if (bank.supports(coin)) {
				LOG.info(RestAssured.get("/" + coin.toString() + "/" + bank.toString()).getBody().asString()
						+ System.lineSeparator());
			} else {
				LOG.info("THIS COIN ISN'T SUPPORTED BY THIS BANK/PROVIDER" + System.lineSeparator());
			}
		}
	}

	@Test(dataProvider = "bank")
	public void retrieveBank(Bank bank) {
		for (Coin coin : Coin.values()) {
			if (coin == Coin.DOLLAR) {
				LOG.info(coin.name() + " at bank: " + bank.name() + System.lineSeparator()
						+ RestAssured.get("/" + bank.toString()).getBody().asString() + System.lineSeparator());
			} else if (bank.supports(coin)) {
				LOG.info(coin.name() + " at bank: " + bank.name() + System.lineSeparator()
						+ RestAssured.get("/" + coin.toString() + "/" + bank.toString()).getBody().asString()
						+ System.lineSeparator());
			}
		}
	}

	@Test(dataProvider = "coin")
	public void retrieveCoin(Coin coin) {
		for (Bank bank : Bank.values()) {
			if (coin == Coin.DOLLAR) {
				LOG.info(coin.name() + " at bank: " + bank.name() + System.lineSeparator()
						+ RestAssured.get("/" + bank.toString()).getBody().asString() + System.lineSeparator());
			} else if (bank.supports(coin)) {
				LOG.info(coin.name() + " at bank: " + bank.name() + System.lineSeparator()
						+ RestAssured.get("/" + coin.toString() + "/" + bank.toString()).getBody().asString()
						+ System.lineSeparator());
			}
		}
	}
}