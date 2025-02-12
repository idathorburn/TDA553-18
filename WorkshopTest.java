import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WorkshopTest {

    private Workshop<Car> workshop;
    private Car volvo;

    @BeforeEach
    void setUp() {
        workshop = new Workshop<>(2);
        volvo = new Volvo240();
    }

    @Test
    void testAddCar() {
        workshop.addCar(volvo);
        assertEquals(1, workshop.getSize());
    }

    @Test
    void testAddCarWhenFull() {
        workshop.addCar(volvo);
        workshop.addCar(new Volvo240());
        assertThrows(IllegalStateException.class, () -> workshop.addCar(new Volvo240()));
    }

    @Test
    void testRemoveCar() {
        workshop.addCar(volvo);
        assertTrue(workshop.removeCar(volvo));
    }
}
