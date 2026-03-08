# Event-Driven Microservices with Apache Kafka (KRaft Mode)

Spring Boot + Kafka (KRaft) + Spring Cloud Gateway

---

## System Architecture

```
Client (Postman)
        |
        v
API Gateway (8080)
        |
        v
Order Service (8081)
        |
        v
Kafka Topic: order-topic
        |
  ---------------------
  |                   |
  v                   v
Inventory Service   Billing Service
   (8082)             (8083)
```

---

## Project Structure

```
CTSC_Microservices_Lab_Event_Driven_Kafka_with_KRaft/
├── api-gateway/
│   ├── src/main/java/com/example/apigateway/
│   │   └── ApiGatewayApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
├── order-service/
│   ├── src/main/java/com/example/orderservice/
│   │   ├── OrderServiceApplication.java
│   │   └── OrderController.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
├── inventory-service/
│   ├── src/main/java/com/example/inventoryservice/
│   │   ├── InventoryServiceApplication.java
│   │   └── InventoryConsumer.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
├── billing-service/
│   ├── src/main/java/com/example/billingservice/
│   │   ├── BillingServiceApplication.java
│   │   └── BillingConsumer.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
├── docker-compose.yml
└── README.md
```

---

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker Desktop

Verify:
```bash
java -version
mvn -version
docker -v
```

---

## Step 1: Start Kafka (KRaft Mode)

```bash
docker compose up -d
```

Verify Kafka is running:
```bash
docker ps
```

---

## Step 2: Build Each Service

From the root folder, navigate into each service and build:

```bash
cd order-service    && mvn clean package -DskipTests
cd ../inventory-service && mvn clean package -DskipTests
cd ../billing-service   && mvn clean package -DskipTests
cd ../api-gateway       && mvn clean package -DskipTests
```

---

## Step 3: Run All Services

Open four separate terminals and run:

**Order Service (port 8081)**
```bash
cd order-service
mvn spring-boot:run
```

**Inventory Service (port 8082)**
```bash
cd inventory-service
mvn spring-boot:run
```

**Billing Service (port 8083)**
```bash
cd billing-service
mvn spring-boot:run
```

**API Gateway (port 8080)**
```bash
cd api-gateway
mvn spring-boot:run
```

---

## Step 4: Test with Postman

**Request:**
```
POST http://localhost:8080/orders
Content-Type: application/json
```

**Body:**
```json
{
  "orderId": "ORD-1001",
  "item": "Laptop",
  "quantity": 1
}
```

**Expected Response:**
```
Order Created & Event Published
```

---

## Expected Logs

**Order Service:**
```
Order Created: {"orderId":"ORD-1001","item":"Laptop","quantity":1}
Event published to Kafka topic: order-topic
```

**Inventory Service:**
```
Inventory received order: {"orderId":"ORD-1001","item":"Laptop","quantity":1}
Stock updated
```

**Billing Service:**
```
Billing received order: {"orderId":"ORD-1001","item":"Laptop","quantity":1}
Invoice generated
```

---

## Service Port Summary

| Service            | Port |
|--------------------|------|
| API Gateway        | 8080 |
| Order Service      | 8081 |
| Inventory Service  | 8082 |
| Billing Service    | 8083 |
| Kafka Broker       | 9092 |

---

## Stop Kafka

```bash
docker compose down
```
