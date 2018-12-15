package CSprojekt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

/** @Author Dawid Spychalski */

/** Glowna klasa programu */

public class Main {

    /** Metoda Main programu*/
    public static void main(String[] args ) throws IOException {

        List<Order> listorders = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        List<Order> orders = new ArrayList<>();

        if (args.length > 0) {
            /** Sprawdzanie czy pobrany argument jest plikiem o *.csv lub *.xml
             * @see Main#readFileCsv(String)
             * @see Main#readFileXml(String) */
            for (String arg : args) {
                if (arg.matches(".*csv")) {
                    listorders = readFileCsv(arg);
                } else if (arg.matches(".*xml")) {
                    listorders = readFileXml(arg);
                } else {
                    System.err.println("Plik " + arg + " ma nieodpowiedni format");
                }
                for (Order order : listorders) {
                    orders.add(order);
                }
            }
        } else {
            System.err.println("Nie pobrano argumentow");
        }

        System.out.println("*************PROGRAM DO OBSLUGI ZAMOWIEN*****************");
        System.out.println();
        /**Pętla nieskończona wykonująca się do momentu kiedy użytkownik nie wpisze w oknie konsoli '9' */
        while (true) {
            System.out.println();
            System.out.println("---------------------------------------------------------------------");
            System.out.println("****************** Mozliwe do wygenerowania raporty *****************");
            System.out.println("1. Ilosc zamowien lacznie");
            System.out.println("2. Ilosc zamowien do klienta o wskazanym identyfikatorze");
            System.out.println("3. Laczna kwota zamowien");
            System.out.println("4. Laczna kwota zamowien do klienta o wskazanym identyfikatorze");
            System.out.println("5. Lista wszystkich zamowien");
            System.out.println("6. Lista zamowien do klienta o wskazanym identyfikatorze");
            System.out.println("7. srednia wartosc zamowienia");
            System.out.println("8. srednia wartosc zamowienia do klienta o wskazanym identyfikatorze. ");
            System.out.println("9. KONIEC");

            System.out.println("Aby wybrac raport wpisz odpowiadajaca mu cyfre: ");

            /**Pętla nieskonczona pozwalająca wybrać użytkownikowi rodzaj generowanego raportu, przerywana jest przez
             * wpisanie do konsoli przez użytkownika '9'
             * Każdy case  z wyjątkiem '9' zawiera metode {@link Main# fileOrConsole}*/
            int choice;
            while (true) {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1: {
                        /**@see Main#numbersOfOrders(List)*/
                        String information = "Ilosc zamowien lacznie: ";
                        /**@see Main#numbersOfOrders(List)*/
                        fileOrConsole(String.valueOf(numbersOfOrders(orders)), information);
                    }
                    break;
                    case 2: {
                        String clientId = clientId();
                        String information = "Ilosc zamowien  dla klienta o identyfikatorze (" + clientId + "): ";
                        /**@see Main#clientNumbersOfOrders(List)*/
                        fileOrConsole(String.valueOf(clientNumbersOfOrders(orders, clientId)), information);
                    }
                    break;
                    case 3: {
                        String information = "Laczna kwota zamowien: ";
                        /**@see Main#totalPrice(List) */
                        fileOrConsole(String.valueOf(totalPrice(orders)), information);
                    }
                    break;
                    case 4: {
                        String clientId = clientId();
                        String information = "Laczna kwota zamowien dla klienta o identyfikatorze (" + clientId
                                + ") : ";
                        /**@see Main#clientTotalPrice(List, String) */
                        fileOrConsole(String.valueOf(clientTotalPrice(orders, clientId)), information);
                    }
                    break;
                    case 5: {
                        String information = "Lista wszystkich zamowien: ";
                        /**@see Main#allOrders(List) */
                        fileOrConsole(String.valueOf(allOrders(orders)), information);
                    }
                    break;
                    case 6: {
                        String clientId = clientId();
                        String information = "Lista wszystkich zamowien dla klienta o identyfikatorze("
                                + clientId + ") : ";
                        /**@see Main#clientAllOrders(List, String) */
                        fileOrConsole(String.valueOf(clientAllOrders(orders, clientId)), information);
                    }
                    break;
                    case 7: {
                        String information = "Srednia wartosc wszystkich zamowien wynosi: ";
                        /**@see Main#averageOrderValue(List) */
                        fileOrConsole(String.valueOf(averageOrderValue(orders)), information);
                    }
                    break;
                    case 8: {
                        String clientId = clientId();
                        String information = "Srednia wartosc wszystkich zamowien dla klienta o identyfikatorze ("
                                + clientId + ") wynosi: ";
                        /**@see Main#clientAverageOrderValue(List, String) */
                        fileOrConsole(String.valueOf(clientAverageOrderValue(orders, clientId)), information);
                    }
                    break;
                    case 9:
                        break;
                    /**Informacja o wprowadzeniu złej wartości*/
                    default:
                        System.out.println("!!! " + choice + " nie jest odpowiednia wartoscia, wpisz ponownie: ");
                }
                /**Warunek kończący drugą pętle*/
                if ((choice == 1) || (choice == 2) || (choice == 3) || (choice == 4) || (choice == 5)
                        || (choice == 6) || (choice == 7) || (choice == 8) || (choice == 9))
                    break;
            }
            /**Warunek zamykający program i kończący pierwszą pętle while*/
            if (choice == 9) break;
        }
    }

    private static void Sort(List<Order> orders) {
        /** @see Order#compareTo(Order) sortowanie po atrybucie clientId  */
        Collections.sort(orders);
        /** @see OrderRequestIdComparator  sortowanie requestId posortowanje listy*/
        Collections.sort(orders, new OrderRequestIdComparator());
    }

    /** Metoda odczytujaca dane z pliku Csv
     * @param filePath ścieżkę do pliku z danymi
     * @see Main#csvToList(List, String)
     * @return lorders lista zamówień pobrana z pliku*/
    private static List<Order> readFileCsv(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List <String> reading = new ArrayList<>();
        try {
            String textLine = bufferedReader.readLine();
            while(textLine != null){
                textLine = bufferedReader.readLine();
                if(textLine==null)
                    break;
                reading.add(textLine);
            }
        }  catch (IOException ex) {
            System.err.println("Brak pliku");
        }
        finally {
            bufferedReader.close();
        }
        List<Order> lorders =csvToList(reading, filePath);
        return lorders;
    }
    /**Metoda przekształcająca dane pobrane z pliku *.csv do listy z zamówieniami, czyli obiektami{@link Order}
     * @param reading jesto lista odczytanych lini z pliku *.csv
     * @param filePath ścieżka do pliku wykorzystana w momencie wystapienia błędnej linii do wyświetlenia informacji
     *                 w jakim pliku ona wystąpiła
     * Motoda sprawdza poprawność danych z wykorzystaniem instrukcji warunkowej if,która korzysta metody
     * {@link Main#isException(String, String, String)}
     * @return orders lista z obiektami{@link Order}*/
    private static List<Order> csvToList (List<String> reading, String filePath) {
        List<Order> orders = new ArrayList<>();
        int ln=0;
        for (String line : reading) {
            String[] l = line.split(",");
            ln++;
            if((l[0].length() <= 6) && (l[0].length() != 0)&& (isException(l[1],l[3],l[4]) == false)) {
                Order order = new Order(l[0], Long.parseLong(l[1]), l[2],
                        Integer.parseInt(l[3]), Double.parseDouble(l[4]));
                orders.add(order);
            } else {
                System.out.println("!!! Linia " + ln + " w pliku "
                        + filePath + " byla nieprawidlowa i zostala pominieta !!!");
            }
        }
        return orders;
    }
    /**Metoda odczytująca dane z pliku i zapisujaca je do lisy reading z obiektami {@link Order}
     * @param filePath ścieżka do pliku *xml
     * @return reading lista z obiektami {@link Order}*/
    private static List<Order> readFileXml(String filePath) {
        List<Order> reading= new ArrayList<>();
        try {
            File file = new File (filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("request");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node firstNode = nodeList.item(i);
                if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element firstElement = (Element) firstNode;

                    NodeList clientIdElementList = firstElement.getElementsByTagName("clientId");
                    Element clientIdElement = (Element) clientIdElementList.item(0);
                    NodeList clientId = clientIdElement.getChildNodes();
                    String c = (clientId.item(0)).getNodeValue();

                    NodeList requestIdElementList = firstElement.getElementsByTagName("requestId");
                    Element requestIdElement = (Element) requestIdElementList.item(0);
                    NodeList requestId = requestIdElement.getChildNodes();
                    String r = (requestId.item(0)).getNodeValue();

                    NodeList namElementList = firstElement.getElementsByTagName("name");
                    Element nameElement = (Element) namElementList.item(0);
                    NodeList name = nameElement.getChildNodes();
                    String n = (name.item(0)).getNodeValue();

                    NodeList quantityElementList = firstElement.getElementsByTagName("quantity");
                    Element quantityElement = (Element) quantityElementList.item(0);
                    NodeList quantity = quantityElement.getChildNodes();
                    String q = (quantity.item(0)).getNodeValue();

                    NodeList priceElementList = firstElement.getElementsByTagName("price");
                    Element priceElement = (Element) priceElementList.item(0);
                    NodeList price = priceElement.getChildNodes();
                    String p = (price.item(0)).getNodeValue();

                    if((c.length() <= 6) && (c.length() != 0)&& (isException(r,q,p) == false)) {
                        Order order = new Order(c, Long.parseLong(r), n,
                                Integer.parseInt(q), Double.parseDouble(p));
                        reading.add(order);
                    } else {
                        System.out.println("!!! Request " + (i + 1) + " w pliku "
                                + filePath + " byl nieprawidlowy i zostal pominiety !!!");
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reading;
    }
    /**Metoda sprawdza czy dane pobrane z pliku mogą być zapisane do atrybutów obiektu {@link Order}
     * @param requestId jest sprawdzany czy można go rzutowac na Long
     * @param quantity jest sprawdzany czy można go rzutowac na Integer
     * @param price jest sprawdzany czy można go rzutować na Double
     * @exception NumberFormatException występuje jeśli danego parametru nie da się rzutować na formaty liczbowe
     * @return true jeśli wyłapuje wyjątek
     * @return false jesli nie wyłapuje wyjątku*/
    private static boolean isException(String requestId,String quantity,String price) {
        try {
            Long.parseLong(requestId);
            Integer.parseInt(quantity);
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
    /** Metoda sprawdzająca ilośc zamówień z plików wczytanych do programu
     * @param orders podwójnie posortowana lista obiektów {@link Order}
     * @return ilośc zamówień*/
    private static int numbersOfOrders(List<Order> orders) {
        Sort(orders);
        int numbers = 1;
        for (int i=0; i <= orders.size()-2; i++) {
            if ((!orders.get(i).getClientId().equals(orders.get(i + 1).getClientId()))
                    || (orders.get(i).getRequestId()!= orders.get(i + 1).getRequestId())) {
                numbers++;
            }
        }
        return numbers;
    }
    /**Metoda sprawdzająca ilośc zamówień dla wybranego klienta
     * @param orders podwójnie posortowana lista obiektóe {@link Order}
     * @param clientId numer klienta dla którego ma być wyświetlona lista zamowień
     * @see Order#getRequestId()
     * @see  Order#getClientId()
     * @return ilośc zamówień dla wybranego klienta*/
    private static int clientNumbersOfOrders(List<Order> orders, String clientId) {
        Sort(orders);
        int numbers = 1;
        for (int i=0; i <= orders.size()-2; i++) {
            if (clientId.equals(orders.get(i).getClientId())) {
                if ((orders.get(i).getRequestId()!= orders.get(i + 1).getRequestId())
                        && (clientId.equals(orders.get(i + 1).getClientId()))) {
                    numbers++;
                }
            }
        }
        return numbers;
    }
    /**Metoda obliczająca łaczną kwotę zamówień
     * @param orders podwójnie posortowana lista obiektów {@link Order}
     * @see Order#getQuantity()
     * @see Order#getPrice()
     * @return łączną kwote zamówień*/
    private static double totalPrice(List<Order> orders) {
        Double totalPrice = 0.0;
        for (int i=0; i <= orders.size()-1; i++) {
            Double orderPrice= orders.get(i).getQuantity()*orders.get(i).getPrice();
            totalPrice = totalPrice + orderPrice;
        }
        return totalPrice;
    }
    /**Metoda obliczająca łaczną kwotę zamówień dla wybranego klienta
     * @param orders podwójnie posortowana lista obiektów {@link Order}
     * @param clientId numer klienta dla którego ma być obliczona łączna kwota zamówień
     * @see Order#getClientId()
     * @see Order#getQuantity()
     * @see Order#getPrice()
     * @return łączną kwote zamówień dla wybranego klienta*/
    private static double clientTotalPrice(List<Order> orders, String clientId) {
        Double totalPrice = 0.0;
        for (int i=0; i <= orders.size()-1; i++) {
            if (clientId.equals(orders.get(i).getClientId())) {
                Double orderPrice = orders.get(i).getQuantity() * orders.get(i).getPrice();
                totalPrice = totalPrice + orderPrice;
            }
        }
        return totalPrice;
    }
    /**Metoda tworząca zmienną allOrders ze wszystkimi zamówieniami
     * @param orders podwójnie posortowana lista obiektów {@link Order}
     * @see Order#toString()
     * @return zmienną ze wszystkimi zamówieniami*/
    private static String allOrders(List<Order> orders) {
        Sort(orders);
        String allOrders = "";
        for (int i=0; i <= orders.size()-1; i++) {
            allOrders =  allOrders + "\n" + orders.get(i).toString();
        }
        return allOrders;
    }
    /**Metoda tworząca zmienną clientAllOrders ze wszystkimi zamówieniami dla wybranego klienta
     * @param orders podwójnie posortowana lista obiektów {@link Order}
     * @param clientId numer klienta
     * @see Order#getClientId()
     * @see Order#toString()
     * @return zmienną ze wszystkimi zamówieniami dla wybranego klienta*/
    private static String clientAllOrders(List<Order> orders, String clientId) {
        Sort(orders);
        String clientallOrders = "";
        for (int i=0; i <= orders.size()-1; i++) {
            if (clientId.equals(orders.get(i).getClientId())) {
                clientallOrders = clientallOrders + "\n" + orders.get(i).toString();
            }
        }
        return clientallOrders;
    }
    /**Metoda obliczająca średnią kwotę wszystkich zamówień
     * @param orders podwójnie posortowana lista obiektów {@link Order}
     * @see Main#totalPrice(List)
     * @see Main#numbersOfOrders(List)
     * @return średnia kwota wszystkich zamówień*/
    private static double averageOrderValue(List<Order> orders) {
        Double averageOrderValue= totalPrice(orders)/numbersOfOrders(orders);
        averageOrderValue *= 100;
        averageOrderValue = Double.valueOf(Math.round(averageOrderValue));
        averageOrderValue /=100;
        return averageOrderValue;
    }
    /**Metoda obliczająca średnią kwotę wszystkich zamówień dla wybranego klienta
     * @param orders podwójnie posortowana lista obiektów {@link Order}
     * @param clientId numer klienta
     * @see Main#totalPrice(List)
     * @see Main#numbersOfOrders(List)
     * @return średnia kwota wszystkich zamówień dla wybranego klienta*/
    private static Double clientAverageOrderValue(List<Order> orders, String clientId) {
        Double averageOrderValue= clientTotalPrice(orders,clientId)/clientNumbersOfOrders(orders, clientId);
        averageOrderValue *= 100;
        averageOrderValue = Double.valueOf(Math.round(averageOrderValue));
        averageOrderValue /=100;
        return averageOrderValue;
    }
    /** Metoda pobierająca podany przez użytkownika numer klienta
     * @return numer klienta*/
    private static String clientId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj identyfikator klienta: ");
        String clientId = scanner.next();
        return clientId;
    }
    /**Metoda pozwalająca wybrać sposób wyświetlenia raportów
     * @param raportData dane do raportu
     * @param information informacje do wyświetlenia w oknie konsoli*/
    private static void fileOrConsole(String raportData, String information)
            throws FileNotFoundException {
        System.out.println("Jak przedstawic raport:");
        System.out.println("1. Zapisac do pliku");
        System.out.println("2. Wypisac w konsoli");
        System.out.println("3. Wypisac w konsoli i zapisac do pliku");
        int choice;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            choice = scanner.nextInt();
            String raport = "*** RAPORT ***";
            switch (choice) {
                case 1:
                    saveInFIle(raportData, information, raport);
                    break;
                case 2:
                    raportInDisplay(raportData, information, raport);
                    break;
                case 3:
                    raportInDisplay(raportData, information, raport);
                    saveInFIle(raportData, information, raport);

                    break;
                default:
                    System.out.println("!!! " + choice + " nie jest odpowiednia wartoscia, wpisz ponownie: ");
            }
            if ((choice==1)|| (choice==2)|| (choice==3)) break;
        }
    }
    /**Metoda wyświetlajaca dane w konsoli
     * @param raportData dane do raportu
     * @param information informacja czego dotyczą dane
     * @param raport nagłowek raportu*/
    private static void raportInDisplay(String raportData, String information, String raport) {
        System.out.println(raport);
        System.out.print(information);
        System.out.println(raportData);
    }
    /**Metoda zapisująca raport do pliku
     * @param raportData dane do raportu
     * @param information informacja czego dotyczą dane
     * @param raport nagłowek raportu*/
    private static void saveInFIle(String raportData, String information, String raport)
            throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwe pliku: ");
        String name = scanner.next();

        PrintWriter save = new PrintWriter(name+ ".csv");
        save.println(raport);
        save.print(information);
        save.println(raportData);
        save.close();
        System.out.println("Plik zostal zapisany");
    }
}
