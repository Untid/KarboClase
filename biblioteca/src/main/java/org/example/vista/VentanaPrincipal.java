package org.example.vista;

import org.example.vista.panel.*;

import javax.swing.*;

/**
 * Ventana principal de la aplicación de gestión de biblioteca.
 *
 * <p>Esta clase representa la interfaz gráfica principal de la aplicación,
 * organizada mediante una pestaña ({@link JTabbedPane}) que agrupa las
 * funcionalidades en secciones lógicas: gestión de libros, socios,
 * préstamos y consultas.</p>
 *
 * <p>Implementa un diseño modular: cada pestaña delega su lógica a un panel
 * especializado (ej. {@link PanelGestionLibros}, {@link PanelPrestamos}, etc.),
 * lo que facilita el mantenimiento, la legibilidad y la evolución del código.</p>
 *
 * @author Javier
 */
public class VentanaPrincipal extends JFrame {

    /**
     * Constructor de la ventana principal.
     *
     * <p>Inicializa la configuración básica de la ventana y sus componentes internos.</p>
     */
    public VentanaPrincipal() {
        configurarVentana();
        initComponents();
    }

    /**
     * Configura las propiedades básicas de la ventana.
     *
     * <p>Establece el título, comportamiento al cerrar, tamaño inicial
     * y posición en la pantalla (centrada).</p>
     */
    private void configurarVentana() {
        setTitle("Sistema de Gestión de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa y organiza los componentes visuales de la ventana.
     *
     * <p>Crea un {@link JTabbedPane} (panel con pestañas) y añade un panel
     * especializado para cada funcionalidad del sistema. Cada panel es responsable
     * de su propia interfaz y lógica de negocio (seguimiento del patrón de separación de preocupaciones).</p>
     *
     * <p>Los paneles utilizados son:
     * <ul>
     *   <li>{@link PanelGestionLibros}: para alta, baja, modificación y búsqueda de libros.</li>
     *   <li>{@link PanelGestionSocios}: para gestionar los datos de los socios.</li>
     *   <li>{@link PanelPrestamos}: para registrar y gestionar préstamos/devoluciones.</li>
     *   <li>{@link PanelConsultas}: para reportes y búsquedas avanzadas (ej. libros vencidos).</li>
     * </ul>
     * </p>
     */
    private void initComponents() {
        // Crear el componente de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Añadir cada panel funcional con un título
        tabbedPane.addTab("📚 Libros", new PanelGestionLibros());
        tabbedPane.addTab("👥 Socios", new PanelGestionSocios());
        tabbedPane.addTab("📥 Préstamos", new PanelPrestamos());
        tabbedPane.addTab("🔍 Consultas", new PanelConsultas());

        // Añadir el panel de pestañas como único componente de la ventana
        add(tabbedPane);
    }
}

