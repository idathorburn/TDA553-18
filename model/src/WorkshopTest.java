import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WorkshopTest {

    private Workshop<Volvo240> workshop;
    private Volvo240 volvo;
    private Saab95 saab;

    @BeforeEach
    void setUp() {
        workshop = new Workshop<>(2);
        volvo = new Volvo240();
    }

    @Test
    void testAddCar() {
        workshop.addCar(volvo);
        Assertions.assertEquals(1, workshop.getSize());
    }

    @Test
    void testAddCarWhenFull() {
        workshop.addCar(volvo);
        workshop.addCar(new Volvo240());
        Assertions.assertThrows(IllegalStateException.class, () -> workshop.addCar(new Volvo240()));
    }

//    Testing workshop does not work with wrong car type

//    @Test
//    void testWrongCarType() {
//        saab = new Saab95();
//        workshop.addCar(saab);
//    }

    @Test
    void testRemoveCar() {
        workshop.addCar(volvo);
        Assertions.assertTrue(workshop.removeCar(volvo));
    }
}
