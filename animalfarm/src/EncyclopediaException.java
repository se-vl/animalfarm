class EncyclopediaException extends Exception
{
    private final String _animal;
    private final String _url;

    public EncyclopediaException(String animal, String url, String message)
    {
        super("could not locate " + animal + " via " + url);
        _animal = animal;
        _url = url;
    }
}
