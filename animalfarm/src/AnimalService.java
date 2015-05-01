import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AnimalService
{
    private Map<String, String> _animals;
    private String _animal;
    private String _description;

    public AnimalService()
    {
        _animals = new HashMap<>();
    }

    public String describe(String givenAnimal)
    {
        _animal = givenAnimal;
        normalizeAnimal();
        _description = _animals.get(_animal);
        if (_description == null)
        {
            System.out.println("Hmm, let me see...");
            consultWikipedia();
            String animalBeforeRedirect = _animal;
            resolveRedirect();
            removeCurlyBracedMetaData();
            removeEmphasizers();
            removeHyperlinks();
            removeFiles();
            removeReferences();
            retainFirstSentence();
            _animals.put(animalBeforeRedirect, _description);
            if (_animal != animalBeforeRedirect)
            {
                normalizeAnimal();
                _animals.put(_animal, _description);
            }
        }
        return _description;
    }

    private void normalizeAnimal()
    {
        _animal = _animal.trim();
        _animal = _animal.toLowerCase();
        _animal = whitespaceStrings.matcher(_animal)
            .replaceAll("_");
    }

    private static final Pattern whitespaceStrings = Pattern.compile("\\s++");

    private void consultWikipedia()
    {
        try
        {
            URL url = new URL(
                    "http://en.wikipedia.org/w/index.php?action=raw&title="
                            + _animal);
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
            _description = "Internet problems. ";
        }
    }

    private void resolveRedirect()
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
}
