package cadastroclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;

public class CadastroClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 4321); 
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); 
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
                
                    System.out.println("------------------------------");
                    System.out.print("Login: ");
                    String login = reader.readLine();
                    
                    System.out.print("Senha: ");
                    String senha = reader.readLine();

                    out.writeObject(login);
                    out.writeObject(senha);
                    out.flush();

                Object respostaInicial = in.readObject();
                if (respostaInicial instanceof String) {
                    String msg = (String) respostaInicial;
                    if (msg.startsWith("ERRO")) {
                        System.out.println("Erro do servidor: " + msg);
                        return;
                    } else {
                        System.out.println("Servidor: " + msg);
                    }               
                }

            //Lista de produtos
            while (true) {
                System.out.print("Digite um comando (L para listar, S para sair): ");
                String comando = reader.readLine();
                out.writeObject(comando);
                out.flush();

                Object resposta = in.readObject();

                if (resposta instanceof String) {
                    String texto = (String) resposta;
                    System.out.println("Servidor: " + texto);
                    if (texto.equalsIgnoreCase("Encerrando conexão.")) {
                        break;  // Cliente sai do loop e encerra
                    }
                } else if (resposta instanceof List) {
                    List<?> lista = (List<?>) resposta;
                    for (Object obj : lista) {
                        if (obj instanceof Produto) {
                            Produto p = (Produto) obj;
                            System.out.println("Produto: " + p.getNome());
                        }
                    }
                } else {
                    System.out.println("Resposta inesperada do servidor.");
                }
            }
            System.out.println("Cliente encerrado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
