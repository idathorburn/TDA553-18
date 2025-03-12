import java.util.ArrayList;
import java.util.List;

public class Workshop<T extends Car> {

    private final int capacity;
    private final List<T> storedCars;

    public Workshop(int capacity) {
        this.capacity = capacity;
        this.storedCars = new ArrayList<>();
    }

    public void addCar(Car car) {
        if (storedCars.size() >= capacity) {
            throw new IllegalStateException("Workshop is full!");
        }
        try {
            storedCars.add((T) car);
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public boolean removeCar(T car) {
        return storedCars.remove(car);
    }

    public T getCar(int index) {
        return storedCars.get(index);
    }

    public int getSize() {
        return storedCars.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasPlaceLeft(){
        return getSize() < getCapacity();
    }
}