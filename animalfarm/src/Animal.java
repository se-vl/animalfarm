public class Animal
{
    private String _name;
    private String _classis;
    private String _description;

    public Animal(String name, String classis, String description)
    {
        _name = name;
        _classis = classis;
        _description = description;
    }

    public String getClassis()
    {
        return _classis;
    }

    @Override
    public String toString()
    {
        return _name + "\n" + _classis + "\n" + _description + "\n";
    }

    public boolean isMammal()
    {
        return _classis.equals("Mammalia");
    }

    public String getName()
    {
        // TODO Auto-generated method stub
        return _name;
    }
}
