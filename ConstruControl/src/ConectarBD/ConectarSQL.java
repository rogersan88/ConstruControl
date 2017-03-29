package ConectarBD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

import MetodosGetSet.DatosUsuario;
import MetodosGetSet.OrdenCompra;

import java.util.ArrayList;
import java.util.Date;


public class ConectarSQL {

	
	static Connection conectar;
	static Statement st;
	static int n=100;
 
    static 	ResultSet rs;
    static 	CallableStatement cs;
	
    Date fechaActual = new Date();
	public 	DateFormat formatoFecha = new SimpleDateFormat("EEEE dd MMMM  yyyy");
    public static  int u=0;
	
	
	
	
	
	public void ConectarBDSQL (String Ubicacion){
		
	
		
		
		try{
			  
			
			switch(Ubicacion){
			
			case ("LegisWifi"):
				
				 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			     conectar= DriverManager.getConnection("jdbc:sqlserver://10.1.50.77:1433;databaseName=ConstruControl","sa","legis2016*");
			     st=conectar.createStatement();
			    break;
			
			    
			case ("LegisLan"):
				
				 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			     conectar= DriverManager.getConnection("jdbc:sqlserver://10.1.16.152\\Rogers;databaseName=ConstruControl","sa","legis2016*");
			     st=conectar.createStatement();
			    break;    
			    
			case ("Casa"):
				
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			    conectar= DriverManager.getConnection("jdbc:sqlserver://Localhost;databaseName=ConstruControl","sa","legis2016*");
			    st=conectar.createStatement();
		        break;
				
			default:
				break;			
			}
			
			  			    		    
		}
			  
			
		catch(Exception ex){
		  
		  
		  JOptionPane.showConfirmDialog(null,"Error en la conexion" + ex);

	}
		
	}
		
	public static ArrayList<DatosUsuario> ConsultarDatosAutenticacion(String Ambiente,String Version){
		
		
		ArrayList <DatosUsuario> login= new ArrayList<DatosUsuario>();
       
        
            
		try{	
	
					
				 cs = conectar.prepareCall("{call Credenciales(?,?)}"); 
				 cs.setString(1, Ambiente);
				 cs.setString(2, Version);
				
				 rs=cs.executeQuery();
		         
		         while(rs.next()){
		        	  
		
			
		      DatosUsuario logindatos= new DatosUsuario();
              logindatos.setCorreo(rs.getString("Usuario"));
              logindatos.setClave(rs.getString("Clave"));
              logindatos.setEstado(rs.getString("Estado"));
              logindatos.setUrl(rs.getString("Url"));
              login.add(logindatos);
              u++;
                         
		 }
                
		      
		     	
	
			}
	catch(Exception ex){
		 JOptionPane.showConfirmDialog(null,"consultar datos autenticacion" + ex);
	}
		return login;
	}
	
	
	public static void ActualizarSesionCredenciales(String Ambiente, String Error, String Version ){
		
		try{	
			
			
			 cs = conectar.prepareCall("{call ActualizarSesionCredenciales(?,?,?)}"); 
			 cs.setString(1, Ambiente);
			 cs.setString(2, Error );
			 cs.setString(3, Version );
			 cs.execute();
		
			 
		
				}
		catch(Exception ex){
			 JOptionPane.showConfirmDialog(null,"Error actualizar datos" + ex);
		}
		
		
		
		
	} 
	
