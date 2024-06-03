package Controller;

import View.ViewAPI;
import Model.ModelAPI;

public class ControllerAPI {

	private static ControllerAPI instance;

    private ModelAPI model;
    private ViewAPI view;

    private ControllerAPI() {
    }

    public static ControllerAPI getInstance() {
		/** Metodo que retorna a unica instancia de ControllerAPI. */
		if (instance == null) {
			instance = new ControllerAPI();
		}
		return instance;
	}

    public void inicializa() {
        model = ModelAPI.getInstance();
        view = ViewAPI.getInstance();
        view.inicializaNomes();
        view.inicializaJogo(model.getTabuleiro1(), model.getTabuleiro2(), model.getTabuleiroOculto1(), model.getTabuleiroOculto2());
    }
}
