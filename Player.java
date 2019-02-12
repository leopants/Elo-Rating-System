package smashelo;

/**
 * A class that creates player objects
 * @author Leo
 */
public class Player {

    private String name;
    private int ranking;

    /**
     * @param name name of the player
     * @param ranking player elo ranking
     */
    public Player(String name, int ranking) {
        this.name = name;
        this.ranking = ranking;
    }

    /**
     * Gets the name of the current player
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current player's elo ranking
     * @return elo rnaking of the player
     */
    public int getRanking() {
        return ranking;
    }

    /**
     * Changes the rank of the player to desired amount
     * @param rank player's elo ranking
     */
    public void setRanking(int rank) {
        this.ranking = rank;
    }
}
