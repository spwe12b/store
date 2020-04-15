package test.Singlten;

public class T implements Runnable {
    @Override
    public void run() {
        Sin sin=Sin.getSin();
        System.out.println(sin);
    }
}
