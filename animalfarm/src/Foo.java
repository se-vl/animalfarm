public class Foo
{
    private static void helper(String s)
    {
        for (int i = 0; i < s.length(); ++i)
        {
            System.out.printf("%d: <%d>%n", i, (int) s.charAt(i));
        }
    }

    public static void main(String[] args)
    {
        // 0: 10
        // 1: 12
        // 2: 3
        // 3: 0,5
        // 4: 1
        // helper("\n");
        System.out.println("\\");

    }
}
