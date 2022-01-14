import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapSort {
    public static void main(String[] args) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("zubi","home");
        hashMap.put("afaq","office");
        //Set entry = hashMap.entrySet();

        List list = new LinkedList(hashMap.entrySet());

        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2)
            {
                return ((Comparable) ((Map.Entry) (o2)).getKey()).compareTo(((Map.Entry) (o1)).getKey());
            }
        });

        LinkedHashMap<String,String> sorted = new LinkedHashMap<>();

        for (Object entry1 : list) {
            sorted.put(((Map.Entry<String,String>)entry1).getKey(), ((Map.Entry<String,String>)entry1).getValue());
        }

        System.out.println("*****************After custom comparator->***************"+sorted);

        Comparator<Map.Entry<String, String>> valueComparator = new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> e1, Map.Entry<String, String> e2) {
                String v1 = e1.getKey();
                String v2 = e2.getKey();
                return v2.compareTo(v1);
            } };

        Collections.sort(list,valueComparator);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for(Object entryList : list){
            linkedHashMap.put(((Map.Entry<String,String>)entryList).getKey(), ((Map.Entry<String,String>)entryList).getValue());
        }

        System.out.print("*******************Custom comparator after usval methid->****************** ");
        System.out.println(linkedHashMap);

        }
    }
