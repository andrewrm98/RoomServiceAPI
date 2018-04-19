Create Table UserAccount (
  userID        VARCHAR(10) PRIMARY KEY,
  firstName     VARCHAR(255),
  middleName    VARCHAR(255),
  lastName      VARCHAR(255),
  userType      VARCHAR(255));

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
  password      VARCHAR(255));

CREATE TABLE Inventory (
  ID                VARCHAR(10) PRIMARY KEY,
  type              VARCHAR(10) UNIQUE,
  quantity          INTEGER);
