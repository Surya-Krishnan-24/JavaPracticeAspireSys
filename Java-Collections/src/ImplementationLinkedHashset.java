import java.util.LinkedHashSet;

public class ImplementationLinkedHashset {
    public static void main(String[] args) {
        LinkedHashSet<LinkedHashsetMessages> messages = new LinkedHashSet<>();


        messages.addFirst(new LinkedHashsetMessages(101,"Surya", "Senthil", "Hai senthil"));
        messages.add(new LinkedHashsetMessages(102, "senthil", "surya", "Hai surya"));
        messages.add(new LinkedHashsetMessages(103,"surya","senthil","How are you"));
        messages.addLast(new LinkedHashsetMessages(103, "surya", "senthil","How are you"));
        messages.addLast(new LinkedHashsetMessages(0,null,null,null));

        System.out.println(messages);

        System.out.println(messages.size());

        System.out.println(messages.isEmpty());

        messages.clear();

        System.out.println(messages.size());

        System.out.println(messages.getFirst());

        System.out.println(messages.getLast());
    }
}
