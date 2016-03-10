import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
 
public class PanelComponent extends JPanel {
    JuliaSet julia;
    JLabel juliaLabel;
    JLabel dcLabel;
    InputMap im;
    JDialogComponent menuConfig;
    private double deltaMoveX = 0.05;
    private double deltaMoveY = 0.05;
    private double deltaZoom = 1.1;
    
    public PanelComponent(String name, int w, int h, int nbThreads) throws IOException{
        if(name.equals("Julia")){
            julia = new JuliaSet(-0.75, 0.11, 1000 , w, h, nbThreads);
            julia.generateJulia();
            juliaLabel = new JLabel();
            add(juliaLabel); 
        
            menuConfig = new JDialogComponent(new JFrame(), "Setup menu");
        
            im = juliaLabel.getInputMap();
            //Deplacement
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Droite");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Gauche");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Haut");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Bas");
            //Zoom
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 
                                KeyEvent.CTRL_DOWN_MASK), "ZoomIn");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 
                                KeyEvent.CTRL_DOWN_MASK), "ZoomOut");
            //Menu
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Echap");
        
            ActionMap am = juliaLabel.getActionMap();
            //Deplacement
            am.put("Droite", new JuliaListener("Droite"));
            am.put("Gauche", new JuliaListener("Gauche"));
            am.put("Haut", new JuliaListener("Haut"));
            am.put("Bas", new JuliaListener("Bas"));
            //Zoom
            am.put("ZoomIn", new JuliaListener("ZoomIn"));
            am.put("ZoomOut", new JuliaListener("ZoomOut"));
            //Echap
            am.put("Echap", new JuliaListener("Echap"));
        }
        
        else if(name.equals("DiamantCarre")){
            
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        juliaLabel.setIcon(new ImageIcon(julia.img));              
    }
    
    public class JuliaListener extends AbstractAction{
        private String cmd;
        
        public JuliaListener(String cmd) {
            this.cmd = cmd;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!cmd.equals("Echap")){
                if (cmd.equals("Gauche")) {
                    julia.moveX -= deltaMoveX; 
                } else if (cmd.equals("Droite")) {
                    julia.moveX += deltaMoveX;
                } else if (cmd.equals("Haut")) {
                    julia.moveY -= deltaMoveY;
                } else if (cmd.equals("Bas")) {
                    julia.moveY += deltaMoveY;
                }else if(cmd.equals("ZoomIn")){
                    julia.zoom *= deltaZoom;
                }else if(cmd.equals("ZoomOut")){
                    julia.zoom /= deltaZoom;
                }
                try {
                    julia.generateJulia();
                } catch (IOException ex) {

                }
                repaint();
            }
            else{
                menuConfig.setVisible(true);
            }
        }
    }
    
    public class JDialogComponent extends JDialog {
            Container contentPane;

            public JDialogComponent(JFrame parent, String title){
                super(parent, title);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                contentPane = this.getContentPane();
                setMenuConfig();
                parent.pack();
            }

            public void setMenuConfig(){
                setSize(600, 400);
                setLocationRelativeTo( null );
                SpringLayout layout = new SpringLayout();
                contentPane.setLayout(layout);
                //Delta X
                JLabel labelX = new JLabel("Abscissa translation : ");
                contentPane.add(labelX);
                final JTextField textX = new JTextField(15);
                textX.setText(new Double(deltaMoveX).toString());
                contentPane.add(textX);

                //Delta Y
                JLabel labelY = new JLabel("Ordinate translation :");
                contentPane.add(labelY);
                final JTextField textY = new JTextField(15);
                textY.setText(new Double(deltaMoveY).toString());
                contentPane.add(textY);
                //Zoom
                JLabel zoom = new JLabel("Zoom : ");
                contentPane.add(zoom);
                final JTextField textZoom = new JTextField(15);
                textZoom.setText(new Double(deltaZoom).toString());
                contentPane.add(textZoom);
                
                //Reel C
                JLabel creel = new JLabel("Real part of C : ");
                contentPane.add(creel);
                final JTextField textCReel = new JTextField(15);
                textCReel.setText(new Double(julia.getCReel()).toString());
                contentPane.add(textCReel);
                
                //Im C
                JLabel cim = new JLabel("Imaginary part of C : ");
                contentPane.add(cim);
                final JTextField textCIm = new JTextField(15);
                textCIm.setText(Double.toString(julia.getCImaginaire()));
                contentPane.add(textCIm);

                //Informations
                JTextField info = new JTextField("Some values to try : ");
                info.setEditable(false);
                info.setBackground(null);
                info.setBorder(null);
                contentPane.add(info);
                
                //Link
                JTextField link = new JTextField("http://fr.wikipedia.org/wiki/Ensemble_de_Julia#/media/File:Julia-Teppich.png");
                link.setEditable(false);
                link.setBackground(null);
                link.setBorder(null);
                contentPane.add(link);
                
                //Save button
                JButton save = new JButton("Save changes");
                save.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deltaMoveX = Double.parseDouble(textX.getText());
                        deltaMoveY = Double.parseDouble(textY.getText());
                        deltaZoom =  Double.parseDouble(textZoom.getText());
                        julia.setCReel(Double.parseDouble(textCReel.getText()));
                        julia.setCImaginaire(Double.parseDouble(textCIm.getText()));
                        try {
                            julia.generateJulia();
                        } catch (IOException ex) {

                        }
                        repaint();
                        dispose();
                    }
                });
                contentPane.add(save);
                
                //Nice positionning
                layout.putConstraint(SpringLayout.WEST, labelX, 10, SpringLayout.WEST, contentPane);
                layout.putConstraint(SpringLayout.NORTH, labelX, 25, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.NORTH, textX, 25, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.WEST, textX, 200, SpringLayout.WEST, contentPane);

                //DeltaY
                layout.putConstraint(SpringLayout.WEST, labelY, 10, SpringLayout.WEST, contentPane);
                layout.putConstraint(SpringLayout.NORTH, labelY, 50, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.NORTH, textY, 50, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.WEST, textY, 200, SpringLayout.WEST, contentPane);

                //Zoom
                layout.putConstraint(SpringLayout.WEST, zoom, 10, SpringLayout.WEST, contentPane);
                layout.putConstraint(SpringLayout.NORTH, zoom, 75, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.NORTH, textZoom, 75, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.WEST, textZoom, 200, SpringLayout.WEST, contentPane); 
                
                //C reel
                layout.putConstraint(SpringLayout.WEST, creel, 10, SpringLayout.WEST, contentPane);
                layout.putConstraint(SpringLayout.NORTH, creel, 125, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.NORTH, textCReel, 125, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.WEST, textCReel, 200, SpringLayout.WEST, contentPane);
                
                //C Im
                layout.putConstraint(SpringLayout.WEST, cim, 10, SpringLayout.WEST, contentPane);
                layout.putConstraint(SpringLayout.NORTH, cim, 150, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.NORTH, textCIm, 150, SpringLayout.NORTH, contentPane);
                layout.putConstraint(SpringLayout.WEST, textCIm, 200, SpringLayout.WEST, contentPane);
            
                //Info
                layout.putConstraint(SpringLayout.WEST, info, 10 ,SpringLayout.WEST, contentPane);
                layout.putConstraint(SpringLayout.NORTH ,info, 200, SpringLayout.NORTH, contentPane);
                
                //Link
                layout.putConstraint(SpringLayout.WEST, link, 10 ,SpringLayout.WEST, contentPane);
                layout.putConstraint(SpringLayout.NORTH ,link, 225, SpringLayout.NORTH, contentPane);
                
                //Button
                layout.putConstraint(SpringLayout.WEST, save, 275, SpringLayout.WEST, contentPane);
                layout.putConstraint(SpringLayout.SOUTH, save, -25, SpringLayout.SOUTH, contentPane);
            }
        }
}