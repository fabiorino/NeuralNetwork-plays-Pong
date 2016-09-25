package NeuralNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveLoad {
    NeuralNetwork nn;
    
    String file_name = "synapses.txt";
    File file = new File(file_name);
    
    boolean first_line = true;
    
    int i, j, k, l, m, n;
    
    public SaveLoad(NeuralNetwork nn) {
        this.nn = nn;
    }
    
    public boolean fileExists() {        
        return file.exists() && ! file.isDirectory();
    }
    
    public void saveToFile() throws FileNotFoundException, UnsupportedEncodingException {
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            if(! first_line) {
                writer.print("\n");
            }
            else {
                first_line = false;
            }
            
            // Append the current generation at the end of the file
            for(l = 0; l < nn.genomes_per_generation; l++) {
                for(i = 0; i < nn.layers_amount - 1; i++) {
                    for(j = 0; j < nn.neurons_amount[i]; j++) {
                        if(i + 1 != nn.layers_amount - 1) {
                            m = nn.neurons_amount[i + 1] - 1;
                        }
                        else {
                            m = nn.neurons_amount[i + 1];
                        }
                        for(k = 0; k < m; k++) {
                            writer.print(nn.synapses[l][i][j][k] + " ");
                        }
                    }
                }
            }
        }
    }
    
    public void loadFromFile() throws FileNotFoundException, IOException {
        String current_line, last_generation = null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        // Get the last line and store it into last_generation
        while((current_line = reader.readLine()) != null) {
            last_generation = current_line;
        }
        
        // Split the string and store the numbers (as text)
        List<String> values = new ArrayList<String>();        
        values = Arrays.asList(last_generation.trim().split(" "));
        
        // Init the synapsis
        n = 0;
        for(l = 0; l < nn.genomes_per_generation; l++) {
            for(i = 0; i < nn.layers_amount - 1; i++) {
                for(j = 0; j < nn.neurons_amount[i]; j++) {
                    if(i + 1 != nn.layers_amount - 1) {
                        m = nn.neurons_amount[i + 1] - 1;
                    }
                    else {
                        m = nn.neurons_amount[i + 1];
                    }
                    for(k = 0; k < m; k++) {
                        nn.synapses[l][i][j][k] = Double.parseDouble(values.get(n));
                        n++;
                    }
                }
            }
        }
    }
}
