import java.util.HashMap;
import java.util.Map;

class AnimalService
{
    private Map<String, String> _animals;

    public AnimalService()
    {
        _animals = new HashMap<>();
        _animals.put(
                "cat",
                "The domestic cat is a small, usually furry, domesticated, and carnivorous mammal.");
        _animals.put(
                "cow",
                "Cattle—colloquially cows—are the most common type of large domesticated ungulates.");
        _animals.put(
                "llama",
                "The llama is a domesticated South American camelid, widely used as a meat and pack animal by Andean cultures since pre-Hispanic times.");
    }

    public String describe(String animal)
    {
        String description = _animals.get(animal);
        if (description == null)
        {
            description = "Sorry, never heard of " + animal + "...";
        }
        return description;
    }
}
