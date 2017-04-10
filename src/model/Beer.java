package model;

public class Beer {

	String name, year, brewery, origin, type;

	public Beer(String name) {
		super();
		this.name = name;
	}

	public Beer(String name, String year, String brewery, String origin, String type) {
		super();
		this.name = name;
		this.year = year;
		this.brewery = brewery;
		this.origin = origin;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBrewery() {
		return brewery;
	}

	public void setBrewery(String brewery) {
		this.brewery = brewery;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
