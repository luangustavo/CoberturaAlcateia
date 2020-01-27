import java.util.ArrayList;

public class Pontos {
	
	double x, y;
    boolean cheiro;
    
    ArrayList<Integer> candidatas;
    ArrayList<Double> distancias_candidatas;
    
    public Pontos(double x, double y) {
            this.x = x;
            this.y = y;
            
            this.cheiro = true;
            this.candidatas = new ArrayList<Integer>();
            this.distancias_candidatas = new ArrayList<Double>();
    }
    
    public boolean GetCheiro(){
        return cheiro;
    }
    
    public void SetCheiro(boolean novo){
        this.cheiro = novo;
    }
    
}