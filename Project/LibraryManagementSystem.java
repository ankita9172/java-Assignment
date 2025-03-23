
import java.util.*;

class Book{
    private String title, author, ISBN;
    private boolean isAvailable;

    public Book(String title, String author, String ISBN){
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true;
    }

    public String getTitle(){ 
	return title; 
	}
    
	public void setTitle(String title){ 
	this.title = title; 
	}

    public String getAuthor(){ 
	return author; 
	}
    
	public void setAuthor(String author){ 
	this.author = author; 
	}

    public String getISBN(){ 
	return ISBN; 
	}
    
	public void setISBN(String ISBN){ 
	this.ISBN = ISBN; 
	}

    public boolean isAvailable(){ 
	return isAvailable; 
	}
    
	public void setAvailable(boolean isAvailable){ 
	this.isAvailable = isAvailable; 
	}

    @Override
    public String toString(){
        return title + " by " + author + " (ISBN: " + ISBN + ")";
    }
}

class Member{
    private String name;
    private int memberID;
    private List<Book> borrowedBooks;

    public Member(String name, int memberID){
        this.name = name;
        this.memberID = memberID;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName(){ 
	return name; 
	}
   
    public int getMemberID(){ 
	return memberID; 
	}
    
	public List<Book> getBorrowedBooks(){ 
	return borrowedBooks; 
	}

    public void borrowBook(Book book){ 
	borrowedBooks.add(book); 
	}
    
	public void returnBook(Book book){ 
	borrowedBooks.remove(book); 
	}

    public void printBorrowedBooks(){
        for (Book book : borrowedBooks){
            System.out.println(book);
        }
    }

    @Override
    public String toString(){
        return "Member Name: " + name + ", ID: " + memberID;
    }
}

class Library{
    private Map<String, Book> books;
    private Set<Member> members;

    public Library(){
        books = new HashMap<>();
        members = new HashSet<>();
    }

    public void addBook(Book book){ 
	books.put(book.getISBN(), book); 
	}
    
	public void addMember(Member member){ 
	members.add(member); 
	}

    public void displayBooks() {
		System.out.println("Available Books: ");
		
        for (Book book : books.values()){
            System.out.println(book);
        }
    }

    public Book searchBook(String findBook){
        return books.get(findBook);
    }

    public Member getMemberById(int memberId){
        for (Member member : members){
            if (member.getMemberID() == memberId){
                return member;
            }
        }
        return null;
    }

    public int getTotalMembers(){ 
	return members.size(); 
	}
    
	public int getTotalBooks(){ 
	return books.size(); 
	}
    
	public int getBorrowedBooksCount(){
        int count = 0;
        for (Book book : books.values()){
            if (!book.isAvailable()){
                count++;
            }
        }
        return count;
    }
	
	public void borrowBookDisplay(){
		
		System.out.println("Borrowed Books:");
		boolean found = false;
    
		for (Book book : books.values()) {
			if (!book.isAvailable()) {
				System.out.println(book);
				found = true;
        }
    }
    
		if (!found) {
        System.out.println("No books are currently borrowed.");
    }
		
	}
}

class BookNotAvailableException extends Exception{
    public BookNotAvailableException(String e){ 
	super(e); 
	}
}

class MemberNotFoundException extends Exception{
    public MemberNotFoundException(String e){ 
	super(e); 
	}
}

class BookAlreadyBorrowedException extends Exception{
    public BookAlreadyBorrowedException(String e){ 
	super(e); 
	}
}

class LibraryService{
    private Library library;

    public LibraryService(Library library){
        this.library = library;
    }

    public void borrowBook(String isbn, int memberId) throws BookNotAvailableException, MemberNotFoundException, BookAlreadyBorrowedException{
        Book book = library.searchBook(isbn);
		
        if (book == null){
            throw new BookNotAvailableException("Book with ISBN " + isbn + " not found.");
        }
		
        if (!book.isAvailable()){
            throw new BookAlreadyBorrowedException("Book with ISBN " + isbn + " is already borrowed.");
        }
		
        Member member = library.getMemberById(memberId);
        
		if (member == null){
            throw new MemberNotFoundException("Member with ID " + memberId + " not found.");
        }
        
		book.setAvailable(false);
        member.borrowBook(book);
    }

    public void returnBook(String isbn, int memberId) throws MemberNotFoundException{
        Member member = library.getMemberById(memberId);
        
		if (member == null){
            throw new MemberNotFoundException("Member with ID " + memberId + " not found.");
        }
        
		Book book = library.searchBook(isbn);
        
		if (book != null && !book.isAvailable()){
            book.setAvailable(true);
            member.returnBook(book);
        }
    }

    public void printStatistics(){
        System.out.println("Total Members: " + library.getTotalMembers());
        System.out.println("Total Books: " + library.getTotalBooks());
        System.out.println("Currently Borrowed Books: " + library.getBorrowedBooksCount());
    }
	
}




public class LibraryManagementSystem{
    public static void main(String[] args){
		
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library);

        while (true){
            System.out.println("1. Add Book\n2. Add Member\n3. Display Books\n4. Borrow Book\n5. Displaying Borrowed Books\n6. Return Book\n7. Books Statictics\n8. Exit");
        
			int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice){
                
				case 1:
                    System.out.print("Enter title : ");
                    String title = scanner.nextLine();
                    
					System.out.print("Enter author : ");
                    String author = scanner.nextLine();
                    
					System.out.print("Enter ISBN : ");
                    String isbn = scanner.nextLine();
                    
					library.addBook(new Book(title, author, isbn));
					System.out.println("Book added: " + title + " by " + author + " (ISBN: " + isbn + ")");
                    break;
                
				case 2:
                    System.out.print("Enter name : ");
                    String name = scanner.nextLine();
                    
					System.out.print("Enter member ID : ");
                    int memberID = scanner.nextInt();
                    
					scanner.nextLine();
                    library.addMember(new Member(name, memberID));
					System.out.println("Member added: " + name + " (ID: " + memberID + ")");
                    break;
                
				case 3:
                    library.displayBooks();
                    break;
                
				case 4:
                    System.out.print("Enter Member ID : ");
                    int borrowMemberID = scanner.nextInt();
                    
					scanner.nextLine();
                    System.out.print("Enter ISBN : ");
                    
					String borrowISBN = scanner.nextLine();
                    
					try {
						libraryService.borrowBook(borrowISBN, borrowMemberID);
						System.out.println("Book borrowed successfully: ");
					}catch (Exception e){
						System.out.println(e.getMessage());
					}
					break;
					
				case 5:
				library.borrowBookDisplay();
				break;
                
				case 6:
                    System.out.print("Enter Member ID : ");
                    int returnMemberID = scanner.nextInt();
                    
					scanner.nextLine();
                    
					System.out.print("Enter ISBN : ");
                    String returnISBN = scanner.nextLine();
                    
					try {
						libraryService.returnBook(returnISBN, returnMemberID);
						System.out.println("Book returned successfully.");
					}catch (Exception e){
						System.out.println(e.getMessage());
					}
					break;
                
				case 7:
					System.out.println("Book Statistics");
					libraryService.printStatistics();
					break;
				
				case 8:
                    System.out.println("Exiting...\nThankYou for using Amol's Library");		//\uD83D\uDE0A
                    return;
					
				
                
				default:
                    System.out.println("Invalid choice, try again.");
            }
        }



	}		
}		
		
		
		
	
