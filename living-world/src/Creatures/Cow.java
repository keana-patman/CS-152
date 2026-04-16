package Creatures;

import java.awt.Color;

import Definitions.Creature;

public class Cow extends Creature{

    public Cow(int x, int y, int size, boolean alive) {
        super(x, y, size, Color.RED, "Cow", alive);
    }
    
}
