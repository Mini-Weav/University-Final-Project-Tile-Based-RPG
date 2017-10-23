import javax.swing.*;
import java.awt.*;

/**
 * Created by lmweav on 23/10/2017.
 */
public class JEasyFrame extends JFrame {
    public Component component;
    public JEasyFrame(Component component, String title) {
        super(title);
        this.component = component;
        getContentPane().add(BorderLayout.CENTER, component);
        pack();
        this.setVisible(true);

        this.setResizable(false);
        this.setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        repaint();
    }
}
