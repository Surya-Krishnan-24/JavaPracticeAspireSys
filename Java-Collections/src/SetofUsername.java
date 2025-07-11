import java.util.Objects;

public class SetofUsername {
    int ID;
    String username;

    public SetofUsername(int ID, String username) {
        this.ID = ID;
        this.username = username;
    }


    @Override
    public String toString() {
        return "SetofUsername{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SetofUsername that = (SetofUsername) o;
        return ID == that.ID && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, username);
    }
}
