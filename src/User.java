import java.util.UUID;

public class User {
    private String name;
    private String id;

    public User(String name) {
        this.name = name;
        UUID uuid = UUID.randomUUID();
        id = uuid.toString();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User: " + name + ", " +
                "id=" + id;
    }
}
