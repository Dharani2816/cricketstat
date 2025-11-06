# Cricket Stats Manager (MySQL + Plain javac)

## Prerequisites
- Java 8+
- MySQL or MariaDB
- MySQL Connector/J jar in `lib/` (e.g., `lib/mysql-connector-j-9.0.0.jar`)

## Setup
1. Create database:
   ```sql
   CREATE DATABASE IF NOT EXISTS cricket_stats;
   USE cricket_stats;
   SOURCE sql/schema.sql;
   ```
2. Update `config/db.properties` with your MySQL `user` and `password`.

## Compile (Windows PowerShell)
```powershell
javac -cp .;lib\mysql-connector-j-*.jar -d out $(Get-ChildItem -Recurse src\*.java | % { $_.FullName })
```

## Run
```powershell
java -cp out;lib\mysql-connector-j-*.jar com.cricketstats.App
```

## Notes
- UI includes: Stats Dashboard, Add Match Form, Match History.
- Aggregates fetched via service layer; CRUD via DAO.

