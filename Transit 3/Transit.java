import java.util.ArrayList;

//import javax.xml.catalog.GroupEntry.PreferType;

//import javax.print.attribute.standard.Sides;

//import jdk.internal.org.jline.terminal.spi.Pty;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 * @return The zero node in the train layer of the final layered linked list
	 */

	 public static TNode makeList (int[] trainStations, int[] busStops, int[] locations) {
		 TNode walkHead = new TNode();
		 TNode busHead = new TNode();
		 TNode trainHead = new TNode();

		 for(int i=0; i<trainStations.length; i++){
			TNode newNode = new TNode(trainStations[i]);
			TNode temp = trainHead; 
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=newNode; 
		}
		 	 
		 for(int x=0; x<busStops.length; x++){
				TNode newNode = new TNode(busStops[x]);
				TNode temp = busHead; 
				while(temp.next!=null){
					temp=temp.next;
	
				}
				temp.next=newNode; 
		}
	
		for(int y=0; y<locations.length; y++){
			TNode newNode = new TNode(locations[y]);
			TNode temp = walkHead; 
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=newNode; 
		 }

		 setDownPointers(trainHead, busHead);
		 setDownPointers(busHead, walkHead);
	 
		 return trainHead;
}

private static void setDownPointers(TNode head, TNode head2){
	TNode ptr2=head2; 
	for(TNode ptr = head; ptr!=null; ptr=ptr.next){ 
		if(ptr.location==ptr2.location){
			ptr.down=ptr2; 
		}
	}
}


	/**
	 * Modifies the given layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param station The location of the train station to remove
	 */
	public static void removeTrainStation(TNode trainZero, int station) {
		TNode next = null;
		for(TNode ptr = trainZero; ptr!=null; ptr=ptr.next){
			next=ptr.next;
			if(next.location==station){
				ptr.next=ptr.next.next;
				break;
			}
			if(next.next==null){
				break;
			}
		}
	}

	/**
	 * Modifies the given layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param busStop The location of the bus stop to add
	 */
	public static void addBusStop(TNode trainZero, int busStop) {
		TNode busHead = trainZero.down;
		TNode walkHead = trainZero.down.down;
		for(TNode ptr = busHead; ptr!=null; ptr=ptr.next){
			TNode next=ptr.next; 
			if(ptr.location==busStop){
				break;
			}
			else if((ptr.location<busStop&&next.location>busStop)){
				TNode newNode = new TNode(busStop);
				ptr.next=newNode; 
				newNode.next=next;
				break;
			}
			else{
				walkHead=walkHead.next; 
			}
		}
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param destination An int representing the destination
	 * @return
	 */
	public static ArrayList<TNode> bestPath(TNode trainZero, int destination) {
		ArrayList<TNode> array = new ArrayList<TNode>();
		TNode busHead = trainZero.down;
		TNode walkHead = trainZero.down.down;
		TNode count = null; 
	
		for (TNode ptr = trainZero; ptr!=null; ptr=ptr.next){
			if((ptr.next==null)||(ptr.next.location>destination)){
				array.add(ptr);
				array.add(ptr.down);
				count=ptr;
				break;
			}
			array.add(ptr);
			
		}

		for(TNode ptr = busHead; ptr!=null; ptr=ptr.next){
			if((ptr.next==null)||(ptr.next.location>destination)){
				array.add(ptr);
				count=ptr;
				break;
			}
			if(ptr.location>=count.location){
				array.add(ptr);
			}
		}

		for(TNode ptr = walkHead; ptr!=null; ptr=ptr.next){
			
			if(ptr.location==destination){
				array.add(ptr);
				break;
			}
			if (ptr.location>=count.location){
				array.add(ptr);
			}
		}
		return array;

	}
/*
		for(int i=0; i<array.size()-1; i++){
			if((array.get(i).location)==(array.get(i+1).location)){
				array.add(i, array.get(i).down);
			}
		}

		return array;
	}
	*/
	
	
	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @return
	 */
	public static TNode duplicate(TNode trainZero) {
		TNode busHead = new TNode();
		TNode walkHead = new TNode(); 
		TNode trainHead = new TNode(); 

		for(TNode ptr = trainZero.next; ptr!=null; ptr=ptr.next){
			TNode newNode = new TNode(ptr.location);
			TNode temp = trainHead; 
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=newNode; 
		}
		for(TNode ptr = trainZero.down.next; ptr!=null; ptr=ptr.next){
			TNode newNode = new TNode(ptr.location);
			TNode temp = busHead; 
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=newNode; 
		}

		for(TNode ptr = trainZero.down.down.next;ptr!=null; ptr=ptr.next){
			TNode newNode = new TNode(ptr.location);
			TNode temp = walkHead; 
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=newNode; 
		}
		
		setDownPointers(trainHead, busHead);
		setDownPointers(busHead, walkHead);
		return trainHead;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public static void addScooter(TNode trainZero, int[] scooterStops) {
		TNode busHead = trainZero.down; 
		TNode walkHead = trainZero.down.down; 
		TNode scootHead = new TNode(); 

		for(int i=0; i<scooterStops.length; i++){
			TNode newNode = new TNode(scooterStops[i]);
			TNode temp = scootHead; 
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=newNode; 
		}
		setDownPointers(busHead, scootHead);
		setDownPointers(scootHead, walkHead);
		
	}
}