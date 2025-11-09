package project;

public class Main {
    public static void main(String[] args) {

        Menu menu = null;
        try {
            menu = new Menu();
            menu.run();
        } finally {
            if (menu != null) {
                menu.shutdown();
            }
        }
    }


}