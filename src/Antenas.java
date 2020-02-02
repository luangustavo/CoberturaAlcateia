import java.util.ArrayList;

public class Antenas {

	double x, y, raio, custo, variacao, qt_pontos, total, fitness;
	ArrayList<Pontos> presas;
    
    public Antenas(double x, double y, double raio, double custo) {
        this.x = x;
        this.y = y;    
        this.raio = raio;
        this.custo = custo;
        
        this.variacao = 0;
        this.total = 0;
        this.presas = new ArrayList<Pontos>();
        
    }
    
    
}