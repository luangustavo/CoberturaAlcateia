
public class Pontos {
	
	double x, y;
    boolean cheiro;

    public Pontos(double x, double y) {
            this.x = x;
            this.y = y;
            
            this.cheiro = true;

    }
    
    public boolean GetCheiro(){
        return cheiro;
    }
    
    public void SetCheiro(boolean novo){
        this.cheiro = novo;
    }
    
}