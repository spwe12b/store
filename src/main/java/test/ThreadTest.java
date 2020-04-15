package test;

public class ThreadTest extends Thread {
    public Integer i1;

    @Override
    public void run() {
        try{
        Demo.add();
        }catch (Exception e){
            e.printStackTrace();
        }
      // Demo.i++;
      // Demo.i.set(Demo.i.get()+1);
    }
}