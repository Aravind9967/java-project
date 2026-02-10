package models;

import java.util.ArrayList;
import java.util.List;

public class CartService {
	private List<Book> cart = new ArrayList<Book>();
	
	public void addToCart(Book book) {
		cart.add(book);
		System.out.println(book.getTitle() + " added to cart.");
		
	}
	
	public double getTotal() {
		double total = 0;
		for(Book b: cart) {
			total = total+b.getPrice();
			
		}
		return total;
		
	}
	
	public void viewCart() {
		if(cart.isEmpty()) {
			System.out.println("your cart is empty.");
			
		}
		else {
			System.out.println("Items is your cart:");
		
			for(Book b : cart) {
				System.out.println("--" + b);
			}
		}
	}

}
