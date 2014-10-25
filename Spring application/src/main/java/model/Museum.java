package model;

public class Museum {

    private final long id;
    private final String name;
    private final Float latitude;
    private final Float longitude;

    public Museum(long id, String name, Float latitude, Float longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public Float getlatitude() {
        return latitude;
    }
    
    public Float getlongitude() {
        return longitude;
    }
}
