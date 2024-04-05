package homePage;

import startGame.*;

class EasyLevel extends DifficultyLevel{
	
    public EasyLevel() 
    {
        super("EASY");
    }

    @Override
    public void startGame() 
    {		
    	new RandomField(10, 10);
    	Game.level = 1;
		Main.game = new Game();	
    }
}
