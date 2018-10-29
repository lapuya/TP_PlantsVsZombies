package tp.p1.game;

import java.util.Random;

import tp.p1.lists.*;
import tp.p1.plants.*;
import tp.p1.zombies.ZombieManager;

public class Game {
	//atributos
	private ZombieList zombieList;
	private PeaShooterList peashooterList;
	private SunFlowerList sunflowerList;
	private LEVEL level;
	private Random rand;
	private int numCiclos;
	private SunCoinManager suncoins;
	private GamePrinter gamePrinter;
	private ZombieManager zombieManager;
	private final int FILAS=4;
	private final int COLUMNAS=8;
	
	//constuctor
	public Game(LEVEL level,Random rand) {
		this.level=level;
		this.rand=rand;
		this.inicializar();
	}
	
	//metodos principales
	public void update() {
		//recolectar girasol
		sunflowerList.update();
		//disparar lanzaguisantes
		peashooterList.update();
		//avanzar y atacar zombies
		zombieList.update();

		//limpiar sin vida
		this.eliminarSinVida();
	}
	
	public void inicializar() {
		zombieList =new ZombieList();
		peashooterList=new PeaShooterList();
		sunflowerList=new SunFlowerList();
		numCiclos=0;
		this.gamePrinter=new GamePrinter(this,FILAS,COLUMNAS);
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
		if(zombieList.Delete())
			zombieManager.setZombiesRestantesVivos(zombieManager.getZombiesRestantesVivos()-1);
		sunflowerList.Delete();
		peashooterList.Delete();
	}
	
	public boolean comprobarDentroTablero(String x, String y) {
		return Integer.parseInt(x)>=0 && Integer.parseInt(x)<FILAS && Integer.parseInt(y)>=0 && Integer.parseInt(y)<COLUMNAS;
	}
	
	public void attackZombie(int x, int damage) {
		
		int i = 0;
		
		//recorremos las columnas en busca de un zombie
		while (i < COLUMNAS && !zombieList.checkZombie(x, i))
		{
			i++;
		}
		//si sale porque encuentra un zombie -> dispara
		if(zombieList.checkZombie(x,i))
		{
			zombieList.decreaseHealth(zombieList.searchPosition(x,i), damage);
		}
	}
	
	public boolean checkWinnerZombie()
	{
		boolean found = false;
		if(zombieList.checkWinnerZombie())
			found = true;
		return found;
	}
	
	public void attackPlant(int x, int y, int damage) {
		if(sunflowerList.checkSunflower(x, y))
		{
			sunflowerList.decreaseHealth(sunflowerList.searchPosition(x, y), damage);
		}
		else 
		{
			peashooterList.decreaseHealth(peashooterList.searchPosition(x, y), damage);
		}
	}
	
	public void addCycle() {
		this.numCiclos++;
	}
	
		
	public String getObject(int x, int y)
	{
		String str;
		
		if(peashooterList.checkPeashooter(x, y))
		{
			str = peashooterList.printPosition(peashooterList.searchPosition(x, y));
		} 
		else if (sunflowerList.checkSunflower(x, y))
		{
			str = sunflowerList.printPosition(sunflowerList.searchPosition(x, y));
		} 
		else if (zombieList.checkZombie(x, y))
		{
			str = zombieList.printPosition(zombieList.searchPosition(x, y));
		} 
		else {
			str = " ";
		}
		return str;
	}
	
	public boolean isNotFinished() { 
		return zombieManager.getZombiesRestantesVivos() > 0 && !zombieManager.zombiGanador(); 
	} 
	 
	public boolean enoughMoneyPeaShooter() { 
		return suncoins.getSunCoins()>=PeaShooter.getCost(); 
	} 
	 
	public boolean enoughMoneySunFlower() { 
		return suncoins.getSunCoins()>=SunFlower.getCost(); 
	} 
	
	public void addZombieAction() {
		int filaZombie= rand.nextInt(FILAS-1);
		if(zombieManager.isZombieAdded()&&checkEmpty(filaZombie,COLUMNAS-1)) {
			addZombie(filaZombie, COLUMNAS-1);
			decreaseZombiesLeft();
		}
	}
	
	public void addPeashooter(int x, int y)
	{
		
		peashooterList.Add(x,y, this);
	}
	
	public void addSunflower(int x, int y)
	{
		sunflowerList.Add(x, y,this);
	}
	
	public void addZombie(int x, int y) {
		zombieList.Add(x, y, this);
	}
	
	public void decreaseZombiesLeft()
	{
		zombieManager.decreaseZombiesLeft();
	}
	
	public void decreaseSuncoins(int cost)
	{
		
		suncoins.decreaseSuncoins(cost);
	}
	
	public boolean checkZombie(int x, int y){
		
		return zombieList.checkZombie(x,  y);
	}
	
	public int getNumZombiesLista()
	{
		return zombieList.getContador();
	}
	
	public int getNumSunflowerLista()
	{
		
		return sunflowerList.getContador();
	}
	
	
	//getters y setters
	public LEVEL getLevel() {
		return level;
	}
	
	public void setGamePrinter(GamePrinter gamePrinter) {
		this.gamePrinter = gamePrinter;
	}
	
	public int getFILAS() {
		return FILAS;
	}

	public int getCOLUMNAS() {
		return COLUMNAS;
	}	
	
	public Random getRand() {
		return rand;
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

	public ZombieManager getZombieManager() {
		return zombieManager;
	}
}
