package com.becks.util;

public class HqlPage {
	public HqlPage() {};
	public HqlPage(int index, int count) {
		this.index = index;
		this.count = count;
	};
	public int count = 20;
	public int index = 0;
}
