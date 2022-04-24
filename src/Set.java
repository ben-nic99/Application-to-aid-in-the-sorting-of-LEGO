import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Set implements Comparable<Set> {
	private String name;
	private String ID;
	private List<Part> parts;
	private int count;
	private int totalpart;
	private boolean confirmed;
	private String path_to_img;
	
	public Set(String ID, String name, int totpart, String img) {
		this.name = name;
		this.ID=ID;
		
		//counter for number of parts of the set found
		count = 1;
		
		//set up list of parts found in set
		List<Part> List = new ArrayList<Part>();
		parts = List;
		
		
		this.totalpart = totpart;
		confirmed = false;
		path_to_img = img;
	}
	
	public Set(String ID, String name, int totpart) { //constructor if no image of set
		this.name = name;
		this.ID=ID;
		count = 1;
		List<Part> List = new ArrayList<Part>();
		parts = List;
		this.totalpart = totpart;
		confirmed = false;
		
		//set image to a "no image available" image
		path_to_img = "https://rebrickable.com/static/img/nil.png";
	}

	public String tostring() {
		Double P = this.probability();
		
		//set to pint to two decimal places
		DecimalFormat df = new DecimalFormat("###.##");
		
		//Likelihood of ownership as well as name and ID of set to be shown
		return (df.format(P*100) + "% " + this.ID + " " + this.name);
		
	}
	
	public boolean check(Set rhs) {//check if already exists a version of this set
		return this.ID.equals(rhs.ID);//only need to check ID as this is unique to each set
	}
	
	public void addCount() {
		count = count + 1;
	}
	
	public int getCount() {
		return count;
	}
	
	public void addPart(Part p) {
		int itrs = 0;
		int tottype = (int) Math.ceil((double)totalpart / 100); //Implement the 1% assumption (each set is no more than 1% of each part type)
		
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).same(p)){
				itrs = itrs + 1;//count how many versions of that part currently in set calculation
			}
		}
		if (itrs <= tottype){//if within the 1%
			parts.add(p);
			
		}
		
		if(confirmed) {
			confirm(); //if set has already been found, confirm again to use up new part
		}
		
		
	}
	
	public Double probability() {
		
		//actual equation for probability has been removed for public version, instead shows percentage of a set owned.
		
		double prob = 0.0;
		if (totalpart != 0) {	
			prob = ((double) count/totalpart);
		}
		
		return prob;
		
	}

	@Override
	public int compareTo(Set o) {//compare method to compare sets based on likeliness 
		
		return o.probability().compareTo(this.probability());
	}
	
	public void confirm() {//to confirm that a set is in the box
		confirmed = true;
		for(int i = 0; i < parts.size(); i++) {
			parts.get(i).Setused(true);//remove all parts in set from future calculations (as used in the set)
		}
	}

	
	//getter methods
	public String getID() {
		
		return this.ID;
	}
	
	public String getimg() {
		return path_to_img;
	}
	
	
}
