import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
	public static boolean letraA(String a) {
		boolean sw=false;	
		for (int i = 0; i < a.length(); i++) {
				if(a.charAt(i)=='a') {
					sw=true;
				}
			}
		return sw;
	}
	public static void main(String[] args) throws IOException {
		
		ServerSocket socketServidor = null;
		Socket socketCliente=null;
		
		BufferedReader entrada = null;
		PrintWriter salida = null;
		
		System.out.println("------Servidor------");
		
		try {
			socketServidor = new ServerSocket(8888);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			while(true) {
				socketCliente =socketServidor.accept();
				entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
				salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())),true); 
				
				while(true) {
					//recibiendo del cliente
					String cad = entrada.readLine();
					//String res="";
					if(cad.equals("Salir") || cad.equals("4")) break;
					
					switch (cad) {
					case "1":
						salida.println("PAPEL");
						break;
					case "2":
						salida.println("PIEDRA");
						break;
					case "3":
						salida.println("TIJERA");
						break;
					
					}
					//enviando 
					System.out.println(cad);
					/*String res = "IMPAR";
					if(cad.length()%2==0) res = "PAR";*/
					
					
					
					
					
					
					
					//String res="";
					/*if(letraA(cad)) {
						res="Tiene la letra A";
					}else {
						res = "No tine la letra A";
					}*/
				//	salida.println(res);
					
				}
				/*
				String cad = entrada.readLine();
				salida.println(cad);*/
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		salida.close();
		entrada.close();
		socketServidor.close();
		socketCliente.close();
	}
}
