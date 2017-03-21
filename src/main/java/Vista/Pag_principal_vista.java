package Vista;

import Contactos.Cliente;
import Controlador.Controlador;
import Datos.Factura;
import Datos.Listar;
import Datos.Llamada;
import Modelo.InterrogaModelo;
import Vista.Interfaces.ActualizaVistaPagPrincipal;
import Vista.Interfaces.InterrogaVista;
import Vista.layouts.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;


/**
 * Created by doomed on 4/05/16.
 */
public class Pag_principal_vista implements ActualizaVistaPagPrincipal, InterrogaVista {
    private Controlador controlador;
    private InterrogaModelo modelo;
    private EscuchadorAnyadir escuchadorAnyadir;
    private escuchadorBuscar escuchadorBuscar;
    private static final String [] etiquetasClientes= {"DNI: ", "Nombre: ","Apellidos: ", "Email: ", "Provincia: ", "Poblacion: ", "CP: ", "Tarifa base: "};
    private static final String [] etiquetasFacturas={"Fecha inicio: ", "Fecha fin: "};
    private static final String [] etiquetasLlamadas={"Minutos: ", "Número: "};
    private boolean isEmpresa=false;
    private static JTextField [] camposClientes= new JTextField[etiquetasClientes.length];
    private JTextField[] camposClientesActualizar= new JTextField[etiquetasClientes.length];
    private static  JTextField [] camposFacturas= new JTextField[etiquetasFacturas.length];
    private static JTextField [] camposLlamadas= new JTextField[etiquetasLlamadas.length];
    private JList listaClientes;
    private JFrame ventana;
    private JTextField dniEliminar;
    private JTextArea AreaInfoPersonal;
    private String dni;
    private long codigofac;
    private JTextArea textAreaFactura;
    private boolean isDia=true;
    private JTextArea areaTarifaCliente;
    private JTextField precioNuevaTarifaBase;
    private JList codigosFactura;
    private JTable tablaLlamadas;

    public Pag_principal_vista() {
        escuchadorAnyadir = new EscuchadorAnyadir();
        escuchadorBuscar= new escuchadorBuscar();
        listaClientes= new JList();
    }

