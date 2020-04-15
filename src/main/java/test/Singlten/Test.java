package test.Singlten;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    public void say(){
        System.out.println("ha");
    }

    public static void main(String[] args) {
        Logger loggerFactory=LoggerFactory.getLogger(Test.class);
    }
}
