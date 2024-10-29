package org.surya

import com.mongodb.MongoClientSettings
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import java.util.Collections

fun main() {
    // MongoDB connection details
    val databaseName = "schoolDB" // database name
    val collectionName = "students" // collection name
    val host = "localhost"
    val port = 27017

    // Uncomment and modify the following for authentication if needed in the future
    // val username = "yourUsername"
    // val password = "yourPassword"
    // val credential = MongoCredential.createCredential(username, databaseName, password.toCharArray())

    val settings = MongoClientSettings.builder()
        .applyToClusterSettings { builder ->
            builder.hosts(Collections.singletonList(ServerAddress(host, port)))
        }
        // Uncomment to add credential in the future if needed
        // .credential(credential)
        .build()

    val mongoClient: MongoClient = MongoClients.create(settings)
    val database: MongoDatabase = mongoClient.getDatabase(databaseName)

    // Creating a Collection
    database.createCollection(collectionName)
    val collection: MongoCollection<Document> = database.getCollection(collectionName)
    println("Collection '$collectionName' created in database '$databaseName'.")

    // CREATE operation: Insert a document (student record)
    val student = Document("name", "Alice Johnson")
        .append("age", 16)
        .append("grade", "10th")
        .append("subjects", listOf("Math", "Science", "English"))
    collection.insertOne(student)
    println("Student record inserted: $student")

    // READ operation: Find documents
    val allStudents = collection.find()
    println("All student records in the collection:")
    for (doc in allStudents) {
        println(doc.toJson())
    }

    // UPDATE operation: Update a student document
    val filter = Document("name", "Alice Johnson")
    val update = Document("\$set", Document("age", 17)) // Promote Alice to 17
    collection.updateOne(filter, update)
    println("Updated student record with name 'Alice Johnson': ${collection.find(filter).first()?.toJson()}")

    // DELETE operation: Delete a student document
    collection.deleteOne(filter)
    println("Deleted student record with name 'Alice Johnson'")

    // Drop the collection (optional)
    database.getCollection(collectionName).drop()
    println("Collection '$collectionName' dropped from database '$databaseName'.")

    // Close the MongoDB client
    mongoClient.close()
    println("Connection closed.")
}