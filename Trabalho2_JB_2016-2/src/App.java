import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


public class App {
	public static void main(String args[]){
		File file = new File("casoa");
		BufferedReader reader = null;
		ArrayList<String> vizinhos;
		Map<String, ArrayList<String>> dic = new HashMap<>();
		
		//leitura do arquivo
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String linha;
			String planeta;
			while((linha = reader.readLine()) != null){
				int primeiroEspaco = linha.indexOf(' ');
				planeta = linha.substring(0, primeiroEspaco);
				linha = linha.substring(primeiroEspaco + 1);
				vizinhos = new ArrayList<>(Arrays.asList(linha.split(" ")));
				dic.put(planeta, vizinhos);
			}			
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
			try{
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
				System.err.println("Erro ao fechar o BufferedReader.");
			}
		}
		//fim da leitura do arquivo
	}
}
