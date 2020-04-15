import javax.servlet.Servlet;

public interface carFactory extends Servlet {
    Car create();
}
