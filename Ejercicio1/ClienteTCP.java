import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCP {
	public static void main(String[] args ) throws IOException {
		Socket socketCliente=null;
		
		BufferedReader entrada = null;
		PrintWriter salida = null;
		
		System.out.println("------Cliente-----");
		System.out.println("Introducir una de las opciones: ");
		System.out.println("Menu");
		System.out.println("Opcion 1");
		System.out.println("Opcion 2");
		System.out.println("Opcion 3");
		System.out.println("Opcion 4: Salir");
		
		try {
			socketCliente = new Socket("localhost",8888);
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())),true); 
		} catch (Exception e) {
			System.out.println(e);
		}
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			while(true) {
			//mensaje por consola
			
			String cad=sc.readLine();
						
			//enviando a servidor
			salida.println(cad);
			if(cad.equals("Salir") || cad.equals("4")) break;
						
			//recibiendo
			cad = entrada.readLine();
			System.out.println("La respuesta del servidor es: "+cad);
						
			}
			/*String cad = sc.readLine();
			salida.println(cad);
			cad=entrada.readLine();
			System.out.println("La respuesta del servidor es: "+cad);
			*/
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		salida.close();
		entrada.close();
		sc.close();
		socketCliente.close();
		
		
	}
}
