import java.util.Arrays;

public class AList<T> implements ListInterface<T> {
	private T[] list; 
	private int numberOfEntries;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 100000;

	public AList() {
		this(DEFAULT_CAPACITY);
	} // end default constructor

	@SuppressWarnings("unchecked")
	public AList(int initialCapacity) {
		
		if (initialCapacity < DEFAULT_CAPACITY)
			initialCapacity = DEFAULT_CAPACITY;
		else 
			checkCapacity(initialCapacity);

		list = (T[]) new Object[initialCapacity + 1];
		numberOfEntries = 0;
	} 

	public void add(T newEntry) {
		add(numberOfEntries + 1, newEntry);
	} 

	private void checkCapacity(int capacity) {
		if (capacity > MAX_CAPACITY)
			throw new IllegalStateException(
					"Attempt to create a list " + "whose capacity exceeds " + "allowed maximum.");
	}

	public void add(int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries + 1)) {
			if (givenPosition <= numberOfEntries) 
				makeRoom(givenPosition); // otherwise make room for this entry
			list[givenPosition] = newEntry;
			numberOfEntries++;
			ensureCapacity(); // Ensure enough room for next add
		} else
			throw new IndexOutOfBoundsException("Given position of add's new entry is out of bounds.");
	} 

	// Makes room for a new entry at newPosition.
	private void makeRoom(int givenPosition) {
		int newIndex = givenPosition;
		int lastIndex = numberOfEntries;

		for (int index = lastIndex; index >= newIndex; index--)
			list[index + 1] = list[index];
	} 

	// Doubles the capacity of the array list if it is full.
	private void ensureCapacity() {
		int capacity = list.length - 1;
		if (numberOfEntries >= capacity) {
			int newCapacity = 2 * capacity;
			checkCapacity(newCapacity);
			list = Arrays.copyOf(list, newCapacity + 1);
		} 
	} 

	public T remove(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			T result = list[givenPosition]; 

			if (givenPosition < numberOfEntries) 
				removeGap(givenPosition);
			list[numberOfEntries] = null;
			numberOfEntries--;
			return result; 
		} else
			throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
	} 

	// Shifts entries that are beyond the entry to be removed to the
	private void removeGap(int givenPosition) {
		int removedIndex = givenPosition;
		for (int index = removedIndex; index < numberOfEntries; index++)
			list[index] = list[index + 1];
	} 

	public void clear() {
		// Clear entries but retain array; no need to create a new array
		for (int index = 1; index <= numberOfEntries; index++) 
			list[index] = null;

		numberOfEntries = 0;
	} 

	public T replace(int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			T originalEntry = list[givenPosition];
			list[givenPosition] = newEntry;
			return originalEntry;
		} else
			throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
	}

	public T getEntry(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			return list[givenPosition];
		} else
			throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
	} 

	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries];
		for (int index = 0; index < numberOfEntries; index++) {
			result[index] = list[index + 1];
		} 

		return result;
	} 

	public boolean contains(T anEntry) {
		boolean found = false;
		int index = 1;
		while (!found && (index <= numberOfEntries)) {
			if (anEntry.equals(list[index])) {
				found = true;
				break;
			}				
			index++;
		} 
		return found;
	} 

	public int getLength() {
		return numberOfEntries;
	}

	public boolean isEmpty() {
		return numberOfEntries == 0; 
	} 
	

} 
