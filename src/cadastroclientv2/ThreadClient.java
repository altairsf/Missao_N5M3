package cadastroclientv2;

import java.io.ObjectInputStream;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import model.Produto;

/**
 *
 * @author Altair
 */
public class ThreadClient extends Thread {
    private ObjectInputStream entrada;
    private JTextArea textArea;
    
    public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Object resposta = entrada.readObject();
                if (resposta instanceof String) {
                    String msg = (String) resposta;
                    SwingUtilities.invokeLater(() -> textArea.append(msg + "\n"));
                } else if (resposta instanceof List) {
                    List<?> lista = (List<?>) resposta;
                    for (Object obj : lista) {
                       if (obj instanceof Produto) {
                           Produto p = (Produto) obj;
                           String linha = "Produto: " + p.getNome() + " | Quantidade: " + p.getQuantidade();
                           SwingUtilities.invokeLater(() -> textArea.append(linha + "\n"));
                       } 
                    }
                }
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> textArea.append("Conexão encerrada.\n"));
        }
    }
}
