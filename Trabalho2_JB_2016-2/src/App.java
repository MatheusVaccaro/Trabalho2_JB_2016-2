import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;



public class App {
	public static void main(String args[]){
		long tempoInicial = System.currentTimeMillis();
		String[] arquivos = {"casoa", "casob", "casoc", "casod", "casoe", "casof", "casog", "casoh", "casoi", "casoj"};
		//String[] arquivos = {"casox.txt"};
		
		for(String arquivo: arquivos){
			long tempoInicialArq = System.currentTimeMillis();
			File file = new File(arquivo);
			Map<String, Nodo> universo = new HashMap<>();
			
			//leitura do arquivo
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("utf-8")))){
				
				String linha;
				String nomeGalaxia;
				int numPlanetas;
				
				while((linha = reader.readLine()) != null){
					
					String[] galaxias = linha.split(" ");
					nomeGalaxia = galaxias[0];
					numPlanetas = getPlanetasFrom(nomeGalaxia);
					int tamanho = galaxias.length;
					
					if(universo.containsKey(nomeGalaxia)){
						Nodo galaxia = universo.get(nomeGalaxia);
						if(galaxia.vizinhos.size() == 0){
							for(int i = 1; i < tamanho; i++){
								galaxia.vizinhos.add(galaxias[i]);
							}
						}
					} else{
						Nodo galaxia = new Nodo(nomeGalaxia, 1, numPlanetas);
						for(int i = 1; i < tamanho; i++){
							galaxia.vizinhos.add(galaxias[i]);
						}
						universo.put(nomeGalaxia, galaxia);
						
					}
					for(int i = 1; i < tamanho; i++){
						if(!universo.containsKey(galaxias[i])){
							numPlanetas = getPlanetasFrom(galaxias[i]);
							Nodo galaxia = new Nodo(galaxias[i], 1, numPlanetas);
							universo.put(galaxias[i], galaxia);
						}
					}
				}

			//fim da leitura do arquivo
				System.out.println("Caso de teste: " + arquivo);
 				//explorar(universo);
				
			}
			catch(FileNotFoundException e){
				System.err.println("Arquivo não encontrado.");
				e.printStackTrace();
			}
			catch(IOException e){
				System.err.println("Erro de leitura.");
				e.printStackTrace();
			}
			finally{
				long tempoFinalArq = System.currentTimeMillis();
				System.out.println("Tempo de execução: " + (tempoFinalArq - tempoInicialArq) + " ms");
				System.out.println();
			}
		}
		long tempoFinal = System.currentTimeMillis();
		System.out.println("Tempo total de execução: " + (tempoFinal - tempoInicial) + " ms");
	}
	
	private static int explorarPlanetas(Map<String, Nodo> dic, Nodo nodo, ArrayList<String> exploradas){
		if(nodo.getTotalPlanetas() != 0) return nodo.getTotalPlanetas();
		exploradas.add(nodo.getNome());
		int maiorNumPlanetasVisitaveis = 0;
		if(nodo.getVizinhos() != null){
			for(String vizinho: nodo.getVizinhos()){
				int numPlanetasVisitaveis = explorarPlanetas(dic, dic.get(vizinho), exploradas);
				if(numPlanetasVisitaveis > maiorNumPlanetasVisitaveis) maiorNumPlanetasVisitaveis = numPlanetasVisitaveis;
			}
		}
		nodo.setTotalPlanetas(nodo.getPlanetas() + maiorNumPlanetasVisitaveis);
		return nodo.getTotalPlanetas();
	}
	
	private static int explorarGalaxias(Map<String, Nodo> dic, Nodo nodo, ArrayList<String> exploradas){
		if(nodo.getTotalGalaxias() != 0) return nodo.getTotalGalaxias();
		exploradas.add(nodo.getNome());
		int maiorNumGalaxiasVisitaveis = 0;
		if(nodo.getVizinhos() != null){
			for(String vizinho: nodo.getVizinhos()){
				int numGalaxiasVisitaveis = explorarGalaxias(dic, dic.get(vizinho), exploradas);
				if(numGalaxiasVisitaveis > maiorNumGalaxiasVisitaveis) maiorNumGalaxiasVisitaveis = numGalaxiasVisitaveis;
			}
		}
		nodo.setTotalGalaxias(nodo.getGalaxias() + maiorNumGalaxiasVisitaveis);
		return nodo.getTotalGalaxias();
	}
	
	public static void explorar(Map<String, Nodo> dic){
		int maiorNum = 0;
		ArrayList<String> exploradas = new ArrayList<String>();
		for(Nodo galaxia: dic.values()){
			if(!exploradas.contains(galaxia)){
				int resp = explorarGalaxias(dic, galaxia, exploradas);
				if(resp > maiorNum) maiorNum = resp;
			}
		}
		System.out.println("Maior número de galáxias visitáveis: " + maiorNum);
		
		exploradas.clear();
		
		for(Nodo galaxia: dic.values()){
			if(!exploradas.contains(galaxia)){
				int resp = explorarPlanetas(dic, galaxia, exploradas);
				if(resp > maiorNum) maiorNum = resp;
			}
		}
		System.out.println("Maior número de planetas visitáveis: " + maiorNum);
	}
	
	public static int getPlanetasFrom(String nome){
		//obtem o num de planetas a partir do nome da galaxia
		int i = 0;
		while(!Character.isDigit(nome.charAt(i))){
			i++;
		}
		int numPlanetas = Integer.parseInt(nome.substring(i));
		return numPlanetas;
	}
}
