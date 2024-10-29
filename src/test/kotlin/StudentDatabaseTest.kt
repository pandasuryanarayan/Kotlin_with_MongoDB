package org.surya

import com.mongodb.MongoClientSettings
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.junit.jupiter.api.*
import java.util.Collections
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class) // Specify that tests should be ordered by the @Order annotation
class StudentDatabaseTest {

    private lateinit var mongoClient: MongoClient
    private lateinit var database: MongoDatabase
    private lateinit var collection: MongoCollection<Document>

    // Setup method to initialize MongoDB connection and create a collection
    @BeforeAll
    fun setUp() {
        val databaseName = "testSchoolDB"
        val collectionName = "testStudents"
        val host = "localhost"
        val port = 27017

        // Uncomment and set your MongoDB username and password if needed
        /*
        val username = "yourUsername"
        val password = "yourPassword"

        val credential = MongoCredential.createCredential(username, databaseName, password.toCharArray())
        */

        // Print statement for database connectivity
        println("Connecting to MongoDB at $host:$port...")

        val settings = MongoClientSettings.builder()
            .applyToClusterSettings { builder ->
                builder.hosts(Collections.singletonList(ServerAddress(host, port)))
            }
            // If using credentials, uncomment this line:
            // .credential(credential)
            .build()

        mongoClient = MongoClients.create(settings)
        database = mongoClient.getDatabase(databaseName)

        // Print statement for successful database connection
        println("Connected to database: $databaseName")

        // Drop the collection if it exists to ensure a clean state
        if (database.listCollectionNames().contains(collectionName)) {
            database.getCollection(collectionName).drop()
            println("Dropped existing collection: $collectionName")
        }

        // Create the collection for testing
        database.createCollection(collectionName)
        collection = database.getCollection(collectionName)

        // Print statement for collection creation
        println("Created collection: $collectionName")
    }

    // Tear down method to clean up after all tests
    @AfterAll
    fun tearDown() {
        collection.drop() // Drop the collection to clean up
        println("Dropped collection: ${collection.namespace.collectionName}") // Print which collection is dropped
        mongoClient.close() // Close the MongoDB client
        println("MongoDB client closed.")
    }

    // 1. Test method for inserting a student
    @Test
    @Order(1) // Ensure this test runs first
    fun testInsertStudent() {
        val student = Document("name", "Alice Johnson")
            .append("age", 16)
            .append("grade", "10th")
            .append("subjects", listOf("Math", "Science", "English"))

        // Insert student
        collection.insertOne(student)
        println("Inserted Document: $student") // Print what document is inserted

        // Check if the student was inserted
        val insertedStudent = collection.find(Document("name", "Alice Johnson")).first()
        assertNotNull(insertedStudent, "The student document should exist after insertion.")
    }

    // 2. Test method for updating the student
    @Test
    @Order(2) // Ensure this test runs second
    fun testUpdateStudent() {
        val filter = Document("name", "Alice Johnson")
        val existingStudent = collection.find(filter).first()

        // Ensure the student exists before attempting to update
        assertNotNull(existingStudent, "The student document should exist before updating.")

        // Proceed with updating the student age
        val update = Document("\$set", Document("age", 17))
        collection.updateOne(filter, update)

        // Retrieve the updated student to verify the update
        val updatedStudent = collection.find(filter).first()
        assertNotNull(updatedStudent, "The updated student document should not be null.")
        assertEquals(17, updatedStudent.getInteger("age"), "The student's age should be updated to 17.")

        // Print statement for document update
        println("Updated Document: $updatedStudent")
    }

    // 3. Test method for deleting the student
    @Test
    @Order(3) // Ensure this test runs third
    fun testDeleteStudent() {
        val filter = Document("name", "Alice Johnson")

        // First, ensure the student exists before deletion
        val existingStudent = collection.find(filter).first()
        assertNotNull(existingStudent, "The student document should exist before deleting.")

        // Print statement for document deletion
        println("Deleting Document: $existingStudent")

        // Perform deletion
        collection.deleteOne(filter)

        // Verify that the student was deleted
        val deletedStudent = collection.find(filter).first()
        assertNull(deletedStudent, "The student document should be deleted.")

        // Print statement confirming the deletion
        println("The document was successfully deleted. No document found with the filter: $filter")
    }
}