import java.util.Scanner;

class REPL
{
    private final Scanner _scanner;
    private String _line;
    private AnimalService _animalService;

    public REPL()
    {
        _scanner = new Scanner(System.in);
        _animalService = new AnimalService();
    }

    public void start()
    {
        printWelcomeMessage();
        readLine();
        while (!_line.isEmpty())
        {
            processLine();
            System.out.println();
            readLine();
        }
    }

    private void printWelcomeMessage()
    {
        System.out.println("Enter the names of animals like cat, cow or llama.");
        System.out.println();
        System.out.println("Just pressing enter exits the program. Have fun!");
        System.out.println();
    }

    private void readLine()
    {
        System.out.print("$ ");
        _line = _scanner.nextLine();
    }

    private void processLine()
    {
        try
        {
            String description = _animalService.describe(_line);
            System.out.println(description);
        }
        catch (EncyclopediaException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
