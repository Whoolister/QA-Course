package solvd.rest_assignment.apirest;

public enum Coin {
	DOLLAR, EURO, REAL;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}