# Smart Library Management System

A full-stack library management system with role-based authentication, letting a library manage books, members, and borrow/return records through a secure web interface.

## Tech Stack

- **Backend:** Java, Spring Boot, Spring Security
- **Database:** MySQL, Spring Data JPA (Hibernate)
- **Frontend:** HTML, CSS, JavaScript
- **Build Tool:** Maven

## Features

- **Authentication & Authorization** — secure signup/login with Spring Security and BCrypt password encoding
- **Role-Based Access Control** — separate ADMIN and MEMBER roles with different page/API access
- **Book Management** — add, view, and search books in the library catalog
- **Member Management** — register and manage library members
- **Borrow & Return Records** — track which member has borrowed which book, and manage returns
- **Dashboard** — live stats (total books, members, borrow records) and recent activity feed
- **REST API** — backend exposes REST endpoints consumed by the frontend

## Project Structure

    src/main/java/com/smartLibrary
     ├── controller/     REST controllers (Book, Member, Borrow, Auth, Dashboard)
     ├── service/        Business logic layer
     ├── repository/     Spring Data JPA repositories
     ├── model/          Entity classes (Book, Member, BorrowRecord, User)
     └── security/       Spring Security config & custom UserDetailsService

    src/main/resources
     ├── static/         Frontend pages (index, login, signup, dashboard, books, members, borrow)
     └── application.properties
