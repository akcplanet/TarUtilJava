package org.testing.project.entity;

public enum NuggetField {

	CUSIP("CUSIP"), INVNUM("INVNUM"), PORTFOLIOS_PORTFOLIO_NAME("PORTFOLIOS_PORTFOLIO_NAME"),
	TOUCH_COUNT("TOUCH_COUNT");

	private String nugget;

	NuggetField(String nugget) {
		this.nugget = nugget;
	}

	public String nugget() {
		return nugget;
	}

}
