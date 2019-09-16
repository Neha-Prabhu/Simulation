package mini;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;


public class RedBlackTreeViewer extends JFrame
{
    RedBlackTree tree = new RedBlackTree();

    JFrame frame = new JFrame("Red Black Tree Simulator");
    JTextField valueField = new JTextField(40);
    JPanel buttonPanel = new JPanel();
    BinaryTreePanel panel = new BinaryTreePanel(null, 200, 60);
    JScrollPane displayArea = new JScrollPane();
    JLabel messageLine = new JLabel();

   
    private abstract class Operation implements ActionListener
    {
        public Operation(String label)
        {
            JButton button = new JButton(label);
            buttonPanel.add(button);
            button.addActionListener(this);
        }
        public void actionPerformed(ActionEvent event)
        {
            String value = valueField.getText();
            messageLine.setText("");
            try
            {
                execute(value);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }  
            panel.setTree(tree.getRoot());
            valueField.requestFocus();
            valueField.selectAll();
        }
        protected abstract void execute(String value);
    }

  
    public RedBlackTreeViewer()
    {
        JPanel valuePanel = new JPanel();
        valuePanel.add(new JLabel("Value: "));
        valuePanel.add(valueField);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(0, 1));
        controlPanel.add(valuePanel);
        controlPanel.add(buttonPanel);

        // NOTE: Hardcoded preferred size!  Fix this in the exercises.
        panel.setPreferredSize(new Dimension(2048, 2048));
        panel.setBackground(Color.white);
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        displayArea.setViewportView(panel);

        frame.setBackground(Color.lightGray);
        frame.getContentPane().add(controlPanel, "North");
        frame.getContentPane().add(displayArea, "Center");
        frame.getContentPane().add(messageLine, "East");
        frame.pack();

        new Operation("Add")
        {
            protected void execute(String value)
            {
                tree.add(value);
            }
        };
    
        new Operation("Search")
        {
            protected void execute(String value)
            {
                messageLine.setText("The value \"" + value + "\" is " +
                    (tree.contains(value) ? "" : "not ") + "found");
            }
        };
     
    }


    public static void main(String[] args) 
    {
        RedBlackTreeViewer viewer = new RedBlackTreeViewer();
        viewer.frame.setSize(2000, 1000);
        viewer.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewer.frame.setVisible(true);
    }
}