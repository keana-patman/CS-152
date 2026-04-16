package Definitions;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Creature {
    protected int gridX;
    protected int gridY;
    Color color;
    int size;
    String name;
    public boolean alive;
    private double breedChance = 0.05;
    private double deathChance = 0.05;

    public Creature(int x, int y, int size, Color color, String name, boolean alive){
        this.gridX = x;
        this.gridY = y;
        this.size = size;
        this.color = color;
        try {
            this.name = getRandomName();
        } catch (Exception e) {
            this.name = name;
        }
        this.alive = alive;
    }

    public void die(){
        if(Math.random() < deathChance){
            alive = false;
        }
    }

    public Creature reproduce(int gridCount){
        Random rand = new Random();

        if(gridCount <= 0){
            return null;
        }

        int newX = gridX + 4;
        int newY = gridY + 4;


        if(Math.random() < breedChance){
            return new Creature(rand.nextInt(newX), rand.nextInt(newY), size, color, name, true);
        }
        return null;
    }


    public void draw(Graphics g){
        g.setColor(color);
        int drawX = gridX * 15 + 4;
        int drawY = gridY * 15 + 4;
        int drawSize = size - 6;
        g.fillRoundRect(drawX, drawY, drawSize, drawSize, 10, 10);
        g.setFont(new Font("Papyrus", Font.PLAIN, 9));
        g.setColor(Color.WHITE); 
        g.drawString(name, drawX + 2, drawY + (drawSize / 2) + 5);
    }

    public String getRandomName() throws IOException{
        ArrayList<String> names = new ArrayList<>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("src/names.txt"));

            String line;

            while((line = reader.readLine()) != null){
                names.add(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if(reader != null) reader.close();
        }

        Random random = new Random();

        return names.get(random.nextInt(names.size()-1));
    }

}
