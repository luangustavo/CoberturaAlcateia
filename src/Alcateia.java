import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Alcateia {
	double custo_total = 0;
	Antenas alfa;
	
	public Alcateia(){
	}
	
	public void loboAlfa(ArrayList<Antenas> antenas) {
		
		Collections.sort (antenas, new Comparator() {
			
			public int compare(Object o1, Object o2) {
				
				Antenas p1 = (Antenas) o1;
				Antenas p2 = (Antenas) o2;
				return p1.fitness < p2.fitness ? -1 : (p1.fitness > p2.fitness ? +1 : 0);
			}
				
			});
		this.alfa = antenas.get(0);
	}
	
	public double cacar(ArrayList<Pontos> pontos, ArrayList<Antenas> antenas, double custo_total) {
		this.custo_total = custo_total;
		for(int i=0; i<pontos.size(); i++) {
			
			double fitness, aux_raio=0, aux_variacao=0 ,melhor_fitness=0;
			int aux=0;
			if(pontos.get(i).candidatas != null) {
				for(int j=0; j<pontos.get(i).candidatas.size(); j++) {
				
					double nova_variacao = 0;
					
					/*Calcular quanto o raio sera necessario para variar*/
					if( antenas.get(pontos.get(i).candidatas.get(j)).raio < pontos.get(i).distancias_candidatas.get(j)) {
						nova_variacao = pontos.get(i).distancias_candidatas.get(j) - antenas.get(pontos.get(i).candidatas.get(j)).raio;
					}
					
					double variacao_total = antenas.get(pontos.get(i).candidatas.get(j)).variacao + nova_variacao;
					
			        if(antenas.get(pontos.get(i).candidatas.get(j)).custo*variacao_total != 0){
			            fitness = ( antenas.get(pontos.get(i).candidatas.get(j)).presas.size()+1) / ( antenas.get(pontos.get(i).candidatas.get(j)).custo*variacao_total);
			        }
			        else { //custo = 0
			        	fitness = Integer.MAX_VALUE-1;
			        }
			        
			        /*Disputa*/
					if(fitness >= melhor_fitness) {
						melhor_fitness = fitness;
						aux = pontos.get(i).candidatas.get(j);
						aux_variacao = nova_variacao;
						
					}
					
				}
				if(pontos.get(i).candidatas.size() > 0){
					antenas.get(aux).presas.add(pontos.get(i));
					antenas.get(aux).fitness = melhor_fitness;
					if(aux_variacao !=0 ) {
						antenas.get(aux).variacao = aux_variacao;
						antenas.get(aux).raio = antenas.get(aux).raio + aux_variacao;
					}
					this.custo_total = this.custo_total + (antenas.get(aux).variacao * antenas.get(aux).custo);
					
					pontos.get(i).cheiro = false;
					
					
					
					double distancia=0, df_x=0, df_y=0;
					for(int j=0; j<pontos.size(); j++) {
						
						/*
						* Calcular a distancia de cada ponto para as antenas
						* D(P1, P2) = {(x1-x2)^2+SQRT(y1-y2)^2}^1/2
						*
						* Decodificando a chave:
						* chave > 0.5 representa que a antena esta sendo utilizada
						* chave <= 0.5 representa que a antena nao esta sendo utilizada
						*/
						
						if(pontos.get(j).cheiro) {
							
							
							df_x = antenas.get(aux).x - pontos.get(j).x;
							df_x = Math.pow(df_x, 2);
							
							df_y = antenas.get(aux).y - pontos.get(j).y;
							df_y = Math.pow(df_y, 2);
							
							distancia = df_x + df_y;
							
							distancia = Math.sqrt(distancia);
							
							if(distancia <= antenas.get(aux).raio) {
								pontos.get(j).cheiro = false;
								antenas.get(aux).presas.add(pontos.get(j));
							}
						}
					}
					
					pontos.get(i).candidatas = null;
					pontos.get(i).distancias_candidatas = null;
					
				}
			}
		}
		
		return this.custo_total;
		
	}
	
	public int procurarPresa(Antenas antena, ArrayList<Pontos> pontos, int i) {
		int indice_presa = 0, presas_cacadas = 0;
		double distancia=0, df_x=0, df_y=0;
		
		/*Procurar a melhor presa*/
		double menor_distancia = Integer.MAX_VALUE;
		for(int j=0; j<pontos.size(); j++) {
			
			/*
			* Calcular a distancia de cada ponto para as antenas
			* D(P1, P2) = {(x1-x2)^2+SQRT(y1-y2)^2}^1/2
			*
			* Decodificando a chave:
			* chave > 0.5 representa que a antena esta sendo utilizada
			* chave <= 0.5 representa que a antena nao esta sendo utilizada
			*/
			
			if(pontos.get(j).cheiro) {
				
				
				df_x = antena.x - pontos.get(j).x;
				df_x = Math.pow(df_x, 2);
				
				df_y = antena.y - pontos.get(j).y;
				df_y = Math.pow(df_y, 2);
				
				distancia = df_x + df_y;
				
				distancia = Math.sqrt(distancia);
				
				if(distancia < menor_distancia) {
					indice_presa = j;
					menor_distancia = distancia;
				}
			}
			else{
				presas_cacadas++;
			}
		}
		
		
		if(presas_cacadas != pontos.size()) {
			
			pontos.get(indice_presa).candidatas.add(i);
			pontos.get(indice_presa).distancias_candidatas.add(menor_distancia);
			
		}
		
		return presas_cacadas;
		
		
	}
	
}
