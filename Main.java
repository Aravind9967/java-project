package models;


import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		services services = new services();
		CartService cartService = new CartService();
		PaymentService paymentService = new PaymentService();
		Scanner scanner = new Scanner(System.in);
		
		Book[] book = {
				new Book("java Fundamentals", 200.0),
				new Book("Advance java", 500.0),
				new Book("MicroServices", 600.0)
				
	};
		
		System.out.println("== Welcome to the Book Store ==");
		System.out.println("Enter username: ");
		String username = scanner.nextLine();
		System.out.println("Enter Password: ");
		String password = scanner.nextLine();
		
		
		if(!services.login(username, password)) {
			System.out.println("Invalid username or password");
			return;
			
		}
		System.out.println("Login sucessfully..");
		
		while (true) {
			System.out.println("1. View Books");
			System.out.println("2. Add to Cart");
			System.out.println("3. View Cart");
			System.out.println("4. Checkout");
			System.out.println("5. Exit");
			System.out.println("choose an option");
			
		int choice = Integer.parseInt(scanner.nextLine());
		
		switch (choice) {
		
		case 1:
			System.out.println("\n Available Books: ");
			for(int i = 0; i < book.length; i++) {
				System.out.println((i+1) + "." +book[i]);
		}
		  break;
		  
		  case 2:
			  
			  System.out.print("\\nAvailable Books:");
			  int bookNum = Integer.parseInt(scanner.nextLine());
			  if (bookNum >= 1 && bookNum <= book.length) {
                  cartService.addToCart(book[bookNum - 1]);
              } else {
                  System.out.println("Invalid book number.");
              }
              break;
              
		  case 3:
              cartService.viewCart();
              break;
          case 4:
              double total = cartService.getTotal();
              System.out.println("Total amount: $" + total);
              paymentService.pay(total);
              return;
          case 5:
              System.out.println("Goodbye!");
              return;
          default:
              System.out.println("Invalid choice.");
              
		}
        System.out.println();
		  
		
			
		}
	}
}


