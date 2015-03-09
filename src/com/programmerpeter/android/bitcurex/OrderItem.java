package com.programmerpeter.android.bitcurex;

public class OrderItem {
	private String rate = null;
	private String amount = null;
	private String mul = null;

	public OrderItem(String rate, String amount, String mul) {
		this.rate = rate;
		this.amount = amount;
		this.setMul(mul);
	}

	public void setrate(String rate) {
		this.rate = rate;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getrate() {
		return rate;
	}

	public String getAmount() {
		return amount;
	}

	public String getMul() {
		return mul;
	}

	public void setMul(String mul) {
		this.mul = mul;
	}
}