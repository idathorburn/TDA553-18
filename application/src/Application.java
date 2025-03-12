import java.awt.*;

public class Application {
    public static void main(String[] args) {
        var carView = new CarView("CarSim 1.0");

        var envMan = new EnvironmentManager();
        envMan.workshops.put(new Workshop<Volvo240>(1), new Point(300,275));
        var imgMan = new ImageManager();
        imgMan.loadImages();
        var simMan = new SimulationManager();
        var carMan = new CarManager(simMan, envMan, imgMan, carView);

        carMan.createCar(Volvo240.class, new Point(0,0));
        carMan.createCar(Saab95.class, new Point(0,100));
        carMan.createCar(Scania.class, new Point(0,200));
        carMan.createCar(Volvo240.class, new Point(0,300));
        carMan.createCar(Volvo240.class, new Point(100,300));

        CarController cc = new CarController(carView, carMan);

        simMan.startSimulation();
    }
}
