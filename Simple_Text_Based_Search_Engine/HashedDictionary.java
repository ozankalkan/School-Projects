import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary<K, V> implements DictionaryInterface<K, V> {
	private TableEntry<K, V>[] hashTable; 
	private int numberOfEntries;
	private int locationsUsed; 
	private static final int DEFAULT_SIZE = 2477; 
	private static final double MAX_LOAD_FACTOR = 0.5;

	public HashedDictionary() {
		this(DEFAULT_SIZE); 
	} 

	@SuppressWarnings("unchecked")
	public HashedDictionary(int tableSize) { // Creates a hash table with a default size of 2477.
		int primeSize = getNextPrime(tableSize);
		if(primeSize >= DEFAULT_SIZE) 
			hashTable = new TableEntry[primeSize];
		else
			hashTable = new TableEntry[DEFAULT_SIZE];
		numberOfEntries = 0;
		locationsUsed = 0;
	}
	
	// Starting from the index corresponding to the key, it searches until it finds the same word or encounters a null index and returns how many indexes it looked at.
	public int collisionCount(K key, String word) {
		
		int i = 0;
		boolean found = false;
		int index = getDoubleHashIndex(key, i);
		//int index = getHashIndex(key); //LP
		
		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn() && key.equals(hashTable[index].getKey())) {
				AList listValue = (AList) hashTable[index].getValue();
				ListEntry entry = (ListEntry) listValue.getEntry(1); // 
				String indexWord = entry.getWord();
				
				if(word.equals(indexWord)) // If the keys are the same, it checks whether the word in the entries of the array list in the value and the newly added word are the same.
					found = true;
				else { // If the words are not the same, a new index is obtained with the double hash function.
					
					i = i + 1; // The variable i creates the coefficient in the double hash function and keeps the number of collisions.
					index = getDoubleHashIndex(key, i); 
					
					/*
					i = i + 1;
					index = (index + 1) % hashTable.length; // For Linear Probing
					*/
				}
			}
			else { // Even if the keys are different, it enters this step when the index is full.
				
				i = i + 1;
				index = getDoubleHashIndex(key, i);
				
				/*
				i = i + 1;
				index = (index + 1) % hashTable.length; // For Linear Probing
				*/
			}
		} 
		return i;
	}

	public boolean isPrime(int num) {
		boolean prime = true;
		for (int i = 2; i <= num / 2; i++) {
			if ((num % i) == 0) {
				prime = false;
				break;
			}
		}
		return prime;
	}

	public int getNextPrime(int num) {
		if (num <= 1)
            return 2;
		else if(isPrime(num))
			return num;
        boolean found = false;   
        while (!found)
        {
            num++;     
            if (isPrime(num))
                found = true;
        }     
        return num;
	}

	public V add(K key, V value) {
		V oldValue; 
		
		int index = getHashIndex(key);
		AList valueList = (AList) value;
		String word = null;
		if(!valueList.isEmpty()) {
			ListEntry entry = (ListEntry) valueList.getEntry(1);
			word = entry.getWord(); // The word is obtained from the array list in value.
		}
		
		
		index = probe(index, key, word); // The key, value and newly added word are sent to the probe function.

		if ((hashTable[index] == null) || hashTable[index].isRemoved()) { 
			hashTable[index] = new TableEntry<K, V>(key, value);
			numberOfEntries++;
			locationsUsed++;
			oldValue = null;
		} else { 
			oldValue = hashTable[index].getValue();
			hashTable[index].setValue(value);
		} 
		
		if (isHashTableTooFull())
			rehash();
		return oldValue;
	}

	private int getHashIndex(K key) {
		int hashIndex = getHashCode(key) % hashTable.length;
		if (hashIndex < 0)
			hashIndex = hashIndex + hashTable.length;
		return hashIndex;
	}
	
	public int getHashCode(K key) { // Returns the integer value of the key.
		int hashCode = (int) key;
		return hashCode;
	}

	public boolean isHashTableTooFull() {
		double load_factor = (double) locationsUsed / (double) hashTable.length;
		if (load_factor >= MAX_LOAD_FACTOR)
			return true;
		return false;
	}

	public void rehash() {
		
		TableEntry<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(2 * oldSize);
		TableEntry<K,V>[] temp = (TableEntry<K, V>[]) new TableEntry[newSize];
		hashTable = temp; 
		numberOfEntries = 0; 
		locationsUsed = 0;
		

		for (int index = 0; index < oldSize; index++) { // All entries in the old table are added to the new table according to their new index
			if((oldTable[index] != null) && (oldTable[index].isIn())) {
				add(oldTable[index].getKey(), oldTable[index].getValue());
			}
		}
	}

	private int probe(int index, K key, String word) {
		boolean found = false;
		int removedStateIndex = -1;
		int i = 0;
		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn()) {
				if (key.equals(hashTable[index].getKey())) { // if the keys are the same, the words are checked
					AList listValue = (AList) hashTable[index].getValue();
					ListEntry entry = (ListEntry) listValue.getEntry(1);
					String indexWord = entry.getWord();
					
					if(word.equals(indexWord)) // if the words are the same, we say that this word has been added to the table before.
						found = true;
					else {
						
						i = i + 1;
						index = getDoubleHashIndex(key, i);
						
						/*
						index = (index + 1) % hashTable.length; // For Linear Probing
						*/
					}
						
				}
					 
				else {
					
					i = i + 1;
					index = getDoubleHashIndex(key, i);
					
					/*
					index = (index + 1) % hashTable.length; // For Linear Probing
					*/
				}
			} 
			else // The first removed or null index encountered when searching for a word is stored.
			{
				if (removedStateIndex == -1)
					removedStateIndex = index;
				
				i = i + 1;
				index = getDoubleHashIndex(key, i);
				
				/*
				index = (index + 1) % hashTable.length; // For Linear Probing
				*/
			} 
		} 
		if (found || (removedStateIndex == -1))
			return index; 
		else
			return removedStateIndex; 
	}

	public V remove(K key, String word) {
		V removedValue = null;
		
		int index = getHashIndex(key);
		
		/* if the remove needs to get Value instead of word. ("String word" becomes "V value"). !!!
		AList valueList = (AList) value;
		String word = null;
		if(!valueList.isEmpty()) {
			ListEntry entry = (ListEntry) valueList.getEntry(1);
			word = entry.getWord();
		}
		*/
		
		index = locate(index, key, word);
		
		if (index != -1) { 
			removedValue = hashTable[index].getValue();
			hashTable[index].setToRemoved();
			numberOfEntries--;
		} 
		return removedValue;
	}

	private int locate(int index, K key, String word) { // returns the index with the same key and the same word.
		boolean found = false;
		int i = 0;
		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn() && key.equals(hashTable[index].getKey())) {
				AList listValue = (AList) hashTable[index].getValue();
				ListEntry entry = (ListEntry) listValue.getEntry(1);
				String indexWord = entry.getWord();
				
				if(word.equals(indexWord))
					found = true;
				else {
					
					i = i + 1;
					index = getDoubleHashIndex(key, i);
					
					/*
					index = (index + 1) % hashTable.length; // For Linear Probing
					*/
				}
			}
			else {
				
				i = i + 1;
				index = getDoubleHashIndex(key, i);
				
				/*
				index = (index + 1) % hashTable.length; // For Linear Probing
				*/
			}
		} 
		int result = -1;
		if (found)
			result = index;
		return result;
	}

	public V getValue(K key, String word) {
		V result = null;
		int index = getHashIndex(key);
		index = locate(index, key, word);
		
		if (index != -1)
			result = hashTable[index].getValue(); 
		return result;
	}

	public boolean contains(K key, String word) { // returns whether the word is in the hash table or not
		int index = getHashIndex(key);
		
		index = locate(index, key, word);
		if (index != -1)
			return true;
		return false;
	}

	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	public int getSize() {
		return numberOfEntries;
	}

	public void clear() {
		while(getKeyIterator().hasNext()) {
			Iterator<V> valueIterator = getValueIterator();
			AList value = (AList)valueIterator.next();
			ListEntry entry =(ListEntry) value.getEntry(1);
			String word = entry.getWord();
			
			
			remove(getKeyIterator().next(), word);		
		}
	}
	
	public boolean search(K key, String word) { // searches for the word in the hash table.
		int index = getHashIndex(key);
		index = locate(index, key, word);
		if (index != -1)
			return true;
		return false;
		
	}
	
	public void indexTable() { // it shows the hash table on the screen in the form of "index - key - word".
		for (int index = 0; index < hashTable.length; index++) {
			if((hashTable[index] != null) && (hashTable[index].isIn())) {
				AList list = (AList)hashTable[index].getValue();
				ListEntry entry =(ListEntry) list.getEntry(1);
				System.out.print(index + " - " + hashTable[index].getKey() +" - " + entry.getWord());
				System.out.println("");
				}
			}
	}
	
	public int getLength() {
		return hashTable.length;
	}
	
	public double getLoadFactor() {
		double load_factor = (double) locationsUsed / (double) hashTable.length;
		return load_factor;
	}
	
	private int getDoubleHashIndex(K key , int i) { // it takes a key and "i" coefficient and returns the result of the double hash function accordingly.
		
		int hashIndex = ((getHashCode(key) % hashTable.length) + (i * (31 - (getHashCode(key) % 31)))) % hashTable.length;
		if (hashIndex < 0)
			hashIndex = hashIndex + hashTable.length;
		return hashIndex;
	}
	
	public Iterator<K> getKeyIterator() {
		return new KeyIterator();
	}

	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}

	private class TableEntry<S, T> {
		private S key;
		private T value;
		private boolean inTable;

		private TableEntry(S key, T value) {
			this.key = key;
			this.value = value;
			inTable = true;
		}

		private S getKey() {
			return key;
		}

		private T getValue() {
			return value;
		}

		private void setValue(T value) {
			this.value = value;
		}

		private boolean isRemoved() {
			return inTable == false;
		}

		private void setToRemoved() {
			inTable = false;
		}

		private void setToIn() {
			inTable = true;
		}

		private boolean isIn() {
			return inTable == true;
		}
	}

	private class KeyIterator implements Iterator<K> {
		private int currentIndex; 
		private int numberLeft; 

		private KeyIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} 

		public boolean hasNext() {
			return numberLeft > 0;
		} 

		public K next() {
			K result = null;
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				result = hashTable[currentIndex].getKey();
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		} 

		public void remove() {
			throw new UnsupportedOperationException();
		} 
	}
	
	private class ValueIterator implements Iterator<V> {
		private int currentIndex; 
		private int numberLeft; 

		private ValueIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} 

		public boolean hasNext() {
			return numberLeft > 0;
		} 

		public V next() {
			V result = null;
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				result = hashTable[currentIndex].getValue();
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		} 

		public void remove() { // Because the interface's remove does not take words, I created a new remove method that also takes words as parameters, and the interface's method was not used.
			throw new UnsupportedOperationException();
		} 
	}

	
}
