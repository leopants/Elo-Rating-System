package smashelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Driver class for the program
 * @author Leo
 */
public class SmashElo {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("PlayerInfo.txt"));
        PlayerBase pb = new PlayerBase();

        if (br.readLine() != null) {
            Scanner fileIn = new Scanner(new File("PlayerInfo.txt"));

            while (fileIn.hasNext()) {
                String playerName = fileIn.next();
                String playerRanking = fileIn.next();
                int ranking = Integer.parseInt(playerRanking);
                Player newPlayer = new Player(playerName, ranking);
                pb.add(newPlayer);
            }

            pb.start();
        } 
        
        else {
            pb.start();
        }
    }

}
