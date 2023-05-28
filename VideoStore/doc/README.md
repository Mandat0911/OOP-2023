# INTE2512-OOP-Group4

## Contribution Information

This project is a collaborative effort, and the contributions of each team member are essential for its success. The following information outlines the contributions made by each member:

- Member 1:
    - Description of contributions made by Nguyen Man Dat (s3877932) (Code Development and Code Execution (All function of application)).
    - Percentage of contribution: 35%

- Member 2:
    - Description of contributions made by Dinh Minh Xuan (s3891847) (Designed UI/UX of Customer, wrote tests).
    - Percentage of contribution: 20%

- Member 3:
    - Description of contributions made by Le Tran Nhat Nam (s3911298) (Handled database integration, optimized performance).
    - Percentage of contribution: 15%

- Member 4:
    - Description of contributions made by Phan Bao Kim Ngan (s3914582) (Conducted research, wrote documentation).
    - Percentage of contribution: 15%

- Member 5:
    - Description of contributions made by Nguyen Hai Lam (s3979802) (Analyzed requirements from project document, clarify scope and objectives).
    - Percentage of contribution: 15%

Note: The above percentages represent different distribution of contributions of all members.

## Bug

This section lists known bugs or issues in the project. It helps to keep track of the problems that need to be addressed.

- Bug 1: Search functionality not working after adding or editing data.
  - Steps to reproduce the bug:
  - - Login as an admin.
  - - Click on the "Add" or "Edit" button.
  - - The Add/Edit window pops up.
  - - Click on the "Confirm" or "Cancel" button.
   - Attempt to use the search box.
  - Expected behavior:
  - - After clicking the "Confirm" or "Cancel" button, the search box should continue to function properly.
  - Actual behavior:
  - - After clicking the "Confirm" or "Cancel" button, the search box becomes unresponsive and does not perform any search operations.

Note: This bug prevents users from effectively searching for data after adding or editing entries. It is crucial to address this issue to ensure seamless search functionality throughout the application.

- Bug 2: Customer rented table view does not display items with null values.
  - Steps to reproduce the bug:
  - - Log in as a customer.
  - - Click on the "Shop" button.
  - - The item table window appears.
  - - Select the desired item and click "Rent" button.
  - - If the number of stock for that item is 0 (out of stock), it is still added to the customer's rented table.
  - - Log out of the customer account.
  - - Attempt to log in again as the customer.
  - - The rented table does not display the item (out of stock) and throws an IOException in the terminal.
  - - Try to use the search box in the rented table.
  - Expected behavior:
  - - After re-logging in as the customer, the rented table should display all rented items, including those that are out of stock.
  - Actual behavior:
  - - After re-logging in as the customer, the rented table does not display any items, and an error is shown in the terminal.
  
## GitHub Repository

The source code for this project can be found on GitHub at the following repository:

[Project Repository](https://github.com/Mandat0911/OOP-2023.git)

#### Admin 
- [username] ("admin")
- [password] ("admin")


Please visit the repository for more details, access to the code, and to contribute to the project.
