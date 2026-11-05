public class Demo {
    public static void main() {
        Config config1 = Config.getInstance();

        Config config2 = Config.getInstance();

        if(config1 == config2){
            System.out.println("Both are same!!");
        }
    }
}