	public static void InsertarOrdemCompraItems( 
			 String Codigo,
			 String Nombre,
			 String Um,
			 String Cantidad,
			 String VrUnitario,
			 String VrParcial,
			 String PDescuento,
			 String VrDescuento,
			 String CheckIva,
			 String VrIva,
			 String VrFletes,
			 String VrNeto,
			 String Numero,
			 String Constructora,
			 String Obra,
			 String CentroCosto,
			 int Registro,
			 int Posicion){
	try{
		cs = conectar.prepareCall("{call InsertarOrdenCompraItems(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1, Codigo);
		cs.setString(2, Nombre);
		cs.setString(3, Um);
		cs.setString(4, Cantidad);
		cs.setString(5, VrUnitario);
		cs.setString(6, VrParcial);
		cs.setString(7, PDescuento);
		cs.setString(8, VrDescuento);
		cs.setString(9, CheckIva);
		cs.setString(10, VrIva);
		cs.setString(11, VrFletes);
		cs.setString(12, VrNeto);
		cs.setString(13, Numero);
		cs.setString(14, Constructora);
		cs.setString(15, Obra);
		cs.setString(16, CentroCosto);
		cs.setInt(17,   Registro);
		cs.setInt(18,   Posicion);
		cs.execute();
		
	}
	
	
	catch(Exception ex){
		 JOptionPane.showConfirmDialog(null,"InsertarOrdemCompraItems" + ex);
	}
	
}
	
    public static void InsertarOrdenCompra( 
     String Numero,
	 String Fecha,
	 String Almacen,
	 String FormaPago,
	 String FechaEntrega,
	 String RetFuente,
	 String Iva,
	 String RetIva,
	 String Concepto,
	 String Proveedor,
	 String Contacto,
	 String Minuta,
	 String Anticipo,
	 String RetIndCom,
	 String RetCree,
	 String VrAnticipo,
	 String VrFletes2,
	 String Subtotal,
	 String LBLDescuento,
	 String LBLRetFuente,
	 String LBLRetIndCom,
	 String LBLIva,
	 String LBLRetCree,
	 String LBLFletes,
	 String LBLTotal,
	 String Estado,
	 String Constructora,
	 String Obra,
	 String CentroCosto,
	 int Registro
	){
		
		try{	
	
					
				    cs = conectar.prepareCall("{call InsertarOrdenCompra(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"); 
				 
				    cs.setString(1, Numero);
				    cs.setString(2, Fecha);
					cs.setString(3, Almacen);
					cs.setString(4, FormaPago);
					cs.setString(5, FechaEntrega);
					cs.setString(6, RetFuente);
					cs.setString(7, Iva);
					cs.setString(8, RetIva);
					cs.setString(9, Concepto);
					cs.setString(10, Proveedor);
					cs.setString(11, Contacto);
					cs.setString(12, Minuta);
					cs.setString(13, Anticipo);
					cs.setString(14, RetIndCom);
					cs.setString(15, RetCree);
					cs.setString(16, VrAnticipo);
					cs.setString(17, VrFletes2);
					cs.setString(18, Subtotal);
					cs.setString(19, LBLDescuento);
					cs.setString(20, LBLRetFuente);
					cs.setString(21, LBLRetIndCom);
					cs.setString(22, LBLIva);
					cs.setString(23, LBLRetCree);
					cs.setString(24, LBLFletes);
					cs.setString(25, LBLTotal);
					cs.setString(26, Estado);
					cs.setString(27, Constructora);
					cs.setString(28, Obra);
					cs.setString(29, CentroCosto);
					cs.setInt(30, Registro);
				    cs.execute();
		         
		       
                
		      
		     	
	
			}
	catch(Exception ex){
		 JOptionPane.showConfirmDialog(null,"InsertarOrdenCompra" + ex);
	}
		
	}
	
    public static ArrayList<OrdenCompra> ConsultarOrdenCompra (){
    	
    	ArrayList <OrdenCompra> DC= new ArrayList<OrdenCompra>();
        
        
        
		try{	
	
					
				 cs = conectar.prepareCall("{call DataOrdenCompra()}"); 
				
				 rs=cs.executeQuery();
		         
		         while(rs.next()){
		        	  
		
			
		        	 OrdenCompra DatosC= new OrdenCompra();
		        	 
		        	 					  
					    DatosC.setNumero(rs.getString(1));
					    DatosC.setFecha(rs.getString(2));
						DatosC.setAlmacen(rs.getString(3));
						DatosC.setFormaPago(rs.getString(4));
						DatosC.setFechaEntrega(rs.getString(5));
						DatosC.setRetFuente(rs.getString(6));
						DatosC.setIva(rs.getString(7));
						DatosC.setRetIva(rs.getString(8));
						DatosC.setConcepto(rs.getString(9));
						DatosC.setProveedor(rs.getString(10));
						DatosC.setContacto(rs.getString(11));
						DatosC.setMinuta(rs.getString(12));
						DatosC.setAnticipo(rs.getString(13));
						DatosC.setRetIndCom(rs.getString(14));
						DatosC.setRetCree(rs.getString(15));
						DatosC.setVrAnticipo(rs.getString(16));
						DatosC.setVrFletes2(rs.getString(17));
						DatosC.setSubtotal(rs.getString(18));
						DatosC.setLBLDescuento(rs.getString(19));
						DatosC.setLBLRetFuente(rs.getString(20));
						DatosC.setLBLRetIndCom(rs.getString(21));
						DatosC.setLBLIva(rs.getString(22));
						DatosC.setLBLRetCree(rs.getString(23));
						DatosC.setLBLFletes(rs.getString(24));
						DatosC.setLBLTotal(rs.getString(25));
						DatosC.setEstado(rs.getString(26));
						DatosC.setConstructora(rs.getString(27));
						DatosC.setObra(rs.getString(28));
						DatosC.setCentroCosto(rs.getString(29));
						DatosC.setRegistro(rs.getInt(30));
						DatosC.setOrden(rs.getInt(31));
					    DC.add(DatosC);
              u++;
                         
		 }
                
		      
		     	
	
			}
	catch(Exception ex){
		 JOptionPane.showConfirmDialog(null,"ConsultarOrdenCompra" + ex);
	}
		return DC;
    	
    	
    }
    
    
     public static ArrayList<OrdenCompra> ConsultarOrdenCompraItems (int Registro,String CentroCosto){
    	
    	ArrayList <OrdenCompra> DC= new ArrayList<OrdenCompra>();
        
        
        
		try{	
	
					
				 cs = conectar.prepareCall("{call DataOrdenCompraItems(?,?)}"); 
				 cs.setInt(1, Registro);
				 cs.setString(2, CentroCosto);
				 rs=cs.executeQuery();
		         
		         while(rs.next()){
		        	  
		
			
		        	 OrdenCompra DatosC= new OrdenCompra();
		        	 
		        	 					
						DatosC.setCodigo(rs.getString(1));
						DatosC.setNombre(rs.getString(2));
						DatosC.setUm(rs.getString(3));
						DatosC.setCantidad(rs.getString(4));
						DatosC.setVrUnitario(rs.getString(5));
						DatosC.setVrParcial(rs.getString(6));
						DatosC.setPDescuento(rs.getString(7));
						DatosC.setVrDescuento(rs.getString(8));
						DatosC.setCheckIva(rs.getString(9));
						DatosC.setVrIva(rs.getString(10));
						DatosC.setVrFletes(rs.getString(11));
						DatosC.setVrNeto(rs.getString(12));
						DatosC.setNumero(rs.getString(13));
						DatosC.setConstructora(rs.getString(14));
						DatosC.setObra(rs.getString(15));
						DatosC.setCentroCosto(rs.getString(16));
						DatosC.setRegistro(rs.getInt(17));
						
						
					    DC.add(DatosC);
              u++;
                         
		 }
                
		      
		     	
	
			}
	catch(Exception ex){
		 JOptionPane.showConfirmDialog(null,"ConsultarOrdenCompraItems" + ex);
	}
		return DC;
    	
    	
    }
    
    public static ArrayList<OrdenCompra> DatosClientes (){
    	
    	ArrayList <OrdenCompra> DC= new ArrayList<OrdenCompra>();
        
        
        
		try{	
	
					
				 cs = conectar.prepareCall("{call DatosCliente()}"); 
				
				 rs=cs.executeQuery();
		         
		         while(rs.next()){
		        	  
		
			
		        	 OrdenCompra DatosC= new OrdenCompra();
		        	 DatosC.setConstructora(rs.getString(1));
		        	 DatosC.setObra(rs.getString(2));
		        	 DatosC.setCentroCosto(rs.getString(3));
		        	 DatosC.setTotalOrdenCompras(rs.getInt(4));
		        	 DatosC.setTotalOrdenComprasParciales(rs.getInt(5));
		        	 DC.add(DatosC);
              u++;
                         
		 }
                
		      
		     	
	
			}
	catch(Exception ex){
		 JOptionPane.showConfirmDialog(null,"DatosClientes" + ex);
	}
		return DC;
    	
    	
    }
    
 public static ArrayList<OrdenCompra> CantidadItems (int Registro,String CentroCosto){
    	
    	ArrayList <OrdenCompra> DC= new ArrayList<OrdenCompra>();
        
        
        
		try{	
	
					
				 cs = conectar.prepareCall("{call DataCantidadItemsOrdenCompra(?,?)}"); 
				 cs.setInt(1, Registro);
				 cs.setString(2, CentroCosto);
				 rs=cs.executeQuery();
		         
		         while(rs.next()){
		        	  
		
			
		        	 OrdenCompra DatosC= new OrdenCompra();
		        	 DatosC.setRegistro(rs.getInt(1));
		        	 DC.add(DatosC);
              u++;
                         
		 }
                
		      
		     	
	
			}
	catch(Exception ex){
		 JOptionPane.showConfirmDialog(null,"DatosClientes" + ex);
	}
		return DC;
    	
    	
    }
    
   
    
    
    
    
    
    
    
    
    
    
    
}
