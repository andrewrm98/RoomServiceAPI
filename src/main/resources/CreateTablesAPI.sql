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
  nodeID        VARCHAR(50),
  messageID     VARCHAR(50),
  password      VARCHAR(255));

CREATE TABLE Inventory (
  ID                  VARCHAR(10) PRIMARY KEY,
  type                VARCHAR(10) UNIQUE,
  quantity            INTEGER,
  requestInventoryID  VARCHAR(10));
