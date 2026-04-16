# Booking Service Example

## 📝 Description
This project is a RESTful API built with **Spring Boot** and **PostgreSQL**. It provides features for managing booking system for events, including advanced filtering, pagination, and audit trails.

## 🔗 Related Resource
* [ERD](https://github.com/risliazis/booking-service/blob/main/ERD_Booking_System.png)
* [Postman Collection](https://github.com/risliazis/booking-service/blob/main/booking-service-api.postman_collection.json)

### Key Features:
* **Booking Ticket:** Booking ticket between windowed time, multiple booking available.
* **Native Query Filtering:** Case-insensitive search using ILIKE.
* **Pagination:** Efficient data fetching using Spring Data Pageable.
* **Audit Trail:** Automatic or manual tracking of creation and modification data.
* **DTO Pattern:** Secure data exposure by separating entities from response models.

---

## ⚙️ Minimum Requirements
To run this project locally, you need:
* **Java:** JDK 17 or higher.
* **Database:** PostgreSQL 13 or higher.
* **Build Tool:** Maven 3.6+ (or the included `./mvnw`).
* **IDE:** IntelliJ IDEA, Eclipse, or VS Code.

---

## 🚀 How to Clone from GitHub

1.  Open your terminal or command prompt.
2.  Navigate to the folder where you want to save the project.
3.  Run the following command:
    ```bash
    git clone https://github.com/risliazis/booking-service.git
    ```
4.  Enter the project directory:
    ```bash
    cd [your-repository-name]
    ```

---

## 🛠️ How to Run the Project

### 1. Database Setup
Create a database in PostgreSQL named `[your_db_name]`. Then, update the `src/main/resources/application.properties` file with your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/[your_db_name]
spring.datasource.username=[your_username]
spring.datasource.password=[your_password]
spring.jpa.hibernate.ddl-auto=update
```

### 2. Run in Local
```properties
mvn clean install
```
```properties
mvn spring-boot:run
```
access the services via:
```properties
http://localhost:8081/booking-service
```