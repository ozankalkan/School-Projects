public class PlayerKeyListeners {
    public static EnigmaExtended enigma = new EnigmaExtended();
    private Maze maze;
    private Player player;

    public PlayerKeyListeners(Maze maze, Player player) {
        this.maze = maze;
        this.player = player;
    }

    public void listenPlayerMove() {
        enigma.console.getTextWindow().addKeyListener(enigma.klis);
        Coords prevCoords = player.getCoords();
        Coords newCoords = prevCoords;

        SlotScanner slotScanner = new SlotScanner(prevCoords, maze);
        Object[] peripherals = slotScanner.getPeripherals();

        if (enigma.keypr == 1) {
            switch (enigma.rkey) {
                case KeyBinds.LEFT_KEY_ARROW:
                    newCoords = onMoveLeft(peripherals[3], prevCoords);
                    break;
                case KeyBinds.RIGHT_KEY_ARROW:
                    newCoords = onMoveRight(peripherals[4], prevCoords);
                    break;
                case KeyBinds.UP_KEY_ARROW:
                    newCoords = onMoveUp(peripherals[1], prevCoords);
                    break;
                case KeyBinds.DOWN_KEY_ARROW:
                    newCoords = onMoveDown(peripherals[6], prevCoords);
                    break;
            }
        }

        if (!Coords.isCoordsSame(prevCoords, newCoords)) {
            player.setCoords(newCoords);
            maze.updateSlot(player);
            maze.updateSlot(new Space(prevCoords.getX(), prevCoords.getY()));
        }
    }

    private Coords onMoveLeft(Object peripheral, Coords prevCoords) {
        if (Entities.isCollectible(peripheral) && player.getBackpack().isFull()) {
            return prevCoords;
        }

        if (Utils.isClassCollidable(peripheral)) {
            checkCollisionWithCollectible(peripheral, prevCoords);
            return new Coords(prevCoords.getX() - 1, prevCoords.getY());
        }

        if (peripheral instanceof ComputerRobot) {
            player.setLifePoints(player.getLifePoints() - 1);
        }

        return prevCoords;
    }

    private Coords onMoveRight(Object peripheral, Coords prevCoords) {
        if (Entities.isCollectible(peripheral) && player.getBackpack().isFull()) {
            return prevCoords;
        }

        if (Utils.isClassCollidable(peripheral)) {
            checkCollisionWithCollectible(peripheral, prevCoords);
            return new Coords(prevCoords.getX() + 1, prevCoords.getY());
        }

        if (peripheral instanceof ComputerRobot) {
            player.setLifePoints(player.getLifePoints() - 1);
        }

        return prevCoords;
    }

    private Coords onMoveUp(Object peripheral, Coords prevCoords) {
        if (Entities.isCollectible(peripheral) && player.getBackpack().isFull()) {
            return prevCoords;
        }

        if (Utils.isClassCollidable(peripheral)) {
            checkCollisionWithCollectible(peripheral, prevCoords);
            return new Coords(prevCoords.getX(), prevCoords.getY() - 1);
        }

        if (peripheral instanceof ComputerRobot) {
            player.setLifePoints(player.getLifePoints() - 1);
        }

        return prevCoords;
    }

    private Coords onMoveDown(Object peripheral, Coords prevCoords) {
        if (Entities.isCollectible(peripheral) && player.getBackpack().isFull()) {
            return prevCoords;
        }

        if (Utils.isClassCollidable(peripheral)) {
            checkCollisionWithCollectible(peripheral, prevCoords);
            return new Coords(prevCoords.getX(), prevCoords.getY() + 1);
        }

        if (peripheral instanceof ComputerRobot) {
            player.setLifePoints(player.getLifePoints() - 1);
        }

        return prevCoords;
    }

    private void checkCollisionWithCollectible(Object peripheral, Coords prevCoords) {
        String className = Utils.getClassNameOfObj(peripheral);

        switch (className) {
            case Entities.TRESSURE:
                onCollisionWithTressure(peripheral, prevCoords);
                break;
            case Entities.TRAP:
                onCollisionWithTrap(peripheral, prevCoords);
                break;
            case Entities.WARP:
                onCollisionWithWarp(peripheral, prevCoords);
                break;
        }
    }

    private void onCollisionWithTressure(Object peripheral, Coords prevCoords) {
        Tressure tressure = (Tressure) peripheral;
        Backpack backpack = player.getBackpack();

        // Update score according to collected treasures score point
        player.setScore(player.getScore() + tressure.getScorePoint());

        if (backpack.isEmpty()) {
            backpack.push(tressure);
            return;
        }

        Object latestItem = backpack.peek();

        if (latestItem instanceof Tressure) {
            Tressure latestItemTressure = (Tressure) latestItem;
            Tressure peripheralTressure = (Tressure) peripheral;

            // Delete latest item if collided tressure types are different
            if (latestItemTressure.SYMBOL == peripheralTressure.SYMBOL) {
                Coords coords = Utils.findEmptyPosition();
                backpack.pop();

                switch (peripheralTressure.SYMBOL) {
                    case '1':
                        break;
                    case '2':
                        player.setEnergy(player.getEnergy() + 30);
                        break;
                    case '3':
                        backpack.push(new Trap(coords));
                        break;
                    case '4':
                        player.setEnergy(player.getEnergy() + 240);
                        break;
                    case '5':
                        backpack.push(new Warp(coords));
                        break;
                }
            } else {
                backpack.pop();
            }
        } else {
            backpack.push(tressure);
        }
    }

    private void onCollisionWithTrap(Object peripheral, Coords prevCoords) {
        Trap trap = (Trap) peripheral;
        player.getBackpack().push(trap);
    }

    private void onCollisionWithWarp(Object peripheral, Coords prevCoords) {
        Warp warp = (Warp) peripheral;
        player.getBackpack().push(warp);
    }
}