
public class SingleLinkedList {
	Node head;
	
	public void add(Object data) {
		if(head == null) {
			Node newNode = new Node(data);
			head = newNode;
		}
		else {
			Node temp = head;
			while(temp.getLink() != null) {
				temp = temp.getLink();
			}
			Node newNode = new Node(data);
			temp.setLink(newNode);
		}
	}
	
	public void sortedAdd(Contestant data) {
		Node temp = head;
		Node previous = null;
		int dataScore = data.getScore();
		Contestant headContestant = null;
		
		if(head != null) {
			headContestant = (Contestant) head.getData();
		}
		
		if(head == null) { // if the list is empty
			Node newNode = new Node(data);
			head = newNode;
		}
		else if (dataScore > headContestant.getScore()) { //if the inserted element greater than the first element
			Node newNode = new Node(data);                
			newNode.setLink(head);
			head = newNode;
		}
		else {
			Contestant tempContestant = (Contestant)temp.getData();
			while(temp != null && dataScore <= (int)tempContestant.getScore() ) {
				previous = temp;
				temp = temp.getLink();
				if(temp != null) {
					tempContestant = (Contestant)temp.getData();
				}
			}
			
			if(temp == null && previous != null) { // Inserts to the end of the list
				Node newNode = new Node(data);
				previous.setLink(newNode);
			}
			else if (previous != null) { // Inserts between two nodes
				Node newNode = new Node(data);
				newNode.setLink(temp);
				previous.setLink(newNode);
			}
		}
	}
	
	public int size() {
		if(head == null) {
			return 0;
		}
		else {
			int count = 0;
			Node temp = head;
			
			while(temp != null) {
				temp = temp.getLink();
				count++;				
			}
			
			return count;
		}
	}
	
	public void display() {
		if(head == null) {
			System.out.println("List is empty!");
		}
		else {
			Node temp = head;
			while(temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
		}
	}
	
	public void sortedDisplay() {
		if(head == null) {
			System.out.println("List is empty!");
		}
		else {
			Node temp = head;
			int counter = 0;
			while(temp != null) {
				Contestant tempContestant =(Contestant)temp.getData(); 
				System.out.println(tempContestant.getName() + " " + tempContestant.getScore());
				counter ++;
				if(counter == 10)
					break;
				temp = temp.getLink();
			}
		}
	}
	
	public void remove(Object dataToDelete) {
		if(head == null) {
			System.out.println("linked list is empty");
		}
		else {
			while((Integer)head.getData() == (Integer)dataToDelete)
				head = head.getLink();
			Node temp = head;
			Node previous = null;
			while(temp != null) {
				if((Integer)temp.getData() == (Integer)dataToDelete) {
					previous.setLink(temp.getLink());
					temp = previous;
				}
				previous = temp;
				temp = temp.getLink();
			}
		}
		
	}
	
	public void removeOneElement(Object dataToDelete) { // removes only the first of the desired element to be removed.
		if(head == null) {
			System.out.println("linked list is empty");
		}
		else {
			if((Integer)head.getData() == (Integer)dataToDelete) {
				head = head.getLink();
			}
			else {
				Node temp = head;
				Node previous = null;
				while(temp != null) {
					if((Integer)temp.getData() == (Integer)dataToDelete) {
						previous.setLink(temp.getLink());
						temp = previous;
						break;
					}
					previous = temp;
					temp = temp.getLink();
				}
			}
		}
	}
	
	public boolean search(Object data) {
		if(head == null) {
			System.out.println("List is empty");
			return false;
		}
		else {
			Node temp = head;
			while(temp != null) {
				if((Integer)temp.getData() == (Integer)data) {
					return true;
				}
				temp = temp.getLink();
			}
			return false;
		}
	}
	
	public boolean searchSameElements(Object data) { // checks if there are four of the same number for yahtzee situation
		if(head == null) {
			return false;
		}
		else {
			Node temp = head;
			int sameElementCounter = 0;
			while(temp != null) {
				if((Integer)temp.getData() == (Integer)data) {
					sameElementCounter++;
				}
				temp = temp.getLink();
			}
			if(sameElementCounter >= 4)
				return true;
			else
				return false;
			
		}
	}
	
	public boolean searchSixConsecutiveNumbers() { // checks the large straight situation
		for(int i = 1; i <= 6; i++) {
			if(search(i) == false) {
				return false;
			}
		}
		return true;
	}

	public Object getElement(int x) { // returns the item in the desired index
		if(head == null)
		{
			System.out.println("List is empty");
			return null;
		}
		else if(x > size() || x < 0 ){
			System.out.println("Index is out of range");
			return null;
		}
		else {
			Node temp = head;
			int count = 1;
			while(temp != null) {
				if(x == count)
					return temp.getData();
				temp = temp.getLink();
				count++;
			}
			return null;
		}

	}
}
