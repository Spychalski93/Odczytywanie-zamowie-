package CSprojekt;

/**Klasa obiektu Order reprezetująca linie zamówienia*/
public class Order implements Comparable<Order> {
    /**@param clientId numer klienta
     * @param requesId numer żadania dla pojedyńczego klienta
     * @param name nazwa produktu w zamowieniu
     * @param quantity liczba produktów
     * @param price cena jednostkowa*/
    public String clientId;
    public long requestId;
    public String name;
    public int quantity;
    public double price;

    /**Metoda wyświetlajaca dane obiektu*/
    @Override
    public String toString() {
        return "Zamowienie:{" + "clientId='" + clientId + '\'' + ", requestId=" + requestId + ", name='" + name + '\'' + ", quantity=" + quantity + ", price=" + price + '}';
    }
    /**Konstruktor obiektu Order*/
    public Order(String clientId, long requestId, String name, int quantity, double price) {
        this.clientId = clientId;
        this.requestId = requestId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    /**@return zwraca numer klienta*/
    public  String getClientId() {
        return clientId;
    }
    /**@return zwraca numer żądania*/
    public long getRequestId() {
        return requestId;
    }
    /**@return zwraca nazwe produktu*/
    public String getName() {
        return name;
    }
    /**@return zwraca ilość produktu*/
    public int getQuantity() {
        return quantity;
    }
    /**@return zwraca cene jednostkową produktu*/
    public double getPrice() {
        return price;
    }

    /**@return zwraca wartość porównania Obiektów*/
    @Override
    public int compareTo(Order o) {
        return this.clientId.compareTo(o.clientId);
    }
}
