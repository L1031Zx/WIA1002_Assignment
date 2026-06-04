# Smart University Library Management System

A robust, console-based library simulation system built in Java. This application showcases core Object-Oriented Programming (OOP) concepts and advanced data structures to manage library inventory, handle real-time student borrowing patterns, track automated timelines, and itemize overdue penalty records.

## 🛠️ Data Structures & System Architecture

To maximize data processing efficiency and handle business logic requirements, the system implements specialized data collection models:

* **Binary Search Tree (`BookBST`)**: Used for the master library catalog. Books are dynamically organized and sorted by their unique ISBN. This allows for fast, high-performance search, insertion, and deletion operations ($O(\log n)$ time complexity).
* **Stack Collection (`BorrowStack`)**: Tracks active checkouts for students following a Last-In, First-Out (LIFO) pattern. Duplicate book titles are handled accurately by always processing operations from the top of the stack down.
* **Payment Ledger Stack (`unpaidFineRecords`)**: Holds isolated historical records of individual overdue book returns, retaining itemized titles and calculated penalty snapshots securely until account balances are paid.

---

## ✨ Key Features

### 🧑‍💼 Librarian Portal
1.  **Catalog a New Book**: Add a brand-new title to the catalog or dynamically increase inventory stock counts for existing titles.
2.  **Find a Book by ISBN**: Quickly locate physical assets utilizing binary search tree mechanics.
3.  **View Inventory List**: Display the entire master library catalog neatly sorted in-order by ISBN.
4.  **Update Stock Quantities**: Modify available copy distribution tallies instantly.
5.  **Edit Book Information**: Update erroneous author data or titles safely without compromising permanent identifier records.
6.  **Remove Book**: Permanently wipe an asset entry out of the tree regardless of its remaining stock level.

### 🎓 Student Portal
1.  **Multi-Criteria Search**: Query the tree system by absolute ISBN index, title keyword patterns, or author names.
2.  **Borrowing Limit Verification**: Allows active checkouts until a strict individual allowance rule threshold (maximum of 5 books) is met.
3.  **Automatic Timeline Simulator**: Automatically assigns a random simulated duration (0 to 9 days) onto checked-out book receipts.
4.  **Possession Records Tracker**: View a personal summary log of all currently held items displaying titles, authors, and overdue alert warnings.
5.  **Interactive Account Statement Portal**: Itemizes overdue items individually. Students can choose whether to process their simulated fine balances immediately or defer transactions using simple numeric selection inputs (`1` or `0`).

---

## 📁 Project Directory Structure

```text
src/wia1002_assignment/
│
├── Book.java               # Core entity model representing structural attributes
├── BookBST.java            # Binary Search Tree catalog controller logic
├── BorrowStack.java        # Custom LIFO stack managing student possession assets
├── LibraryADT.java         # Interface specification defining baseline system operations
├── SmartLibrary.java       # Core system coordinator managing integrated data modules
└── Main.java               # Execution runner controlling console terminal menus
