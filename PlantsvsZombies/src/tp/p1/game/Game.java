package tp.p1.game;

import tp.p1.lists.*;
import tp.p1.zombies.ZombieManager;

public class Game {
	//atributos
	private ZombieList zombieList;
	private PeaShooterList peashooterList;
	private SunFlowerList sunflowerList;
	private LEVEL level;
	private int seed;
	private int numCiclos;
	private SunCoinManager suncoins;
	private GamePrinter gamePrinter;
	private ZombieManager zombieManager;
	
	//constuctor
	public Game(LEVEL level,int seed) {
		this.level=level;
		this.seed=seed;
		this.inicializar();
	}
	
	//metodos principales
	public void update() {
		//recolectar girasol
		for(int i=0;i<sunflowerList.getContador();i++) {
			suncoins.setSunCoins(sunflowerList.getSunFlower(i).recolectar(suncoins.getSunCoins()));
		}
		//disparar lanzaguisantes
		for(int i=0;i<peashooterList.getContador();i++) {
			peashooterList.getPeaShooter(i).disparar();
		}
		//avanzar zombies
		for(int i=0;i<zombieList.getContador();i++){
			zombieList.getZombie(i).avanzar();
		}
		//atacar zombies
		for(int i=0;i<zombieList.getContador();i++) {
			zombieList.getZombie(i).atacar();
		}
		//limpiar sin vida
		this.eliminarSinVida();
		
		//aumentar ciclo interno zombie y girasol
		for(int i = 0; i < zombieList.getContador(); i++)
			zombieList.getZombie(i).setInternalCycle(zombieList.getZombie(i).getInternalCycle()+1);
		
		for(int i = 0; i < sunflowerList.getContador(); i++)
			sunflowerList.getSunFlower(i).setInternalCycle(sunflowerList.getSunFlower(i).getInternalCycle()+1);
		
		
		/*Solucion alternativa
		sunflowerList.increaseCycleSunflowers();
		zombieList.increaseCycleZombies();
		*/
	}
	
	public void inicializar() {
		zombieList =new ZombieList();
		peashooterList=new PeaShooterList();
		sunflowerList=new SunFlowerList();
		numCiclos=0;
		this.gamePrinter=new GamePrinter(this,4,8);
		this.suncoins=new SunCoinManager(this);
		suncoins.setSunCoins(50);
		this.zombieManager=new ZombieManager(this);
	}
	
	public String toString() {
		String salida1="Number of cycles: "+numCiclos;
		String salida2="\nSun coins: "+suncoins.getSunCoins();
		String salida3="\nRemaining zombies: "+zombieManager.getZombiesRestantes();
		
		return salida1+salida2+salida3+gamePrinter.toString();
	}
	
	
	public boolean checkEmpty(int x,int y) {
		boolean empty = false;
		
		if(!peashooterList.checkPeashooter(x, y) && !sunflowerList.checkSunflower(x, y) && !zombieList.checkZombie(x, y)) {
			empty = true;
		}
		return empty;
	}
	
	public void eliminarSinVida() {
		for(int i=0;i<zombieList.getContador();i++) {
			if(zombieList.getZombie(i).getHealthPoints()==0) {
				zombieList.Delete(i);
			}
			if(sunflowerList.getSunFlower(i).getHealthPoints()==0) {
				sunflowerList.Delete(i);
			}
			if(peashooterList.getPeaShooter(i).getHealthPoints()==0) {
				peashooterList.Delete(i);
			}
		}
	}
	
		
	public String getObject(int x, int y)
	{
		String str;
		int i;
		if(peashooterList.checkPeashooter(x, y))
		{
			i = peashooterList.searchPosition(x, y);
			str = peashooterList.printPosition(i);
		} else if (sunflowerList.checkSunflower(x, y))
		{
			i = sunflowerList.searchPosition(x, y);
			str = sunflowerList.printPosition(i);
		} else if (zombieList.checkZombie(x, y))
		{
			i = zombieList.searchPosition(x, y);
			str = zombieList.printPosition(i);
		} else {
			str = " ";
		}
		return str;
	}
	
	
	
	//getters y setters
	public LEVEL getLevel() {
		return level;
	}
	public ZombieList getZombieList() {
		return zombieList;
	}
	
	public void setGamePrinter(GamePrinter gamePrinter) {
		this.gamePrinter = gamePrinter;
	}

	public PeaShooterList getPeashooterList() {
		return peashooterList;
	}
	
	public SunFlowerList getSunflowerList() {
		return sunflowerList;
	}

	public int getSeed() {
		return seed;
	}

	public int getNumCiclos() {
		return numCiclos;
	}

	public void setNumCiclos(int numCiclos) {
		this.numCiclos = numCiclos;
	}

	public SunCoinManager getSuncoins() {
		return suncoins;
	}

	public void setSuncoins(SunCoinManager suncoins) {
		this.suncoins = suncoins;
	}

	public GamePrinter getGamePrinter() {
		return gamePrinter;
	}

	public ZombieManager getZombieManager() {
		return zombieManager;
	}
		
}
