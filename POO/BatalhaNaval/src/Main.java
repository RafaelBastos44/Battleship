import Controller.ControllerAPI;

public class Main {
    public static void main(String[] args) {
        // Criação do Controller
        ControllerAPI controller = ControllerAPI.getInstance();

        // Inicialização da aplicação
        controller.inicializa();
    }
}