import javax.swing.*;
import javax.swing.Timer;

import Creatures.Cow;
import Definitions.Creature;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;

public class World extends JPanel{
    private int gridCount = 30;
    private int cellSize = 15;
    private List<Creature> animals;
    private boolean skip = false;


    public World(int n){ 
        setBackground(new Color(43,43,43));

        animals = new ArrayList<>();

        createCreature();
    }

    public void createCreature(){
        Random rand = new Random();
        List<Supplier<Creature>> types = Arrays.asList(
            () -> new Cow(rand.nextInt(gridCount), rand.nextInt(gridCount), cellSize, true)
        );

        List<Creature> babies = new ArrayList<>();

        Timer timer = new Timer(1000, e ->{
            if(Math.random() < 0.5){
                animals.add(types.get(rand.nextInt(types.size())).get());
            }
            if(animals.size() > 2){
                for(Creature a: animals){
                    if(skip){
                        skip = false;
                        continue;
                    }
                    else{
                        skip = true;
                        Creature baby = a.reproduce(gridCount);

                        if(baby != null){
                            babies.add(baby);
                        }
                    }
                }
            }
            for(Creature a: new ArrayList<>(animals)){
                a.die();
                if(!a.alive){
                    animals.remove(a);
                }
            }
            
            animals.addAll(babies);
            repaint();    
        });
        timer.start();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Creature a: animals)
                a.draw(g);
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("A Living World");
        frame.add(new World(100));
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
