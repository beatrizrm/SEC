CREATE TABLE account (
	public_key VARCHAR(1024),
	user VARCHAR(50),
	balance INT,
	CONSTRAINT pk_account PRIMARY KEY (public_key)
);

CREATE TABLE transaction_info (
	transaction_id SERIAL,
	source VARCHAR(1024),
	destination VARCHAR(1024),
	amount INT,
	status INT,
	ts VARCHAR(100),
	CONSTRAINT pk_transaction_info PRIMARY KEY (transaction_id),
	CONSTRAINT fk_source FOREIGN KEY (source) references account(public_key),
	CONSTRAINT fk_destination FOREIGN KEY (destination) references account(public_key)
);

CREATE TABLE transaction_history (
	public_key VARCHAR(1024),
	transaction_id INT,
	sign INT,
	CONSTRAINT pk_transaction_history PRIMARY KEY (public_key, transaction_id),
	CONSTRAINT fk_public_key FOREIGN KEY (public_key) references account(public_key),
	CONSTRAINT fk_transaction_id FOREIGN KEY (transaction_id) references transaction_info(transaction_id)
);