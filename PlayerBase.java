package smashelo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Class that holds the players of the game and manipulates that information
 * @author Leo
 */
public class PlayerBase {

    Player players[];
    int size = 10;
    int counter;
    Scanner scnr = new Scanner(System.in);
    Player firstPlayer;
    Player secondPlayer;

    /**
     * Initializes a data base of players done as an array
     */
    public PlayerBase() {
        players = new Player[size]; //creates an array of player objects
        counter = 0;
    }
    
    /**
     * Begins the program and creates the main window for navigating the user interface
     */
    public void start() {
        JOptionPane.showMessageDialog(null, "Welcome.");    //introductory window
        JFrame window = new JFrame();   //new main frame

        JButton add = new JButton("<html>Add<br />Player</html>");  //buttons in the frame all follow this convention
        add.setBounds(50, 30, 80, 80);  
        add.addActionListener(new ActionListener() {    //action if the button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                addNew();
                window.setVisible(true);
            }
        });

        JButton viewRankings = new JButton("<html>View<br />Rankings</html>");
        viewRankings.setBounds(150, 30, 80, 80);
        viewRankings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                print();
                window.setVisible(true);
            }
        });
        
        JButton match = new JButton("<html>Input<br />Match</html>");
        match.setBounds(250, 30, 80, 80);
        match.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                match();
                window.setVisible(true);
            }
        });
        
        JButton remove = new JButton("<html>Remove<br />Player</html>");
        remove.setBounds(350, 30, 80, 80);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                remove();
                window.setVisible(true);
            }
        });
        
        JButton clear = new JButton("<html>Clear<br />Info</html>");
        clear.setBounds(450, 30, 80, 80);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                try {
                    clearInfo();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PlayerBase.class.getName()).log(Level.SEVERE, null, ex);
                }
                window.setVisible(true);
            }
        });

        JButton close = new JButton("Close");
        close.setBounds(550, 30, 80, 80);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                try {
                    close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PlayerBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        window.add(add);    //add the buttons to the frame
        window.add(viewRankings);
        window.add(match);
        window.add(remove);
        window.add(clear);
        window.add(close);

        window.setSize(680, 200);
        window.setLayout(null);
        window.setLocationRelativeTo(add);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Used to get the average of the ratings of the players in order to assign the rating of new players
     * @return an integer value of the average of all player's ratings
     */
    public int getAverage() {
        int sum = 1200;
        for (int i = 0; i < counter; i++) {
            sum += players[i].getRanking();
        }
        return sum / (counter + 1);
    }
    
    /**
     * Removes information from save file and restarts the players list
     * @throws FileNotFoundException 
     */
    public void clearInfo() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("PlayerInfo.txt");
        for(int i = 0; i < counter; i++) {
            players[i] = null;
        }
        counter = 0;
    }

    /**
     * Adds a new player to the list of players with a rating equal to the average of the players
     */
    public void addNew() {
        String playerName = JOptionPane.showInputDialog(null, "What is the name of the player?");
        Player addedPlayer = new Player(playerName, getAverage());
        players[counter] = addedPlayer;
        counter++;
        JOptionPane.showMessageDialog(null, "Player '" + playerName + "' succesfully added.");
    }

    /**
     * Add method used to add players from the save file
     * @param player person to be added
     */
    public void add(Player player) {
        players[counter] = player;
        counter++;
    }

    /**
     * Method to attain a list of players and their respective ratings
     */
    public void print() {
        String[] columnNames = {"Player Name", "Rating"};
        String[][] data = new String[counter][2];

        for (int i = 0; i < counter; i++) {
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    data[i][j] = players[i].getName();
                }
                if (j == 1) {
                    data[i][j] = Integer.toString(players[i].getRanking());
                }
            }
        }

        JTable table = new JTable(data, columnNames);

        JOptionPane.showMessageDialog(null, new JScrollPane(table));

    }
    
    /**
     * Prompts the user for the name to remove and removes that person from the list
     */
    public void remove() {
        String playerName = JOptionPane.showInputDialog("Name of player you wish to remove?");
        
        for(int i = 0; i < counter; i++) {
            if(players[i].getName().equals(playerName)) {
                for(int j = i; j < counter - 1; j++) {
                    players[j] = players[j+1];
                }
                break;
            }
           
        }
        counter --;
    }
    
    /**
     * Prints the names to a save file for future use and closes the application
     * @throws FileNotFoundException 
     */
    public void close() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("PlayerInfo.txt");
        for(int i = 0; i < counter; i++) {
            writer.println(players[i].getName() + " " + players[i].getRanking());
        }
        writer.close();
        System.exit(0);
    }

    /**
     * Method for inputing the results of a match
     */
    public void match() {
        String player1 = JOptionPane.showInputDialog("Please enter the name of the first player");
        
        if (checkPlayer(player1) < 0) {
            JOptionPane.showMessageDialog(null, "Please insert this player first and then they can be ranked");
            return;
        }
        firstPlayer = players[checkPlayer(player1)];

        String player2 = JOptionPane.showInputDialog("Please enter the name of the second player");
        
        if (checkPlayer(player2) < 0) {
            JOptionPane.showMessageDialog(null, "Please insert this player first and then they can be ranked");
            return;
        }
        secondPlayer = players[checkPlayer(player2)];
        
        String[] choice = {player1, player2};
        
        String input = (String) JOptionPane.showInputDialog(null, "Which player won?",
                "Select Winner", JOptionPane.QUESTION_MESSAGE, null, choice, choice[0]);
        
        float firstRankFloat = firstPlayer.getRanking();
        float secondRankFloat = secondPlayer.getRanking();

        if(input.equals(player1)) {
            EloRating(firstRankFloat, secondRankFloat, 50, true);
        }
        else {
            EloRating(firstRankFloat, secondRankFloat, 50, false);
        }
        

    }

    /**
     * Checks if a player is in the database of players
     * @param x player to be checked
     * @return -1 if the player is not found or the index of the player
     */
    public int checkPlayer(String x) {
        int index = -1;
        for (int i = 0; i < counter; i++) {
            if (x.equalsIgnoreCase(players[i].getName())) {
                return i;
            }
        }
        return index;
    }

    /**
     * Probability of player one defeating over player two
     * @param rating1 elo rating of the first player
     * @param rating2 elo rating of the second player
     * @return probability of one beating two
     */
    public float probability(float rating1, float rating2) {
        return 1.0f * 1.0f / (1 + 1.0f * (float) (Math.pow(10, 1.0f * (rating1 - rating2) / 400)));
    }

    /**
     * Updates the elo ratings of the two players involved in a match based on who wins
     * @param firstRankFloat the rating of the first player
     * @param secondRankFloat the rating of the second player
     * @param K some constant to normalize the winnings
     * @param d true if player one won, false if player two wins 
     */
    public void EloRating(float firstRankFloat, float secondRankFloat, int K, boolean d) {

        float Pb = probability(firstRankFloat, secondRankFloat);

        float Pa = probability(secondRankFloat, firstRankFloat);

        if (d == true) {
            firstRankFloat = firstRankFloat + K * (1 - Pa);
            secondRankFloat = secondRankFloat + K * (0 - Pb);
        } else {
            firstRankFloat = firstRankFloat + K * (0 - Pa);
            secondRankFloat = secondRankFloat + K * (1 - Pb);
        }

        firstPlayer.setRanking(Math.round(firstRankFloat));
        secondPlayer.setRanking(Math.round(secondRankFloat));
        print();
    }
}
