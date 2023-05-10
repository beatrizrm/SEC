# SEC_22

Highly Dependable Systems 2021-2022, 2nd semester 1st period project


## Authors

**Group 14**

86391 [Beatriz Alves](mailto:beatriz.alves@tecnico.ulisboa.pt)

92453 [Diogo Branco](mailto:diogo.m.p.c.branco@tecnico.ulisboa.pt)

102178 [Tomás Guerra](mailto:tomas.guerra@tecnico.ulisboa.pt)

## Installing

### 1. Setting up the database

Download and install [PostgreSQL](https://www.postgresql.org/download/).

Initialize the database files:

```sh
sudo -u postgres initdb -D /var/lib/postgres/data
```

Start and enable the service, if needed:

```
sudo systemctl start postgresql.service
sudo systemctl enable postgresql.service
```

Create a new user and databases for the four replicas:

```sh
sudo -u postgres psql -c "CREATE ROLE sec LOGIN PASSWORD 'sec';"
sudo -u postgres psql -c "CREATE DATABASE bank0 WITH OWNER = sec;"
sudo -u postgres psql -c "CREATE DATABASE bank1 WITH OWNER = sec;"
sudo -u postgres psql -c "CREATE DATABASE bank2 WITH OWNER = sec;"
sudo -u postgres psql -c "CREATE DATABASE bank3 WITH OWNER = sec;"
```

Create the database tables:

```sh
psql -U sec -d bank0 -f "create_tables.sql"
psql -U sec -d bank1 -f "create_tables.sql"
psql -U sec -d bank2 -f "create_tables.sql"
psql -U sec -d bank3 -f "create_tables.sql"
```

### 2. Installing the project

To compile and install all modules:

```
mvn clean install
```

## Running the tests

Open three terminal instances. In the first two, start the server and the BFTService:

```sh
cd bank
mvn compile exec:java
```

```sh
cd bftservice
mvn compile exec:java
```

In the third one, start the tester:

```sh
cd bftservice-tester
mvn verify
```

There are tests for the audit, checkAccount and openAccount requests (integrated with the others), but
as we aren't able to create two clients in the same client app (for security purposes), tests for sendAmount and
receiveAmount weren't made.

## Running the project

Open seven terminal instances - four for the server, one for the BFTService and two for the clients.

### 1. Running the servers

In each of the four terminals, change to the server's directory and start the replicas:

```sh
cd bank
mvn compile exec:java -Dexec.args="bank sec sec 0"
```

```sh
cd bank
mvn compile exec:java -Dexec.args="bank sec sec 1"
```

```sh
cd bank
mvn compile exec:java -Dexec.args="bank sec sec 2"
```

```sh
cd bank
mvn compile exec:java -Dexec.args="bank sec sec 3"
```

### 2. Running the BFTService

Change to the BFTService's directory and start it:

```sh
cd bftservice
mvn compile exec:java
```

### 3. Running the clients

In the other **two terminal instances** (**ClientA** and **ClientB**), change to the client's directory and start the client:

```sh
cd client
mvn compile exec:java
```

## Client commands

### 1. Open account

Enter the following command on **ClientA**. This will associate the user "Alice" to her public key.

```
open_account Alice
```

Enter the following command on **ClientB**.

```
open_account Bob
```

### 2. Login

After closing the client, it is possible to login to the same account with the command:

```
login Alice
```

### 3. Send amount

To send 50 euros from Alice to Bob, enter on **ClientA**:

```
send_amount 50 Alice Bob
```

### 4. Check account

To check Bob's balance and his pending transactions, enter the following command on **ClientB**:

```
check_account Bob
```

Each transaction is described by its id, source user, destination user, amount, status and timestamp.

### 5. Receive amount

To make Bob accept the transfer, enter the following command on **ClientB**, specifying the transaction id returned by the `check_account` command.

```
receive_amount Bob 1
```

### 6. Audit

To check a client's full transaction history, enter the following command:

```
audit Alice
```