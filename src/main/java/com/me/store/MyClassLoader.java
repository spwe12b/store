package com.me.store;

import java.io.File;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {

    public Class<?> loadClass(String path,String name)throws Exception{
            Class c =defineClass(name,load(path),0,load(path).length);
      return c;
    }
    private byte[] load(String path)throws Exception{
       File file=new File(path);
        FileInputStream fileInputStream=new FileInputStream(file);
        byte[] bytes=new byte[(int)file.length()];
        fileInputStream.read(bytes);
        return bytes;
    }

    public static void main(String[] args)throws Exception {
       MyClassLoader myClassLoader=new MyClassLoader();
       Class c=myClassLoader.loadClass("G:\\str.txt","str");
    }
    public void say(){
        System.out.println("hahaha");
    }
}
