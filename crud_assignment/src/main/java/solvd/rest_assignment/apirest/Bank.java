package solvd.rest_assignment.apirest;

public enum Bank {
	BBVA(Coin.DOLLAR, Coin.EURO, Coin.REAL), PIANO(Coin.DOLLAR), HIPOTECARIO(Coin.DOLLAR, Coin.EURO),
	GALICIA(Coin.DOLLAR, Coin.EURO), SANTANDER(Coin.DOLLAR), CIUDAD(Coin.DOLLAR), SUPERVIELLE(Coin.DOLLAR),
	PATAGONIA(Coin.DOLLAR), COMAFI(Coin.DOLLAR), NACION(Coin.DOLLAR, Coin.EURO, Coin.REAL), BIND(Coin.DOLLAR),
	BANCOR(Coin.DOLLAR), CHACO(Coin.DOLLAR, Coin.EURO, Coin.REAL), PAMPA(Coin.DOLLAR, Coin.EURO),
	MAYORISTA(Coin.DOLLAR);

	private Coin[] supportedCoins;

	private Bank(Coin... supportedCoins) {
		this.supportedCoins = supportedCoins;
	}

	public boolean supports(Coin coin) {
		for (Coin supportedCoin : supportedCoins) {
			if (supportedCoin == coin) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
