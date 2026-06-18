import controller.LojaController;
import view.LojaView;

public class Main {
    public static void main(String[] args) {
        LojaController lojaController = new LojaController();
        LojaView view = new LojaView(lojaController);
        view.iniciarMenu();
    }
}