# Online Exchange

## Description
This JavaFX project is a comprehensive simulation of an exchange service platform, designed to provide a variety of functionalities, including swaps, exchanges, offer registration, and much more. The platform is engineered to support multi-trading and utilizes socket programming to facilitate real-time interactions, allowing multiple users to operate concurrently on a local server. This makes it ideal for environments where real-time updates and simultaneous user activities are critical.

### Key Functionalities
1. **Swaps and Exchanges:** Users can perform swaps and exchanges of various assets within the platform. The process is streamlined to ensure fast and secure transactions, reflecting real-world trading environments.
2. **Offer Registration:** Users can register offers for swaps and exchanges, making their terms available to other users. This feature supports a dynamic marketplace where users can negotiate and finalize deals.
3. **User Registration and Management:** The platform supports robust user registration and management systems. Users can create accounts, log in, and manage their profiles with ease. All user information is securely stored in SQL tables.
4. **Wallet and Transaction History:** Each user has a dedicated wallet that tracks their assets and transaction history. This ensures transparency and allows users to monitor their trading activities over time.
5. **Blog and Community Features:** Users can create and read blog posts within the platform, fostering a sense of community. This feature allows users to share insights, strategies, and news related to the exchange services.
6. **Real-Time Multi-Trading:** The use of socket programming enables real-time multi-trading capabilities. Multiple users can trade simultaneously without any lag, ensuring a smooth and efficient trading experience.

### Technical Details
- **JavaFX**: The user interface is built using JavaFX, a robust framework for creating cross-platform desktop applications with rich graphical user interfaces. JavaFX provides tools and libraries for building modern UIs and supports complex layouts and visualizations.

- **Socket Programming**: Implemented to manage real-time communication between clients and the server. Socket programming allows multiple users to interact simultaneously with the application, enabling real-time updates and transactions.

- **MySQL Database**: All user data, including registration details, wallet balances, transaction history, offers, and blog entries, are stored in a MySQL database. MySQL is a popular choice for relational databases due to its performance, scalability, and ease of use.


## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Screenshots](#screenshots)

## Features
- User registration and management
- Wallet and transaction history
- Offer registration and management
- Blog functionality
- Multi-trading support
- Socket programming for real-time interaction
- Local server support

## Installation

### Prerequisites
- JDK 11 or higher
- JavaFX SDK
- MySQL or any SQL-based database
- Maven

### Step-by-Step Guide
1. **Clone the Repository**
    ```bash
    git clone https://github.com/Jahan-Jahan/Online_Exchange.git
    cd Online_Exchange
    ```

2. **Setup Database**
    - Install and configure MySQL.
    - Create a new database.
    - Use the database schema section and create tables

3. **Install Dependencies**
    - Create user and test program
    - If you encounter any problems, read the **Usage**

## Usage
### Basic Usage
Once the application is running, you can:
- Register a new user
- Log in with existing credentials
- Perform trades, swaps, and manage offers
- View transaction history and wallet balance
- Read and create blog posts

## Database Schema

### Users Table

| **Column**        |    **Type**   |   **Description**    |
|---------------|-----------|------------------|
|   id          |     INT   |   PRIMARY_KEY, AUTO_INCRIMENT |
| username      |  VARCHAR  |      UNIQUE      |
| firstName     |  VARCHAR  |           |
| lastName      |  VARCHAR  |           |
| password      |  VARCHAR  |           |
|   email       |  VARCHAR  |      UNIQUE      |
| phoneNumber   |  VARCHAR  |      UNIQUE      |
| profileImage  |  VARCHAR  |                  |


### Assets Table

| **Column**        |    **Type**   |   **Description**    |
|---------------|-----------|------------------|
|   id          |     INT   |   PRIMARY_KEY, AUTO_INCRIMENT |
| username      |  VARCHAR  |      UNIQUE      |
| dollar     |  DOUBLE  |           |
| euro      |  DOUBLE  |           |
| toman      |  DOUBLE  |           |
|   yen       |  DOUBLE  |           |
| pound   |  DOUBLE  |            |

### Comments Table

| **Column**        |    **Type**   |   **Description**    |
|---------------|-----------|------------------|
|   comment_id          |     INT   |   PRIMARY_KEY, AUTO_INCRIMENT |
| username      |  VARCHAR  |            |
| comment     |  TEXT  |           |

### Blog Table

| **Column**        |    **Type**   |   **Description**    |
|---------------|-----------|------------------|
|   id          |     INT   |   PRIMARY_KEY, AUTO_INCRIMENT |
| time      |  VARCHAR  |            |
| username     |  VARCHAR  |           |
| title      |  VARCHAR  |           |
| text      |  TEXT  |           |

### Offers Table

| **Column**        |    **Type**   |   **Description**    |
|---------------|-----------|------------------|
|   id          |     INT   |   PRIMARY_KEY, AUTO_INCRIMENT |
| username      |  VARCHAR  |      UNIQUE      |
| src     |  VARCHAR  |           |
| des      |  VARCHAR  |           |
| price      |  DOUBLE  |           |


### History Table

| **Column**        |    **Type**   |   **Description**    |
|---------------|-----------|------------------|
|   id          |     INT   |   PRIMARY_KEY, AUTO_INCRIMENT |
| time      |  VARCHAR  |            |
| buyer     |  VARCHAR  |           |
| seller      |  VARCHAR  |           |
| src      |  VARCHAR  |           |
| des      |  VARCHAR  |           |
| price      |  DOUBLE  |           |

## Screenshots

### Login-Page
![Login](https://github.com/Jahan-Jahan/Online_Exchange/blob/main/images/login.png)

### Main-Page
![Main](https://github.com/Jahan-Jahan/Online_Exchange/blob/main/images/Main.png)

