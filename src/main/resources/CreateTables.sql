Create Table UserAccount (
  userID        VARCHAR(10) PRIMARY KEY,
  firstName     VARCHAR(255),
  middleName    VARCHAR(255),
  lastName      VARCHAR(255),
  language      VARCHAR(255),
  userType      VARCHAR(255));

Create Table Message (
  messageID     VARCHAR(10) PRIMARY KEY,
  message       VARCHAR(255),
  isRead        BOOLEAN,
  sentDate      DATE,
  senderID      VARCHAR(10),
  receiverID    VARCHAR(10),
  CONSTRAINT fk_message_senderID FOREIGN KEY (senderID) REFERENCES UserAccount(userID) ON DELETE CASCADE,
  CONSTRAINT fk_message_receiverID FOREIGN KEY (receiverID) REFERENCES UserAccount(userID) ON DELETE CASCADE);

Create Table Request (
  requestID     VARCHAR(10) PRIMARY KEY,
  requestType   VARCHAR(255),
  priority      INTEGER,
  isComplete    BOOLEAN,
  adminConfirm  BOOLEAN,
  startTime     TIMESTAMP,
  endTime       TIMESTAMP,
  nodeID        VARCHAR(10),
  messageID     VARCHAR(10) UNIQUE,
  password      VARCHAR(255),
  CONSTRAINT fk_message_messageID FOREIGN KEY (messageID) REFERENCES Message(messageID) ON DELETE CASCADE);

CREATE TABLE Inventory (
  ID                VARCHAR(10) PRIMARY KEY,
  type              VARCHAR(10) UNIQUE,
  quantity          INTEGER,
  location          VARCHAR(10));
