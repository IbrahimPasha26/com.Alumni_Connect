# com.Alumni_Connect
 Alumni-Connect
Database Structure
This section outlines the database schema for the Alumni Connect project. The following tables are included in the schema:

1. Alumni Table
Column Name	Data Type	Nullable	Default	Primary Key
ALUMNI_ID	NUMBER	No	-	Yes
NAME	VARCHAR2(100)	No	-	No
EMAIL	VARCHAR2(100)	No	-	No
PHONE_NUMBER	VARCHAR2(15)	Yes	-	No
YEAR_OF_GRADUATION	NUMBER(4,0)	No	-	No
COURSE	VARCHAR2(100)	No	-	No
CURRENT_JOB_TITLE	VARCHAR2(100)	Yes	-	No
CURRENT_COMPANY	VARCHAR2(100)	Yes	-	No
LOCATION	VARCHAR2(100)	Yes	-	No
BIO	CLOB	Yes	-	No
PROFILE_PICTURE	VARCHAR2(255)	Yes	-	No
2. Admin Table
Column Name	Data Type	Nullable	Default	Primary Key
ADMIN_ID	NUMBER	No	-	Yes
USERNAME	VARCHAR2(50)	No	-	No
PASSWORD	VARCHAR2(225)	No	-	No
EMAIL	VARCHAR2(100)	No	-	No
NAME	VARCHAR2(100)	No	-	No
3. Student Table
Column Name	Data Type	Nullable	Default	Primary Key
STUDENT_ID	NUMBER	No	-	Yes
FIRST_NAME	VARCHAR2(100)	No	-	No
LAST_NAME	VARCHAR2(100)	No	-	No
EMAIL	VARCHAR2(100)	No	-	No
PHONE_NUMBER	VARCHAR2(15)	Yes	-	No
ENROLLMENT_YEAR	NUMBER(4,0)	No	-	No
COURSE	VARCHAR2(100)	No	-	No
CURRENT_STATUS	VARCHAR2(100)	Yes	-	No
GRADUATION_YEAR	NUMBER(4,0)	Yes	-	No
PROFILE_PICTURE	VARCHAR2(255)	Yes	-	No
4. User Table
Column Name	Data Type	Nullable	Default	Primary Key
USER_ID	NUMBER	No	-	Yes
USERNAME	VARCHAR2(255)	No	-	No
PASSWORD	VARCHAR2(150)	No	-	No
EMAIL	VARCHAR2(150)	No	-	No
FULL_NAME	VARCHAR2(150)	Yes	-	No
ADDRESS	VARCHAR2(255)	Yes	-	No
DATE_REGISTERED	TIMESTAMP(6)	Yes	CURRENT_TIMESTAMP	No
5. Contact Table
Column Name	Data Type	Nullable	Default	Primary Key
ID	NUMBER	No	-	Yes
NAME	VARCHAR2(100)	No	-	No
EMAIL	VARCHAR2(255)	No	-	No
PHONE_NUMBER	VARCHAR2(20)	Yes	-	No
MESSAGE	CLOB	Yes	-	No
SUBMITTED_AT	TIMESTAMP(6)	Yes	CURRENT_TIMESTAMP	No
Relationships
Alumni-Events Relation: Alumni can participate in multiple events. The ALUMNI_EVENT_ID is used to map ALUMNI_ID to EVENT_ID.

Admin Control: Admins can manage alumni, events, and contact forms.

