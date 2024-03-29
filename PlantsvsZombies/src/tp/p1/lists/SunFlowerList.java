package tp.p1.lists;

import tp.p1.plants.SunFlower;
import tp.p1.game.Game;


public class SunFlowerList {
	private SunFlower[] list;
	int contador=0;
	private static final int BOARD_SIZE=32;

	public SunFlowerList() {
		list=new SunFlower[BOARD_SIZE];
	}

	public boolean Add(int x, int y, Game game) {
		SunFlower sf = new SunFlower(x, y, game);
		if(contador<BOARD_SIZE) {
			list[contador]=sf;
			contador++;
		}
		return contador<BOARD_SIZE;
	}
	public void Delete() {
		for(int i=0;i<contador;i++) {
			if(contador > 0 && list[i].getHealthPoints() <= 0)
			{
				list[i]=list[i+1];
				contador--;
			}
		}
	}

	public void update() {
		for(int i=0;i<contador;i++) {
			list[i].updateSunFlower();
		}
	}

	public void decreaseHealth(int x,int y, int damage)
	{
		int pos = searchPosition(x, y);
		list[pos].decreaseHealth(damage);
	}

	public boolean checkSunflower(int x, int y)
	{
		int i=0;
		boolean encontrado=false;
		while(i<contador&&!encontrado) {
			if((list[i].getX()==x)&&(list[i].getY()==y)) {
				encontrado=true;
			}
			else {
			i++;
			}
		}
		return encontrado;
	}

	private int searchPosition(int x,int y) {
		int i=0;
		boolean encontrado=false;
		while(i<contador&&!encontrado) {
			if((list[i].getX()==x)&&(list[i].getY()==y)) {
				encontrado=true;
			}
			else {
			i++;
			}
		}
		return i;
	}

	public String printPosition(int x, int y)
	{
		int pos = searchPosition(x,y);
		return list[pos].toString();
	}

	public int getContador() {
		return contador;
	}

}
