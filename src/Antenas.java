
public class Antenas {

	double x, y, raio, custo, variacao, qt_pontos;
    
    public Antenas(double x, double y, double raio, double custo) {
        this.x = x;
        this.y = y;    
        this.raio = raio;
        this.custo = custo;
        
        this.variacao = 0;    
        this.qt_pontos = 0;
        
    }
    public double Fitness() {
        double fitness = -1;
        if(this.custo*this.variacao != 0){
            fitness = qt_pontos/(custo*variacao);

        }
        return fitness;
    }
}