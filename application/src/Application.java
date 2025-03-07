public class Application {
    public static void main(String[] args) {
        var carView = new CarView("CarSim 1.0");

        var envMan = new EnvironmentManager();
        var imgMan = new ImageManager();
        imgMan.loadImages();
        var simMan = new SimulationManager();
        var carMan = new CarManager(simMan, envMan, imgMan);
        carMan.addObserver(carView);

        CarController cc = new CarController(carView, carMan);
    }
}
