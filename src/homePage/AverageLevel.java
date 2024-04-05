package homePage;


import startGame.*;

class AverageLevel extends DifficultyLevel{
	
    public AverageLevel() 
    {
        super("AVERAGE");
    }

    @Override
    public void startGame() 
    {
    	new RandomField(12, 12);
    	Game.level = 2;
		Main.game = new Game();	
    }
}