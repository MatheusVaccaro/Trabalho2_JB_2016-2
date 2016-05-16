import java.util.ArrayList;

public class Nodo {
	private String nome;
	private int galaxias;
	private int planetas;
	private int totalGalaxias;
	private int totalPlanetas;
	private ArrayList<String> vizinhos;
	
	public Nodo(String nome, int galaxias, int planetas, ArrayList<String> vizinhos){
		this.nome = nome;
		this.galaxias = galaxias;
		this.planetas = planetas;
		this.vizinhos = vizinhos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getGalaxias() {
		return galaxias;
	}

	public void setGalaxias(int galaxias) {
		this.galaxias = galaxias;
	}

	public int getPlanetas() {
		return planetas;
	}

	public void setPlanetas(int planetas) {
		this.planetas = planetas;
	}

	public int getTotalGalaxias() {
		return totalGalaxias;
	}

	public void setTotalGalaxias(int totalGalaxias) {
		this.totalGalaxias = totalGalaxias;
	}

	public int getTotalPlanetas() {
		return totalPlanetas;
	}

	public void setTotalPlanetas(int totalPlanetas) {
		this.totalPlanetas = totalPlanetas;
	}

	@Override
	public String toString() {
		return "Nodo [nome=" + nome + ", galaxias=" + galaxias + ", planetas="
				+ planetas + ", totalGalaxias=" + totalGalaxias
				+ ", totalPlanetas=" + totalPlanetas + ", vizinhos=" + vizinhos
				+ "]";
	}

	public ArrayList<String> getVizinhos() {
		return vizinhos;
	}

	public void setVizinhos(ArrayList<String> vizinhos) {
		this.vizinhos = vizinhos;
	}
	

}
