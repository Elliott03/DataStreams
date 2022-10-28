import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;

public class Frame extends JFrame {
    JPanel mainPanel;


    JPanel textPanel;

    JTextArea allText;
    JTextArea subText;

    JScrollPane allScroll;
    JScrollPane subScroll;


    JPanel controlPanel;

    JTextField searchString;

    JButton loadButton;

    JButton searchButton;

    JButton quitButton;
    List<String> list;





    public Frame() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        createTextPanel();
        mainPanel.add(textPanel, BorderLayout.CENTER);

        createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createTextPanel() {
        textPanel = new JPanel();
        allText = new JTextArea(20, 40);
        subText = new JTextArea(20, 40);

        allScroll = new JScrollPane(allText);
        subScroll = new JScrollPane(subText);
        allText.setEditable(false);
        subText.setEditable(false);

        textPanel.add(allScroll);
        textPanel.add(subScroll);
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        loadButton = new JButton("Load File");
        searchString = new JTextField(15);
        searchButton = new JButton("Search");
        quitButton = new JButton("Quit");

        loadButton.addActionListener((ActionEvent ae) -> {
            JFileChooser chooser = new JFileChooser();
            File selectedFile;

            String record = "";
            try {


                File workingDirectory = new File(System.getProperty("user.dir"));

                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    Path file = selectedFile.toPath();
                    InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    int line = 0;
                    list = new ArrayList<>();
                    while (reader.ready()) {
                        record = reader.readLine();
                        list.add(record);
                        allText.append(record + "\n");
                        line++;


                    }
                    reader.close();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        searchButton.addActionListener((ActionEvent ae) -> {
            String testString = searchString.getText();
            List<String> tempList =
            list.stream().filter(e -> e.toLowerCase().contains(testString.toLowerCase()))
                    .collect(Collectors.toList());
            for (String s : tempList) {
                subText.append(s + "\n");
            }
        });

        quitButton.addActionListener((ActionEvent ae) -> {
            System.exit(0);
        });

        controlPanel.add(loadButton);
        controlPanel.add(searchString);
        controlPanel.add(searchButton);
        controlPanel.add(quitButton);
    }



}
