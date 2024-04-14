
public class Backpack {
	private Stack items;
	private Object[] usedItems = {};

	public Backpack() {
		items = new Stack(8);
	}

	public void push(Object item) {
		items.push(item);
	}

	public Object pop() {
		return items.pop();
	}

	public boolean isFull() {
		return items.isFull();
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public int size() {
		return items.size();
	}

	public Object peek() {
		return items.peek();
	}

	public Object[] getUsedItems() {
		return usedItems;
	}

	public void setUsedItems(Object[] usedItems) {
		this.usedItems = usedItems;
	}
}
