package cadastroclientv2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CadastroClientV2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 4321); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {

            //Janela de saída
            SaidaFrame saidaFrame = new SaidaFrame();
            saidaFrame.setVisible(true);

            //Thread
            ThreadClient threadClient = new ThreadClient(in, saidaFrame.texto);
            threadClient.start();

            // Login e senha
            System.out.print("Digite o login: ");
            String login = reader.readLine();
            out.writeObject(login);
            System.out.print("Digite a senha: ");
            String senha = reader.readLine();
            out.writeObject(senha);
            out.flush();

            //Comandos
            String comando;
            while (true) {
                System.out.println("--------------------------:");
                System.out.println("\nMenu:");
                System.out.println("L - Listar | X - Finalizar | E - Entrada | S - Saída");

                System.out.print("Digite o comando: ");
                comando = reader.readLine();

                if (comando.equalsIgnoreCase("L")) {
                    out.writeObject("L");
                    out.flush();
                } else if (comando.equalsIgnoreCase("E") || comando.equalsIgnoreCase("S")) {
                    out.writeObject(comando);
                    out.flush();

                    System.out.print("Digite o ID do Movimento: ");
                    int idMovimento = Integer.parseInt(reader.readLine());
                    out.writeObject(idMovimento);

                    System.out.print("Digite o ID da pessoa: ");
                    int idPessoa = Integer.parseInt(reader.readLine());
                    out.writeObject(idPessoa);

                    System.out.print("Digite o ID do produto: ");
                    int idProduto = Integer.parseInt(reader.readLine());
                    out.writeObject(idProduto);

                    System.out.print("Digite a quantidade: ");
                    int quantidade = Integer.parseInt(reader.readLine());
                    out.writeObject(quantidade);

                    System.out.print("Digite o valor unitário: ");
                    int valorUnitario = Integer.parseInt(reader.readLine());
                    out.writeObject(valorUnitario);

                    out.flush();

                } else if (comando.equalsIgnoreCase("X")) {
                    out.writeObject("SAIR");
                    out.flush();
                    break;
                } else {
                    System.out.println("Comando inválido.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
