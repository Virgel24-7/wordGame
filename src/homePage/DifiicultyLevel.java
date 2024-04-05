package homePage;

abstract class DifficultyLevel
{
    private String name;

    public DifficultyLevel(String name)
    {
        this.name = name;
    }

    public abstract void startGame();

    public String getName() 
    {
        return name;
    }
}