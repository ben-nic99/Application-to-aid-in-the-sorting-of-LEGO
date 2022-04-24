import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Sorter implements ActionListener { 

	private static List<Set> setList;
	private static List<Set> setconfirmed;
	
	
	private JLabel current_part;
	private JLabel current_col;
	private String PartID_to_send = "";
	private String colID_to_send = "";
	
	private JFrame frame;
	private JPanel panel_top;
	private JPanel panel_bottom;
	private JPanel panel_center;
	
	private JScrollPane scrollresults;
	private JPanel panel_results;
	
	private JScrollPane scrollcol;
	private JPanel panel_col;
	

	private JButton btn_home;
	private JButton btn_addBricks;
	private JButton btn_showProb;
	
	private JComboBox tag1;
	private JComboBox tag2;
	private JComboBox tag3;
	private JComboBox tag4;
	private JPanel tag_panel;
	
	private JPanel Probs;
	
	private static List<Element> input_parts;
	private static List<Colours> input_cols;
	
	public Sorter() {
		//set out main structure of GUI
		frame = new JFrame();
		panel_top = new JPanel();
		panel_bottom = new JPanel();
		panel_center = new JPanel();
		
		
		
		panel_top.setLayout(new FlowLayout());
		
		//add logo and "sorter" label
		ImageIcon Logo = new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
		JLabel logo_lab = new JLabel(Logo);
		JLabel txt = new JLabel("Sorter");
		panel_top.add(logo_lab);
		panel_top.add(txt);
	
		
		
		//bottom bar
		panel_bottom.setLayout(new FlowLayout());
		btn_home = new JButton("home");
		btn_addBricks = new JButton("add Bricks");
		btn_showProb = new JButton("show Probability");
		panel_bottom.add(btn_home);
		panel_bottom.add(btn_addBricks);
		panel_bottom.add(btn_showProb);
		
		//add actions for buttons to perform
		btn_home.addActionListener(this);
		btn_addBricks.addActionListener(this);
		btn_showProb.addActionListener(this);
		
		panel_center.setPreferredSize(new Dimension(512,384));
		
		//write out list of tags that can be searched by
		String[] tags = {"Add Tag", "Brick", "Plate", "Slope", "Wedge", "Curved", "Tile", "Round", "Square",  "1x", "2x", "3x", "4x", "Bracket", "Clip", "Modified", "Wheel", "Printed", "Plant", "Technic", "Bar", "Axle",  "Vehicle", "Minifig_part", "Animal", "Torso", "Legs", "Head", "Headgear", "Accessory", "Weapon"}; 
		tag1 = new JComboBox(tags);
		tag2 = new JComboBox(tags);
		tag3 = new JComboBox(tags);
		tag4 = new JComboBox(tags);
		
		//add behaviour to tag drop downs
		tag1.addActionListener(this);
		tag2.addActionListener(this);
		tag3.addActionListener(this);
		tag4.addActionListener(this);
		
		//create panel for tags
		tag_panel = new JPanel();
		tag_panel.setLayout(new FlowLayout());
		tag_panel.add(tag1);
		tag_panel.add(tag2);
		tag_panel.add(tag3);
		tag_panel.add(tag4);
		
		
		//add panels to frame
		frame.add(panel_top,BorderLayout.NORTH);
		frame.add(panel_bottom,BorderLayout.SOUTH);
		frame.add(panel_center,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("LEGO_Sorter");
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		
		//set up arrays for user selectables
		input_parts = new ArrayList<Element>();
		input_cols = new ArrayList<Colours>();
		
		
		new Sorter();
		

		try {
			
			
			//open file of bricks that can be input
			Path file = Path.of("bricks.json");
			
			String Bricks = Files.readString(file);
			
			
			
			JSONObject obj = new JSONObject(Bricks);
			
			JSONArray part = new JSONArray();
			part = (JSONArray) obj.get("Bricks");
			
			
			for(int i = 0; i<part.length(); i++) {//loop through all bricks in file converting then to Element objects
				JSONObject p = part.getJSONObject(i);
				
				JSONArray tags = new JSONArray();
				tags = (JSONArray) p.get("tags");
				String name_inp = (String) p.get("name");
				String ID_inp = (String) p.get("ID");
				String img_inp = (String) p.get("img");
				List<String> tags_inp = new ArrayList<String>();
				
				for (int j = 0; j<tags.length(); j++) {//loop through element tags
					tags_inp.add((String) tags.get(j));
					
				}
				Element e = new Element(ID_inp, name_inp,img_inp,tags_inp);
				
				//add new element to array of elements
				input_parts.add(e);
			}
		}
		catch(Exception e){//if file not found
			System.out.println("file not found");
		}
		
		
		try {
			
			//load file of possible colours
			Path file = Path.of("Colours.json");
		
			String Cols = Files.readString(file);
			
			
			
			JSONObject obj = new JSONObject(Cols);
			
			JSONArray colour = new JSONArray();
			colour = (JSONArray) obj.get("Colours");
			
			
			for(int i = 0; i<colour.length(); i++) {//loop through colours file converting to colours objects
				JSONObject c = colour.getJSONObject(i);
				
				String name_inp = (String) c.get("name");
				String ID_inp = (String) c.get("ID");
				String img_inp = (String) c.get("img");
			
				Colours cl = new Colours(ID_inp, name_inp,img_inp);
				
				input_cols.add(cl);
			}
		}
		catch(Exception e){
			System.out.println("file not found");
		}

		//set up lists for all sets and confirmed sets
		setList = new ArrayList<Set>();
		setconfirmed = new ArrayList<Set>();
		
		
		
	
			
	}
	
	
	
	public static void getSets(String partno, String col, List<Set> setlist) {
		try {
			//build up request to be sent
			String reqStr;
			reqStr = "https://rebrickable.com/api/v3/lego/parts/" + partno + "/colors/" + col + "/sets/?page_size=1000";
		
		
			//create part object for part entered
			Part p = new Part (partno,col);
		
		
			HttpClient client = HttpClient.newHttpClient();
			//set up client to send request
		
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(reqStr))
					.setHeader("Authorization", "key [rebrickable API key removed]").build();//authorise //would need a rebrickable API key re adding.
		
			//get responce from database
			String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
		
			//send response to be parsed 
			Parse(response,setlist,p);
		}
		catch(Exception e){//if can't send request for some reason (e.g.no Internet, no API key.)
			JOptionPane.showMessageDialog(null, "Can't connect to Database, Try checking your Internet connection");
		}
		
		
	}
	
	public static void Parse(String Response, List<Set> setlist, Part p) {
		
		
		JSONObject obj = new JSONObject(Response);
		//create JSON Object from file
		
		
		JSONArray sets = new JSONArray();
		//set up array for sets
		
		sets = (JSONArray) obj.get("results");
		//copy results over to the array
		
		p.SetNum(sets.length());
		
		for (int i = 0; i< sets.length(); i++) {
			//iterate through sets
			
			JSONObject set = sets.getJSONObject(i);
			//get each set separately
			
			
			Set s;
			try {//try to set with picture
				s = new Set(set.getString("set_num"), set.getString("name"), set.getInt("num_parts"), set.getString("set_img_url"));
			}
			catch(Exception e) {//if no image call other set constructor
				s = new Set(set.getString("set_num"), set.getString("name"), set.getInt("num_parts"));
			}
			
			boolean already = false;
			for(int j = 0; j<setlist.size();j++) {
				
				if (setlist.get(j).check(s)){//if already a version of that set, add to its count
					setlist.get(j).addCount();//increase parts of set found
					setlist.get(j).addPart(p);
					already = true;
					
				}
			}
			if(!already) {//if set does not exist in system
				s.addPart(p);//add part to set
				setlist.add(s);//add set to setlist
			
			}
		}
		
		
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {//handle actions for some buttons
		if (e.getSource()==btn_home)//home button
        {
			
		   //reset center of screen
		   panel_center.removeAll();
			
		   panel_center.revalidate();
		   panel_center.repaint();
		   
           
           tag_panel.setVisible(false);
           
           for(int i = 0;i <setconfirmed.size(); i++) {//loop through any confirmed sets
        	   JLabel p = new JLabel();
        	   p.setText(setconfirmed.get(i).tostring());
        	   panel_center.add(p);//show confirmed sets on screen
           }
           
           //tag_panel.
           frame.add(panel_center,BorderLayout.CENTER);
        }
		else if(e.getSource()==btn_addBricks) {//if add bricks button
			
			//reset center panel
			panel_center.removeAll();
			panel_center.revalidate();
			panel_center.repaint();
			
			panel_center.setLayout(new BoxLayout(panel_center, BoxLayout.Y_AXIS));
			
			
			tag_panel.setVisible(true);
			panel_results = new JPanel();
			
			//set scroll pane for bricks to be scrolled through
			scrollresults = new JScrollPane(panel_results, 
					   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
					   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_center.add(tag_panel);
			
			//set up scroll pane for colours
			panel_col = new JPanel();
			scrollcol = new JScrollPane(panel_col, 
					   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
					   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			scrollresults.setPreferredSize(new Dimension(panel_center.getWidth(), (panel_center.getHeight()/2)+500));
			
			current_part = new JLabel();
			current_col = new JLabel();
			
			
			
			panel_col.setLayout(new GridLayout(0,4));
			for (int i = 0; i< input_cols.size(); i++) {//loop through all colours
				
				//set up panel to display colour information
				JPanel colpan = new JPanel();
			    colpan.setLayout(new FlowLayout());
			    JPanel subcpan = new JPanel();
			    subcpan.setLayout(new BoxLayout(subcpan, BoxLayout.Y_AXIS));
			    JLabel colnam = new JLabel();
			    
			    //add image of colour
			    ImageIcon img = new ImageIcon(new ImageIcon(input_cols.get(i).getImg()).getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));
			    JLabel img_lab = new JLabel(img);
			    colnam.setText(input_cols.get(i).getName());
			    colpan.add(img_lab);
			    subcpan.add(colnam);
			    
			    //add button to select colour
			    JButton JB = new JButton(input_cols.get(i).getID());
			    
				JB.addActionListener(new ActionListener() {//when colour selected
			    	@Override
			    	public void actionPerformed(ActionEvent evt) {
			    		 
			    		 //set current selected colour to colour user selects
			    		 colID_to_send = JB.getText();
			    		 current_col.setText("colour " + JB.getText());
			    	}
				});
				subcpan.add(JB);
				colpan.add(subcpan);
				panel_col.add(colpan);
			}
			
			
			//scrollresults.add(panel_results);
			JPanel bottom_cent = new JPanel();
			JButton add = new JButton("add");//button to add part to calculations (if found in box)
			
			add.addActionListener(new ActionListener() {
		    	@Override
		    	public void actionPerformed(ActionEvent evt) {
		    		if ((PartID_to_send != "") && (colID_to_send != "")){
		    			
		    			//print what was added (used in user tests as report of actions user took)
		    			System.out.println("added " + PartID_to_send + " in colour " + colID_to_send);
		    			
		    			//call the method to add sets
		    			getSets(PartID_to_send,colID_to_send,setList);
		    			
		    			//reset selected part and colour
		    			current_part.setText("");
		    			current_col.setText("");
		    			PartID_to_send = "";
		    			colID_to_send = "";
		    			
		    			//reset tag boxes
		    			tag1.setSelectedIndex(0);
		    			tag2.setSelectedIndex(0);
		    			tag3.setSelectedIndex(0);
		    			tag4.setSelectedIndex(0);
		    		}
		    	}
			});
			
			
			//add all frames to screen
			bottom_cent.setLayout(new FlowLayout());
			bottom_cent.add(current_part);
			bottom_cent.add(current_col);
			bottom_cent.add(add);
			
			panel_center.add(scrollresults);
			panel_center.add(scrollcol);
			panel_center.add(bottom_cent);
			frame.add(panel_center,BorderLayout.CENTER);
			
			//call search method (looks for matching parts)
			Search();
		}
		else if(e.getSource()==btn_showProb) {//if show probability button pressed
			
			//reset screen
			panel_center.removeAll();
			panel_center.revalidate();
			panel_center.repaint();
			
			
			tag_panel.setVisible(false);
			
			
			panel_center.setLayout(new BoxLayout(panel_center, BoxLayout.Y_AXIS));
			
			
			
			
			Collections.sort(setList);//sort sets (by likelihood)
			for(int i = 0; i<setList.size(); i++) {//loop through all sets
				JLabel p = new JLabel();
				String Str_pr = "";
				
				Str_pr = (Str_pr + setList.get(i).tostring() + "\n");
				
				JButton show = new JButton("picture of " + setList.get(i).getID());//add button to show picture of set
				
				show.addActionListener(new ActionListener() {//event if button is pressed to get set image
			    	@Override
			    	public void actionPerformed(ActionEvent evt) {
			    		String ID = show.getText().substring(11);//isolate set ID
			    		
			    		for(int i = 0; i<setList.size(); i++) {
			    			
			    			
			    			 if (setList.get(i).getID().equals(ID)) {//search to find related set
			    				 
			    				 try {
			    					 	String path = setList.get(i).getimg();//get path set image
			    					 	
			    				    	URL url;
			    				    	
			    						url = new URL(path);
			    						
			    						BufferedImage image = ImageIO.read(url);//follow path to image
			    						
			    						ImageIcon imgicn = new ImageIcon(image.getScaledInstance(500, 500, Image.SCALE_SMOOTH));
			    						
			    						
			    						JLabel picLabel = new JLabel(imgicn);
			    						JOptionPane.showMessageDialog(null, picLabel, ID, JOptionPane.PLAIN_MESSAGE, null);//pop up box with image
			    				 }
			    				 catch(Exception e) {
			    					 
			    				 }
			    			 }
			    		}
			    	}
				});
				
				
				JButton confirm = new JButton(setList.get(i).getID());
				
				confirm.addActionListener(new ActionListener() {//button confirming a set is in the box
			    	@Override
			    	public void actionPerformed(ActionEvent evt) {
			    		 for(int i = 0; i<setList.size(); i++) {//loop through to find matching set
			    			 if (setList.get(i).getID() == confirm.getText()) {//once found set that has been confirmed
			    				 
			    				 //output (used for user testing to track user interactions
			    				 System.out.println("Confirmed " + setList.get(i).tostring());
			    				 
			    				 
			    				 setList.get(i).confirm();//call set confirm method
			    				 setconfirmed.add(setList.get(i));//put set on confirmed list
			    				 
			    				 //reset page as probabilities changed
			    				 panel_center.removeAll();
			    				 panel_center.revalidate();
			    				 panel_center.repaint();
			    				
			    			 }
			    		 }
			    	}
				});
				
				
				JPanel out = new JPanel();
				out.setLayout(new FlowLayout());
				
				//add buttons adjacent to each set in list
				out.add(p);
				out.add(show);
				out.add(confirm);
				
				//add to page
				p.setText(Str_pr);
				
				if(setList.get(i).probability() != 0) {//removes any sets with no probability 
					panel_center.add(out);
				}
				
				
				
			}
			
			
			
			frame.add(panel_center,BorderLayout.CENTER);
			
		}
		
		//if any of the drop downs, call search function
		else if(e.getSource()==tag1){
			Search();
		}
		else if(e.getSource()==tag2){
			Search();
		}
		else if(e.getSource()==tag3){
			Search();
		}
		else if(e.getSource()==tag4){
			Search();
		}
	}
	
	
	private void Search() {
		
		//reset page
		panel_results.removeAll();
		panel_results.revalidate();
		panel_results.repaint();
		
		
		panel_results.setLayout(new GridLayout(0,2));
		for (int i = 0; i< input_parts.size(); i++) {//loop through parts
			//search comparisons
			int match = 0;
			
			//accound for any un-set drop downs
			if (tag1.getSelectedItem().equals("Add Tag")) {
				match = match +1;
			}
			if (tag2.getSelectedItem().equals("Add Tag")) {
				match = match +1;
			}
			if (tag3.getSelectedItem().equals("Add Tag")) {
				match = match +1;
			}
			if (tag4.getSelectedItem().equals("Add Tag")) {
				match = match +1;
			}
			
			
			for(int j = 0; j <input_parts.get(i).getTags().size(); j++) {//loop through parts tags
				//check if any tags match
				if(input_parts.get(i).getTags().get(j).equals(tag1.getSelectedItem())) {
					match = match +1;
				}
				else if(input_parts.get(i).getTags().get(j).equals(tag2.getSelectedItem())) {
					match = match +1;
				}
				else if(input_parts.get(i).getTags().get(j).equals(tag3.getSelectedItem())) {
					match = match +1;
				}
				else if(input_parts.get(i).getTags().get(j).equals(tag4.getSelectedItem())) {
					match = match +1;
				}
			}
			
			if(match == 4) {//if part matches all set tags
				
			    
			    //set up panel for parts
			    JPanel partpan = new JPanel();
			    partpan.setLayout(new FlowLayout());
			    JPanel subpan = new JPanel();
			    subpan.setLayout(new BoxLayout(subpan, BoxLayout.Y_AXIS));
			    JLabel partnam = new JLabel();
			    ImageIcon img = new ImageIcon(new ImageIcon(input_parts.get(i).getImg()).getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));
			    JLabel img_lab = new JLabel(img);
			    partnam.setText(input_parts.get(i).getName());
			    partpan.add(img_lab);
			    subpan.add(partnam);
			    JButton JB = new JButton(input_parts.get(i).getID());
			    
				JB.addActionListener(new ActionListener() {//add the button to select the part.
			    	@Override
			    	public void actionPerformed(ActionEvent evt) {
			    		 
			    		 PartID_to_send = JB.getText();
			    		 current_part.setText("part " + JB.getText());
			    	}
				});
				
				subpan.add(JB);
				partpan.add(subpan);
				panel_results.add(partpan);
				
			}
		}
		
	}
}
