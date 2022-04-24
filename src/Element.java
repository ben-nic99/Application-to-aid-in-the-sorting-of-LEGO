import java.util.List;

//used for inputting and searching parts, has different information to Part class which is used for calculations
public class Element {
	private String ID;
	private String name;
	private String img;
	private List<String> tags;
	
	public Element(String ID, String name, String img, List<String> tags) {
		this.ID = ID;
		this.name = name;
		this.img = img;
		this.tags = tags;
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
	public List<String> getTags() {
		return this.tags;
	}
}
