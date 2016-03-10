import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ahaas
 */
public class JuliaSet extends Complex{
    BufferedImage img;
    Complex c;
    int hauteur;
    int largeur;
    double zoom = 1;
    int maxIterations = 1000;
    double moveX = 0.;
    double moveY = 0;
    int threadNumber;
    
    public JuliaSet(double cx, double cy, int maxIter, int haut, int larg, int thread){
        super();
        c = new Complex();
        c.reel = cx;
        c.imaginaire = cy;
        this.maxIterations = maxIter;
        this.hauteur = haut;
        this.largeur = larg;
        img = new BufferedImage(haut, larg, BufferedImage.TYPE_INT_ARGB);
        threadNumber = thread;
    }
    
    public void setCReel(double v){
        c.reel = v;
    }
    public double getCReel(){
        return c.reel;
    }
    public double getCImaginaire(){
        return c.imaginaire;
    }
    public void setCImaginaire(double v){
        c.imaginaire = v;
    }
    
    public void generateJulia() throws IOException{
        for(int i=1; i<=threadNumber; i++){
            if(threadNumber == 1){
                new JuliaThread(0, hauteur, 0, largeur).run();
            }
            else{
                new JuliaThread(0, hauteur/i, 0, largeur).start();
            }
        }
    }
    
    private class JuliaThread extends Thread{
        private int startI;
        private int endI;
        private int startJ;
        private int endJ;
        private Complex z;
        
        public JuliaThread(int si, int ei, int sj, int ej){
            startI = si;
            endI = ei;
            startJ = sj;
            endJ = ej;
            z = new Complex();
        }
        
        public void run(){
            int color;
        
            for(int i=startI; i<endI; i++){
                for(int j=startJ; j<endJ; j++){
                    z.reel = i;
                    z.imaginaire = j;
                    color = inJulia(1.5*(i-hauteur/2)/(0.5*hauteur*zoom)+moveX
                                    , (j-largeur/2)/(0.5*largeur*zoom)+moveY);
                    img.setRGB(i, j, color);
                }
            }
        }
        
        private int inJulia(double x, double y){
            z.reel = x;
            z.imaginaire = y;
            //Coloration du Julia set : 
            //http://stackoverflow.com/questions/369438/smooth-spectrum-for-mandelbrot-set-rendering
            double smoothcolor = Math.exp(-z.abs());
            int i;
            for(i=0; i<maxIterations && z.abs() < 4; i++){
                //f(z) = z²+c
                z.squareIt();
                z.addThis(c);
                smoothcolor += Math.exp(-z.abs());
            }
            return Color.HSBtoRGB((float)(0.80 + 45*smoothcolor/maxIterations) ,1.0f
                               ,0.5f);
        }
    }
}
