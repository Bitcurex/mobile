package com.programmerpeter.android.bitcurex;

public class MarketItem {
	private String id = null, price = null, amount = null, type = null, date = null, volume = null;

	public MarketItem(String id, String price, String amount, String type, String date, String volume) {
		this.setId(id);
		this.setPrice(price);
		this.setAmount(amount);
		this.setType(type);
		this.setVolume(volume);
		this.setDate(date);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

}