@echo off
mvn clean install
cd src\main\java\CSprojekt
javac Order.java OrderRequestIdComparator.java Main.java

