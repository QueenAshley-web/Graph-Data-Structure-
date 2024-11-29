import java.util.*;

class FacebookGraph {
    private Map<String, Set<String>> userNetwork = new HashMap<>();

    // Add a user to the graph
    public void addUser(String user) {
        userNetwork.putIfAbsent(user, new HashSet<>());
    }

    // Add a friendship (edge)
    public void addFriendship(String user1, String user2) {
        if (!userNetwork.containsKey(user1) || !userNetwork.containsKey(user2)) {
            System.out.println("One or both users do not exist!");
            return;
        }
        userNetwork.get(user1).add(user2);
        userNetwork.get(user2).add(user1);
    }

    // Get friends of a user
    public Set<String> getFriends(String user) {
        return userNetwork.getOrDefault(user, Collections.emptySet());
    }

    // Suggest friends (mutual friends)
    public Set<String> suggestFriends(String user) {
        if (!userNetwork.containsKey(user)) return Collections.emptySet();

        Set<String> suggestions = new HashSet<>();
        Set<String> friends = userNetwork.get(user);

        for (String friend : friends) {
            for (String friendOfFriend : userNetwork.get(friend)) {
                if (!friendOfFriend.equals(user) && !friends.contains(friendOfFriend)) {
                    suggestions.add(friendOfFriend);
                }
            }
        }
        return suggestions;
    }

    public static void main(String[] args) {
        FacebookGraph fb = new FacebookGraph();

        // Add users
        fb.addUser("Alice");
        fb.addUser("Bob");
        fb.addUser("Charlie");
        fb.addUser("Diana");
        fb.addUser("Eve");

        // Add friendships
        fb.addFriendship("Alice", "Bob");
        fb.addFriendship("Bob", "Charlie");
        fb.addFriendship("Charlie", "Diana");
        fb.addFriendship("Alice", "Eve");

        // Get friends of a user
        System.out.println("Alice's Friends: " + fb.getFriends("Alice"));

        // Suggest friends for Alice
        System.out.println("Friend Suggestions for Alice: " + fb.suggestFriends("Alice"));
    }
}