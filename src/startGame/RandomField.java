package startGame;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.swing.JPanel;

public class RandomField extends GameField{  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] origWArr;
	private String[] wArr;
	private int wDex = 0;
	private int[][] locArray;
	Random random =  new Random();
	
	public RandomField(int gridSize, int nOfWords) {
		this.gridSize = gridSize;
		GameField.setnOfWords(nOfWords);
		GameField.oNofWords = nOfWords;
		origWArr = new String[nOfWords];
		wArr = new String[nOfWords];
		setWordArray(new String[nOfWords]);
		pickWordArray();
		setcArray(new Cell[gridSize][gridSize]);
		setGrid(new JPanel(new GridLayout(gridSize, gridSize, 2, 2)));
		do {
			initializeC();
		}while(!createRandomGrid());
		
		setWordArray(wArr);
	}

	private void pickWordArray() {
		do {
			int x = (int) Math.floor(Math.random()*433);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("files/bank.db")))) {
		          for (int i = 0; i < x; i++)
		              br.readLine();
		          origWArr[wDex] = br.readLine().toUpperCase();
		     } catch (IOException e) {
				e.printStackTrace();
			}
			if(!(isWordFound(origWArr[wDex]))) {
				wDex++;
			}
		}while(wDex<getnOfWords());
	}

	
	private void initializeC() {
		for(int i=0; i < gridSize; i++) {
			for(int j=0; j < gridSize; j++) {
				getcArray()[i][j] = new Cell();
			}
		}
		
		//close all edges
		//up
		for(int j=0; j < gridSize; j++) {
			getcArray()[0][j].closeDirection(0);
		}
		//right
		for(int j=0; j < gridSize; j++) {
			getcArray()[j][gridSize-1].closeDirection(1);
		}
		//down
		for(int j=0; j < gridSize; j++) {
			getcArray()[gridSize-1][j].closeDirection(2);
		}
		//left
		for(int j=0; j < gridSize; j++) {
			getcArray()[j][0].closeDirection(3);
		}
	}

	private boolean createRandomGrid() {
		String temp;
		int oldX, oldY;
		int x, y;
		int lDex = 0;
		boolean flag = false;
		
		for(int i=0; i<getnOfWords(); i++) {
			temp = origWArr[i];
			
			locArray = new int[temp.length()][2];
			flag = false;
			
			while(!flag) {
				int xit = 0;
				do{
					y = (int) Math.floor(Math.random()*(gridSize)); 
					x = (int) Math.floor(Math.random()*(gridSize)); 
					if(xit==30) {
						return false;
					}
					xit++;
				}while(getcArray()[y][x].isDEnd());
				
				addToGrid(i, temp.charAt(lDex), lDex, y, x);
				lDex++;
				oldX = x;	oldY = y;
				flag = true;
				
				while(lDex<(temp.length()) && flag == true) {
					while(true){
						int[] availableDir = getAvailableDir(y, x);
						int direc = availableDir[1+(int) 
						            Math.floor(Math.random()*(availableDir[0]-1))];
							
						switch(direc) {
							case(0): y--; break;
							case(1): x++; break;
							case(2): y++; break;
							case(3): x--; break;
						}
						try {
							if(getcArray()[y][x].isDEnd()) {
								getcArray()[oldY][oldX].closeDirection(direc);
								x = oldX;	y = oldY;
							}
							else if(getcArray()[y][x].getLetter() == '\0') {
								addToGrid(i, temp.charAt(lDex), lDex, y, x);
								lDex++;
								oldX = x;	oldY = y;
								break;
							}
						}catch(Exception e) {
							return false;
						}
						
						if(isAllEnd(y,x)) {
							locArray[lDex][0] = -1;	locArray[lDex--][1] = -1;		
							
							if(lDex>0){
								x = locArray[lDex][1];	y = locArray[lDex][0];
							}
							
							getcArray()[oldY][oldX].setLetter('\0');
							getcArray()[oldY][oldX].closeCell();
							getcArray()[y][x].closeCell();
							closeDirec(x,y,oldX,oldY);
							if(lDex<=0) {
								flag = false;
								lDex = 0;
								break;
							}
							closeDirec(locArray[lDex-1][1], locArray[lDex-1][0], x, y);
							x = locArray[lDex-1][1];	y = locArray[lDex-1][0];
							oldX = x;	oldY = y;
						}
					}
					if(lDex==(temp.length())) {
						flag=true;
					}
				}
				
			}
			
			wArr[i] = temp;
			lDex = 0;
			locArray = null;
		}
		
		for(int i=0; i < gridSize; i++) {
			for(int j=0; j < gridSize; j++) {
				if(getcArray()[i][j].getLetter() == '\0') {
					getcArray()[i][j].setLetter((char) ('A' + random.nextInt(26)));
					getcArray()[i][j].setY(i);
					getcArray()[i][j].setX(j);
				}
				getGrid().add(getcArray()[i][j].cellBox());
			}
		}
		
		return true;
	}

	private int[] getAvailableDir(int y, int x) {
		int[] array = new int[5];
		int j = 1;
		array[0] = j;
		for(int i = 0; i<4; i++) {
			if(getcArray()[y][x].isDirOpen(i)) {
				array[j++] = i;
			}
		}
		if(j>1)	array[0] = j-1;
		
		return array;
	}

	private void addToGrid(int i, char letter, int lDex, int y, int x) {
		getcArray()[y][x].setLetter(letter);
		getcArray()[y][x].closeCell();
		getcArray()[y][x].setY(y);
		getcArray()[y][x].setX(x);
		locArray[lDex][0] = y;	locArray[lDex][1] = x;
	}

	private void closeDirec(int x, int y, int nullX, int nullY) {
		if(y-nullY == 1)	getcArray()[y][x].closeDirection(0);
		if(x-nullX == -1)	getcArray()[y][x].closeDirection(1);
		if(y-nullY == -1)	getcArray()[y][x].closeDirection(2);
		if(x-nullX == 1)	getcArray()[y][x].closeDirection(3);
	}

	private boolean isAllEnd(int y, int x) {
		Cell[][] c = getcArray();
		if(y == 0 && x == 0) {								//top-left corner
			if(!(c[y][x+1].isDEnd()))	return false;
			if(!(c[y+1][x].isDEnd()))	return false;
		}
		else if(y == 0 && x == gridSize-1) {				//top-right corner
			if(!(c[y+1][x].isDEnd()))	return false;
			if(!(c[y][x-1].isDEnd()))	return false;
		}
		else if(y == gridSize-1 && x == gridSize-1) {		//bottom-right corner
			if(!(c[y-1][x].isDEnd()))	return false;
			if(!(c[y][x-1].isDEnd()))	return false;
		}
		else if(y == gridSize-1 && x == 0) {				//bottom-left corner
			if(!(c[y-1][x].isDEnd()))	return false;
			if(!(c[y][x+1].isDEnd()))	return false;
		}
		else if(y == 0) {									//top-edge
			if(!(c[y][x+1].isDEnd()))	return false;
			if(!(c[y+1][x].isDEnd()))	return false;
			if(!(c[y][x-1].isDEnd()))	return false;
		}
		else if(x == gridSize-1) {							//right-edge
			if(!(c[y-1][x].isDEnd()))	return false;
			if(!(c[y+1][x].isDEnd()))	return false;
			if(!(c[y][x-1].isDEnd()))	return false;
		}
		else if(y == gridSize-1) {							//bottom-edge
			if(!(c[y-1][x].isDEnd()))	return false;
			if(!(c[y][x+1].isDEnd()))	return false;
			if(!(c[y][x-1].isDEnd()))	return false;
		}
		else if(x == 0) {									//left-edge
			if(!(c[y-1][x].isDEnd()))	return false;
			if(!(c[y][x+1].isDEnd()))	return false;
			if(!(c[y+1][x].isDEnd()))	return false;
		}
		else {
			if(!(c[y-1][x].isDEnd()))	return false;
			if(!(c[y][x+1].isDEnd()))	return false;
			if(!(c[y+1][x].isDEnd()))	return false;
			if(!(c[y][x-1].isDEnd()))	return false;
		}
		return true;
	}

	private boolean isWordFound(String word) {
		for(int i = 0; i<wDex; i++) {
			if(word.equals(origWArr[i]))
				return true;
		}
		
		return false;
	}
}
