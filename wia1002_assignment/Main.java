package wia1002_assignment;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryADT library = new SmartLibrary();
        Scanner inputScanner = new Scanner(System.in);

        // Preload baseline sample data
        library.preloadBook(1001, "The Great Gatsby", "F. Scott Fitzgerald");
        library.preloadBook(1005, "To Kill a Mockingbird", "Harper Lee");
        library.preloadBook(1003, "1984", "George Orwell");
        library.preloadBook(1002, "Animal Farm", "George Orwell");

        System.out.println("====================================================");
        System.out.println("       WELCOME TO THE SMART UNIVERSITY LIBRARY      ");
        System.out.println("====================================================");

        while (true) {
            System.out.println("\n>>> CHOOSE YOUR ACCESS PORTAL <<<");
            System.out.println("1. Librarian Portal");
            System.out.println("2. Student Portal");
            System.out.println("3. Turn Off System");
            System.out.print("Please enter your choice (1-3): ");

            String userRoleInput = inputScanner.nextLine().trim();
            int portalSelection;
            try {
                portalSelection = Integer.parseInt(userRoleInput);
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Invalid choice! Please type a valid option number (1, 2, or 3).");
                continue;
            }

            // Exits and turns off the application
            if (portalSelection == 3) {
                System.out.println("\nShutting down library application. Goodbye!");
                inputScanner.close();
                System.exit(0);
            }

            // ==========================================
            // LIBRARIAN INTERFACE
            // ==========================================
            if (portalSelection == 1) {
                boolean insideLibrarian = true;
                while (insideLibrarian) {
                    System.out.println("\n--- LIBRARIAN INTERFACE ---");
                    System.out.println("1. Catalog a New Book");
                    System.out.println("2. Find a Book by ISBN");
                    System.out.println("3. View Entire Library Inventory List");
                    System.out.println("4. Update Book Stock Quantities");
                    System.out.println("5. Edit Book Information");
                    System.out.println("6. Remove Book from System");
                    System.out.println("7. Back to Main Screen");
                    System.out.print("Select an option (1-7): ");

                    try {
                        int optionChoice = Integer.parseInt(inputScanner.nextLine().trim());
                        switch (optionChoice) {
                            case 1:
                                System.out.println("\n--- CATALOG NEW BOOK ---");
                                int newIsbn = -1;
                                while (newIsbn == -1) {
                                    System.out.print("Enter Book ISBN (Numbers Only): ");
                                    try {
                                        newIsbn = Integer.parseInt(inputScanner.nextLine().trim());
                                        if (newIsbn <= 0) {
                                            System.out.println("[ERROR] ISBN number must be greater than zero.");
                                            newIsbn = -1;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("[ERROR] Please enter a valid numerical ISBN.");
                                    }
                                }
                                System.out.print("Enter Book Title: ");
                                String newTitle = inputScanner.nextLine().trim();
                                System.out.print("Enter Author Name: ");
                                String newAuthor = inputScanner.nextLine().trim();

                                library.addBook(newIsbn, newTitle, newAuthor);
                                break;
                                
                            case 2:
                                System.out.print("\nEnter ISBN to search: ");
                                try {
                                    library.searchBookByISBN(Integer.parseInt(inputScanner.nextLine().trim()));
                                } catch (NumberFormatException e) {
                                    System.out.println("[ERROR] ISBN must contain numbers only.");
                                }
                                break;

                            case 3:
                                library.displayEntireCatalogue();
                                break;

                            case 4:
                                System.out.println("\n--- UPDATE BOOK STOCK QUANTITY ---");
                                int stockIsbn = -1;
                                while (stockIsbn == -1) {
                                    System.out.print("Enter Book ISBN: ");
                                    try {
                                        stockIsbn = Integer.parseInt(inputScanner.nextLine().trim());
                                    } catch (NumberFormatException e) {
                                        System.out.println("[ERROR] Please input digits only.");
                                    }
                                }
                                
                                int newStock = -1;
                                while (newStock == -1) {
                                    System.out.print("Enter New Total Available Copies: ");
                                    try {
                                        newStock = Integer.parseInt(inputScanner.nextLine().trim());
                                        if (newStock < 0) {
                                            System.out.println("[ERROR] Stock counts cannot be negative.");
                                            newStock = -1;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("[ERROR] Please input whole numbers only.");
                                    }
                                }
                                library.updateBookStock(stockIsbn, newStock);
                                break;
                                
                            case 5: 
                                System.out.println("\n--- EDIT BOOK DETAILS ---");
                                System.out.println("(Note: The ISBN acts as a permanent system identifier and cannot be modified)");
                                int editIsbn = -1;
                                while (editIsbn == -1) {
                                    System.out.print("Enter the ISBN of the book to edit: ");
                                    try {
                                        editIsbn = Integer.parseInt(inputScanner.nextLine().trim());
                                    } catch (NumberFormatException e) {
                                        System.out.println("[ERROR] Please enter numbers only.");
                                    }
                                }
                                System.out.print("Enter Corrected / New Book Title: ");
                                String updatedTitle = inputScanner.nextLine().trim();
                                System.out.print("Enter Corrected / New Author Name: ");
                                String updatedAuthor = inputScanner.nextLine().trim();
                                
                                library.editBookDetails(editIsbn, updatedTitle, updatedAuthor);
                                break;
                                
                            case 6:
                                System.out.println("\n--- REMOVE BOOK FROM INVENTORY ---");
                                System.out.print("Enter the ISBN of the book to permanently remove: ");
                                try {
                                    int removeIsbn = Integer.parseInt(inputScanner.nextLine().trim());
                                    library.removeBook(removeIsbn);
                                } catch (NumberFormatException e) {
                                    System.out.println("[ERROR] Invalid entry! ISBN must be numeric numbers only.");
                                }
                                break;
                                
                            case 7:
                                insideLibrarian = false;
                                System.out.println("\nLogging out of librarian portal...");
                                break;

                            default:
                                System.out.println("[ERROR] Out of bounds! Please pick an option from 1 to 7.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\n[ERROR] Invalid entry. Please enter a number.");
                    }
                }
            }

            // ==========================================
            // STUDENT INTERFACE
            // ==========================================
            else if (portalSelection == 2) {
                boolean insideStudent = true;
                while (insideStudent) {
                    System.out.println("\n--- STUDENT INTERFACE ---");
                    System.out.println("1. Find a Book by ISBN");
                    System.out.println("2. Find a Book by Title Word");
                    System.out.println("3. Find Books by Author");
                    System.out.println("4. Borrow a Book");
                    System.out.println("5. Return a Book");
                    System.out.println("6. View My Borrowed Books List");
                    System.out.println("7. View / Pay My Late Fees Balance");
                    System.out.println("8. Back to Main Screen");
                    System.out.print("Select an option (1-8): ");

                    try {
                        int studentChoice = Integer.parseInt(inputScanner.nextLine().trim());
                        switch (studentChoice) {
                            case 1:
                                int searchIsbn = -1;
                                while (searchIsbn == -1) {
                                    System.out.print("Enter Book ISBN (Numbers Only): ");
                                    try {
                                        searchIsbn = Integer.parseInt(inputScanner.nextLine().trim());
                                        if (searchIsbn <= 0) {
                                            System.out.println("[ERROR] ISBN must be a positive number greater than zero.");
                                            searchIsbn = -1;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("[ERROR] Invalid input! Please enter numbers only.");
                                    }
                                }
                                library.searchBookByISBN(searchIsbn);
                                break;

                            case 2:
                                System.out.print("\nEnter Title keyword to search: ");
                                library.searchBookByTitle(inputScanner.nextLine().trim());
                                break;

                            case 3:
                                System.out.print("\nEnter Author Name to search: ");
                                library.searchBookByAuthorName(inputScanner.nextLine().trim());
                                break;

                            case 4:
                                System.out.print("\nEnter ISBN of the book you want to borrow: ");
                                try {
                                    library.borrowBook(Integer.parseInt(inputScanner.nextLine().trim()));
                                } catch (NumberFormatException e) {
                                    System.out.println("[ERROR] Invalid selection. ISBN must be numeric digits only.");
                                }
                                break;

                            case 5:
                                System.out.print("\nEnter ISBN of the book you are returning: ");
                                try {
                                    library.returnBook(Integer.parseInt(inputScanner.nextLine().trim()));
                                } catch (NumberFormatException e) {
                                    System.out.println("[ERROR] Invalid selection. ISBN must be numeric.");
                                }
                                break;

                            case 6:
                                library.viewLatestHistory();
                                break;

                            case 7:
                                System.out.println("\n--- MY ACCOUNT BALANCE ---");
                                // Display account details summary list
                                boolean hasFines = library.checkAndPayFines(false); 
                                
                                if (hasFines) {
                                    int payDecision = -1;
                                    while (payDecision == -1) {
                                        System.out.println("\nWould you like to pay off this balance right now?");
                                        System.out.println("1. Yes, pay outstanding balance");
                                        System.out.println("0. No, keep balance outstanding");
                                        System.out.print("Please enter your choice (1 or 0): ");
                                        
                                        try {
                                            payDecision = Integer.parseInt(inputScanner.nextLine().trim());
                                            
                                            if (payDecision == 1) {
                                                library.checkAndPayFines(true);
                                            } else if (payDecision == 0) {
                                                System.out.println("\nReturning to student menu without processing payment.");
                                            } else {
                                                System.out.println("[ERROR] Out of bounds! Please type exactly 1 or 0.");
                                                payDecision = -1;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("[ERROR] Invalid input! Please enter a numeric 1 or 0 only.");
                                        }
                                    }
                                }
                                break;

                            case 8:
                                insideStudent = false;
                                System.out.println("\nLogging out of student portal...");
                                break;

                            default:
                                System.out.println("\n[ERROR] Out of bounds! Please pick an option from 1 to 8.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\n[ERROR] Invalid entry. Please enter a choice number.");
                    }
                }
            } else {
                System.out.println("\n[ERROR] Option selection out of bounds. Please pick 1, 2, or 3.");
            }
        }
    }
}