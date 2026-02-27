import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class UsernameSystem {
    // Stores existing usernames (username -> userId)
    private final Map<String, String> registeredUsers = new ConcurrentHashMap<>();

    // Tracks popularity of attempted usernames (username -> attemptCount)
    private final Map<String, AtomicInteger> attemptTracker = new ConcurrentHashMap<>();

    public UsernameSystem() {
        // Mock data for 10 million users simulation
        registeredUsers.put("john_doe", "UID123");
        registeredUsers.put("admin", "UID001");
    }
    public boolean checkAvailability(String username) {
        boolean isTaken = registeredUsers.containsKey(username.toLowerCase());

        if (isTaken) {
            // Increment popularity count safely
            attemptTracker.computeIfAbsent(username.toLowerCase(), k -> new AtomicInteger(0))
                    .incrementAndGet();
            return false;
        }
        return true;
    }


    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int suffix = 1;

        while (suggestions.size() < 3) {
            String candidate = username + suffix;
            if (!registeredUsers.containsKey(candidate)) {
                suggestions.add(candidate);
            }
            suffix++;
        }
        return suggestions;
    }

    public String getMostAttempted() {
        return attemptTracker.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().get()))
                .map(Map.Entry::getKey)
                .orElse("No attempts recorded");
    }

    public static void main(String[] args) {
        UsernameSystem sys = new UsernameSystem();

        // 1. Check availability
        System.out.println("Is 'john_doe' available? " + sys.checkAvailability("john_doe"));
        System.out.println("Is 'jane_smith' available? " + sys.checkAvailability("jane_smith"));

        // 2. Get Suggestions
        if (!sys.checkAvailability("john_doe")) {
            System.out.println("Suggestions for 'john_doe': " + sys.suggestAlternatives("john_doe"));
        }

        // 3. Check popularity (simulating multiple attempts)
        sys.checkAvailability("admin");
        sys.checkAvailability("admin");
        System.out.println("Most attempted username: " + sys.getMostAttempted());
    }
}
