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
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.List;
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






public class CapturarDataOrdenCompra {
  
	
	static WebDriver driver;
	
	
	private ScreenRecorder screenRecorder;
	Connection conectar;
	Statement st;
	ResultSet rs;
	Date fechaActual = new Date();
	//DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    public 	DateFormat formatoFecha = new SimpleDateFormat("EEEE dd MMMM  yyyy");
 	public  static String  texto,valor,N,M;
    public  	String cuentaerror,cuentaestado,Caso,dato,mainWinID,newAdwinID;
	public static  WebElement element;
	public static java.util.List<WebElement> contar;
	Select seleccionar;
	List radio;
	boolean estado;
	Actions act;
	public static int i=0,u=0,dinero,Clic=0,Orden=0;
	public static double f;

	
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
		ArrayList <DatosUsuario> login= ConectarSQL.ConsultarDatosAutenticacion(Ambiente,"1");
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
	
	   
		
	    try{
			Set<String> myWindowHandle = driver.getWindowHandles();
			Iterator<String> itererator =myWindowHandle.iterator();
			mainWinID = itererator.next();
		while(itererator!=null){
			
		//Thread.sleep(10000);
		Thread.sleep(1000);
		//wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		newAdwinID = itererator.next();
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
		driver.switchTo().window(newAdwinID);
						    
		    ConectarSQL.ActualizarSesionCredenciales(Ambiente, "OK","1");
	   
		 
		//DEBE IR SIEMPRE -1 CUANDO SE CAMBIA ES POR CONFIGURAR X CANTIDAD DE DATOS 
		u=-1;    
	}
	
	@Parameters ({"RutaImagenes"})
	@Test (dependsOnMethods={"Autenticacion"},invocationCount=5 )
	public void OrdenCompra(String Ruta) throws Exception{
		
		
		 WebDriverWait wait = new WebDriverWait(driver, 30);
		u++;
		ArrayList <OrdenCompra> DC= ConectarSQL.DatosClientes();
		OrdenCompra Datosc =DC.get(u);
		
		//CAMBIAR EMPRESA-OBRA-CENTRO COSTO
		wait.until(ExpectedConditions.elementToBeClickable(By.id("lblUsuario")));
		driver.findElement(By.id("lblUsuario")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ddlConstructora")));
		seleccionar= new Select(driver.findElement(By.name("ddlConstructora"))); 
	    seleccionar.selectByVisibleText(Datosc.getConstructora());
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("ddObras")));
	    seleccionar= new Select(driver.findElement(By.name("ddObras"))); 
	    seleccionar.selectByVisibleText(Datosc.getObra());
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.elementToBeClickable(By.name("ddCentroCosto")));
	    seleccionar= new Select(driver.findElement(By.name("ddCentroCosto"))); 
	    seleccionar.selectByVisibleText(Datosc.getCentroCosto());
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("OkButton")));
	    driver.findElement(By.id("OkButton")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("rptMenuPrincipal_ctl01_btnMenu")));
	    driver.findElement(By.id("rptMenuPrincipal_ctl01_btnMenu")).click();
		Thread.sleep(1000);
		
		//CAPTURA LA INFORMACON POR OBRA Y CENTRO DE COSTO 
		
		int r,ConObras=Datosc.getTotalOrdenCompras();
		int c=0;
		int Siguiente=1;
		i=0;
		
		if(ConObras>=1){
		
		for(r=1;r<=ConObras;r++){
		 Orden++;	
		 i++;
		 c++;
		 int b=9;
		 
		 MetodosGetSet.OrdenCompra Oc= new MetodosGetSet.OrdenCompra(); 
		 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("rptAgrupamiento_ctl01_rptMenuSecundario_ctl01_hlOpcion")));                           
		 driver.findElement(By.id("rptAgrupamiento_ctl01_rptMenuSecundario_ctl01_hlOpcion")).click();
		 //driver.findElement(By.id("rptAgrupamiento_ctl00_rptMenuSecundario_ctl01_hlOpcion")).click();
		 driver.switchTo().frame("frmPrincipal");
		 wait.until(ExpectedConditions.elementToBeClickable(By.name("ddEstadoDocumento"))); 
		 
		 seleccionar= new Select(driver.findElement(By.name("ddEstadoDocumento"))); 
		 seleccionar.selectByVisibleText("Mostrar Todos");
		// Thread.sleep(3000);
		 
         if(c>10){
			 
			 c=1;
			 Siguiente=Siguiente+1;
		 }
		 
		 if(i>10)
		 {
			 
			 if(i>110){
				 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td[11]/a"))); 
				 driver.findElement(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td[11]/a")).click();			 
				 Thread.sleep(500); 
				 
				 
			 }
         if(i>210){
        	 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td[12]/a"))); 
				 driver.findElement(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td[12]/a")).click();			 
				 Thread.sleep(500); 
				 
			 }
			 
         if(i>310){
        	 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td[12]/a"))); 
			 driver.findElement(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td[12]/a")).click();			 
			Thread.sleep(500); 
			 
		 }
         if(i>410){
        	 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td[12]/a"))); 
			 driver.findElement(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td[12]/a")).click();			 
			Thread.sleep(500); 
			 
		 }
		
         if(i<=110)
        
         {
        	 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td["+Siguiente+"]/a"))); 
			 driver.findElement(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td["+Siguiente+"]/a")).click();			 
			 Thread.sleep(500);
         }
		
           else{
         
        	 b=Siguiente-b;
        	 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td["+b+"]/a"))); 
        	 driver.findElement(By.xpath("//*[@id='gvLista']/tbody/tr[12]/td/table/tbody/tr/td["+b+"]/a")).click();			 
			 Thread.sleep(500);
         
         
           }
		 }
		 
		 Thread.sleep(1000);
		
		 if(c<=8){
		 M="0"+String.valueOf(c+1);	 
		 }else{
			
		 M=String.valueOf(c+1);				 
		 }
		 wait.until(ExpectedConditions.elementToBeClickable(By.id("gvLista_ctl"+M+"_ibEditar"))); 
		 
		 driver.findElement(By.id("gvLista_ctl"+M+"_ibEditar")).click();
		 Thread.sleep(3000);
		
		 if(isAlertPresent()==true) 
		    {
			   
		        Alert alert=driver.switchTo().alert();
		        alert.accept(); 
		        takeScreenShotTest(driver,"Error "+i,Ruta);
		        
		     
		   }    
		 
		 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("StateLabel1_lblLabelState")));
		 
		// Thread.sleep(2000);
		 //ESTADO
		 element=driver.findElement(By.id("StateLabel1_lblLabelState"));
		 texto=element.getText();
		 Oc.setEstado(texto);
		 
		 //NUMERO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblNombre"));
		 texto=element.getText();
		 Oc.setNumero(texto);
		 		 
		 //FECHA
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_tbFechaInicio"));
		 texto=element.getAttribute("value");
		 Oc.setFecha(texto);
		 		 
		 //ALMACEN
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_ddAlmacen"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Oc.setAlmacen(texto);
				 
		 //FORMA DE PAGO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_ddFormaPago"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Oc.setFormaPago(texto);
		 		 
		 //FECHA DE ENTREGA
		 element=driver.findElement(By.name("Tabs$tpInfoBasica$tbFechaEntrega"));
		 texto=element.getAttribute("value");
		 Oc.setFechaEntrega(texto);
		 
		 //RET FUENTE
		 element=driver.findElement(By.name("Tabs$tpInfoBasica$ddReteFuente"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Oc.setRetFuente(texto);
		 
		 //IVA
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_ddlIVA"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Oc.setIva(texto);
		 
		 // %RET IVA
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblPorcentajeRetIva"));
		 texto=element.getText();
		 Oc.setRetIva(texto);
		 
		 //CONCEPTO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_tbConcepto"));
		 texto=element.getText();
		 Oc.setConcepto(texto);
		 
		 //PROVEEDOR
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_ddProveedor"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Oc.setProveedor(texto);
		 
		 //CONTACTO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_tbContacto"));
		 texto=element.getAttribute("value");
		 Oc.setContacto(texto);
		 
		 //MINUTA
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_ddMinuta"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Oc.setMinuta(texto);
		 
		 //ANTICIPO		 
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_tbAnticipo"));
		 texto=element.getAttribute("value");
		 Oc.setAnticipo(texto);
		 
		 //RET IND COM
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_ddReteIC"));
		 seleccionar=new Select(element);
		 texto=seleccionar.getFirstSelectedOption().getText();
		 Oc.setRetIndCom(texto);
		 
		 // %RET CREE 
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblporcentajeCREE"));
		 texto=element.getText();
		 Oc.setRetCree(texto);
		 
		 //Thread.sleep(2000);
		 // DETALLE DE LA ORDEN DE COMPRA , LOS RESULTADOS INICIAN EN LA POSICION 102
		 
		 int l= (driver.findElements(By.xpath("//*[@id='Tabs_tpInfoBasica_gvActividad']/tbody/tr")).size())-1;
		 int k;
		 int Posicion=0;
		 for(k=2;k<=l;k++){
		 
		 Posicion++;
			 
			
			
		 // CODIGO
		 element=driver.findElement(By.xpath("//*[@id='Tabs_tpInfoBasica_gvActividad']/tbody/tr["+k+"]/td[3]"));
		 texto=element.getText();
		 Oc.setCodigo(texto);
		 
		 //NOMBRE
		 element=driver.findElement(By.xpath("//*[@id='Tabs_tpInfoBasica_gvActividad']/tbody/tr["+k+"]/td[4]"));
		 texto=element.getText();
		 Oc.setNombre(texto);
		 
		 // UM
		 element=driver.findElement(By.xpath("//*[@id='Tabs_tpInfoBasica_gvActividad']/tbody/tr["+k+"]/td[5]"));
		 texto=element.getText();
		 Oc.setUm(texto);
		 
		 if(k<=9){
			 
		  N="0"+String.valueOf(k);	 
			 
		 }else{
			 
			 N=String.valueOf(k);	 
			 
		 } 
			 
			 
		 
		 // CANTIDAD
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_tb_Cantidad"));
		 texto=element.getAttribute("value");
		 Oc.setCantidad(texto);
		 
		 // VR UNITARIO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_tb_VUnitario"));
		 texto=element.getAttribute("value");
		 Oc.setVrUnitario(texto);
		 
		 // VR PARCIAL
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_ValorParcial"));
		 texto=element.getText();
		 Oc.setVrParcial(texto);
		 
		 // %DESCUENTO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_tb_PDescuento"));
		 texto=element.getAttribute("value");
		 Oc.setPDescuento(texto);
		 
		 // VRDESCUENTO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_ValorDescuento"));
		 texto=element.getText();
		 Oc.setVrDescuento(texto);
		 
		 // CHECK IVA
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_cb_Iva"));
		 //estado=element.isDisplayed();
		 texto=element.getAttribute("checked");
		 if(element==null){
			 texto="false";
			 
		 }else{
			 
			 texto="true";
		 }
		// texto = Boolean.toString(estado);
		 Oc.setCheckIva(texto);;
		 
		 //VR IVA
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_ValorIVA"));
		 texto=element.getText();
		 Oc.setVrIva(texto);
		 
		 // VR FLETES
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_ValorFletes"));
		 texto=element.getText();
		 Oc.setVrFletes(texto);
		 		 
		 // VR NETO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_gvActividad_ctl"+N+"_ValorNeto"));
		 texto=element.getText();
		 Oc.setVrNeto(texto);
		 
		 ConectarSQL.InsertarOrdemCompraItems(Oc.getCodigo(), Oc.getNombre(), Oc.getUm(), Oc.getCantidad(), Oc.getVrUnitario(), Oc.getVrParcial(), Oc.getPDescuento(), Oc.getVrDescuento(), Oc.getCheckIva(), Oc.getVrIva(), Oc.getVrFletes(), Oc.getVrNeto(),Oc.getNumero(),Datosc.getConstructora(),Datosc.getObra(),Datosc.getCentroCosto(),Orden,Posicion);
		
		 //Thread.sleep(2000);
		 }
		 
		 
		 		 
		 // DETALLE TOTALES
		 
		 // VR ANTICIPO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_tbAnticipo2"));
		 texto=element.getAttribute("value");
		 Oc.setVrAnticipo(texto);
		 
		 //VR FLETES
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_tbFletes"));
		 texto=element.getAttribute("value");
		 Oc.setVrFletes2(texto);
		 
		 //SUBTOTAL
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblSubtotal"));
		 texto=element.getText();
		 Oc.setSubtotal(texto);
		 
		 //DESCUENTO
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblTotalDescuento"));
		 texto=element.getText();
		 Oc.setLBLDescuento(texto);
		 
		 //RET FUENTE
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblReteFuente"));
		 texto=element.getText();
		 Oc.setLBLRetFuente(texto);
		 
		 //RET IND COM 
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblReteIC"));
		 texto=element.getText();
		 Oc.setLBLRetIndCom(texto);
		 
		 //IVA
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblTotalIva"));
		 texto=element.getText();
		 Oc.setLBLIva(texto);
		 
	     // RET IVA
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblTotalReteIVA"));
		 texto=element.getText();
		 Oc.setRetIva(texto);
		 
		 // RET CREE
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblValorReteCREE"));
		 texto=element.getText();
		 Oc.setLBLRetCree(texto);
		 
		 // FLETES
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblTotalFletes"));
		 texto=element.getText();
		 Oc.setLBLFletes(texto);
		 
		 //TOTAL
		 element=driver.findElement(By.id("Tabs_tpInfoBasica_lblTotal"));
		 texto=element.getText();
		 Oc.setLBLTotal(texto);
		 
		 ConectarSQL.InsertarOrdenCompra(Oc.getNumero(), Oc.getFecha(), Oc.getAlmacen(), Oc.getFormaPago(), Oc.getFechaEntrega(), Oc.getRetFuente(), Oc.getIva(), Oc.getRetIva(), Oc.getConcepto(), Oc.getProveedor(), Oc.getContacto(), Oc.getMinuta(), Oc.getAnticipo(), Oc.getRetIndCom(), Oc.getRetCree(),  Oc.getVrAnticipo(), Oc.getVrFletes2(), Oc.getSubtotal(), Oc.getLBLDescuento(), Oc.getLBLRetFuente(), Oc.getLBLRetIndCom(), Oc.getLBLIva(), Oc.getLBLRetCree(), Oc.getLBLFletes(), Oc.getLBLTotal(),Oc.getEstado(),Datosc.getConstructora(),Datosc.getObra(),Datosc.getCentroCosto(),Orden);
	   
		 driver.switchTo().defaultContent();
		 //Thread.sleep(2000);
		}
		}
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
			    ConectarSQL.ActualizarSesionCredenciales(Du.getCorreo(), "Finalizado","1");
			}
			else {
				Assert.assertEquals(1, Clic);
				ConectarSQL.ActualizarSesionCredenciales(Du.getCorreo(), "Finalizado","1");	
			}
					}
		
		
	     }
		
		
		
			
			
	  }
	
	



	 public static void takeScreenShotTest(WebDriver driver, String imageName,String Ruta) {
		    //Directorio donde quedaran las imagenes guardadas
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
		 
		 
	 
		   
	 @Parameters ({"RutaImagenes"})
   @AfterMethod
  public void CapturaImgen(ITestResult result ,ITestContext context,String Ruta) throws Exception {
		
		 			
		   
			
		 if (result.getStatus() == ITestResult.FAILURE) {
			 
			 ArrayList <OrdenCompra> DC= ConectarSQL.DatosClientes();
			 OrdenCompra Datosc =DC.get(u);	
			  
			 takeScreenShotTest(driver, "Se Genero Un Error Con La Empresa "+Datosc.getConstructora()+" Obra "+Datosc.getObra()+" Centro Costo "+Datosc.getCentroCosto()+" Orden "+Orden ,Ruta);
			 driver.switchTo().defaultContent();
			 Thread.sleep(500);
			
			 
						  
			 	   }
 

	 }
	
	 
	 
	 
	@Parameters ({"Grabacion"})
	@AfterTest
	  public void tearDown(String Grabacion) throws Exception {
			
		   
		 
		  if(Clic <=1){
		   //driver.quit();
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
	

	


	
	
	
	
	
	
	

