public class BackpackKeyListeners {
    public static EnigmaExtended enigma = new EnigmaExtended();
    private Maze maze;
    private Player player;

    public BackpackKeyListeners(Maze maze, Player player) {
        this.maze = maze;
        this.player = player;
    }

    public void listenItemUse() {
        Backpack backpack = player.getBackpack();
        if (backpack.isEmpty())
            return;

        enigma.console.getTextWindow().addKeyListener(enigma.klis);
        Coords coords = player.getCoords();

        SlotScanner slotScanner = new SlotScanner(coords, maze);
        Object[] peripherals = slotScanner.getPeripherals();

        if (enigma.keypr == 1) {
            int x = player.getCoords().getX(), y = player.getCoords().getY();
            switch (enigma.rkey) {
                case KeyBinds.A_KEY:
                    onItemUse(peripherals[3], new Coords(x - 1, y));
                    break;
                case KeyBinds.D_KEY:
                    onItemUse(peripherals[4], new Coords(x + 1, y));
                    break;
                case KeyBinds.W_KEY:
                    onItemUse(peripherals[1], new Coords(x, y - 1));
                    break;
                case KeyBinds.S_KEY:
                    onItemUse(peripherals[6], new Coords(x, y + 1));
                    break;
            }
        }

        maze.updateSlot(player);
    }

    private void onItemUse(Object overwritedEntity, Coords newCoords) {
        Backpack backpack = player.getBackpack();

        if (!(overwritedEntity instanceof Wall)) {
            Object itemToPlace = backpack.pop();
            if (itemToPlace instanceof Trap) {
                Trap newTrap = new Trap(newCoords);
                newTrap.setUsed(true);
                newTrap.handleTrappingPeripherals(maze, true);

                maze.updateSlot(newTrap);
                backpack.setUsedItems(Utils.pushObjectToArray(backpack.getUsedItems(), newTrap));
            }
            if (itemToPlace instanceof Warp) {
                Warp newWarp = new Warp(newCoords);
                newWarp.setUsed(true);
                maze.updateSlot(newWarp);
                backpack.setUsedItems(Utils.pushObjectToArray(backpack.getUsedItems(), newWarp));
            }
        }
    }
}
