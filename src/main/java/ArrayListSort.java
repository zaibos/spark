import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayListSort {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<Integer>(Arrays.asList(4,6,2,1,3,0,8,5,9));
        Object[] arr = list.toArray();

        //Collections.sort(list,Collections.reverseOrder());

        for (Object integer : arr) {
            System.out.println(integer);
        }

    }
}
