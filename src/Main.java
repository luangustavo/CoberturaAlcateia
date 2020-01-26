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
		Collections.sort (antenas, new Comparator() {
			
			public int compare(Object o1, Object o2) {
				
				Antenas a1 = (Antenas) o1;
				Antenas a2 = (Antenas) o2;
				return a1.x > a2.x ? -1 : (a1.x > a2.x ? +1 : 0);
			}
				
			});
		
		System.out.println(antenas.get(0).x);
		
		int indice_presa, presas_cacadas;
		while(true) {
			presas_cacadas = 0;
			
			Collections.sort (antenas, new Comparator() {
				
				public int compare(Object o1, Object o2) {
					
					Antenas a1 = (Antenas) o1;
					Antenas a2 = (Antenas) o2;
					return a1.Fitness() > a2.Fitness() ? -1 : (a1.Fitness() > a2.Fitness() ? +1 : 0);
				}
					
				});
			
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
					/*Caca*/
					if(antenas.get(i).raio < menor_distancia) {
						antenas.get(i).variacao = menor_distancia - antenas.get(i).raio;
						antenas.get(i).raio = menor_distancia;
					}
					pontos.get(indice_presa).cheiro = false;
					antenas.get(i).presas.add(pontos.get(indice_presa));
				}
				
			}
			if(presas_cacadas == pontos.size()) {
				break;
			}
		}
		
		Collections.sort (antenas, new Comparator() {
			
			public int compare(Object o1, Object o2) {
				
				Antenas a1 = (Antenas) o1;
				Antenas a2 = (Antenas) o2;
				return a1.x > a2.x ? -1 : (a1.x > a2.x ? +1 : 0);
			}
				
			});
		
		for(int i=0; i<antenas.size(); i++) {
			System.out.println(antenas.get(i).presas.size());
		}
		
		
		
		System.out.println(antenas.get(0).x);

	}

}