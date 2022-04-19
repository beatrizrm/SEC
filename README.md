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

Create a new user and database:

```sh
sudo -u postgres psql -c "CREATE ROLE sec LOGIN PASSWORD 'sec';"
sudo -u postgres psql -c "CREATE DATABASE bank WITH OWNER = sec;"
```

Create the database tables:

```sh
psql -U sec -d bank -f "create_tables.sql"
```

### 2. Installing the project

To compile and install all modules:

```
mvn clean install
```

## Running the tests

Open two terminal instances. In the first one, start the server:

```sh
cd bank
mvn compile exec:java
```

In the second one, start the tester:

```sh
cd bank-tester
mvn verify
```

There are tests for the audit, checkAccount and openAccount requests (integrated with the others), but
as we aren't able to create two clients in the same client app (for security purposes), tests for sendAmount and
receiveAmount weren't made.

## Running the project

Open three terminal instances - one for the server and two for the clients.

### 1. Running the server

Change to the server's directory and start the server:

```sh
cd bank
mvn compile exec:java
```

### 2. Running the clients

In the other two terminal instances (**ClientA** and **ClientB**), change to the client's directory and start the client:

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

---

# Initializing 4 different dbs (FIXME, TEMP)

4 replicas => tolerate 1 fault

original server instance started earlier ("0")

1, 2, 3: instance number of each replica


```
sudo mkdir /tmp/postgres/ && sudo chown postgres /tmp/postgres
sudo mkdir /var/lib/postgres/data1 && sudo chown postgres /var/lib/postgres/data1
sudo mkdir /var/lib/postgres/data2 && sudo chown postgres /var/lib/postgres/data2
sudo mkdir /var/lib/postgres/data3 && sudo chown postgres /var/lib/postgres/data3

sudo -u postgres initdb -D /var/lib/postgres/data1
sudo -u postgres initdb -D /var/lib/postgres/data2
sudo -u postgres initdb -D /var/lib/postgres/data3
```

```
sudo -u postgres pg_ctl -D /var/lib/postgres/data1 -o "-p 5433" -l /tmp/postgres/log1 start
sudo -u postgres pg_ctl -D /var/lib/postgres/data2 -o "-p 5434" -l /tmp/postgres/log2 start
sudo -u postgres pg_ctl -D /var/lib/postgres/data3 -o "-p 5435" -l /tmp/postgres/log3 start
```

```sh
sudo -u postgres psql -c "CREATE ROLE sec LOGIN PASSWORD 'sec';" -p 5433
sudo -u postgres psql -c "CREATE DATABASE bank WITH OWNER = sec;" -p 5433
psql -U sec -d bank -f "create_tables.sql" -p 5433

sudo -u postgres psql -c "CREATE ROLE sec LOGIN PASSWORD 'sec';" -p 5434
sudo -u postgres psql -c "CREATE DATABASE bank WITH OWNER = sec;" -p 5434
psql -U sec -d bank -f "create_tables.sql" -p 5434

sudo -u postgres psql -c "CREATE ROLE sec LOGIN PASSWORD 'sec';" -p 5435
sudo -u postgres psql -c "CREATE DATABASE bank WITH OWNER = sec;" -p 5435
psql -U sec -d bank -f "create_tables.sql" -p 5435
```

---

# Running the client (FIXME, TEMP)

```
cd client
mvn compile exec:java -Dexec.args="4"
```

# Running the 4 replicas (FIXME, TEMP)

```
cd bank
mvn compile exec:java -Dexec.args="bank sec sec 0"
mvn compile exec:java -Dexec.args="bank sec sec 1"
mvn compile exec:java -Dexec.args="bank sec sec 2"
mvn compile exec:java -Dexec.args="bank sec sec 3"
```