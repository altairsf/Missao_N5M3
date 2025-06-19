package cadastroclientv2;

import javax.swing.JDialog;
import javax.swing.JTextArea;

/**
 *
 * @author Altair
 */
public class SaidaFrame extends JDialog {
    public JTextArea texto;
    public SaidaFrame() {
        setBounds(100, 100, 400, 300);
        setModal(false);

        texto = new JTextArea();
        add(texto);
    }    
}