    public void setModelo(InterrogaModelo modelo) {
        this.modelo = modelo;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void GUI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame ventana = new JFrame("Aplicación Telefonica de WebStore");
        Container contenedor = ventana.getContentPane();
        ventana.setLocationRelativeTo(null);

        //Se configuran las pestañas
        JTabbedPane pestañas = new JTabbedPane();

            //Configuramos la pestaña de informacion basica

                JPanel info_general = new JPanel();
                info_general.setBorder(BorderFactory.createLoweredBevelBorder());
                info_general.setLayout(new BorderLayout());

                //Aparece la info general
                JPanel  info= new JPanel();
                info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));
                AreaInfoPersonal= new JTextArea("\n\n\n\n\n\n\n\n\t\t-Selecciona un dni para ver la información general del cliente- ");
                AreaInfoPersonal.setAlignmentX(Component.CENTER_ALIGNMENT);
                AreaInfoPersonal.setEditable(false);
                AreaInfoPersonal.setBorder(BorderFactory.createEmptyBorder());
                AreaInfoPersonal.setOpaque(false);
                AreaInfoPersonal.setBackground(new Color(0,0,0,0));
                info.add(Box.createHorizontalGlue());
                info.add(Box.createVerticalGlue());
                info.add(AreaInfoPersonal);


                //Panel con boton de actualizacion info
                JPanel boton_info= new JPanel();
                boton_info.setLayout(new BorderLayout());
                info_general.add(info,BorderLayout.NORTH);
                info_general.add(boton_info,BorderLayout.SOUTH);

          //Configuramos la pestaña de facturas
                JPanel facturas = new JPanel();
                facturas.setBorder(BorderFactory.createLoweredBevelBorder());
                facturas.setLayout(new BorderLayout());

                //Configuramos cod fac
                JPanel codfac= new JPanel();
                codfac.setLayout(new BoxLayout(codfac,BoxLayout.PAGE_AXIS));


                JLabel infocodfac= new JLabel("<html>"+"<font size=3><b>Codigo de factura</b></font>"+"<html>");
                infocodfac.setAlignmentX(Component.TOP_ALIGNMENT);
                codfac.add(infocodfac);

                codigosFactura= new JList();
                codigosFactura.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                JScrollPane scrollPaneCodigosFacturas= new JScrollPane(codigosFactura);
                scrollPaneCodigosFacturas.setAlignmentX(Component.CENTER_ALIGNMENT);
                if(codigosFactura!=null){
                    codigosFactura.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                    if(dni!=null) {
                                        Object codigo = codigosFactura.getSelectedValue();
                                        codigofac = Long.parseLong(codigo.toString());
                                        ActualizarInfoFacturas();
                                    }
                                }

                        });
                }
                codfac.add(infocodfac);
                codfac.add(scrollPaneCodigosFacturas);

                //configuramos Info factura
                JPanel infoFactura= new JPanel();
                infoFactura.setLayout(new BorderLayout());
                JPanel i_infoFactura= new JPanel();
                i_infoFactura.setLayout(new BoxLayout(i_infoFactura,BoxLayout.PAGE_AXIS));

                JLabel factura = new JLabel("<html>"+"<b>Informacion sobre la factura</b>"+"</html>");
                infoFactura.add(factura, BorderLayout.NORTH);

                textAreaFactura= new JTextArea("\n\n\n\n\n\t\t-Selecciona un código de factura-");
                textAreaFactura.setOpaque(false);
                textAreaFactura.setEditable(false);
                textAreaFactura.setBackground(new Color(0,0,0,0));
                textAreaFactura.setAlignmentX(Component.CENTER_ALIGNMENT);

                i_infoFactura.add(Box.createVerticalGlue());
                i_infoFactura.add(textAreaFactura);
                i_infoFactura.add(Box.createVerticalGlue());

                infoFactura.add(i_infoFactura,BorderLayout.CENTER);

                //configuramos los botones de facturas
                JPanel botones_fac= new JPanel();
                botones_fac.setLayout(new BoxLayout(botones_fac,BoxLayout.LINE_AXIS));
                JButton buscarFacturasFechas= new JButton("Buscar");
                buscarFacturasFechas.setActionCommand("facturas");
                buscarFacturasFechas.addActionListener(escuchadorBuscar);
                buscarFacturasFechas.setAlignmentX(Component.RIGHT_ALIGNMENT);

                JButton emitir= new JButton("Emitir");
                emitir.addActionListener(escuchadorAnyadir);
                emitir.setAlignmentX(Component.RIGHT_ALIGNMENT);

                botones_fac.add(Box.createHorizontalGlue());
                botones_fac.add(buscarFacturasFechas);
                botones_fac.add(emitir/*, BorderLayout.EAST*/);


                facturas.add(codfac,BorderLayout.WEST);
                facturas.add(infoFactura, BorderLayout.CENTER);
                facturas.add(botones_fac, BorderLayout.SOUTH);


          //Configuramos la pestaña de llamadas
                JPanel llamadas = new JPanel();
                llamadas.setBorder(BorderFactory.createLoweredBevelBorder());
                llamadas.setLayout(new BorderLayout());
                //hacemos el panel para las llamadas
                JPanel panel_table= new JPanel(new BorderLayout());
                JPanel i_panel_table= new JPanel(new BorderLayout());


                //Creamos la tabla

                tablaLlamadas= new JTable(new TablaModelLlamadas());

                tablaLlamadas.setPreferredScrollableViewportSize(new Dimension(500,300));
                tablaLlamadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                tablaLlamadas.setEnabled(false);
                JScrollPane scrollPane= new JScrollPane(tablaLlamadas);
                scrollPane.setVisible(true);
                i_panel_table.add(scrollPane,BorderLayout.CENTER);


                panel_table.add(i_panel_table, BorderLayout.NORTH);


                //hacemos el panel para los botones
                JPanel botonesLlamadas= new JPanel();
                botonesLlamadas.setLayout(new BoxLayout(botonesLlamadas,BoxLayout.LINE_AXIS));


                JButton nuevaLlamada= new JButton("Nueva llamada");
                JButton buscarLlamadasFechas= new JButton("Buscar");
                buscarLlamadasFechas.setActionCommand("llamadas");
                buscarLlamadasFechas.addActionListener(escuchadorBuscar);
                nuevaLlamada.addActionListener(escuchadorAnyadir);
                nuevaLlamada.setAlignmentX(Component.RIGHT_ALIGNMENT);
                buscarLlamadasFechas.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
                botonesLlamadas.add(Box.createHorizontalGlue());
                botonesLlamadas.add(buscarLlamadasFechas);
                botonesLlamadas.add(nuevaLlamada);

                llamadas.add(botonesLlamadas,BorderLayout.SOUTH);
                llamadas.add(panel_table, BorderLayout.CENTER);

            //configuramos la pestaña de tarifas
                JPanel tarifas = new JPanel();
                tarifas.setBorder(BorderFactory.createLoweredBevelBorder());
                tarifas.setLayout(new BorderLayout());

                //texto informativo
                JPanel tarifas_info = new JPanel();
                JLabel label_tarifas_info= new JLabel("<html>"+"<b>Además de la tarifa base tenemos las siguientes tarifas adicionales :</b><br/>" +
                        "<ol><u>Tarifa de Domingo:</u> Los domingos las llamadas son gratuitas.<br/></ol>" +
                        "<ol><u>Tarifa Horaria:</u> De Lunes a Sabado de 16:00 a 20:00 h las llamadas a 5 cent/min</ol>"+"</html>");
                tarifas_info.add(label_tarifas_info);

                //tarifa Cliente
                JPanel tarifasCliente= new JPanel();
                tarifasCliente.setLayout(new BoxLayout(tarifasCliente, BoxLayout.PAGE_AXIS));
                areaTarifaCliente= new JTextArea("\n\n\t\t-Selecciona un cliente para ver sus tarifas-");
                areaTarifaCliente.setEditable(false);
                areaTarifaCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
                areaTarifaCliente.setBackground(new Color(0,0,0,0));
                tarifasCliente.add(areaTarifaCliente);


                //Botones de las tarifas
                JPanel botones_tarifa = new JPanel();
                botones_tarifa.setLayout(new BoxLayout(botones_tarifa,BoxLayout.LINE_AXIS));

                JButton nueva= new JButton("Expandir tarifa");
                nueva.addActionListener(escuchadorAnyadir);
                JButton modificar= new JButton("Modificar tarifa base");
                modificar.addActionListener(escuchadorAnyadir);

                botones_tarifa.add(Box.createHorizontalGlue());
                botones_tarifa.add(modificar);
                botones_tarifa.add(nueva);

                tarifas.add(tarifas_info, BorderLayout.NORTH);
                tarifas.add(tarifasCliente, BorderLayout.CENTER);
                tarifas.add(botones_tarifa, BorderLayout.SOUTH);




        pestañas.addTab("Información General", info_general);
        pestañas.addTab("Facturas", facturas);
        pestañas.addTab("Llamadas", llamadas);
        pestañas.addTab("Tarifas", tarifas);



        //Creamos los botones de Añadir Eliminar
        JButton jbAñadir = new JButton("Añadir");
        jbAñadir.addActionListener(escuchadorAnyadir);
        JButton jbEliminar = new JButton("Eliminar");
        jbEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaEliminar();
            }
        });
        JButton jbBuscar= new JButton("Buscar");
        jbBuscar.setActionCommand("clientes");
        jbBuscar.addActionListener(escuchadorBuscar);


        //Creamos el panel que tendra los botones
        JPanel clientesBotones = new JPanel();
        clientesBotones.add(jbAñadir);
        clientesBotones.add(jbEliminar);
        clientesBotones.add(jbBuscar);


        //Creamos el panel que contendra el nombre de los clientes
        JPanel clientesAmostrar = new JPanel(new BorderLayout());
        JLabel etiquetaClientesAmostrar= new JLabel("Código de los clientes");
        clientesAmostrar.add(etiquetaClientesAmostrar, BorderLayout.NORTH);
        String[] datos = new String[50];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = i + "";
        }

        ActualizarListaClientes(modelo.listaClientes());
        listaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneListaClientes = new JScrollPane(listaClientes);
        if(listaClientes!=null){
        listaClientes.addMouseListener(new MouseAdapter() { //TODO
            @Override
            public void mouseClicked(MouseEvent e) {
                Object objeto=listaClientes.getSelectedValue();
                dni=objeto.toString();
                ActualizarInfoCli();
                ActualizarTarifa();
                ActualizarFacturas();
                ActualizarLlamadas();

            }
        });}
        clientesAmostrar.setLayout(new BoxLayout(clientesAmostrar, BoxLayout.PAGE_AXIS));
        scrollPaneListaClientes.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientesAmostrar.add(scrollPaneListaClientes);


        //Añadimos clientes a mostrar y clientes botones al panel ListaClientes
        JPanel ListaClientes = new JPanel();
        ListaClientes.setLayout(new BorderLayout());
        ListaClientes.add(clientesAmostrar, BorderLayout.CENTER);
        ListaClientes.add(clientesBotones, BorderLayout.SOUTH);


        //Añadimos a la ventana los paneles existentes
        contenedor.add(ListaClientes, BorderLayout.WEST);
        contenedor.add(pestañas, BorderLayout.CENTER);
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controlador.almacenaDatos();
                System.exit(0);
            }
        });

        ventana.pack();
        ventana.setVisible(true);


    }

    private void ActualizarLlamadas() {
        ActualizarListaLlamadas(modelo.listaLlamadas(dni));
    }

    private void ActualizarInfoFacturas() {

        textAreaFactura.setText(modelo.filtaFactura(dni,codigofac).toString());


    }


    private void ActualizarFacturas() {
        ActualizarListaFacturas(modelo.listaFacturas(dni));
        textAreaFactura.setText("\n\n\n\n\t\tSelecciona un codigo de factura");



    }

    private void ventanaEliminar() {
        JDialog ventanaEliminar=new JDialog(ventana,true);
        ventanaEliminar.setLocationRelativeTo(ventana);
        ventanaEliminar.setTitle("Eliminar Cliente");
        Container contendedor=ventanaEliminar.getContentPane();
        JPanel panelEliminar=new JPanel();
        JLabel Eliminar=new JLabel("DNI: ");
        dniEliminar=new JTextField(10);
        JButton cancelar=new JButton("Cancelar");
        JButton aceptar= new JButton("Aceptar");
        contendedor.add(panelEliminar);
        panelEliminar.add(Eliminar); panelEliminar.add(dniEliminar); panelEliminar.add(cancelar); panelEliminar.add(aceptar);
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaEliminar.dispose();
            }
        });

        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.eliminaDatos();
                ventanaEliminar.dispose();
            }
        });
        ventanaEliminar.pack();
        ventanaEliminar.setVisible(true);
    }

    public void creaGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI();
            }
        });
    }

    private  class EscuchadorAnyadir implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton boton= (JButton)e.getSource();
            String texto=boton.getText();
            switch(texto){
                case "Añadir":
                    ventanaClienteAnyadir();
                    break;
                case "Emitir":
                    if(dni!=null)
                    ventanaFacturaEmitir();
                    else
                    ventanaPrecaucion();
                    break;
                case "Nueva llamada":
                    if(dni!=null)
                    ventanaLlamadaNuevaLlam();
                    else
                    ventanaPrecaucion();
                    break;
                case "Expandir tarifa":
                    if(dni!=null)
                    ventanaTarifaExpandir();
                    else
                    ventanaPrecaucion();
                    break;
                case"Modificar tarifa base":
                    if (dni!=null)
                    ventanaTarifaModificar();
                    else
                    ventanaPrecaucion();
                    break;
            }
        }
    }

    private class escuchadorBuscar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String command=e.getActionCommand();
            switch (command){
                case("facturas"):
                    if(dni!=null)
                    ventanaBuscarFacturas();
                    else
                    ventanaPrecaucion();
                    break;
                case ("clientes"):
                    ventanaBuscarClientes();
                    break;
                case("llamadas"):
                    if (dni!=null)
                    ventanaBuscarLlamadas();
                    else
                    ventanaPrecaucion();
                    break;

            }
        }
    }

    private void ventanaBuscarClientes() {
        JDialog ventanaBuscarClientes= new JDialog(ventana,true);
        ventanaBuscarClientes.setTitle("Buscar facturas por fechas");
        ventanaBuscarClientes.setLocationRelativeTo(ventana);

        Container contenedor = ventanaBuscarClientes.getContentPane();

        JPanel Fechas = new JPanel(new BorderLayout());
        JPanel fecha1_2= new JPanel(new FlowLayout());
        JLabel fecha1= new JLabel(etiquetasFacturas[0]);
        camposFacturas[0]= new JTextField("DD/MM/YYYY",10);
        JLabel fecha2 = new JLabel(etiquetasFacturas[1]);
        camposFacturas[1]= new JTextField("DD/MM/YYYY",10);
        fecha1_2.add(fecha1); fecha1_2.add(camposFacturas[0]);
        fecha1_2.add(fecha2); fecha1_2.add(camposFacturas[1]);
        Fechas.add(fecha1_2, BorderLayout.CENTER);
        JPanel botones= new JPanel(new BorderLayout());

        JButton Cancela=new JButton("Cancela");



        Cancela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaBuscarClientes.dispose();
            }
        });
        JPanel insideBotones= new JPanel(new BorderLayout());
        JButton buscaClientes= new JButton("Busca");
        buscaClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.buscarCliente();
                ventanaBuscarClientes.dispose();
            }
        });
        insideBotones.add(buscaClientes, BorderLayout.CENTER);
        botones.add(insideBotones, BorderLayout.EAST);
        botones.add(Cancela, BorderLayout.CENTER);
        Fechas.add(botones,BorderLayout.EAST);

        contenedor.add(Fechas,BorderLayout.NORTH);
        ventanaBuscarClientes.pack();
        ventanaBuscarClientes.setVisible(true);

    }

    private void ventanaBuscarLlamadas() {
        JDialog ventanaBuscarLlamadas= new JDialog(ventana,true);
        ventanaBuscarLlamadas.setTitle("Buscar facturas por fechas");
        ventanaBuscarLlamadas.setLocationRelativeTo(ventana);

        Container contenedor = ventanaBuscarLlamadas.getContentPane();

        JPanel Fechas = new JPanel(new BorderLayout());
        JPanel fecha1_2= new JPanel(new FlowLayout());
        JLabel fecha1= new JLabel(etiquetasFacturas[0]);
        camposFacturas[0]= new JTextField("DD/MM/YYYY",10);
        JLabel fecha2 = new JLabel(etiquetasFacturas[1]);
        camposFacturas[1]= new JTextField("DD/MM/YYYY",10);
        fecha1_2.add(fecha1); fecha1_2.add(camposFacturas[0]);
        fecha1_2.add(fecha2); fecha1_2.add(camposFacturas[1]);
        Fechas.add(fecha1_2, BorderLayout.CENTER);
        JPanel botones= new JPanel(new BorderLayout());

        JButton Cancela=new JButton("Cancela");

        Cancela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaBuscarLlamadas.dispose();
            }
        });

        JPanel insideBotones= new JPanel(new BorderLayout());
        JButton buscaLlamadas= new JButton("Busca");
        buscaLlamadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.busacrLlamada();
                ventanaBuscarLlamadas.dispose();
            }
        });
        insideBotones.add(buscaLlamadas, BorderLayout.CENTER);
        botones.add(insideBotones, BorderLayout.EAST);
        botones.add(Cancela, BorderLayout.CENTER);
        Fechas.add(botones,BorderLayout.EAST);

        contenedor.add(Fechas,BorderLayout.NORTH);
        ventanaBuscarLlamadas.pack();
        ventanaBuscarLlamadas.setVisible(true);

    }

    private void ventanaBuscarFacturas() {
        JDialog ventanaBuscarFacturas= new JDialog(ventana,true);
        ventanaBuscarFacturas.setTitle("Buscar facturas por fechas");
        ventanaBuscarFacturas.setLocationRelativeTo(ventana);

        Container contenedor = ventanaBuscarFacturas.getContentPane();

        JPanel Fechas = new JPanel(new BorderLayout());
        JPanel fecha1_2= new JPanel(new FlowLayout());
        JLabel fecha1= new JLabel(etiquetasFacturas[0]);
        camposFacturas[0]= new JTextField("DD/MM/YYYY",10);
        JLabel fecha2 = new JLabel(etiquetasFacturas[1]);
        camposFacturas[1]= new JTextField("DD/MM/YYYY",10);
        fecha1_2.add(fecha1); fecha1_2.add(camposFacturas[0]);
        fecha1_2.add(fecha2); fecha1_2.add(camposFacturas[1]);
        Fechas.add(fecha1_2, BorderLayout.CENTER);
        JPanel botones= new JPanel(new BorderLayout());

        JButton Cancela=new JButton("Cancela");

        Cancela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaBuscarFacturas.dispose();
            }
        });
        JPanel insideBotones= new JPanel(new BorderLayout());
        JButton buscaFacturas= new JButton("Busca");
        buscaFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.buscarFactura();
                ventanaBuscarFacturas.dispose();
            }
        });
        insideBotones.add(buscaFacturas, BorderLayout.CENTER);
        botones.add(insideBotones, BorderLayout.EAST);
        botones.add(Cancela, BorderLayout.CENTER);
        Fechas.add(botones,BorderLayout.EAST);

        contenedor.add(Fechas,BorderLayout.NORTH);
        ventanaBuscarFacturas.pack();
        ventanaBuscarFacturas.setVisible(true);

    }

    private void ventanaTarifaModificar() {
        JDialog ventanaTarifaModificar= new JDialog();
        ventanaTarifaModificar.setTitle("Modificar Tarifa Base");
        ventanaTarifaModificar.setLocationRelativeTo(ventana);
        Container contenedor=ventanaTarifaModificar.getContentPane();
        JPanel modifica=new JPanel();
        JLabel tarifaBase=new JLabel("Precio tarifa base (€): ");
        precioNuevaTarifaBase= new JTextField(""+modelo.filtraCliente(dni).getTarifa().getPrecio());
        modifica.add(tarifaBase);
        modifica.add(precioNuevaTarifaBase);
        JPanel botones= new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.LINE_AXIS));
        JButton cancelar= new JButton("Cancelar");
        JButton aceptar= new JButton("Aceptar");
        botones.add(Box.createHorizontalGlue());
        botones.add(cancelar);
        botones.add(aceptar);
        contenedor.add(botones, BorderLayout.SOUTH);
        contenedor.add(modifica, BorderLayout.NORTH);
        ventanaTarifaModificar.pack();
        ventanaTarifaModificar.setVisible(true);
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaTarifaModificar.dispose();
            }
        });
        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.nuevaTarifaBase();
                ventanaTarifaModificar.dispose();
            }
        });

    }

    @Override
    public void ActualizarListaClientes(List<Cliente> lista) {
        String[] nuevo= new String[lista.size()];
        DefaultListModel modelo= new DefaultListModel();

        for(int i=0; i<nuevo.length;i++){
            nuevo[i]=lista.get(i).getDni();
            modelo.addElement(nuevo[i]);
        }

        listaClientes.setModel(modelo);
    }

    @Override
    public void ActualizarTarifa() {
        areaTarifaCliente.setText(modelo.getTarifa(dni).descripcion());
    }

    @Override
    public void ActualizarInfoCli() {
        Cliente cliente=modelo.filtraCliente(dni);
        AreaInfoPersonal.setText("\n\n    DNI: "+cliente.getDni()+"\n\n"+"   Fecha de Alta: "+cliente.getFecha().getTime()+"\n\n"+
                "   Nombre: "+cliente.getNombre()+"\n\n"+"   Email: "+cliente.getCorreoElectronico()+"\n\n"+
                "   Provincia: "+cliente.getDireccion().getProvincia()+"\n\n"+"   CP: "+cliente.getDireccion().getCodigoPostal()+"\n" +
                "\n"
                +"   Poblacion: "+cliente.getDireccion().getPoblacion());

    }

    @Override
    public void ActualizarListaLlamadas(List<Llamada> lista) {
        String [][]datos=new String[lista.size()][3];
        for(int i=0;i<lista.size();i++) {
            datos[i][0] = lista.get(i).getNumero();
            datos[i][1]=lista.get(i).getFecha().getTime().toString();
            datos[i][2]=lista.get(i).getMinutos()+"";
        }
        AbstractTableModel modelo= new TablaModelLlamadas(datos);
        tablaLlamadas.setModel(modelo);

    }

    @Override
    public void ActualizarListaFacturas(List<Factura> lista) {
        DefaultListModel modelo=new DefaultListModel();
        for (int i=0; i<lista.size();i++){
            modelo.addElement(lista.get(i).getCodigo());
        }
        codigosFactura.setModel(modelo);
    }

    private void ventanaClienteAnyadir(){
        JDialog anyadirCliente= new JDialog(ventana,true);
        anyadirCliente.setTitle("Añadir un cliente");
        anyadirCliente.setLocationRelativeTo(ventana);
        Container contenedor= anyadirCliente.getContentPane();


        //configuracion botones guardar y cancelar
        JPanel botones= new JPanel(new BorderLayout());
        JPanel botones_interior_ventana= new JPanel(new BorderLayout());
        JButton guardar= new JButton("Guardar");
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.nuevoCliente();
                anyadirCliente.setVisible(false);

            }
        });
        JButton cancelar= new JButton("Cancelar");
        cancelar.setActionCommand("Clientes");
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anyadirCliente.dispose();
            }
        });


        botones.add(guardar, BorderLayout.EAST);
        botones.add(cancelar, BorderLayout.CENTER);

        botones_interior_ventana.add(botones,BorderLayout.EAST);

        //configuracion de clientes
        JPanel datosCliente= new JPanel( new BorderLayout());
        JPanel particular= new JPanel(new SpringLayout());



        int numEtiquetas=etiquetasClientes.length;
        for (int i=0; i<numEtiquetas; i++){
            JLabel label = new JLabel(etiquetasClientes[i]);
            particular.add(label);
            camposClientes[i]= new JTextField(20);
            label.setLabelFor(camposClientes[i]);
            particular.add(camposClientes[i]);
        }

        SpringUtilities.makeCompactGrid(particular, numEtiquetas,2,6,6,6,6);

        //configuracion eleccion empresa o particular
        JPanel eleccion = new JPanel(new BorderLayout());
        JRadioButton botonParticular=new JRadioButton("Particular",true);
        JRadioButton botonEmpresa= new JRadioButton("Empresa",false);
        botonEmpresa.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(botonEmpresa.isSelected()) {
                    isEmpresa=true;
                    camposClientes[2].setEnabled(false);
                }else {
                    isEmpresa=false;
                    camposClientes[2].setEnabled(true);
                }
            }
        });
        ButtonGroup grupo= new ButtonGroup();
        grupo.add(botonEmpresa);
        grupo.add(botonParticular);

        eleccion.add(botonParticular, BorderLayout.CENTER);
        eleccion.add(botonEmpresa,BorderLayout.EAST);

        datosCliente.add(particular, BorderLayout.CENTER);

        contenedor.add(datosCliente, BorderLayout.CENTER);
        contenedor.add(botones_interior_ventana, BorderLayout.SOUTH);
        contenedor.add(eleccion, BorderLayout.NORTH);
        anyadirCliente.setResizable(false);
        anyadirCliente.pack();
        anyadirCliente.setVisible(true);


    }

    private void ventanaFacturaEmitir(){
        JDialog ventanaFactura= new JDialog(ventana,true);
        ventanaFactura.setLocationRelativeTo(ventana);
        ventanaFactura.setTitle("Emitir Factura");
        Container contenedor=ventanaFactura.getContentPane();
        JPanel contieneLasFechas= new JPanel(new BorderLayout());
        JPanel interior_contieneLasFechas= new JPanel(new FlowLayout());
        JLabel fecha1= new JLabel(etiquetasFacturas[0]);
        JLabel fecha2= new JLabel(etiquetasFacturas[1]);
        camposFacturas[0]= new JTextField(20);
        camposFacturas[1]= new JTextField(20);
        interior_contieneLasFechas.add(fecha1); interior_contieneLasFechas.add(camposFacturas[0]);
        interior_contieneLasFechas.add(fecha2); interior_contieneLasFechas.add(camposFacturas[1]);
        contieneLasFechas.add(interior_contieneLasFechas, BorderLayout.CENTER);
        JPanel panelBotones= new JPanel(new BorderLayout());
        JButton Emitir= new JButton("Emitir");//TODO
        JButton Cancelar= new JButton("Cancelar");
        Cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaFactura.dispose();
            }
        });
        Emitir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.nuevaFactura();
                ventanaFactura.dispose();
            }
        });
        panelBotones.add(Emitir, BorderLayout.EAST);
        panelBotones.add(Cancelar, BorderLayout.CENTER);

        contenedor.add(panelBotones, BorderLayout.EAST);
        contenedor.add(contieneLasFechas, BorderLayout.CENTER);
        ventanaFactura.pack();
        ventanaFactura.setVisible(true);
    }

    private void ventanaLlamadaNuevaLlam(){
        JDialog ventanaLLamada= new JDialog(ventana,true);
        ventanaLLamada.setTitle("Hacer llamadas");
        ventanaLLamada.setLocationRelativeTo(ventana);
        Container contenedor=ventanaLLamada.getContentPane();

        JPanel datosLlamada= new JPanel( new BorderLayout());
        JPanel llamada= new JPanel(new SpringLayout());


        int numEtiquetas=etiquetasLlamadas.length;
        for (int i=0; i<numEtiquetas; i++){
            JLabel label = new JLabel(etiquetasLlamadas[i]);
            llamada.add(label);
            camposLlamadas[i]= new JTextField(10);
            label.setLabelFor(camposLlamadas[i]);
            llamada.add(camposLlamadas[i]);
        }

        SpringUtilities.makeCompactGrid(llamada, numEtiquetas,2,6,6,6,6);

        datosLlamada.add(llamada, BorderLayout.CENTER);

        JPanel botonesLLamada =new JPanel(new BorderLayout());
        JPanel i_botonoesLlamada= new JPanel(new BorderLayout());
        JButton LLamar = new JButton("LLamar");
        LLamar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.nuevaLlamada();
                ventanaLLamada.dispose();
            }
        });
        JButton Colgar= new JButton("Cancelar");
        Colgar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaLLamada.dispose();
            }
        });
        i_botonoesLlamada.add(LLamar, BorderLayout.EAST);
        i_botonoesLlamada.add(Colgar, BorderLayout.CENTER);
        botonesLLamada.add(i_botonoesLlamada, BorderLayout.EAST);

        contenedor.add(botonesLLamada, BorderLayout.SOUTH);
        contenedor.add(datosLlamada, BorderLayout.CENTER);
        ventanaLLamada.pack();
        ventanaLLamada.setVisible(true);
    }

    private void ventanaTarifaExpandir(){
        JDialog ventanaTarifa= new JDialog(ventana,true);
        ventanaTarifa.setTitle("Tarifa Nueva");
        ventanaTarifa.setLocationRelativeTo(ventana);
        Container contenedor=ventanaTarifa.getContentPane();
        JPanel opciones= new JPanel(new BorderLayout());
        JRadioButton dia= new JRadioButton("Tarifa de Domingo", true);
        JRadioButton franja= new JRadioButton("Tarifa Horaria",false);
        ButtonGroup grupo= new ButtonGroup();
        grupo.add(dia);
        grupo.add(franja);
        opciones.add(dia, BorderLayout.CENTER);
        opciones.add(franja,BorderLayout.EAST);

        JPanel botonesTarifa =new JPanel(new BorderLayout());
        JPanel i_botonoesTarifa= new JPanel(new BorderLayout());
        JButton Aceptar = new JButton("Aceptar");
        Aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(franja.isSelected())
                    isDia=false;
                controlador.nuevaTarifa();
                ventanaTarifa.dispose();
            }
        });
        JButton Cancelar= new JButton("Cancelar");
        Cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaTarifa.dispose();
            }
        });
        i_botonoesTarifa.add(Aceptar, BorderLayout.EAST);
        i_botonoesTarifa.add(Cancelar, BorderLayout.CENTER);
        botonesTarifa.add(i_botonoesTarifa, BorderLayout.EAST);

        contenedor.add(opciones,BorderLayout.CENTER);
        contenedor.add(botonesTarifa, BorderLayout.SOUTH);
        ventanaTarifa.pack();
        ventanaTarifa.setVisible(true);
    }

    @Override
    public String[] getAtributosClientes() {
        String[] devoler=new String [camposClientes.length];
        for(int i=0; i<devoler.length;i++){
            devoler[i]=camposClientes[i].getText();
        }
        return devoler;
    }

    @Override
    public boolean isEmpresa() {
        return isEmpresa;
    }

    @Override
    public double getTarifaBase() {
        return Double.parseDouble(camposClientes[camposClientes.length-1].getText());
    }

    @Override
    public String getDniEliminar(){
        return dniEliminar.getText();
    }

    @Override
    public boolean isDia(){
        return isDia;
    }

    @Override
    public double getPrecioTarifaBaseNueva() {
        return Double.parseDouble(precioNuevaTarifaBase.getText());
    }

    @Override
    public String getDni(){
        return dni;
    }

    @Override
    public List<Calendar> getFechasBusquedas(){
        List<Calendar> devolver= new ArrayList<>();
        String[] vector=camposFacturas[0].getText().split("/");
        devolver.add( new GregorianCalendar(Integer.parseInt(vector[2]),Integer.parseInt(vector[1])-1,Integer.parseInt(vector[0])));
        vector=camposFacturas[1].getText().split("/");
        devolver.add( new GregorianCalendar(Integer.parseInt(vector[2]),Integer.parseInt(vector[1])-1,Integer.parseInt(vector[0]),23,59,59));
        return devolver;
    }

    class TablaModelLlamadas extends AbstractTableModel {
        private String[] columnNames = {"Numero", "Fecha", "Duracion(min)"};
        private String[][] data = new String[0][3];

        public TablaModelLlamadas(){super();}

         public TablaModelLlamadas(String [][] datos){
             super();
             data=datos;

         }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        public void setDatos(String [][]datos){
            this.data=datos;
        }
    }
    @Override
    public String [] getCampoLlamadas(){
        String [] devolver=new String[camposLlamadas.length];
        for (int i=0; i<devolver.length;i++){
            devolver[i]=camposLlamadas[i].getText();
        }
        return devolver;
    }


    private void ventanaPrecaucion() {
        JDialog ventanaPrecaucion= new JDialog(ventana,true);
        ventanaPrecaucion.setTitle("Atencion");
        ventanaPrecaucion.setLocationRelativeTo(ventana);

        Container contenedor=ventanaPrecaucion.getContentPane();

        JPanel informacion= new JPanel();
        informacion.setLayout(new BoxLayout(informacion, BoxLayout.LINE_AXIS));
        ImageIcon icono= new ImageIcon("precaucion.png");
        JLabel info= new JLabel("<html>"+"<font size=4><b>Primero elige un cliente</b><font>",icono,JLabel.CENTER);
        informacion.add(info);
        JButton ok= new JButton("Aceptar");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPrecaucion.dispose();
            }
        });
        informacion.add(Box.createHorizontalGlue());
        informacion.add(ok);
        contenedor.add(informacion);
        ventanaPrecaucion.pack();
        ventanaPrecaucion.setVisible(true);

    }
}
