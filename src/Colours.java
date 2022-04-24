
//used for storing colour info
public class Colours {
	private String ID;
	private String name;
	private String img;
	
	public Colours(String ID, String name, String img) {
		this.ID = ID;
		this.name = name;
		this.img = img;

	}
	
	//getter methods
	public String getID() {
		return this.ID;
	}
	public String getImg() {
		return this.img;
	}
	public String getName() {
		return this.name;
	}

}