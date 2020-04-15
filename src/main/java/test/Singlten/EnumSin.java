package test.Singlten;

public enum  EnumSin {
      instance{
        public void say(){
            System.out.println("哈哈哈");
        }
      };
      public static EnumSin getInstance(){
          return instance;
      }


}
