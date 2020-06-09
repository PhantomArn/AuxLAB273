import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;

public class ServidorUDP {
	public static int contarPalabras(String s) {
	    int contador = 1, pos;
	    s = s.trim(); //eliminar los posibles espacios en blanco al principio y al final                              
	    if (s.isEmpty()) { //si la cadena está vacía
	        contador = 0;
	    } else {
	            pos = s.indexOf(" "); //se busca el primer espacio en blanco
	            while (pos != -1) {   //mientras que se encuentre un espacio en blanco
	                   contador++;    //se cuenta una palabra
	                   pos = s.indexOf(" ", pos + 1); //se busca el siguiente espacio en blanco                       
	            }                                     //a continuación del actual
	    }
	    return contador;
	}
	public static String recuperar(String cad, int tamaño) {
		String res="";
		for(int i=0; i<tamaño;i++) {
			res +=cad.charAt(i);
		}
		return res;
	}
	
	public static boolean esPrimo(String cad) {
		System.out.println("La cadena es :"+ cad+"-");
		
		int n= Integer.parseInt(cad);
		if(n<2) return false;
		for(int x=2; x*x<=n;x++) {
			if(n%x==0)return false;
			
		}
		return true;
	}
	public static void main(String[] args) {
		
		System.out.println("-----SERVIDOR-----");
		try {
			DatagramSocket socketUDP = new DatagramSocket(8888);
			byte [] buffer = new byte[1000];
			while(true) {
				DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
				socketUDP.receive(peticion);
				
				//DatagramPacket mensaje = new DatagramPacket(peticion.getData(), peticion.getLength(), peticion.getAddress(), peticion.getPort());
				//socketUDP.send(mensaje);
				
				
				
				
				String res =new String(peticion.getData());
				System.out.println(res);
				int tam =0; 
				tam=contarPalabras(res);
				String numCadena= Integer.toString(tam);
				
				
				//String x= recuperar(res, peticion.getLength());
				
				/*String env= "Si Primo";
				if(!esPrimo(x)) {
					env = "No es primo";
				}*/
				
				byte [] enviar=numCadena.getBytes();
				DatagramPacket mensaje = new DatagramPacket(enviar, numCadena.length(), peticion.getAddress(), peticion.getPort());
				socketUDP.send(mensaje);
				
				
				
				
				
				System.out.println("Los datos son: "+new String(peticion.getData()));
				tam=0;
				//System.out.println("Puerto del cliente: "+peticion.getPort());
				//System.out.println("Tamaño enviado: "+peticion.getLength());
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
