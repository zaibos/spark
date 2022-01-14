//@FunctionalInterface
interface base{
    default void base1(){
        System.out.println("base constructor");
    }

}

//class derived extends base{
//    derived(){
//        System.out.println("dervide const");
//    }
//    derived(int x, int y){
//        super(x);
//        System.out.println("derived const-> " +x+" y->"+ y);
//    }
//}

class zuhaib implements base{
//    zuhaib(int x, int y, int z){
//        super(x,y);
//        System.out.println("zuhaib->" + x+" y->" + y+ "z-> "+z);
//    }

    @Override
    public void base1(){
        System.out.println("zuhaib");;
    }

//    @Override
//    public void base(){
//
//    }
}

public class Prep {
    public static void main(String args[]){
        zuhaib z = new zuhaib();
        z.base1();
    }
}
