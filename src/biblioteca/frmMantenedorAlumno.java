package biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class frmMantenedorAlumno extends javax.swing.JFrame {

     Object[][] filas=null;
     String cod=null;
   
    public frmMantenedorAlumno() {
        initComponents();
    }

    
    private void limpiar()
  {
      this.jtxtnombres.setText("");
      this.jtxtdireccion.setText("");
      this.jtxtapellidos.setText("");
      this.jftxttelfono.setText("");
      this.jcmbdistrito.setSelectedIndex(0);
      this.jcmbsexo.setSelectedIndex(0);
      this.jchbhabilitado.setSelected(false);
  }
 
  private void eliminarRegistro(int codigo)
  {
      Connection ocn=null;
  JOptionPane mensaje=new JOptionPane();
  Statement consulta=null;
 
  try
  {
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
  }
 
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema de Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
 
  try
  {
      ocn=DriverManager.getConnection("jdbc:sqlserver://casa; databasename=Biblioteca", "sa", "cp246725");
      consulta=ocn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
       consulta.executeUpdate("delete from alumnos where codusuario=" + codigo);
       mensaje.showMessageDialog(this, "El alumno se elimino de la Base de Datos", "Sistema Biblioteca",JOptionPane.INFORMATION_MESSAGE);
       ocn.close();
  }
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
 
  }
 
private void actualizarRegistro(Alumno objeto, int codigo)
{
  Connection ocn=null;
  JOptionPane mensaje=new JOptionPane();
  Statement consulta=null;
 
  try
  {
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
  }
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema de Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
 
  try
  {
      ocn=DriverManager.getConnection("jdbc:sqlserver://casa; databasename=Biblioteca", "sa", "cp246725");
      consulta=ocn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
       consulta.executeUpdate("update alumnos set nombres='" + objeto.getNombres() + "',apellidos='" + objeto.getApellidos() + "',direccion='" + objeto.getDireccion() + "',phono='" +
       objeto.getTelefono() + "',coddistrito=" + objeto.buscarCodigo() + ",sexo='" + objeto.getSexo() +
               "',habilitado=" + (objeto.isHabilitado()?1:0) + " where codusuario=" + codigo);
       mensaje.showMessageDialog(this, "El alumno " + objeto.getApellidos().trim().toUpperCase() + ", "  + objeto.getNombres().trim().toUpperCase() + " se actualizo correctamente", "Sistema Biblioteca",JOptionPane.INFORMATION_MESSAGE);
       ocn.close();
  }
 
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
 
}
 
private void insertarRegistro(Alumno objeto)
{
  Connection ocn=null;
  JOptionPane mensaje=new JOptionPane();
  Statement consulta=null;
  try
  {
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
  }
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema de Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
 
  try
  {
      ocn=DriverManager.getConnection("jdbc:sqlserver://casa; databasename=Biblioteca", "sa", "cp246725");
      consulta=ocn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//       consulta.executeUpdate("insert into alumnos(nombres,apellidos,direccion,phono,coddistrito,sexo,habilitado) " +
//               "values('" + objeto.getNombres() + "','" + objeto.getApellidos() + "','" + objeto.getDireccion() +
//               "','" + objeto.getTelefono() + "'," + objeto.buscarCodigo() + ",'" + objeto.getSexo() + "'," + objeto.isHabilitado()?1:0) + ")");
       mensaje.showMessageDialog(this, "El alumno " + objeto.getApellidos().trim().toUpperCase() + ", "  + objeto.getNombres().trim().toUpperCase() + " se registro correctamente", "Sistema Biblioteca",JOptionPane.INFORMATION_MESSAGE);
 
  }
 
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
}
 
private void cargarData()
{
   JOptionPane mensaje=new JOptionPane();
  Connection ocn=null;
  Statement consulta1=null;
   Statement consulta2=null;
    ResultSet lector=null;
    ResultSet cantfilas=null;
 
  try
  {
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
  }
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, "El controlador no cargo correctamente", "Sistema de Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
 
   try
   {
      ocn=DriverManager.getConnection("jdbc:sqlserver://casa; databasename=Biblioteca", "sa", "cp246725");
      consulta2=ocn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      consulta1=ocn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      lector=null;
      lector=consulta2.executeQuery("select codusuario, nombres, apellidos, direccion, phono, d.distrito, " +
              "case when sexo='F' then 'FEMENINO' else 'MASCULINO' end as sexo, habilitado " +
              "from dbo.Alumnos a inner join distritos d on a.coddistrito=d.coddistrito");
       cantfilas=consulta1.executeQuery("select count(*) from alumnos");
        cantfilas.next();
        Object[] columnas={"Codigos","Nombres","Apellidos","Dirección","Teléfono","Distrito","Sexo","Habilitado"};
        filas=new Object[cantfilas.getInt(1)+1][8];
      int c=0;
      int i=0;
      while(lector.next())
      {
              filas[i][0]="" + lector.getInt(1);
              filas[i][1]="" + lector.getString(2);
              filas[i][2]="" + lector.getString(3);
              filas[i][3]="" + lector.getString(4);
              filas[i][4]="" + lector.getString(5);
              filas[i][5]="" + lector.getString(6);
              filas[i][6]="" + lector.getString(7);
              filas[i][7]="" + lector.getBoolean(8);
          i++;
      }
      DefaultTableModel mydata=new DefaultTableModel(filas,columnas);
      this.JTable.setModel(mydata);
      this.JTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      this.jtxtdireccion.setLineWrap(true);
      lector.close();
      ocn.close();
   }
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
}
 
private void formWindowOpened(java.awt.event.WindowEvent evt) {                  
// TODO add your handling code here:
 JOptionPane mensaje=new JOptionPane();
  Connection ocn=null;
  Statement consulta=null;
     ResultSet lector=null;
 
  try
  {
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
  }
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema de Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
 
  try
  {
      ocn=DriverManager.getConnection("jdbc:sqlserver://casa; databasename=Biblioteca", "sa", "cp246725");
      consulta=ocn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      lector=consulta.executeQuery("select distrito from distritos order by distrito");
      while(lector.next())
      {
      this.jcmbdistrito.addItem(lector.getString(1));
      }
      this.jcmbsexo.addItem("MASCULINO");
      this.jcmbsexo.addItem("FEMENINO");
      this.cargarData();
      lector.close();
      ocn.close();
  }
  catch(Exception err)
  {
      mensaje.showMessageDialog(this, err.getMessage(), "Sistema Biblioteca", JOptionPane.ERROR_MESSAGE);
  }
}                 
 
private void jbtnmodificarMouseClicked(java.awt.event.MouseEvent evt) {                           
// TODO add your handling code here:
  cod=filas[this.JTable.getSelectedRow()][0].toString();
  this.jtxtnombres.setText(filas[this.JTable.getSelectedRow()][1].toString());
  this.jtxtapellidos.setText(filas[this.JTable.getSelectedRow()][2].toString());
  this.jtxtdireccion.setText(filas[this.JTable.getSelectedRow()][3].toString());
  this.jftxttelfono.setText(filas[this.JTable.getSelectedRow()][4].toString());
  this.jcmbdistrito.setSelectedItem(filas[this.JTable.getSelectedRow()][5].toString());
  this.jcmbsexo.setSelectedItem(filas[this.JTable.getSelectedRow()][6].toString());
  this.jchbhabilitado.setSelected(Boolean.parseBoolean(filas[this.JTable.getSelectedRow()][7].toString()));
}                          
 
private void jbtnagregarMouseClicked(java.awt.event.MouseEvent evt) {                         
// TODO add your handling code here:
  Alumno objeto=new Alumno();
  objeto.setNombres(this.jtxtnombres.getText());
  objeto.setApellidos(this.jtxtapellidos.getText());
  objeto.setDireccion(this.jtxtdireccion.getText());
  objeto.setDistrito(this.jcmbdistrito.getSelectedItem().toString());
  if(this.jcmbsexo.getSelectedItem().toString().compareTo("MASCULINO")==0)
  {
      objeto.setSexo("M");
  }
  else
  {
      objeto.setSexo("F");
  }
  objeto.setTelefono(this.jftxttelfono.getText());
  objeto.setHabilitado(this.jchbhabilitado.isSelected());
  this.insertarRegistro(objeto);
  this.limpiar();
  this.cargarData();
}                        
 
private void jbtnguardarMouseClicked(java.awt.event.MouseEvent evt) {                         
// TODO add your handling code here:
   Alumno objeto=new Alumno();
  objeto.setNombres(this.jtxtnombres.getText());
  objeto.setApellidos(this.jtxtapellidos.getText());
  objeto.setDireccion(this.jtxtdireccion.getText());
  objeto.setDistrito(this.jcmbdistrito.getSelectedItem().toString());
  if(this.jcmbsexo.getSelectedItem().toString().compareTo("MASCULINO")==0)
  {
     objeto.setSexo("M");
  }
  else
  {
      objeto.setSexo("F");
  }
  objeto.setTelefono(this.jftxttelfono.getText());
  objeto.setHabilitado(this.jchbhabilitado.isSelected());
  this.actualizarRegistro(objeto, Integer.parseInt(cod));
  this.limpiar();
  this.cargarData();
}                        
 
private void jbtnsalirMouseClicked(java.awt.event.MouseEvent evt) {                       
// TODO add your handling code here:
  System.exit(0);
}                      
 
private void jbnteliminarMouseClicked(java.awt.event.MouseEvent evt) {                          
// TODO add your handling code here:
  this.eliminarRegistro(Integer.parseInt(cod));
  this.limpiar();
  this.cargarData();
}
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        JTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxtnombres = new javax.swing.JTextField();
        jtxtapellidos = new javax.swing.JTextField();
        jtxtdireccion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jftxttelfono = new javax.swing.JTextField();
        jcmbdistrito = new javax.swing.JComboBox();
        jcmbsexo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jchbhabilitado = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(JTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 600, 220));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Alumno"));
        jPanel1.setToolTipText("");
        jPanel1.setName("kklllkll"); // NOI18N
        jPanel1.setLayout(null);

        jLabel1.setText("Se encuentra habilitado?");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(320, 180, 130, 15);

        jLabel2.setText("Apellidos:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(40, 90, 70, 15);

        jLabel3.setText("Direccion:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 120, 70, 15);
        jPanel1.add(jtxtnombres);
        jtxtnombres.setBounds(100, 60, 190, 19);

        jtxtapellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtapellidosActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtapellidos);
        jtxtapellidos.setBounds(100, 90, 190, 19);
        jPanel1.add(jtxtdireccion);
        jtxtdireccion.setBounds(100, 120, 190, 80);

        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(33, 220, 80, 25);

        jButton2.setText("Actualizar");
        jPanel1.add(jButton2);
        jButton2.setBounds(140, 220, 83, 25);

        jButton3.setText("Selecionar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(250, 220, 85, 25);

        jButton4.setText("Eliminar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(360, 220, 70, 25);

        jButton5.setText("Salir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(460, 220, 55, 25);

        jLabel4.setText("Nombres:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(40, 60, 70, 15);

        jLabel5.setText("Telefono:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(320, 60, 50, 15);

        jLabel6.setText("Distrito:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(320, 100, 50, 15);
        jPanel1.add(jftxttelfono);
        jftxttelfono.setBounds(380, 60, 190, 19);

        jcmbdistrito.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jcmbdistrito);
        jcmbdistrito.setBounds(380, 100, 190, 24);

        jcmbsexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jcmbsexo);
        jcmbsexo.setBounds(380, 140, 190, 24);

        jLabel7.setText("Sexo:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(320, 140, 50, 15);
        jPanel1.add(jchbhabilitado);
        jchbhabilitado.setBounds(450, 180, 20, 21);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 590, 270));
        jPanel1.getAccessibleContext().setAccessibleName("Datos del Alumno");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtapellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtapellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtapellidosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmMantenedorAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMantenedorAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMantenedorAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMantenedorAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMantenedorAlumno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jchbhabilitado;
    private javax.swing.JComboBox jcmbdistrito;
    private javax.swing.JComboBox jcmbsexo;
    private javax.swing.JTextField jftxttelfono;
    private javax.swing.JTextField jtxtapellidos;
    private javax.swing.JTextField jtxtdireccion;
    private javax.swing.JTextField jtxtnombres;
    // End of variables declaration//GEN-END:variables
}
