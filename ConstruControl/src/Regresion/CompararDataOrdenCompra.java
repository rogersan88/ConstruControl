package Regresion;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ConectarBD.ConectarSQL;
import MetodosGetSet.DatosUsuario;
import MetodosGetSet.OrdenCompra;
import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;






public class CompararDataOrdenCompra {
  
	
	static WebDriver driver;
	
	
	private ScreenRecorder screenRecorder;
	Connection conectar;
	Statement st;
	ResultSet rs;
	Date fechaActual = new Date();
	//DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    public 	DateFormat formatoFecha = new SimpleDateFormat("EEEE dd MMMM  yyyy");
 	public  static String  texto,valor,N,M,BotonEstado;
    public static 	String cuentaerror,cuentaestado,Caso,dato,mainWinID=null,newAdwinID=null,Dato,Ventana[];
	public static  WebElement element;
	public static java.util.List<WebElement> contar;
	Select seleccionar;
	List radio;
	boolean estado;
	Actions act;
	public static int i=0,u=0,dinero,Clic=0;
	public static double f,f1;
	public static  int c=0, Siguiente=0,Con=0;
    public static Set<String> myWindowHandle;
    public static Iterator<String> itererator ;
	
	@Parameters ({"Navegador","Grabacion","Ubicacion"})
	@BeforeTest
	  public void Setup(String Navegador,String Grabacion,String Ubicacion) throws Exception {
		
		ConectarSQL conectar = new ConectarSQL();
		conectar.ConectarBDSQL(Ubicacion);
		
				
		switch(Navegador){
		
		
		case ("Chrome"):
		
			System.setProperty("webdriver.chrome.driver","C:\\SeleniumDrivers\\chromedriver.exe");
		    driver = new ChromeDriver();
		    break;
		    
		case ("IE"):
			
			System.setProperty("webdriver.ie.driver","C:\\SeleniumDrivers\\IEDriverServer.exe");
		    driver = new InternetExplorerDriver();
		    break;    
		
		default:
			break;
		
		}
		
		
		//configuracion grabacion video

		GraphicsConfiguration gc = GraphicsEnvironment//
		.getLocalGraphicsEnvironment()//
		.getDefaultScreenDevice()//
		.getDefaultConfiguration();
		File file = new File("c:\\imagenes");
		screenRecorder = new ScreenRecorder(gc, null, new Format(MediaTypeKey,MediaType.FILE, MimeTypeKey, MIME_AVI),
		new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey,
				ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, (int) 24,FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f,
		KeyFrameIntervalKey, (int) (15 * 60)),
		new Format(MediaTypeKey,MediaType.VIDEO, EncodingKey, "black", FrameRateKey,Rational.valueOf(30)),
		null,file);
		
		
		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
		driver.manage().deleteAllCookies();
	
		
		if (Grabacion.equals("OK")){
			
			screenRecorder.start();
			
			
		}
			
		
				}
		
	
	
	
	@Parameters ({"Ambiente"})
	@Test
	public void Autenticacion(String Ambiente) throws Exception {
	
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		ArrayList <DatosUsuario> login= ConectarSQL.ConsultarDatosAutenticacion(Ambiente,"2");
		DatosUsuario Du =login.get(u);
		
		ArrayList <OrdenCompra> DC= ConectarSQL.DatosClientes();
		OrdenCompra Datosc =DC.get(u);
		
		driver.get(Du.getUrl());
		driver.findElement(By.id("tbUsuario")).clear();
		driver.findElement(By.id("tbUsuario")).sendKeys(Du.getCorreo());
		driver.findElement(By.id("tbContrasenha")).clear();
	    driver.findElement(By.id("tbContrasenha")).sendKeys(Du.getClave());
	    driver.findElement(By.id("btnContinuar")).click();
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("ddlConstructora")));
	  
	   
	    //SELECCIONAMOS EMPRESA
	  	
	    
	    seleccionar= new Select(driver.findElement(By.name("ddlConstructora"))); 
	    seleccionar.selectByVisibleText(Datosc.getConstructora());
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.elementToBeClickable(By.name("ddObra")));
	    seleccionar= new Select(driver.findElement(By.name("ddObra"))); 
	    seleccionar.selectByVisibleText(Datosc.getObra());
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.elementToBeClickable(By.name("ddCentroCosto")));
	    seleccionar= new Select(driver.findElement(By.name("ddCentroCosto"))); 
	    seleccionar.selectByVisibleText(Datosc.getCentroCosto());
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("btnLogin")));
	    driver.findElement(By.id("btnLogin")).click();
	    Thread.sleep(1000);
	
	    Set<String> myWindowHandle = driver.getWindowHandles();
		Iterator<String> itererator =myWindowHandle.iterator();
		mainWinID = itererator.next();
		
		try{
			
		while(itererator!=null){
			
		//Thread.sleep(10000);
		Thread.sleep(3000);
		//wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		newAdwinID = itererator.next();
		Thread.sleep(1000);
		//Thread.sleep(2000);
	}
		
			
		
		}
		catch(Exception ex){
			
		//	JOptionPane.showConfirmDialog(null,"Error :" +ex);
			
	    }
		Thread.sleep(2000);
		driver.switchTo().window(mainWinID);
	    driver.close();
	    Thread.sleep(1000);
	    
	    
	    if(newAdwinID==null){
	    
	    	newAdwinID=driver.getWindowHandle();
	    	
	    	driver.switchTo().window(newAdwinID);
	    	
	    }else
	    {
	    	
	    	driver.switchTo().window(newAdwinID);
	    }
		//Thread.sleep(2000);
		
		 ConectarSQL.ActualizarSesionCredenciales(Ambiente, "OK","2");
		 wait.until(ExpectedConditions.elementToBeClickable(By.id("rptMenuPrincipal_ctl01_btnMenu")));
		 driver.findElement(By.id("rptMenuPrincipal_ctl01_btnMenu")).click();
		element=driver.findElement(By.id("rptAgrupamiento_ctl01_rptMenuSecundario_ctl00_hlOpcion"));				    
		   
	   
		 
		//DEBE IR SIEMPRE -1 CUANDO SE CAMBIA ES POR CONFIGURAR X CANTIDAD DE DATOS 
		u=-1;    
	}
	
	



	@Parameters ({"RutaImagenes"})
	@Test (dependsOnMethods={"Autenticacion"},invocationCount=39)
	public void OrdenCompra(String Ruta) throws Exception{
		
		
		u++;
		
		WebDriverWait wait = new WebDriverWait(driver, 30);		
		ArrayList <OrdenCompra> DC= ConectarSQL.ConsultarOrdenCompra();
		OrdenCompra Datosc =DC.get(u);
		
		
	   
		//Thread.sleep(1000);
		
		//CAPTURA LA INFORMACON POR OBRA Y CENTRO DE COSTO 
		
	
		// c=Datosc.getOrden();
		 
		//c++;
		
		 
		 Thread.sleep(1000);
		 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("rptAgrupamiento_ctl01_rptMenuSecundario_ctl01_hlOpcion")));
		 driver.findElement(By.id("rptAgrupamiento_ctl01_rptMenuSecundario_ctl01_hlOpcion")).click();
		// driver.findElement(By.id("rptAgrupamiento_ctl00_rptMenuSecundario_ctl01_hlOpcion")).click();
		 driver.switchTo().frame("frmPrincipal");
		 Thread.sleep(500);
		 driver.switchTo().frame("iframeList");
		 
		 
		 
		 
		 
		 
		 wait.until(ExpectedConditions.elementToBeClickable(By.name("Ids")));
		 seleccionar= new Select(driver.findElement(By.name("Ids"))); 
		 seleccionar.selectByVisibleText("Todos");
		 Thread.sleep(500);
		 wait.until(ExpectedConditions.elementToBeClickable(By.name("miyazaki_length")));
		 seleccionar= new Select(driver.findElement(By.name("miyazaki_length"))); 
		 seleccionar.selectByVisibleText("100");
		 Thread.sleep(500);
		
		 /*
		 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='miyazaki']/thead/tr/th[6]")));
		 driver.findElement(By.xpath("//*[@id='miyazaki']/thead/tr/th[6]")).click();
		 Thread.sleep(500);
		 */
		 wait.until(ExpectedConditions.elementToBeClickable(By.name("miyazaki_input")));
		 driver.findElement(By.name("miyazaki_input")).clear();
		 driver.findElement(By.name("miyazaki_input")).sendKeys(Datosc.getCentroCosto());
        
         if(c>100){
       
			 
			// c=1;
			// Siguiente=Siguiente+1;
		    Siguiente=(c/100)+1;
		    c=c-((Siguiente-1)*100);
		    if(c==0){
		    	
		    	c=100;
		    }
			 
			 
         }
		 
		 if(Siguiente>=1)
		 {
			 
			 
			 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='miyazaki_paginate']/span/a["+Siguiente+"]")));
             driver.findElement(By.xpath("//*[@id='miyazaki_paginate']/span/a["+Siguiente+"]")).click();			 
			 Thread.sleep(500);
        
		 }
		 Thread.sleep(500);
		 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='miyazaki']/tbody/tr["+c+"]/td[2]/a")));	 
		 element=driver.findElement(By.xpath("//*[@id='miyazaki']/tbody/tr["+c+"]/td[2]/a"));
		 texto=element.getAttribute("class");
		 BotonEstado=texto;
		 
		// Thread.sleep(1000);	
		 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='miyazaki']/tbody/tr["+c+"]/td[7]")));	 
		 driver.findElement(By.xpath("//*[@id='miyazaki']/tbody/tr["+c+"]/td[7]")).click();
		// Thread.sleep(2000);
		
		 if(isAlertPresent()==true) 
		    {
			   
		        Alert alert=driver.switchTo().alert();
		        alert.accept(); 
		        takeScreenShotTest(driver,"Error "+i,Ruta,null);
		        
		     
		   }    
		 
		
	
		 Thread.sleep(500);
		 
		 //ESTADO
		 
	
		if(Datosc.getEstado().equals("Guardado Definitivamente")){
		
		
			
			if(BotonEstado.equals("ico_tres"))
			{
			 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='total']/div[5]/label[1]")));
			 element=driver.findElement(By.xpath("//*[@id='total']/div[5]/label[1]"));
		     texto=element.getText();
			 Assert.assertEquals("Estado definitivo", texto);
			}
			if(BotonEstado.equals("ico_check")) {
				
				 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='total']/div[5]/label[4]")));
				 element=driver.findElement(By.xpath("//*[@id='total']/div[5]/label[4]"));
			     texto=element.getText();
			     Assert.assertEquals("Estado finalizado", texto);
				
				
			}
			 
		}
			 
			 
			
		if(Datosc.getEstado().equals("Anulado")){   
		
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='total']/div[5]/label[3]")));
			 element=driver.findElement(By.xpath("//*[@id='total']/div[5]/label[3]"));
		     texto=element.getText();
			 Assert.assertEquals("Estado anulado", texto);
		}   
		if(Datosc.getEstado().equals("Finalizado")){ 
		
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='total']/div[5]/label[4]")));
			 element=driver.findElement(By.xpath("//*[@id='total']/div[5]/label[4]"));
		     texto=element.getText();
			 Assert.assertEquals("Estado finalizado", texto);
		}    
		
		if(Datosc.getEstado().equals("Guardado Parcialmente")){
			
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='total']/div[5]/label[2]")));
			 element=driver.findElement(By.xpath("//*[@id='total']/div[5]/label[2]"));
			 texto=element.getText();
			 Assert.assertEquals(Datosc.getFecha(), texto);
			 
		     
		}	 
			 
	    Thread.sleep(4000);
		
		 		 
		 //FECHA
		
		 element=driver.findElement(By.xpath("//*[@id='total']/div[4]/div[2]/input"));
		 texto=element.getAttribute("value");
		 Assert.assertEquals(Datosc.getFecha(), texto);
		 
		 
		//CONCEPTO
		 element=driver.findElement(By.id("TxaConcepto"));
		 texto=element.getText();
		 Assert.assertEquals(Datosc.getConcepto(), texto);
		 
		 
		//PROVEEDOR
		 
		 element=driver.findElement(By.id("sel_tercero"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Assert.assertEquals(Datosc.getProveedor(), texto);
		 
		//CONTACTO
		 element=driver.findElement(By.id("contacto"));
		 texto=element.getText();
		 Assert.assertEquals(Datosc.getContacto(), texto);
		 
		 //ANTICIPO		 
		 element=driver.findElement(By.xpath("//*[@id='txt_anticipo']"));
		 texto=element.getAttribute("value");
		 Assert.assertEquals(Datosc.getAnticipo(), texto);
		 
		 
		//MINUTA
		 element=driver.findElement(By.id("sel_Minuta"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 
		 if (Datosc.getMinuta().equals("SIN MINUTA")){
			 
			 Assert.assertEquals("Seleccione uno", texto);
			 
		 }else {
			 
			 Assert.assertEquals(Datosc.getMinuta(), texto);
		 }
		 
		 
		// %RET IVA
	    element=driver.findElement(By.id("sel_retIva"));
		seleccionar=new Select(element);
		texto=seleccionar.getFirstSelectedOption().getText();
		if(Datosc.getRetIva().equals("0.00")){
			                    
			Assert.assertEquals("NINGUNA (0,0%)", texto.toUpperCase());
			
		}
		else{
		Assert.assertEquals(Datosc.getRetIva(), texto);
		}
		 
		//FECHA DE ENTREGA
		 element=driver.findElement(By.id("TxtFechaEntrega"));
		 texto=element.getAttribute("value");
		 Assert.assertEquals(Datosc.getFechaEntrega(), texto);
		
		
		//RET IND COM
		 element=driver.findElement(By.id("sel_retIndCom"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 if(Datosc.getRetIndCom().equals("(NINGUNA) (0.000%) ")){
			 
			 Assert.assertEquals("(NINGUNA) (0,0%)", texto);
			 
		 }else{
			 Assert.assertEquals(Datosc.getRetIndCom(), texto);
			 
		 }
		 
		 
		 
		 
		//RET FUENTE
		 element=driver.findElement(By.id("sel_retFuente"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
         if(Datosc.getRetFuente().equals("(NINGUNA) (0.0%) ")){
			 
			 Assert.assertEquals("(NINGUNA) (0,0%)", texto);
			 
		 }else{
			 Assert.assertEquals(Datosc.getRetFuente().replace(".",","),texto.replace(")", ") "));
			 
		 }
		 
		 
		 
		
		//FORMA DE PAGO
		 element=driver.findElement(By.id("sel_pago"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Assert.assertEquals(Datosc.getFormaPago(), texto);
		 
		 
		 		 
		 // DETALLE DE LA ORDEN DE COMPRA , LOS RESULTADOS INICIAN EN LA POSICION 32
		 
		 ArrayList <OrdenCompra> DCICan= ConectarSQL.CantidadItems(Datosc.getRegistro(),Datosc.getCentroCosto());
		 OrdenCompra Datoscican =DCICan.get(0);
		 
		 int l= Datoscican.getRegistro();
		 int k;
		 
		 i=-1;
		 for(k=1;k<=l;k++){
		 
			 i++;
			 ArrayList <OrdenCompra> DCI= ConectarSQL.ConsultarOrdenCompraItems(Datosc.getRegistro(),Datosc.getCentroCosto());
			 OrdenCompra Datosci =DCI.get(i);
			 
				 
		 //NOMBRE
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[1]"));
		 texto=element.getText();
		 Assert.assertEquals(Datosci.getNombre().replaceAll("[º°]", ""), texto.replaceAll("[º°]", ""));
		 
		 // UM
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[2]"));
		 texto=element.getText();
		
		
		 switch (Datosci.getUm()){
		 
		 case ("un"):
			 
			 texto=Datosci.getUm();
		     break;
         case ("ton"):
			 
			 texto=Datosci.getUm();
		     break;    
		     
		 case ("gl"):
			 texto=texto.replace("n","");
			 break;
		
		 case ("und"):
			 texto=Datosci.getUm();
			 break;	 
			 
		 case ("rl"):
			 texto=Datosci.getUm();
			 break;	 	 
			 
		default:
			    break;
			    
		 }
		 
		 Assert.assertEquals(Datosci.getUm(), texto);
		 
		 	 
		 // CANTIDAD
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[4]"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosci.getCantidad().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",",""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		
		 		 
		 // VR UNITARIO
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[5]/input"));
		 texto=element.getAttribute("value");
		 f=Double.parseDouble(Datosci.getVrUnitario().replaceAll(",.",""));
		 f1=Double.parseDouble(texto.replaceAll(",.", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 
		 // %DESCUENTO
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[7]/input"));
		 texto=element.getAttribute("value");
		 f=Double.parseDouble(Datosci.getPDescuento().replaceAll(",.",""));
		 f1=Double.parseDouble(texto.replaceAll(",.", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		
		 
		 		 
		 // VR PARCIAL
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[8]"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosci.getVrParcial().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		
		 
		 
		 //IVA
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[9]/select"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		
		 
		 
		 if(Datosci.getCheckIva().equals("true")){
		 Assert.assertEquals(Datosc.getIva(), texto);
		 }else{
			 Assert.assertEquals("(NINGUNA) (0,0%)", texto); 
			 
		 }
		 
		 
		 
		 /*
		 switch (Datosc.getIva()){
		 
		 case ("Otro"):
			 
			 Assert.assertEquals(Datosc.getIva().replaceAll("-",""), texto.replaceAll("()- ", ""));
		     break;
		     
		 case ("IVA "):
			 Assert.assertEquals("IVA 19% (19,0%)", texto);
			 break;
		
		 
			 
		default:
			
			 if(Datosc.getIva().startsWith("(NINGUNA)")){
				 Assert.assertEquals("(NINGUNA) (0,0%)", texto);
					 }else{
						 
						 Assert.assertEquals(Datosc.getIva(), texto); 
						 
						 
					 }
			    break;
			    
		 }*/
		 
		
		 
		 
		
		 
     	 // VR FLETES
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[10]/input"));
		 texto=element.getAttribute("value");
		 f=Double.parseDouble(Datosci.getVrFletes().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 
		 // VR NETO
		 element=driver.findElement( By.xpath("//*[@id='tbCompras']/tbody/tr["+k+"]/td[11]"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosci.getVrNeto().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 		 	 
		 }
		 
		 
		 		 
		 // DETALLE TOTALES
		 
				 
		 //SUBTOTAL
		 element=driver.findElement(By.id("Lbl_subTotal"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosc.getSubtotal().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",",""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 //DESCUENTO
		 element=driver.findElement(By.id("Lbl_descT"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosc.getLBLDescuento().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",",""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 //RET FUENTE
		 element=driver.findElement(By.id("Lbl_retFuenteT"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosc.getLBLRetFuente().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",",""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 
		 
		 //RET IND COM 
		 element=driver.findElement(By.id("Lbl_sretIndComT"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosc.getLBLRetIndCom().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		
		 
		 //IVA
		 element=driver.findElement(By.id("Lbl_IvaT"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosc.getLBLIva().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 
		 
	     // RET IVA
		 element=driver.findElement(By.id("Lbl_retIvaT"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosc.getRetIva().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		
		 
		 	 
		 // FLETES
		 element=driver.findElement(By.id("Lbl_FletesT"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosc.getLBLFletes().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",", ""));
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 //TOTAL
		 element=driver.findElement(By.id("Lbl_Total"));
		 texto=element.getText();
		 f=Double.parseDouble(Datosc.getLBLTotal().replaceAll(",",""));
		 f1=Double.parseDouble(texto.replaceAll(",", ""));
		
		 Assert.assertEquals(String.format("%.0f",f), String.format("%.0f",f1));
		 
		 
		 
		  driver.switchTo().defaultContent();
		  driver.switchTo().defaultContent();
		//}
		//}
	}
	
	

	@Parameters ({"Ambiente"})
	@Test (dependsOnMethods={"OrdenCompra"})
	public void CerraSesion(String Ambiente) throws Exception {
	
		
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		ArrayList <DatosUsuario> login= ConectarSQL.ConsultarDatosAutenticacion(Ambiente,"1");
		DatosUsuario Du =login.get(0);
		
		
		
		
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnlogOut")));
		driver.findElement(By.id("btnlogOut")).click();
		//Thread.sleep(1000);
		
		
	    
		boolean resultado=driver.getCurrentUrl().endsWith("Login.aspx");
		while(resultado==false){
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnlogOut")));	
		driver.findElement(By.id("btnlogOut")).click();
		//Thread.sleep(1000);
		Clic++;
		
		resultado=driver.getCurrentUrl().endsWith("Login.aspx");
		if (resultado==true){
			
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='form1']/div[3]/div[1]/div[1]/div/div[1]/div[1]")));
			element = driver.findElement(By.xpath("//*[@id='form1']/div[3]/div[1]/div[1]/div/div[1]/div[1]"));
			texto=element.getText();
			Assert.assertEquals("Ingresar al sistema", texto);
			if(Clic==1){
			    ConectarSQL.ActualizarSesionCredenciales(Du.getCorreo(), "Finalizado","2");
			}
			else {
				Assert.assertEquals(1, Clic);
				ConectarSQL.ActualizarSesionCredenciales(Du.getCorreo(), "Finalizado","2");	
			}
					}
		
		
	     }
		
		
		
			
			
	  }
	
	



	 public static void takeScreenShotTest(WebDriver driver, String imageName,String Ruta,String Error) throws Exception{
		    //Directorio donde quedaran las imagenes guardadas
		
		 
		// a=0;
	    //	 b=1;
		 
		// element=driver.findElement(By.xpath("//*[@id='total']/div[9]/label[3]"));
		
		// if(Error =="1" || Error =="2") {
			 
			 if( Error ==null) {
			 
			  File directory = new File(Ruta);

			    try {
			       if (directory.isDirectory()) {
			          //Toma la captura de imagen
			          File imagen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			          //Mueve el archivo a la carga especificada con el respectivo nombre
			          FileUtils.copyFile(imagen, new File(directory.getAbsolutePath()   + "\\" + imageName + ".png"));
			       } else {
			          //Se lanza la excepcion cuando no encuentre el directorio
			          throw new IOException("ERROR : La ruta especificada no es un directorio!");
			       }
			    } catch (IOException e) {
			       //Impresion de Excepciones
			       e.printStackTrace();
			    }
			 
			 			 		 }
		 else {
		 
	
		 
		 
		 
		 try {
		    	// 1. Make screenshot of all screen
		    	
			 driver.switchTo().window(newAdwinID);
			 driver.switchTo().frame("frmPrincipal");
			 Thread.sleep(500);
			 driver.switchTo().frame("iframeList");
		    	
			 File directory = new File(Ruta);
		    		 
		    	   Actions actions = new Actions(driver);
		    	   actions.moveToElement(element);
		    	   actions.build().perform();
		    	   
		    	   Thread.sleep(1000);
		    	   
		    	   int x =element.getLocation().getX();
   		     

   		     int y = element.getLocation().getY()-130;

   		     int width = element.getSize().getWidth();

   		     int height = element.getSize().getHeight();
   		     
		    	
		    	
   		      	
		    	 File scrFile = ((TakesScreenshot) driver)
		    		       .getScreenshotAs(OutputType.FILE);

		    		     BufferedImage image = ImageIO.read(scrFile);

		    		     Graphics2D graphics = image.createGraphics();

		    		    

		    		     graphics.setFont(graphics.getFont().deriveFont(30f));
		    		     //The colour for the rectangle which is to be drawn around the element
		    		     graphics.setColor(Color.RED);

		    		     //Thickness of each side of the rectangle
		    		    graphics.setStroke(new BasicStroke(1.0f));

		    		       
		    		     
		    		     
		    		     //To draw the rectangle around the element
		    		    graphics.drawRect(x, y, width, height);
		    		    
		    		     graphics.drawString(Error,  300,y);

		    		     
		    		     //Path to save the screenshot
		    		     ImageIO.write(image, "png", new File(directory.getAbsolutePath()   + "\\" + imageName + ".png"));
		    			
		    		     
		    		     
		    		     
		    } catch (IOException e) {
		       //Impresion de Excepciones
		       e.printStackTrace();
		       
		     
		    }
		    
		 }
		 }
		 
		 
	 
		   
	 @Parameters ({"RutaImagenes"})
   @AfterMethod
  public void CapturaImgen(ITestResult result ,ITestContext context,String Ruta) throws Exception {
		
		 			
		   
			
		 if (result.getStatus() == ITestResult.FAILURE) {
			 
			 driver.switchTo().defaultContent();
			  driver.switchTo().defaultContent();
			 
			 ArrayList <OrdenCompra> DC= ConectarSQL.ConsultarOrdenCompra();
			 OrdenCompra Datosc =DC.get(u);	
			 
			 boolean resultado=result.getThrowable().getLocalizedMessage().startsWith("expected");
			 
			 if(resultado == true){
				 
			     takeScreenShotTest(driver, "Se Genero Un Error Con La Empresa "+Datosc.getConstructora()+" Obra "+Datosc.getObra()+" Centro Costo "+Datosc.getCentroCosto()+" Orden "+Datosc.getOrden() ,Ruta,result.getThrowable().getLocalizedMessage());
				 
				 
				 driver.switchTo().defaultContent();
				 Thread.sleep(500);
				 
				 
			 }else{
			 
			 			  
			takeScreenShotTest(driver, "Se Genero Un Error Con La Empresa "+Datosc.getConstructora()+" Obra "+Datosc.getObra()+" Centro Costo "+Datosc.getCentroCosto() ,Ruta,null);
				 
				 driver.switchTo().defaultContent();
			 Thread.sleep(500);
			 }
			 
						  
			 	   }
 

	 }
	
	 
	 
	 
	@Parameters ({"Grabacion"})
	@AfterTest
	  public void tearDown(String Grabacion) throws Exception {
			
		 newAdwinID=null;
		 mainWinID=null;
		  if(Clic <=1){
			 
		 //  driver.quit();
		  }
		   if(Grabacion.equals("OK")){
			screenRecorder.stop();
		   }
	
  
	  }
	public boolean isAlertPresent() {
		 boolean foundAlert = false;
	      WebDriverWait wait = new WebDriverWait(driver, 1 /*timeout in seconds*/);
	  try{
	      wait.until(ExpectedConditions.alertIsPresent());
	      foundAlert = true;
	  }catch(TimeoutException eTO){
		  foundAlert = false;
	   }
	  return foundAlert;
	}
	
	
	
	

}
	

	


	
	
	
	
	
	
	

