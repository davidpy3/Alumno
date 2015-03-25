package biblioteca;
 
import java.sql.*;
import javax.swing.*;
 
public class Alumno {
  private int codigo;
  private String nombres;
  private String apellidos;
  private String direccion;
  private String telefono;
  private int coddistrito;
  private String sexo;
  private boolean habilitado;
  private String _distrito;
 
  public Alumno()
  {
      codigo=0;
      nombres="";
      apellidos="";
      direccion="";
      telefono="";
      coddistrito=0;
      sexo="";
      habilitado=false;
  }
 
  public Alumno(String nombre, String apellido, String _direccion,String phono,int codditri, String sex, boolean habilit )
  {
      codigo=0;
      nombres=nombre;
      apellidos=apellido;
      direccion=_direccion;
      telefono=phono;
      coddistrito=codditri;
      sexo=sex;
      habilitado=habilit;
  }
 
  public int buscarCodigo()
  {
          JOptionPane mensaje=new JOptionPane();
          Connection ocn=null;
          Statement consulta1=null;
          ResultSet lector=null;
          int resp=-1;
         try
          {
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
          }
          catch(Exception err)
          {
              mensaje.showMessageDialog(null, "El controlador no cargo correctamente", "Sistema de Biblioteca", JOptionPane.ERROR_MESSAGE);
          }
   
       try
         {
             ocn=DriverManager.getConnection("jdbc:sqlserver://192.168.10.10:1433;databaseName=pegasus;integratedSecurity=true");
             consulta1=ocn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
 
             lector=consulta1.executeQuery("select coddistrito from distritos where distrito='" + _distrito + "'");
 
             lector.next();
 
             if(lector==null)
             {
                resp=-1;
             }
             else
             {
                 resp=Integer.parseInt(lector.getString(1));
             }
 
             lector.close();
             ocn.close();
         }
         catch(Exception err)
         {
            mensaje.showMessageDialog(null, "Error al tratar de buscar el codigo", "Sistema Biblioteca", JOptionPane.ERROR_MESSAGE);
         }
 
          return resp;
  }
 
  public void setDistrito(String distrito)
  {
      _distrito=distrito;
  }
 
  public String getDistrito()
  {
      return _distrito;
  }
 
  public String getApellidos() {
      return apellidos;
  }
 
  public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
  }
 
  public int getCoddistrito() {
      return coddistrito;
  }
 
  public void setCoddistrito(int coddistrito) {
      this.coddistrito = coddistrito;
  }
 
  public int getCodigo() {
      return codigo;
  }
 
  public void setCodigo(int codigo) {
      this.codigo = codigo;
  }
 
  public String getDireccion() {
      return direccion;
  }
 
  public void setDireccion(String direccion) {
      this.direccion = direccion;
  }
 
  public boolean isHabilitado() {
      return habilitado;
  }
 
  public void setHabilitado(boolean habilitado) {
      this.habilitado = habilitado;
  }
 
  public String getNombres() {
      return nombres;
  }
 
  public void setNombres(String nombres) {
      this.nombres = nombres;
  }
 
  public String getSexo() {
      return sexo;
  }
 
  public void setSexo(String sexo) {
      this.sexo = sexo;
  }
 
  public String getTelefono() {
      return telefono;
  }
 
  public void setTelefono(String telefono) {
      this.telefono = telefono;
  }
 
}