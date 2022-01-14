public class myclass {
    String s = "zuahib";
    myclass(String str){
        this.s = str;
        System.out.println(s);
    }

    public static void main(String[] args) {
        myclass obj = new myclass("zubi");
        System.out.println(obj.s);

        myclass obj1 = new myclass("zubinew");
        System.out.println(obj1.s);



    }
}
