# advprog_individual

<details>

<summary>Module 1</summary>

### Reflection 1
I tried to keep the code clean by using meaningful names and separating the controller, service, and repository so each part handles its own job. for security, i used uuids instead of simple integers for product ids to make them harder to guess. looking back, i made a mistake using a get request to delete products which isn't safe, so i need to improve it by changing it to a post request in the future.

### Reflection 2
1. After writing these unit tests, I feel much more confident in my code's stability. It’s a relief knowing that if I break something in the future, the tests will catch it immediately. 
    You need enough tests to cover the normal use, negative cases, and edge cases.
    
    To make sure our tests are enough, we can use code coverage tools to see which lines are actually being run. However, 100% code coverage does not mean the code is bug-free. It only proves that every line was executed, not that the logic is perfect or that every possible scenario has been handled. We still need to think critically about potential logical errors that coverage tools might miss.

2. If I were to create a new functional test suite by just copy-pasting the setup procedures and instance variables from the previous one, it would definitely lower the code quality. This approach creates a lot of code duplication, which violates the "Don't Repeat Yourself" (DRY) principle. It makes the code harder to maintain because if I ever need to change the setup logic (like the base URL), I’d have to update it in multiple files, increasing the risk of mistakes.
   To fix this and make the code cleaner, I should create a base class (e.g., BaseFunctionalTest) that handles the common setup and configuration. Then, all my functional test classes can just extend this base class. This keeps the code modular, reusable, and much easier to read.

</details>
