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
		//String[] arquivos = {"casey"};
		
		for(String arquivo: arquivos){
			long tempoInicialArq = System.currentTimeMillis();
			File file = new File(arquivo);
			ArrayList<String> vizinhos;
			Map<String, Nodo> universo = new HashMap<>();
			
			//leitura do arquivo
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("utf-8")))){
				
				String linha;
				String nomeGalaxia;
				
				while((linha = reader.readLine()) != null){
					int primeiroEspaco = linha.indexOf(' ');
					nomeGalaxia = linha.substring(0, primeiroEspaco);
					linha = linha.substring(primeiroEspaco + 1);
					vizinhos = new ArrayList<>(Arrays.asList(linha.split(" ")));
					int numPlanetas = getPlanetasFrom(nomeGalaxia);
					Nodo galaxia = new Nodo(nomeGalaxia, 1, numPlanetas, vizinhos);
					universo.put(nomeGalaxia, galaxia);
					for(String vizinho: vizinhos){
						if(!universo.containsKey(vizinho)){
							numPlanetas = getPlanetasFrom(vizinho);
							universo.put(vizinho, new Nodo(vizinho, 1, numPlanetas, null));
						}
					}
				}
				
				/*while((linha = reader.readLine()) != null){
					int primeiroEspaco = linha.indexOf(' ');
					nomeGalaxia = linha.substring(0, primeiroEspaco);
					linha = linha.substring(primeiroEspaco + 1);
					vizinhos = new ArrayList<>(Arrays.asList(linha.split(" ")));
					int numPlanetas = getPlanetasFrom(nomeGalaxia);
					Nodo galaxia = new Nodo(nomeGalaxia, 1, numPlanetas, vizinhos);
					universo.put(nomeGalaxia, galaxia);
				}*/
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
