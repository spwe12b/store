package test.Singlten;

public class Sin {
    private Sin(){

    }
      private static Sin sin;
      public static Sin getSin(){
          if(sin==null){
              sin=new Sin();
          }
          return sin;
      }


}
