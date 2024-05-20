import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// To play sound
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Question4
{
	
	private static final int F_WIDTH = 500;
	private static final int F_HEIGHT = 600;

	// Audio file (in the same directory) that would be played once user give invalid input
	private static final String WARNING_AUDIO = "invalid-input-warning.wav";

	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setTitle("Question 4");
		frame.setSize(F_WIDTH, F_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		// Set frame's content pane with an empty border.
		JPanel contentPanel = new JPanel(new BorderLayout());
		Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		contentPanel.setBorder(padding);
		frame.setContentPane(contentPanel);
		
		// ArrayList to store all input fields that are to be displayed
		ArrayList<JTextField> inputFields = new ArrayList<JTextField>();
		
		// Components to set number of inputs needed
		JLabel numCountLabel = new JLabel("How many numbers would you like to enter?\t");
		SpinnerNumberModel model = new SpinnerNumberModel(5, 2, 10, 1);
		JSpinner numCountSpinner = new JSpinner(model);
		
		// Labels to show instructions and results
		JLabel instruction = new JLabel("Input numbers into the input fields below.");
		JLabel resultsLabel = new JLabel("Click \"Calculate Sum!\" to get results");
		resultsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		// Main panel where instruction (NORTH), input fields (CENTER) and results (SOUTH) are displayed
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(padding);
		mainPanel.add(instruction, BorderLayout.NORTH);
		mainPanel.add(resultsLabel, BorderLayout.SOUTH);
		contentPanel.add(mainPanel, BorderLayout.CENTER);
		
		numCountSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				inputFields.clear();
				JSpinner input = (JSpinner) e.getSource();
				int numCount = (int) input.getValue();
				
				JPanel inputPanel = new JPanel(new GridLayout(numCount / 2 + 1, 2, 20, 20));
				inputPanel.setBorder(padding);
				
				// Reset mainPanel
				mainPanel.removeAll();
				mainPanel.add(instruction, BorderLayout.NORTH);
				mainPanel.add(inputPanel, BorderLayout.CENTER);
				mainPanel.add(resultsLabel, BorderLayout.SOUTH);
				
				for (int i = 0; i < numCount; i++)
				{
					inputFields.add(new JTextField());
					inputPanel.add(inputFields.get(i));
					frame.setVisible(true);
				}
			}
		});
		
		JButton submitButton = new JButton("Calculate Sum!");
		submitButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me)
			{
				boolean hasInvalidInputs = false;
				double sum = 0;
				for (JTextField input : inputFields)
				{
					try
					{
						sum += Double.parseDouble(input.getText());
					}
					catch (NumberFormatException e)
					{
						hasInvalidInputs = true;
						if (input.getText().isBlank())
						{
							input.setText("INPUT NOT PROVIDED!");
						}
						else
						{
							input.setText("ONLY NUMBERS ALLOWED!");							
						}
						playSound(WARNING_AUDIO);
						continue;
					}
				}
				if (!hasInvalidInputs)
				{					
					resultsLabel.setText("Sum: " + sum);
				}
			}
		});
		contentPanel.add(submitButton, BorderLayout.SOUTH);
		
		JPanel settingsPanel = new JPanel(new GridLayout(2, 1));
		settingsPanel.add(numCountLabel);
		settingsPanel.add(numCountSpinner);
		contentPanel.add(settingsPanel, BorderLayout.NORTH);
		
		frame.setVisible(true);
	}
	
	private static void playSound(String filePath)
	{
		try
		{
			File audioFile = new File(filePath);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());

			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.start();
		}
		catch (Exception e)
		{
			// Do nothing. If file is not found or other errors unfold, no audio will play.
		}
	}
}