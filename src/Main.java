import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		ArrayList<Pontos> pontos = new ArrayList<Pontos>();
		ArrayList<Antenas> antenas = new ArrayList<Antenas>();
		
		Scanner entrada_pontos = new Scanner(new FileReader("entrada_pontos.txt"));
		Scanner entrada_antenas = new Scanner(new FileReader("entrada_antenas.txt"));
		
		while (entrada_pontos.hasNextLine()) {
			   String linha = entrada_pontos.nextLine();
			   
			   String posicao[];
			   posicao = linha.split(",");
			   //System.out.println(Double.parseDouble(posicao[0]));
			   //Criando os pontos de acordo com o arquivo de entrada
			   Pontos ponto = new Pontos( Double.parseDouble(posicao[0]), Double.parseDouble(posicao[1]) );
			   
			   //Adicionando o ponto na lista
			   pontos.add(ponto);
			   
		}
		
		while (entrada_antenas.hasNextLine()) {
			   String linha = entrada_antenas.nextLine();
			   
			   String posicao[];
			   posicao = linha.split(",");
			   //System.out.println(Double.parseDouble(posicao[0]));
			   //Criando as antenas de acordo com o arquivo de entrada
			   Antenas antena = new Antenas(Double.parseDouble(posicao[0]), Double.parseDouble(posicao[1]), Double.parseDouble(posicao[2]), Double.parseDouble(posicao[3]));
			   
			   //Adicionando o ponto na lista
			   antenas.add(antena);
			   
		}
		
		
		for(int i=0; i<antenas.size(); i++) {
			System.out.println("(" + antenas.get(i).x +", " + antenas.get(i).y + ") | R: " + antenas.get(i).raio);
		}
		
		int indice_presa, presas_cacadas;
		double custo_total=0;
		
		while(true) {
			
			presas_cacadas = 0;
			
			for(int i=0; i<antenas.size(); i++) {
				
				double distancia=0, df_x=0, df_y=0;
				
				indice_presa = 0;
				presas_cacadas = 0;
				
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
						
						
						df_x = antenas.get(i).x - pontos.get(j).x;
						df_x = Math.pow(df_x, 2);
						
						df_y = antenas.get(i).y - pontos.get(j).y;
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
				
			}
			if(presas_cacadas == pontos.size()) {
				break;
			}
			
			/*Caca*/
			
			for(int i=0; i<pontos.size(); i++) {
				double fitness, aux_raio=0, aux_variacao=0 ,melhor_fitness=0;
				int aux=0;
				if(pontos.get(i).candidatas != null) {
					for(int j=0; j<pontos.get(i).candidatas.size(); j++) {
					
						double nova_variacao = 0;
						
						if( antenas.get(pontos.get(i).candidatas.get(j)).raio < pontos.get(i).distancias_candidatas.get(j)) {
							nova_variacao = pontos.get(i).distancias_candidatas.get(j) - antenas.get(pontos.get(i).candidatas.get(j)).raio;
						}
						
						double variacao_total = antenas.get(pontos.get(i).candidatas.get(j)).variacao + nova_variacao;
						
				        if(antenas.get(pontos.get(i).candidatas.get(j)).custo*variacao_total != 0){
				            fitness = ( antenas.get(pontos.get(i).candidatas.get(j)).presas.size()+1) / ( antenas.get(pontos.get(i).candidatas.get(j)).custo*variacao_total);
				            
				        }
				        else {
				        	variacao_total = 0;
				        	fitness = Integer.MAX_VALUE;
				        }
				        
						if(fitness > melhor_fitness) {
							melhor_fitness = fitness;
							aux = j;
							aux_variacao = variacao_total;
						}
						
					}
					if(pontos.get(i).candidatas.size() > 0){
						antenas.get(pontos.get(i).candidatas.get(aux)).presas.add(pontos.get(i));
						antenas.get(pontos.get(i).candidatas.get(aux)).variacao = aux_variacao;
						antenas.get(pontos.get(i).candidatas.get(aux)).raio = aux_variacao;
						
						custo_total = antenas.get(pontos.get(i).candidatas.get(aux)).variacao * antenas.get(pontos.get(i).candidatas.get(aux)).custo;
						
						pontos.get(i).candidatas = null;
						pontos.get(i).distancias_candidatas = null;
						pontos.get(i).cheiro = false;
						
					}
				}
			}
			/*
			if(antenas.get(i).raio < menor_distancia) {
				antenas.get(i).variacao = menor_distancia - antenas.get(i).raio;
				custo_total = antenas.get(i).variacao * antenas.get(i).custo;
				antenas.get(i).raio = menor_distancia;
			}
			
			pontos.get(indice_presa).cheiro = false;
			antenas.get(i).presas.add(pontos.get(indice_presa));
			*/
		}
		
	
		
		System.out.println("====================================================");
		for(int i=0; i<antenas.size(); i++) {
			System.out.println("(" + antenas.get(i).x +", " + antenas.get(i).y + ") | R: " + antenas.get(i).raio  + " | Presas: "+ antenas.get(i).presas.size());
			
		}
		System.out.println("CUSTO TOTAL = " + custo_total);
	}

}