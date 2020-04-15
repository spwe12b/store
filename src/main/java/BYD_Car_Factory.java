public class BYD_Car_Factory implements carFactory {
    @Override
    public Car create() {
        return new BYD_car();
    }
}
