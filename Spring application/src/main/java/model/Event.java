package model;

public class Event {

	private final long id;
	private final String name;
	private final String beginning;
	private final String end;
	private final float cost;
	private final Float latitude;
	private final Float longitude;

	public Event(long id, String name, String beginning, String end, float cost, Float latitude, Float longitude) {
		this.id = id;
		this.name = name;
		this.beginning = beginning;
		this.end = end;
		this.cost = cost;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBeginning() {
		return beginning;
	}

	public String getEnd() {
		return end;
	}

	public double getCost() {
		return cost;
	}

	public Float getLatitude() {
		return latitude;
	}

	public Float getLongitude() {
		return longitude;
	}
}
