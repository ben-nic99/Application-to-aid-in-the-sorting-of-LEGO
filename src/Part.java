

//used for the calculations in the system, as such only requires IDs and the number of sets, different from Element class which is used for inputs to the system
public class Part {
	private String ID;
	private String ColourID;
	private int numOfSets;
	private boolean used; //for tracking if the part is used in a set that has been confirmed
	
	
	public Part(String ID, String ColourID) {
		this.ID = ID;
		this.ColourID = ColourID;
	}
	
	public String getID(){
		return ID;
	}
	
	public String getColour() {
		return ColourID;
	}
	
	public void SetNum(int num){//number of sets part appears in
		this.numOfSets = num;
	}
	
	public int GetnumSet() {
		return numOfSets;
	}
	public void Setused(boolean used){//set if part has been used up (is in a confirmed set)
		this.used = used;
	}
	
	public Boolean Getused() {
		return used;
	}
	
	public Boolean same(Part p) {//check if two parts are of the same type
		return (ID.equals(p.ID) && ColourID.equals(p.ColourID));
	}
}
