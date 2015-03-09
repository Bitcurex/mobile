package com.programmerpeter.android.bitcurex;

public class OfferItem {
	private String id = null, limit = null, type = null, currency = null, volume = null;

	public OfferItem(String id, String limit, String type, String currency, String volume) {
		this.setId(id);
		this.setLimit(limit);
		this.setType(type);
		this.setCurrency(currency);
		this.setVolume(volume);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
}