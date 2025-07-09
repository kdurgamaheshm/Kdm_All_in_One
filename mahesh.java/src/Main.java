class Vechicle{
    void car(){
        System.out.println("BMW car");
    }
}
class Vechicle2 extends Vechicle{
    @Override
    void car(){
        System.out.println("Tata car");
    }
}
public class Main {
    public static void main(String[] args) {
        Vechicle2 my_obj = new Vechicle2();
        my_obj.car();
        System.out.println("Hello, World!");
    }
}