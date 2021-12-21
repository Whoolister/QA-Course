package solvd.rest_assignment.money_value;

import java.util.Date;

public class Value {
	Date date;
	double buy;
	double sell;

	public double getBuy() {
		return buy;
	}

	public void setBuy(double buy) {
		this.buy = buy;
	}

	public double getSell() {
		return sell;
	}

	public void setSell(double sell) {
		this.sell = sell;
	}

	public String toString() {
		return "Value : [Date: " + date.toString() + " | Buy: " + buy + " | Sell: " + sell + "]";
	}
}
