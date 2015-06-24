package com.nms.sc.beans;

public class Item {
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getLicensingInfo() {
		return licensingInfo;
	}
	public void setLicensingInfo(String licensingInfo) {
		this.licensingInfo = licensingInfo;
	}
	private String itemName;
	private String itemDescription;
	private String licensingInfo;
	private String parentCategoryId;
	private String platform;
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getParentCategoryId() {
		return parentCategoryId;
	}
	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	
}
