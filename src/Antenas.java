import java.util.ArrayList;

public class Antenas {

	double x, y, raio, custo, variacao;
	ArrayList<Pontos> presas;
    
    public Antenas(double x, double y, double raio, double custo) {
        this.x = x;
        this.y = y;    
        this.raio = raio;
        this.custo = custo;
        
        this.variacao = 0;
        
        this.presas = new ArrayList<Pontos>();
        
    }
    public double Fitness() {
        double fitness = Integer.MAX_VALUE;
        if(this.custo*this.variacao != 0){
            fitness = presas.size()/(custo*variacao);

        }
        return fitness;
    }
}