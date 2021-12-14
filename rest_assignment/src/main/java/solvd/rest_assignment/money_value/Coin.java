package solvd.rest_assignment.money_value;

public enum Coin {
	DOLLAR, EURO, REAL;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
