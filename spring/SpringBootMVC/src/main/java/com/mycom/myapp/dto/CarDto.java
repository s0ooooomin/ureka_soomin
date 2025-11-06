package com.mycom.myapp.dto;

public class CarDto {
	private String name2;
	private int price;
//	private Integer price;
	private String owner;
	
	
	public CarDto() { System.out.println("CarDto ()"); }
	public CarDto(String name2, int price, String owner) {
		System.out.println("CarDto (String name, int price, String owner)");
		this.name2 = name2;
		this.price = price;
		this.owner = owner;
	}
	
	
	public String getName() {
		System.out.println("getName()");
		return name2;
	}
	public void setName(String name2) {
		System.out.println("setName(String name2)");
		this.name2 = name2;
	}
	public int getPrice() {
		System.out.println("getPrice()");
		return price;
	}
	public void setPrice(int price) {
		System.out.println("setPrice(int price)");
		this.price = price;
	}
	public String getOwner() {
		System.out.println("getOwner()");
		return owner;
	}
	public void setOwner(String owner) {
		System.out.println("setOwner(String owner)");
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "CarDto [name=" + name2 + ", price=" + price + ", owner=" + owner + "]";
	}
	
	
}
