package day12;

public final class Region {
    char plant;
    int perimeter;
    int area;

    public Region(char plant, int perimeter, int area) {
        this.plant = plant;
        this.perimeter = perimeter;
        this.area = area;
    }

    @Override
    public String toString() {
        return "plant: "+ plant +", area: "+area+", perimeter: "+perimeter;
    }
}
