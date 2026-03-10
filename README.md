# advprog_individual

<details>
<summary>Init and Details</summary>

**Gunata Prajna Putra Sakri | 2406453461**

**Deployment Page:** crucial-irma-kingfrog711-c0522eb9.koyeb.app/

## Getting Started

### Prerequisites
This project requires **Java 21**.

### 1. Set Java Version (Mac/Linux)
Run these commands in your terminal to point your environment to Java 21 and avoid Gradle version parsing errors:
```bash
# Set JAVA_HOME to version 21
export JAVA_HOME=$(/usr/libexec/java_home -v 21)

# Verify the version
java -version
```

### 2. How to Test
To run the automated tests and ensure the application is working correctly, run this command in your terminal:
```bash
./gradlew clean test
```

</details>

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

<details>
<summary>Module 2</summary>

### Reflection
i checked sonarcloud and noticed a few code smells, like some unused imports that were just sitting around in the files. i fixed them by simply removing the unused lines to keep the code clean and maintainable.

i believe my current setup definitely fits the definition of ci/cd. every time i push my code to github, the actions automatically trigger a workflow that runs all my unit tests and checks the code quality using sonarcloud, which handles the continuous integration part. then, once all those checks pass, koyeb automatically detects the new changes and deploys the app to the live server without me having to do it manually. this creates a complete continuous deployment loop that makes the whole development process much safer and faster.

</details>

<details>
<summary>Module 3</summary>
## Module 3 Reflection

**1) Explain what principles you apply to your project!**
* **Single Responsibility Principle (SRP):** I separated `CarController` from the `ProductController.java` file into its own file. Each controller now has a single responsibility dealing with its respective domain.
* **Dependency Inversion Principle (DIP):** In `CarController`, I changed the injected service from the concrete class `CarServiceImpl` to the interface `CarService`. High-level modules should depend on abstractions, not concrete implementations.
* **Liskov Substitution Principle (LSP):** I removed `extends ProductController` from `CarController`. A car controller isn't a direct substitute for a product controller, so inheriting its behaviors and routings just to share a file violates LSP.

**2) Explain the advantages of applying SOLID principles to your project with examples.**
* **Easier Testing & Maintenance (DIP):** By injecting the `CarService` interface instead of `CarServiceImpl`, I can easily mock the service when writing unit tests for `CarController` without relying on actual database or business logic.
* **Better Readability and Reduced Merge Conflicts (SRP):** Having `ProductController` and `CarController` in their own respective files makes it significantly easier to navigate. If multiple developers are working on different features, they won't run into merge conflicts modifying the same file.

**3) Explain the disadvantages of not applying SOLID principles to your project with examples.**
* **Rigid Codebase (Failing DIP):** If I continued to rely on `CarServiceImpl` directly, and later wanted to introduce a new way to handle cars (like an external API service), I would have to rewrite the controller entirely to change the dependency.
* **High Risk of Regression (Failing SRP & LSP):** Keeping two controllers in one file makes the file overly large and complex. If I modify `ProductController`, I risk accidentally breaking `CarController`. Furthermore, forcing `CarController` to extend `ProductController` implies that any changes made to the base product routing logic could unintentionally break the car routing logic.

</details>

<details>
<summary>Module 4</summary>
## Module 4 Reflections

### Reflection 1: TDD Experience and F.I.R.S.T. Principles
Going through the Test-Driven Development (TDD) process for this module was a highly structured experience. By strictly adhering to the Red-Green-Refactor cycle, I found that TDD enforces a clear focus on the exact requirements before any implementation begins. Writing the failing tests first (the "Red" phase) naturally forced me to think about the edge cases—such as exactly how a 16-character voucher code should be validated or how an empty address in Cash on Delivery should be handled—without getting bogged down by the implementation details.

Regarding the F.I.R.S.T. principles, I believe the tests created in this exercise strongly align with them:
* **Fast:** The unit tests execute very quickly because they mock the repository layer and do not rely on heavy database setups or external network calls.
* **Isolated/Independent:** Each test sets up its own `Order` or `Payment` data in the `@BeforeEach` setup method, ensuring that the outcome of one test does not affect the state of another.
* **Repeatable:** The tests yield the same results regardless of the environment, as there are no external dependencies or time-sensitive assertions that would cause flaky results.
* **Self-Validating:** Every test relies on specific JUnit assertions (like `assertEquals` or `assertThrows`) to determine success or failure automatically, requiring no manual log checking.
* **Timely:** The tests were written *before* the production code, which is the core essence of the TDD workflow we followed.

### Reflection 2: Teammate Code Review (Bonus 2)
**[My Teammate: Muhammad Rafi Nazir Pratama (2406453556)]**

**Branch I Worked on: https://github.com/KKI-MuhammadRafiNazirPratama-2406453556/Module-Projects/tree/order**

**1. What do you think about your partner's code? Are there any aspects that are still lacking?**
My partner's code successfully separates concerns between the layers and functions as intended. However, there were minor code cleanliness issues, specifically involving unused variables/parameters that cluttered the controller definitions.

**2. What did you do to contribute to your partner's code?**
I branched off their repository, removed the dispensable code from their controller layer, and submitted a Pull Request detailing the exact code smell I fixed.

**3. What code smells did you find on your partner's code?**
* **Unused Parameters (Dispensables):** In `CarController.java`, the POST mapping methods (`createCarPost` and `editCarPost`) explicitly requested a `Model` object as a parameter, but the parameter was entirely unused within the method body.

**4. What refactoring steps did you suggest and execute to fix those smells?**
* **Remove Unused Parameter:** I safely deleted the `Model model` argument from the method signatures of both POST endpoints. Since Spring MVC dynamically resolves parameters, removing it from the signature gracefully prevents the framework from needlessly injecting the object, cleaning up the code without altering any actual routing logic.

</details>