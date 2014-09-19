package app.entity;

public class MyReport {
	public String title;
	public Long orders;
	public Double sum;

	public MyReport() {
		
	}	
	
	public MyReport(String title, Long orders, Double sum) {
		this.title = title;
		this.orders = orders;
		this.sum = sum;
	}
	
	public MyReport(Integer i, Long orders, Double sum) {
		this.title = "";
		this.orders = orders;
		this.sum = sum;
	}

	public MyReport(Integer year, Integer month, Integer day, Long orders, Double sum) {
		this.title = ""+year+"/"+month+"/"+day;
		this.orders = orders;
		this.sum = sum;
	}	
	
	public int getTmp() {
		return 0;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getOrders() {
		return orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}
}
