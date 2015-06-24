package com.nms.sc.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Category {
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	String label;
	Set<Category> children;
	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	String categoryID;
	
	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	Set<Item> items;
	
	public void addItem(Item newItem){
		if (items == null) {
			items = new HashSet<Item>();
			items.add(newItem);
		} else {
			items.add(newItem);
		}
	}

	public void addItems(List<Item> items){
		for (Item item:items){
			addItem(item);
		}
	}

	
	public Category(String label){
		this.label=  label;
	}
	
	public void addChild(Category childCategory){
		if (children != null){
			children.add(childCategory);
		} else {
			children = new HashSet<Category>();
			children.add(childCategory);
		}
	}
	
	public boolean isChildExists(String childName){
		boolean isExists = false;
		if (children != null) {
			for(Category cat:children){
				if (cat.getLabel().equalsIgnoreCase(childName)){
					isExists=true;
				}
			}
		}
		return isExists;
	}
	
	public Category getChild(String childName){
		Category childCat = null;
		if (children != null){
			for(Category cat:children){
				if (cat.getLabel().equalsIgnoreCase(childName)){
					childCat = cat;
					break;
				}
			}
		}
		return childCat;
	}
	
	public boolean hasItems(){
		if (items != null && items.size() > 0)
			return true;
		else 
			return false;
	}
	
	public boolean hasChildrens(){
		if (children != null && children.size() > 0)
			return true;
		else 
			return false;
	}	
}
