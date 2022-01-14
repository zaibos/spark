import java.util.*;

public class SynchronizedMap {
    public static void main(String[] args) {
        HashMap<Integer,String> hashMap = new HashMap<>();
        hashMap.put(3,"farha");
        hashMap.put(2,"anshu");
        hashMap.put(1,"zuhaib");
        Map map = Collections.synchronizedMap(hashMap);
        Set set = map.entrySet();
        synchronized (map){
            Iterator itr = set.iterator();
            while(itr.hasNext()){
                Map.Entry pair = (Map.Entry)itr.next();
                System.out.println(pair.getKey()+"="+pair.getValue());
            }
        }


    }
}
