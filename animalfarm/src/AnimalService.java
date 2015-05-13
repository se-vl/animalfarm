import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AnimalService
{
    private Map<String, Animal> _animals;

    private String _animal;
    private String _description;
    private String _name;
    private String _classis;

    public AnimalService()
    {
        _animals = new HashMap<>();
    }

    private void detectName()
    {
        Matcher matcher = names.matcher(_description);
        matcher.find();
        _name = matcher.group(1);
    }

    private static final Pattern names = Pattern.compile("\\|\\s*+name\\s*+=\\s*+([\\w ]++)");

    private void detectClassis()
    {
        Matcher matcher = classises.matcher(_description);
        matcher.find();
        _classis = matcher.group(1);
    }

    private void detectNameAndClassis()
    {
        detectName();
        detectClassis();
    }

    private static final Pattern classises = Pattern.compile("\\|\\s*+classis\\s*+=\\s*+(\\w++)");

    public Animal describe(String givenAnimal) throws EncyclopediaException
    {
        _animal = givenAnimal;
        normalizeAnimal();
        Animal animal = _animals.get(_animal);
        if (animal == null)
        {
            System.out.println("Hmm, let me see...");
            consultWikipedia();
            String animalBeforeRedirect = _animal;
            resolveRedirect();

            removeEmphasizers();
            removeHyperlinks();
            removeFiles();
            removeReferences();
            detectNameAndClassis();
            removeCurlyBracedMetaData();
            retainFirstSentence();

            animal = new Animal(_name, _classis, _description);

            _animals.put(animalBeforeRedirect, animal);
            if (_animal != animalBeforeRedirect)
            {
                normalizeAnimal();
                _animals.put(_animal, animal);
            }
        }
        return animal;
    }

    private void normalizeAnimal()
    {
        _animal = _animal.trim();
        _animal = _animal.toLowerCase();
        _animal = whitespaceStrings.matcher(_animal)
            .replaceAll("_");
    }

    private static final Pattern whitespaceStrings = Pattern.compile("\\s++");

    private void consultWikipedia() throws EncyclopediaException
    {
        String urlString = "http://en.wikipedia.org/w/index.php?action=raw&title="
                + _animal;
        try
        {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                sb.append(inputLine);
                sb.append('\n');
            }
            in.close();
            _description = sb.toString();
        }
        catch (MalformedURLException ex)
        {
            throw new AssertionError("http should always work, review code");
        }
        catch (IOException ex)
        {
            throw new EncyclopediaException(_animal, urlString);
        }
    }

    private void resolveRedirect() throws EncyclopediaException
    {
        Matcher matcher = redirects.matcher(_description);
        if (matcher.matches())
        {
            String target = matcher.group(1);
            // You can't simply call normalizeAnimal here.
            // Try with Sri Lanka and see for yourself ;)
            _animal = whitespaceStrings.matcher(target)
                .replaceAll("_");
            consultWikipedia();
        }
    }

    private Pattern redirects = Pattern.compile(
            "#REDIRECT \\[\\[([^\\]]++)\\]\\].*", Pattern.DOTALL);

    private void removeCurlyBracedMetaData()
    {
        StringBuilder sb = new StringBuilder();
        int depth = 0;
        for (char x : _description.toCharArray())
        {
            switch (x)
            {
            case '{':
                ++depth;
                break;

            case '}':
                --depth;
                break;

            default:
                if (depth == 0)
                {
                    sb.append(x);
                }
            }
        }
        _description = sb.toString();
    }

    private void removeEmphasizers()
    {
        _description = emphasizers.matcher(_description)
            .replaceAll("");
    }

    private static final Pattern emphasizers = Pattern.compile("'{2,}");

    private void removeHyperlinks()
    {
        _description = hyperlinks.matcher(_description)
            .replaceAll("$1");
    }

    private static final Pattern hyperlinks = Pattern.compile("\\[\\[(?:[^\\]|\\[]++\\|)?+([^\\]|\\[]++)\\]\\]");

    private void removeFiles()
    {
        _description = files.matcher(_description)
            .replaceAll("");
    }

    private static final Pattern files = Pattern.compile("\\[\\[File:[^\\]]++\\]\\]");

    private void removeReferences()
    {
        _description = references.matcher(_description)
            .replaceAll("");
    }

    private static final Pattern references = Pattern.compile("<ref[^/>]*+/>|<ref[^>]*+>[^<]*+</ref>");

    private void retainFirstSentence()
    {
        Matcher matcher = sentences.matcher(_description);
        matcher.find();
        _description = matcher.group(1);
    }

    private static final Pattern sentences = Pattern.compile(
            "^([\\w][^.]*+\\.)\\s", Pattern.MULTILINE);

    public Collection<Animal> getMammals()
    {
        Set<Animal> puffer = new HashSet<Animal>();
        for (Animal animal : _animals.values())
        {
            if (animal.isMammal())
            {
                puffer.add(animal);
            }
        }
        return puffer;
    }
}
