/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;

/**
 *
 * @author ahaas
 */
public class Projet {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {        
        int nbThread = 2;
        if(args.length != 2 && args.length != 3){
            System.out.println("Usage : <largeur> <hauteur> "
                    + "(<nombre de thread>)");
            return;
        }
        if(args.length == 3)
            nbThread = Integer.parseInt(args[2]);
        
        new Window(1600, 900, nbThread);
    }
}
