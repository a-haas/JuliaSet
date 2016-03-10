/**
 *
 * @author ahaas
 */
public class Complex {
    protected double reel;
    protected double imaginaire;
    
    public void squareIt(){
        double x = this.reel;
        double y = this.imaginaire;
        
        this.reel = x*x - y*y;;
        this.imaginaire = x*y + x*y;
    }
    
    public void addThis(Complex c){
        this.reel += c.reel;
        this.imaginaire += c.imaginaire;
    }

    public void soustractThis(Complex c){
        this.reel -= c.reel;
        this.imaginaire -= c.imaginaire;
    }
    
    public void timeThis(Complex c){
        double x = this.reel;
        double y = this.imaginaire;
        
        this.reel = x*c.reel - y*c.imaginaire;
        this.imaginaire = x*c.imaginaire + y*c.reel;
    }
    
    public void scalarIt(double x){
        this.reel *= x;
        this.imaginaire *= x;
    }
    
    public void divideAndConquer(Complex c){
        double x = this.reel;
        double y = this.imaginaire;
        
        this.reel = (x*c.reel + y*c.imaginaire)
                    /(c.reel*c.reel + c.imaginaire*c.imaginaire);
        this.imaginaire = (y*c.reel - x*c.imaginaire)
                          /(c.reel*c.reel + c.imaginaire*c.imaginaire);
    }
    
    public void squareItAndTimesIt(){
        double x = this.reel;
        double y = this.imaginaire;
        
        this.squareIt();
        Complex tmp = new Complex();
        tmp.reel = x;
        tmp.imaginaire = y;
        this.timeThis(tmp);
    }
    
    public double abs(){
        return  Math.sqrt(this.reel * this.reel + this.imaginaire * this.imaginaire);
    }
    
    public static double sqrtReel(double a, double b){
        double res;
        res = Math.sqrt((a+Math.sqrt(a*a + b*b))/2);
        return res;
    }
    
    public static double sqrtIm(double b, double x){
        return b/(2*x);
    }
}