package homePage;


import startGame.*;

class DifficultLevel extends DifficultyLevel{
	
    public DifficultLevel() 
    {
        super("DIFFICULT");
    }

    @Override
    public void startGame() 
    {
    	new RandomField(14, 14);
    	Game.level = 3;
		Main.game = new Game();
    }
}
