package com.poll;

public class PollItemDTO {
	private int itemnum;
	private String item;
	private String[] items;
	private int count;
	private int num;
	public PollItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PollItemDTO(int itemnum, String item, String[] items, int count, int num) {
		super();
		this.itemnum = itemnum;
		this.item = item;
		this.items = items;
		this.count = count;
		this.num = num;
	}
	public int getItemnum() {
		return itemnum;
	}
	public void setItemnum(int itemnum) {
		this.itemnum = itemnum;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String[] getItems() {
		return items;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
