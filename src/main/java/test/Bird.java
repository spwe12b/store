package test;

public abstract class Bird implements IFlyAnimal {
    @Override
    public void eat() {

    }

    @Override
    public void fly() {
        System.out.println("鸟类有飞翔的能力");
    }
}
