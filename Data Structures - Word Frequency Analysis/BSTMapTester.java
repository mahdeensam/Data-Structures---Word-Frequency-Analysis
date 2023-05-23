/*!SECTION
 * Mahdeen Ahmed Khan Sameer
 * BSTMapTester Class: To test BSTMap.java
 */

import java.util.Comparator;
import java.math.BigDecimal;

public class BSTMapTester {
    
    public static void test1(){
        System.out.println("Test1: Basic Operations");
        BSTMap<Integer, String> map = new BSTMap<>();

        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        System.out.println("Expected: one, Actual: " + map.get(1));
        System.out.println("Expected: two, Actual: " + map.get(2));
        System.out.println("Expected: three, Actual: " + map.get(3));

        System.out.println("Expected: 3, Actual: " + map.size());

        map.remove(1);
        System.out.println("Expected: null, Actual: " + map.get(1));
        System.out.println("Expected: 2, Actual: " + map.size());
    }

    public static void test2(){
        System.out.println("\nTest2: Custom Comparator");
        Comparator<BigDecimal> bigDecimalComparator = Comparator.comparingDouble(BigDecimal::doubleValue);

        BSTMap<BigDecimal, String> map = new BSTMap<>(bigDecimalComparator);
        map.put(new BigDecimal("1.0"), "one");
        map.put(new BigDecimal("2.0"), "two");
        map.put(new BigDecimal("3.0"), "three");

        System.out.println("Expected: one, Actual: " + map.get(new BigDecimal("1.0")));
        System.out.println("Expected: two, Actual: " + map.get(new BigDecimal("2.0")));
        System.out.println("Expected: three, Actual: " + map.get(new BigDecimal("3.0")));
    }

    public static void main(String[] args){
        test1();
        test2();
    }
}
