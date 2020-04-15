public class BMW_Car_Factory implements carFactory {
    @Override
    public Car create() {
        return new BMW_car();
    }
}
