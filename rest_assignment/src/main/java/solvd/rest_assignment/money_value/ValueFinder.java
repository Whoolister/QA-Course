package money_value;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class ValueFinder {
	@DataProvider(name = "bankAndCoin")
	public Object[][] provideBankAndCoin() {
		return new Object[][] { { Coin.DOLLAR, Bank.BBVA }, { Coin.EURO, Bank.GALICIA }, { Coin.REAL, Bank.NACION },
				{ Coin.REAL, Bank.MAYORISTA }, { Coin.EURO, Bank.COMAFI } };
	}

	@Test(dataProvider = "bankAndCoin")
	public void retrieveBankAndCoin(Coin coin, Bank bank) {
		RestAssured.baseURI = "https://api-dolar-argentina.herokuapp.com/api";
		System.out.println(coin.name() + " at bank: " + bank.name());

		if (coin == Coin.DOLLAR) {
			System.out.println(RestAssured.get("/" + bank.toString()).getBody().asString() + System.lineSeparator());
		} else if (coin == Coin.EURO || coin == Coin.REAL) {
			if (bank.supports(coin)) {
				System.out.println(RestAssured.get("/" + coin.toString() + "/" + bank.toString()).getBody().asString()
						+ System.lineSeparator());
			} else {
				System.out.println("THIS COIN ISN'T SUPPORTED BY THIS BANK/PROVIDER" + System.lineSeparator());
			}
		}
	}
}