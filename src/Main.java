import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		ArrayList<Pontos> pontos = new ArrayList<Pontos>();
		ArrayList<Antenas> antenas = new ArrayList<Antenas>();
		ArrayList<Antenas> antigas = new ArrayList<Antenas>();
		
		Alcateia alcateia = new Alcateia();
		
		Scanner entrada_pontos = new Scanner(new FileReader("entrada_pontos.txt"));
		Scanner entrada_antenas = new Scanner(new FileReader("entrada_antenas.txt"));
		
		/*Recebendo os pontos*/
		while (entrada_pontos.hasNextLine()) {
			   String linha = entrada_pontos.nextLine();
			   
			   String posicao[];
			   posicao = linha.split(",");
			   Pontos ponto = new Pontos( Double.parseDouble(posicao[0]), Double.parseDouble(posicao[1]) );
			   
			   pontos.add(ponto);
			   
		}
		/*Recebendo as antenas*/
		while (entrada_antenas.hasNextLine()) {
			   String linha = entrada_antenas.nextLine();
			   
			   String posicao[];
			   posicao = linha.split(",");
			   Antenas antena = new Antenas(Double.parseDouble(posicao[0]), Double.parseDouble(posicao[1]), Double.parseDouble(posicao[2]), Double.parseDouble(posicao[3]));
			   Antenas antiga = new Antenas(Double.parseDouble(posicao[0]), Double.parseDouble(posicao[1]), Double.parseDouble(posicao[2]), Double.parseDouble(posicao[3]));
			   
			   antenas.add(antena);
			   antigas.add(antiga);
			   
			   
		}
		
		/*Setando os cheiros das presas que ja estao cacadas como falso*/
		for(int i=0; i<antenas.size(); i++) {
			System.out.println("(" + antenas.get(i).x +", " + antenas.get(i).y + ") | R: " + antenas.get(i).raio);
			
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
					
					
					df_x = antenas.get(i).x - pontos.get(j).x;
					df_x = Math.pow(df_x, 2);
					
					df_y = antenas.get(i).y - pontos.get(j).y;
					df_y = Math.pow(df_y, 2);
					
					distancia = df_x + df_y;
					
					distancia = Math.sqrt(distancia);
					
					if(distancia <= antenas.get(i).raio) {
						pontos.get(j).cheiro = false;
						
					}
				}
			}
		}
		
		int indice_presa, presas_cacadas;
		double custo_total=0;
		
		while(true) {
			
			presas_cacadas = 0;
			alcateia.loboAlfa(antenas);
			for(int i=0; i<antenas.size(); i++) {
				/*Busca da presa otima*/
				presas_cacadas = alcateia.procurarPresa(antenas.get(i), pontos, i);
				
			}
			if(presas_cacadas == pontos.size()) {
				break;
			}
			
			/*Caca*/
			custo_total = alcateia.cacar(pontos, antenas, custo_total);
			
		}
		System.out.println("----------------------------------------------------");
		for(int i=0; i<antenas.size(); i++) {
			double teste = antenas.get(i).custo*antenas.get(i).variacao;
			System.out.println("(" + antenas.get(i).x +", " + antenas.get(i).y + ") | R: " + antenas.get(i).raio  + " | Presas: "+ antenas.get(i).presas.size()
					+ " | " + teste);
		}
		System.out.println("====================================================");
		System.out.println("CUSTO TOTAL = " + custo_total);
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				new Plotar( pontos, antenas, antigas);
			}
		});
	}

}