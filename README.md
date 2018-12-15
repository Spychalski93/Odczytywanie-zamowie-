# Odczytywanie-zamowie-

Przykładowe uruchomienie projektu w środowisku Linux:

$ compile.sh && ./run.sh [argumenty]

I windows

> compile.bat && run.bat [argumenty]

Zadanie
Program do obsługi zamówień.
1. Program na wejściu przyjmuje jako argument listę plików csv i xml.
2. Każdy plik zawiera jedno lub więcej zamówień (format w załączniku).
3. Każde zamówienie należy zapisać w „bazie danych” (użyj bazy in memory).
4. Zamówienie zawiera obowiązkowe pola:
a. ClientId – alfanumeryczne, bez spacji nie dłuższe niż 6 znaków,
b. RequestId – numeryczne long,
c. Name – alfanumeryczne ze spacjami nie dłuższe niż 255 znaków,
d. Quantity – numeryczne int,
e. Price – numeryczne zmiennoprzecinkowe podwójnej precyzji.
5. Program pozwala na wygenerowanie następujących raportów:
a. Ilość zamówień łącznie,
b. Ilość zamówień do klienta o wskazanym identyfikatorze,
c. Łączna kwota zamówień,
d. Łączna kwota zamówień do klienta o wskazanym identyfikatorze,
e. Lista wszystkich zamówień,
f. Lista zamówień do klienta o wskazanym identyfikatorze,
g. Średnia wartość zamówienia ,
h. Średnia wartość zamówienia do klienta o wskazanym identyfikatorze.
6. Każdy raport można wyświetlić na ekranie, albo zapisać do pliku csv (format nie jest
narzucony).
7. Baza danych nie jest dzielona pomiędzy uruchomieniami.
8. Nieprawidłowe linie w zamówieniu są ignorowane, ale informacja o złym formacie jest
wypisywana na ekran.


