package CSprojekt;

import java.util.Comparator;
/**Klasa umożliwiająca sortowanie*/
public class OrderRequestIdComparator implements Comparator <Order> {

    /**Metoda porównuje wartość atrybutu requestId
     * @return zwraca (1,-1,0) i wykorzystuje te wartości do sortowania*/
    @Override
    public int compare(Order o1, Order o2) {
       if ((o1.getClientId().equals(o2.getClientId())) && (o1.getRequestId()>o2.getRequestId()))return 1;
       else if ((o1.getClientId().equals(o2.getClientId())) && (o1.getRequestId()<o2.getRequestId()))return -1;
       else if (((o1.getClientId().equals(o2.getClientId())) && (o1.getRequestId()==o2.getRequestId()))) return 0;
       else return 0;
    }
}
