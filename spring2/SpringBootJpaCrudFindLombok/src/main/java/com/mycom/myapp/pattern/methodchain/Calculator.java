package com.mycom.myapp.pattern.methodchain;

public class Calculator {
	private int first;
	private int second;

	// #1. methodchain (X)
//	public void setFirst(int first) {
//		this.first = first;
//	}
//	public void setSecond(int second) {
//		this.second = second;
//	}
//	
//	public void showAdd() {
//		System.out.println("Add " + this.first + " and " + this.second + " = " + (this.first + this.second));
//	}
//	public void showSub() {
//		System.out.println("Sub " + this.first + " and " + this.second + " = " + (this.first - this.second));
//	}
	
	// #2. methodchain (O)
	public Calculator setFirst(int first) {
		this.first = first;
		return this;
	}
	public Calculator setSecond(int second) {
		this.second = second;
		return this;
	}
	
	public Calculator showAdd() {
		System.out.println("Add " + this.first + " and " + this.second + " = " + (this.first + this.second));
		return this;
	}
	public Calculator showSub() {
		System.out.println("Sub " + this.first + " and " + this.second + " = " + (this.first - this.second));
		return this;
	}
	

}
