package NeuralNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class SaveLoad {
    private final NeuralNetwork nn;
    
    private final String file_name = "synapses.txt";
    private final File file = new File(file_name);
    
    private boolean first_line = true;
    
    public SaveLoad(NeuralNetwork nn) {
        this.nn = nn;
    }
    
    protected boolean fileExists() {        
        return file.exists() && ! file.isDirectory();
    }
    
    protected void saveToFile() throws FileNotFoundException, UnsupportedEncodingException {
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            if(! first_line) {
                writer.print("\n");
            }
            else {
                first_line = false;
            }
            
            // Append the current generation at the end of the file
            for(int l = 0; l < nn.genomes_per_generation; l++) {
                for(int i = 0; i < nn.layers_amount - 1; i++) {
                    for(int j = 0; j < nn.neurons_amount[i]; j++) {
                        int m;
                        if(i + 1 != nn.layers_amount - 1) {
                            m = nn.neurons_amount[i + 1] - 1;
                        }
                        else {
                            m = nn.neurons_amount[i + 1];
                        }
                        for(int k = 0; k < m; k++) {
                            writer.print(nn.synapses[l][i][j][k] + " ");
                        }
                    }
                }
            }
        }
    }
    
    protected void loadFromFile() throws FileNotFoundException, IOException {
        String current_line, last_generation = null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        // Get the last line and store it into last_generation
        while((current_line = reader.readLine()) != null) {
            last_generation = current_line;
        }
        
        // Split the string and store the numbers (as text)
        List<String> values = Arrays.asList(last_generation.trim().split(" "));
        
        // Init the synapsis
        int n = 0;
        for(int l = 0; l < nn.genomes_per_generation; l++) {
            for(int i = 0; i < nn.layers_amount - 1; i++) {
                for(int j = 0; j < nn.neurons_amount[i]; j++) {
                    int m;
                    if(i + 1 != nn.layers_amount - 1) {
                        m = nn.neurons_amount[i + 1] - 1;
                    }
                    else {
                        m = nn.neurons_amount[i + 1];
                    }
                    for(int k = 0; k < m; k++) {
                        nn.synapses[l][i][j][k] = Double.parseDouble(values.get(n));
                        n++;
                    }
                }
            }
        }
    }
}
