package plursal;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import plursal_UI.PLUI;


public class Plursal {

    private String command, file_path;
    private List<String> tokens; 
    private int lines_count = 0;
    
    public Plursal() {
        
    }
    
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setSize(666,420);
                PLUI plui = new PLUI();
                frame.add(plui);
                plui.setVisible(true);
                frame.setVisible(true);

            }
        });
    }


    
    public Plursal(String file_path) {
        tokens = new ArrayList<String>();
        try
        {
          if(!file_path.endsWith(".txt")) {
              JOptionPane.showMessageDialog(new JFrame(), "Wrong file extension, use .txt file!");
              throw new Exception("The file should be in .txt format");
          }
          BufferedReader reader = new BufferedReader(new FileReader(file_path));
          String line;
          while ((line = reader.readLine()) != null)
          {
            tokens.addAll(Arrays.asList(line.split(" ")));
            tokens.add("\n");
            lines_count++;
          }
          reader.close();
          this.file_path = file_path;
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", file_path);
          JOptionPane.showMessageDialog(new JFrame(), "Wrong filename " + file_path);
          file_path = null;
          e.printStackTrace();
          
        }
    }
    
    public void execute(String command) throws Exception {
        try {
            String[] splitArray = command.split("\\s+");
            if(splitArray.length >= 1 && splitArray.length <= 4 && splitArray.length != 3) {
                if(splitArray.length == 2) {
                    ArrayList<Integer> commands = new ArrayList<Integer>();
                    for(int i = 0; i < splitArray.length; i++) {
                        commands.add(Integer.parseInt(splitArray[i]));
                        if(commands.get(i) <= 0 || commands.get(i) > lines_count) {
                            throw new Exception("Invalid value for the index: " + commands.get(i));
                        }
      
                    }
                    try {
                        //System.out.println(commands.get(0) + " " + commands.get(1) + "\n\n");
                        swapLine(tokens, commands.get(0), commands.get(1));
                        //System.out.println("\n\n" + tokens.toString());
                        } catch(Exception InvalidSwap) {
                            throw InvalidSwap;
                        }
                    
                } else if(splitArray.length == 4) {
                    
                }
            } else {
                throw new Exception("Wrong command");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public String printStack() {
        String joinString = "";
        if (tokens == null || tokens.size() == 0) {
            return "";
        } else if (tokens.size() == 1) {
            return tokens.get(0);
        } else {
            StringBuilder sb = new StringBuilder(tokens.size() * 1 + tokens.get(0).length());
            sb.append(tokens.get(0));
            for (int i = 1; i < tokens.size(); i++) {
                sb.append(" ");
                sb.append(joinString).append(tokens.get(i));
            }
            
            return sb.toString();
        }
        
    }
    
    public void swapLine(List<String> tokens, int index1, int index2) {
        int start_first = 0, end_first = 0, start_second = 0, end_second = 0;
        for(int i = 0; i < tokens.size(); i++){
            if(tokens.get(i) == "\n") {
                if(--index1 == 0) {
                    end_first = i;
                    break;
                } else {
                    if(i+1 <= tokens.size())
                        start_first = i+1;
                    else
                        start_first = i;
                }
            }
            //System.out.println("exec\n");
        }
        
        for(int i = 0; i < tokens.size(); i++){
            if(tokens.get(i) == "\n") {
                if(--index2 == 0) {
                    end_second = i;
                    break;
                } else {
                    if(i+1 <= tokens.size())
                        start_second = i+1;
                    else
                        start_second = i;
                }
            }
        }
        
       
       ArrayList<String> temp = new ArrayList<String>();
       for(int i = start_first; i < end_first; i++) {
           temp.add(tokens.get(i));
           tokens.set(i, "");
       }
       
       int token_start_temp = start_first;
       for(int i = start_second; i < end_second; i++) {
           tokens.set(token_start_temp, tokens.get(i));
           token_start_temp++;
           tokens.set(i, "");
       }
      
       for(int i = start_first, j = 0; j < end_first; i++, j++) {
           tokens.set(start_second, temp.get(j));
           start_second++;
       }
       
       System.out.println(tokens.toString());
        
       
       
    }

    public String getFileName() {
        return file_path;
    }

    public String ToString() {
        StringBuilder sb = new StringBuilder(65536);

        for (String classLines : tokens)
                sb.append(classLines);
        
        String result = sb.toString();
        return result;
    }

}
