# Student Database Management System

## Description
This project is a Student Database Management System implemented in Kotlin using MongoDB. It allows you to perform basic operations like inserting, updating, and deleting student records in a MongoDB database.

## Features
- Insert student records with details such as name, age, grade, and subjects.
- Update existing student records.
- Delete student records from the database.
- All operations are tested using JUnit 5.

## Technologies Used
- **Programming Language**: Kotlin
- **Database**: MongoDB
- **Testing Framework**: JUnit 5

## Prerequisites
Before you begin, ensure you have the following installed:
- [Java JDK 8 or higher](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Kotlin](https://kotlinlang.org/docs/command-line.html)
- [MongoDB](https://www.mongodb.com/try/download/community)
- [Gradle](https://gradle.org/install/)

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/student-database.git
   cd student-database
   ```

2. **Set up MongoDB**:
   - Ensure your MongoDB server is running on `localhost` at the default port `27017`. 
   - Optionally, modify the connection settings in the code to use authentication if required.

3. **Open the project**:
   - Open the project in your preferred IDE (e.g., IntelliJ IDEA).

4. **Build the project**:
   ```bash
   ./gradlew build
   ```

## Usage

To run the tests, use the following command:
```bash
./gradlew test
```
This will execute the tests and display the results in the console. The tests include operations for inserting, updating, and deleting student records.

## Testing

The project includes unit tests using JUnit 5. The tests are located in the `src/test/kotlin/org/surya` directory.

### Running Tests
You can run the tests directly from your IDE or use the command line:
```bash
./gradlew test
```

## Code Structure
- `src/main/kotlin/org/surya/`: Contains the main application code for managing student records.
- `src/test/kotlin/org/surya/`: Contains unit tests for verifying the functionality of the application.

## Example
Here’s a brief example of how to insert a student record:

```kotlin
val student = Document("name", "Alice Johnson")
    .append("age", 16)
    .append("grade", "10th")
    .append("subjects", listOf("Math", "Science", "English"))
collection.insertOne(student)
```

## Contributions
Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## License
This project is licensed under the Apache License 2.0 License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements
- [MongoDB Documentation](https://docs.mongodb.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
