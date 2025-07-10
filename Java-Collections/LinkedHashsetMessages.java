import java.util.Objects;

public class LinkedHashsetMessages {
    int ID;
    String Sender;
    String Reciever;
    String message;

    public LinkedHashsetMessages(int ID, String sender, String reciever, String message) {
        this.ID = ID;
        Sender = sender;
        Reciever = reciever;
        this.message = message;
    }

    @Override
    public String toString() {
        return "LinkedHashsetMessages{" +
                "ID=" + ID +
                ", Sender='" + Sender + '\'' +
                ", Reciever='" + Reciever + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LinkedHashsetMessages that = (LinkedHashsetMessages) o;
        return ID == that.ID && Objects.equals(Sender, that.Sender) && Objects.equals(Reciever, that.Reciever) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Sender, Reciever, message);
    }
}
